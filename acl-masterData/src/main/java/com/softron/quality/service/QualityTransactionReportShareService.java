package com.softron.quality.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.softron.common.businessobjects.Response;
import com.softron.common.utils.DateUtils;
import com.softron.common.utils.ResponseUtils;
import com.softron.production.dto.ProductionReportDto;
import com.softron.production.repository.ReportLayoutRepository;
import com.softron.quality.dto.GraphDataDto;
import com.softron.quality.dto.QualityReportDto;
import com.softron.quality.dto.QualityReportShareDto;
import com.softron.quality.repository.QualityTransactionRepository;
import com.softron.schema.admin.entity.production.ReportLayout;


@Service
public class QualityTransactionReportShareService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	QualityTransactionRepository qualityTransactionRepository;
	
	@Autowired
	ReportLayoutRepository reportLayoutRepository;
	
	String root = "QualityTransaction"; 
	
	@Value("${spring.datasource2.jdbcUrl}")
	private String jdbcUrl;

	@Value("${spring.datasource2.username}")
	private String username;

	@Value("${spring.datasource2.password}")
	private String password;
	
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	public Response getQcPassReportShare(Long reportLayoutId) {
	  try {
		List<QualityReportShareDto> reportLayout = new ArrayList<QualityReportShareDto>();		
		Optional<ReportLayout> reportLayoutOpt = reportLayoutRepository.findById(reportLayoutId);

		ReportLayout report = reportLayoutOpt.get();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String endDate = formatter.format(date);
		Date endDateTime = formatter.parse(endDate);
		Date formDateTime = report.getFromDate();
		Date toDateTime = report.getToDate();
		
		long durationMilli = Math.abs(endDateTime.getTime() - toDateTime.getTime());
		long startDateMilli = formDateTime.getTime() + durationMilli;
		
		String startDateTime = formatter.format(startDateMilli);
			
		
		String condition;
		Double allresult = 0.0;
		String timeUnit = report.getViewType();
		switch (timeUnit) {
		case "month":
			condition = "%Y-%m";
			break;
		case "date":
			condition = "%Y-%m-%d";
			break;
		case "hour":
			condition = "%Y-%m-%d:%h";
			break;
		default:
			condition = "%Y-%m-%d:%h";
			break;
		}
		
		String resultSelectQuery = "SELECT  ";
		String resultFromQuery = "  FROM ( ";
		String headSelectQuery = "SELECT total.qDate,common.client,common.company,common.plant,common.section,total.subSection";
		String headSelectSummeryOuery = "";
		String resultGroupByQuery = " GROUP BY  ";
		
		String selectQuery = " SELECT DATE_FORMAT(quality_transaction.created_at,:condition) AS qDate,section.parent_id,section.name AS subSection ";
		String sumQuery = " ,SUM(quality_transaction.sample_size) ";
		String fromQuery = " FROM quality_transaction,section,quality_type ";
		String whereQuery = " WHERE section.id = quality_transaction.section_id AND quality_transaction.qualitytype_id = quality_type.id AND quality_type.type = 1 AND quality_transaction.active = 1 AND quality_transaction.created_at BETWEEN :startDate AND :endDate ";
		String groupByQuery = " GROUP BY qDate,section.parent_id,subSection ";
		
		String totalCheck = "";
		String totalOk = "";
		String totalAlter = "";
		String totalReject = "";
		String totalDefect = "";
		
		

		String removeDataFieldJasonFormate = removeJasonFormate(report.getDataFields());
		String removeColumnJasonFormate =  removeJasonFormate(report.getColumnFields());
		String removeRowJasonFormate =  removeJasonFormate(report.getRowFields());

		String[] dataFields = removeDataFieldJasonFormate.split(",");
		String[] columns = removeColumnJasonFormate.split(",");
		String[] rows = removeRowJasonFormate.split(",");
		
		List list = new ArrayList(Arrays.asList(columns));    
		list.addAll(Arrays.asList(rows));     
		Object[] columnAndRows = list.toArray();
		int i = 0;
		for(Object columnAndRow : columnAndRows) {
			if(i++ == columnAndRows.length - 1){
				resultSelectQuery += columnAndRow;
				resultGroupByQuery += columnAndRow;
			}else {
				resultSelectQuery += columnAndRow;
				resultSelectQuery += ",";
				resultGroupByQuery += columnAndRow;
				resultGroupByQuery += ",";
			}
		}
		
		for(String data:dataFields) {
			if(data.equals("totalCheck")) {
				 resultSelectQuery += ",totalCheck";
				 headSelectQuery +=" ,total.totalCheck ";
				 totalCheck += selectQuery+sumQuery+" AS totalCheck\r\n" + fromQuery + whereQuery + groupByQuery;
			}else if(data.equals("qcPass")) {
				resultSelectQuery += ",qcPass";
				headSelectQuery +=" ,total_qcPass.qcPass ";
				 totalOk += selectQuery+sumQuery+" AS qcPass\r\n" + fromQuery + whereQuery +" AND quality_transaction.check_output = 'ok' " + groupByQuery;
			}else if(data.equals("totalAlter")) {
				resultSelectQuery += ",totalAlter";
				headSelectQuery +=",total_alter.totalAlter";
				totalAlter += selectQuery+sumQuery+" AS totalAlter\r\n" + fromQuery + whereQuery +" AND quality_transaction.check_output = 'alter' " + groupByQuery;
			}else if(data.equals("reject")) {
				resultSelectQuery += ",reject";
				headSelectQuery +=",total_reject.reject";
				totalReject += selectQuery+sumQuery+" AS reject\r\n" + fromQuery + whereQuery + " AND quality_transaction.check_output = 'reject' " + groupByQuery;
			}else if(data.equals("defect")) {
				resultSelectQuery += ",defect";
				headSelectQuery +=",defect_quantity.defect";
				totalDefect += selectQuery+",COUNT(*) AS defect\r\n" + fromQuery+",quality_defect\r\n" + whereQuery +" AND quality_defect.qualitytransaction_id = quality_transaction.id AND quality_defect.active = 1 " + groupByQuery;
			}else if(data.equals("qcPassPercent")) {
				resultSelectQuery += ",qcPassPercent";
				headSelectSummeryOuery += ",100 * total_qcPass.qcPass / total.totalCheck AS 'qcPassPercent'";
			}else if(data.equals("alterPercent")) {
				resultSelectQuery += ",alterPercent";
				headSelectSummeryOuery += ",100 * total_alter.totalAlter / total.totalCheck AS 'alterPercent'";
			}else if(data.equals("rejectPercent")) {
				resultSelectQuery += ",rejectPercent";
				headSelectSummeryOuery += ",100 * total_reject.reject / total.totalCheck AS 'rejectPercent'";
			}else {
				resultSelectQuery += ",dhu";
				headSelectSummeryOuery += ",100 * defect_quantity.defect / total.totalCheck AS 'dhu'";
			}
		}
		
		
		String query = resultSelectQuery+resultFromQuery+headSelectQuery+headSelectSummeryOuery+" FROM\r\n" + 
				"    (SELECT CLIENT.name AS client,company.name AS company,plant.name AS plant,section.name AS section,section.id\r\n" + 
				"    FROM CLIENT, company,plant,section\r\n" + 
				"    WHERE CLIENT.active = 1 AND company.client_id = CLIENT.id AND company.active = 1 AND plant.company_id = company.id AND plant.active = 1 AND section.plant_id = plant.id AND section.parent_id IS NULL AND section.active = 1\r\n" + 
				") AS common\r\n" + 
				"LEFT JOIN( " + totalCheck + ") AS total\r\n" + 
				"ON common.id = total.parent_id\r\n" + 
				"LEFT JOIN(" + totalOk + ") AS total_qcPass\r\n" + 
				"ON total.qDate = total_qcPass.qDate AND total.parent_id = total_qcPass.parent_id AND total.subSection = total_qcPass.subSection\r\n" + 
				"LEFT JOIN(" + totalAlter + ") AS total_alter\r\n" + 
				"ON total.qDate = total_alter.qDate AND total.parent_id = total_alter.parent_id AND total.subSection = total_alter.subSection\r\n" + 
				"LEFT JOIN( " + totalReject + " ) AS total_reject\r\n" + 
				"ON total.qDate = total_reject.qDate AND total.parent_id = total_reject.parent_id AND total.subSection = total_reject.subSection\r\n" + 
				"LEFT JOIN( " + totalDefect + ") AS defect_quantity\r\n" + 
				"ON total.qDate = defect_quantity.qDate AND total.parent_id = defect_quantity.parent_id AND total.subSection = defect_quantity.subSection ) AS resultTable " + resultGroupByQuery;
		
		Query q = entityManager.createNativeQuery(query);
		q.setParameter("startDate", startDateTime);
		q.setParameter("endDate", endDate);
		q.setParameter("condition", condition);
		List<Object[]> qcPass = q.getResultList();
		
		System.out.println(rows);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, qcPass,
				String.format("%s retrieved Successfully", root));
	  } catch (ParseException e) {
		  return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response getProductionReportShare(Long reportLayoutId) {
		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		List<ProductionReportDto> productioninfoList = new ArrayList<ProductionReportDto>();
		try {
			List<QualityReportShareDto> reportLayout = new ArrayList<QualityReportShareDto>();		
			Optional<ReportLayout> reportLayoutOpt = reportLayoutRepository.findById(reportLayoutId);

			ReportLayout report = reportLayoutOpt.get();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String endDate = formatter.format(date);
			Date endDateTime = formatter.parse(endDate);
			Date formDateTime = report.getFromDate();
			Date toDateTime = report.getToDate();
			
			long durationMilli = Math.abs(endDateTime.getTime() - toDateTime.getTime());
			long startDateMilli = formDateTime.getTime() + durationMilli;
			
			String startDateTime = formatter.format(startDateMilli);
				
			
			String condition;
			Double allresult = 0.0;
			String timeUnit = report.getViewType();
			switch (timeUnit) {
			case "month":
				condition = "%Y-%m";
				break;
			case "date":
				condition = "%Y-%m-%d";
				break;
			case "hour":
				condition = "%Y-%m-%d:%h";
				break;
			default:
				condition = "%Y-%m-%d:%h";
				break;
			}
			
			
			String resultSelectQuery = "SELECT  ";
			String resultGroupByQuery = " GROUP BY  ";
			String resultOrderByQuery = " ORDER BY  ";
			String selectQuery = "";
			String quantitySumQuery = "";
			String smvAvgQuery = "";
			
			
			String removeDataFieldJasonFormate = removeJasonFormate(report.getDataFields());
			String removeColumnJasonFormate =  removeJasonFormate(report.getColumnFields());
			String removeRowJasonFormate =  removeJasonFormate(report.getRowFields());

			String[] dataFields = removeDataFieldJasonFormate.split(",");
			String[] columns = removeColumnJasonFormate.split(",");
			String[] rows = removeRowJasonFormate.split(",");
			
			List list = new ArrayList(Arrays.asList(columns));    
			list.addAll(Arrays.asList(rows));     
			Object[] columnAndRows = list.toArray();
			int i = 0;
			for(Object columnAndRow : columnAndRows) {
				if(i++ == columnAndRows.length - 1){
					resultSelectQuery += columnAndRow;
					resultGroupByQuery += columnAndRow;
					resultOrderByQuery += columnAndRow;
				}else {
					resultSelectQuery += columnAndRow;
					resultSelectQuery += ",";
					resultGroupByQuery += columnAndRow;
					resultGroupByQuery += ",";
					resultOrderByQuery += columnAndRow;
					resultOrderByQuery += ",";
				}
			}
			
			for(String data:dataFields) {
				if(data.equals("quantity")) {
					 selectQuery += ",common.pr AS quantity";
					 quantitySumQuery += ",sum(Erp_ProductivityTransaction.Quantity) AS pr";
				}else if(data.equals("SMV")) {
					selectQuery += ",inventoryWorkStudy.SMV";
					smvAvgQuery += ",AVG(Erp_InventoryWorkStudy.StandartTime1) AS SMV";
				}
			}
			
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();
			
			String query = "DECLARE @startHour DATETIME, @oneHour DATETIME         \r\n" + 
					"SET @startHour = substring(convert(char(8),'8:30',108), 1, 5)\r\n" + 
					"SET @oneHour = substring(convert(char(8),'1:00',108), 1, 5)\r\n" + 
					"\r\n" + resultSelectQuery +"  FROM (\r\n" + 
					"select common.CompanyName AS company,common.ProductionLine AS line,common.WorkOrderItemId,common.dd AS productionTransactionDate,common.hr AS productionTransactionHour,workOrderItem.WorkOrderNo AS orderNo,workOrderItem.InventoryName AS styleName,workOrderItem.CurrentAccountName AS buyer "+selectQuery+" \r\n" + 
					"FROM \r\n" + 
					"(SELECT Erp_Company.CompanyName,Erp_ProductivityTransaction.ProductionLine,Erp_ProductivityTransaction.WorkOrderItemId,CONVERT(DATE, Erp_ProductivityTransaction.InsertedAt) AS dd,Format(cast(substring(convert(char(8),Erp_ProductivityTransaction.InsertedAt,108), 1, 5) -@startHour +@oneHour AS dateTime ),'HH','en-us') as hr "+quantitySumQuery+" \r\n" + 
					"FROM\r\n" + 
					"Sentez.dbo.Erp_ProductivityTransaction,Sentez.dbo.Erp_WorkOrderItem,Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_Company\r\n" + 
					"WHERE Erp_ProductivityTransaction.CompanyId=Erp_Company.RecId AND Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId AND Erp_ProductivityTransaction.IsDeleted = 0 AND Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId AND Erp_WorkOrderItem.Status = 1 AND Erp_InventoryWorkStudy.ProcessId=Erp_ProductivityTransaction.ProcessId AND Erp_InventoryWorkStudy.ProductionProcessId=15 AND Erp_ProductivityTransaction.InsertedAt between '" + startDateTime + "' and '" + endDate + "' \r\n" + 
					"GROUP BY CONVERT(DATE, Erp_ProductivityTransaction.InsertedAt),Format(cast(substring(convert(char(8),Erp_ProductivityTransaction.InsertedAt,108), 1, 5) -@startHour +@oneHour AS dateTime ),'HH','en-us'),Erp_ProductivityTransaction.WorkOrderItemId,Erp_Company.CompanyName,Erp_ProductivityTransaction.ProductionLine\r\n" + 
					") AS common\r\n" + 
					"LEFT JOIN \r\n" + 
					"(SELECT Erp_WorkOrderItem.RecId,Erp_WorkOrderItem.InventoryId,Erp_WorkOrder.WorkOrderNo,Erp_CurrentAccount.CurrentAccountName,Erp_Inventory.InventoryName\r\n" + 
					"	FROM Erp_WorkOrderItem,Erp_WorkOrder,Erp_CurrentAccount,Erp_Inventory\r\n" + 
					"	WHERE Erp_WorkOrderItem.WorkOrderId = Erp_WorkOrder.RecId AND Erp_WorkOrder.Status = 1	AND Erp_WorkOrder.CurrentAccountId = Erp_CurrentAccount.RecId	AND Erp_WorkOrderItem.InventoryId = Erp_Inventory.RecId	AND Erp_WorkOrderItem.Status = 1\r\n" + 
					") AS workOrderItem\r\n" + 
					"ON common.WorkOrderItemId = workOrderItem.RecId\r\n" + 
					"LEFT JOIN\r\n" + 
					"(SELECT Erp_InventoryWorkStudy.InventoryId "+smvAvgQuery+"  FROM Erp_InventoryWorkStudy\r\n" + 
					"	GROUP BY Erp_InventoryWorkStudy.InventoryId\r\n" + 
					") AS inventoryWorkStudy\r\n" + 
					"ON workOrderItem.InventoryId = inventoryWorkStudy.InventoryId\r\n" + 
					") AS resultTable\r\n" + resultGroupByQuery + resultOrderByQuery;
			
			rs = stmt.executeQuery(query);
			
			List<Object[]> records=new ArrayList<Object[]>();
			while(rs.next()){
			    int cols = rs.getMetaData().getColumnCount();
			    Object[] arr = new Object[cols];
			    for(int j=0; j<cols; j++){
			      arr[j] = rs.getObject(j+1);
			    }
			    records.add(arr);
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, records,
					String.format("%s retrieved Successfully", root));
		}catch (Exception exc) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	private String removeJasonFormate(String data) {
		return data.replace("[", "").replace("]", "").replaceAll("[\"'\u2018\u2019\u201c\u201d]", "");
	}

}
