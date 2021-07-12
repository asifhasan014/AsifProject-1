package com.softron.production.service;

import com.softron.utils.DecimalValueConversion;
import com.softron.utils.SentezIntegration;
import com.softron.utils.SentezIntegrationForTargetAndManpower;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.capacitystudy.dto.EmployeeProfileFromCapacityDto;
import com.softron.masterdata.capacitystudy.dto.OperationAnalysisCSDto;
import com.softron.masterdata.capacitystudy.dto.OperatorCycleTimeTrendsDto;
import com.softron.production.dto.HourlyMonitoringDto;
import com.softron.production.dto.OrderDetailsGraphDto;
import com.softron.production.dto.ProductionChartDto;
import com.softron.production.dto.ProductionDboardQcDto;
import com.softron.production.dto.ProductionDto;
import com.softron.production.dto.ProductionReportDto;
import com.softron.quality.dto.GraphDataDto;
import com.softron.quality.dto.QcPassReportDto;
import com.softron.quality.dto.QualityDefectDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.repository.OrganizationRepository;

@Service
public class ProductionService {

	@PersistenceContext
	private EntityManager entityManager;

	String root = "Production";

	@Value("${spring.datasource2.jdbcUrl}")
	private String jdbcUrl;

	@Value("${spring.datasource2.username}")
	private String username;

	@Value("${spring.datasource2.password}")
	private String password;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	AdminService adminService;

	/* Below this code use for sentez database Start from here */
	public Response getAll() {
		try {

			ProductionDto abc = SentezIntegration.api1(jdbcUrl, username, password);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, abc, 0, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getProductionBarChart(String endDate) {
		try {

			List<GraphDataDto> productionBarChartList = new ArrayList<GraphDataDto>();

			productionBarChartList = SentezIntegration.api2(jdbcUrl, username, password, endDate);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionBarChartList, 0,
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getProductionHourlyGraph(String endDate) {
		try {

			List<GraphDataDto> productionBarChartList = new ArrayList<GraphDataDto>();

			productionBarChartList = SentezIntegration.api3(jdbcUrl, username, password, endDate);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionBarChartList, 0,
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getProductionReport(String startDate, String endDate) {
		try {

			List<ProductionReportDto> productionList = new ArrayList<ProductionReportDto>();

			productionList = SentezIntegration.api6(jdbcUrl, username, password, startDate, endDate);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionList, 0, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getProductionReportQcPass(String startDate, String endDate,List<Long> orgIdList,List<String> orderEntityList) {
		try {

			List<ProductionReportDto> productionList = new ArrayList<ProductionReportDto>();
			
			List<Organization> orgList = new ArrayList<Organization>();
			for(Long orgId : orgIdList) {
			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);
			
			adminService.getOrganizationList(orgId, orgList);
			}
			
			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();
			
			for(Organization org: orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}
			
			
			String organization = StringUtils.join(orgIds, ',');
			String orderEntityCondition = "";
			
			if(orderEntityList != null) {
				String orderList = orderEntityList.toString().replaceAll("(^\\[|\\]$)", "");
				String orderEntityConditionHead = " and quality_transaction.orderentity_id in (  ";
				String orderEntityConditionTail = "  ) ";
				orderEntityCondition += orderEntityConditionHead + orderList + orderEntityConditionTail;
			}
			
			String query = "SELECT date_format(quality_transaction.created_at, '%Y-%m-%d' ) AS dt ,date_format(quality_transaction.created_at, '%l %p' ) AS hr,organizations.name AS org,customer.name AS buyer,style.name AS style,order_entity.name AS order_no,  SUM(quality_transaction.sample_size)AS hrProduction,FORMAT(((style.smv*SUM(quality_transaction.sample_size)*100)/(60*(target_and_manpower.number_of_helper+target_and_manpower.number_of_operator))),2) as efficiency\r\n" + 
					"					 \r\n" + 
					"from quality_transaction,order_entity,customer,style,organizations,target_and_manpower\r\n" + 
					"					 \r\n" + 
					"WHERE quality_transaction.created_at  BETWEEN :startDate AND :endDate \r\n" + 
					"					 \r\n" + 
					"AND quality_transaction.check_output='ok' \r\n" + 
					"\r\n" + 
					"and quality_transaction.org_id=target_and_manpower.org_id\r\n" + 
					"					 \r\n" + 
					"AND quality_transaction.orderentity_id=order_entity.id \r\n" + 
					"					 \r\n" + 
					"AND order_entity.style_id=style.id \r\n" + 
					"					 \r\n" + 
					"AND order_entity.customer_id=customer.id \r\n" + 
					"					 \r\n" + 
					"AND quality_transaction.org_id=organizations.id \r\n" + 
					"					 \r\n" + 
					"AND quality_transaction.org_id IN ("+organization+" ) \r\n " + orderEntityCondition +
					"					 \r\n" + 
					"  GROUP BY dt,hr,quality_transaction.org_id,quality_transaction.orderentity_id";
			
			Query q = entityManager.createNativeQuery(query);
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);

			List<Object[]> objects = q.getResultList();


			Double result = 0.0;
			String value = null;
		
			
			for (Object[] obj : objects) {
				
				ProductionReportDto productionDto = new ProductionReportDto();

				productionDto.setProductionDate(obj[0] != null ?obj[0].toString(): "");
				productionDto.setProductionHour(obj[1] != null ?obj[1].toString(): "");
				productionDto.setOrganization(obj[2] != null ?obj[2].toString(): "");
				productionDto.setBuyer(obj[3] != null ?obj[3].toString(): "");
				productionDto.setStyleName(obj[4] != null ?obj[4].toString(): "");
				productionDto.setOrderNo(obj[5] != null ?obj[5].toString(): "");
				productionDto.setProduction(obj[6] != null ?obj[6].toString(): "");
				productionDto.setEfficiency(obj[7] != null ?obj[7].toString(): "");

				productionDto.setOrganizationList(orgNames);
				productionList.add(productionDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionList, productionList.size(), String.format("All %ses", root));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getProductionDashBoard(String currentDateAndTime) {
		try {

			ProductionDto productionDto = SentezIntegration.gwtProductionDashBoard(jdbcUrl, username, password,
					currentDateAndTime);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionDto, 0, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getProductionDashBoardQCPass(String currentDateAndTimeString, Long orgId) {
		try {

			ProductionDboardQcDto productionDto = new ProductionDboardQcDto();

			String dateString = currentDateAndTimeString.split(" ")[0];
			String timeString = currentDateAndTimeString.split(" ")[1];
			String endTime = dateString + " " + timeString;

			int workingHr = 10;

			String dayStartTimeString = "8:30:00";

			double passedWorkingHr = 0.0;
			int endHr;
			int endMinit;
			int dayStartMinute;
			int hr_st_timehr;

			int passingWorkingMinitint;

			DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date time = sdf.parse(timeString);
			Date dayStartTime = sdf.parse(dayStartTimeString);

			endHr = time.getHours();
			endMinit = time.getMinutes();
			dayStartMinute = dayStartTime.getMinutes();

			long timeDurationofworking = time.getTime() - dayStartTime.getTime();
			System.out.println("substruct two date :" + timeDurationofworking);

			String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeDurationofworking),
					TimeUnit.MILLISECONDS.toMinutes(timeDurationofworking)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDurationofworking)),
					TimeUnit.MILLISECONDS.toSeconds(timeDurationofworking)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDurationofworking)));

			Date hmsDate = sdf.parse(hms);

			double hmsint = hmsDate.getMinutes();
			double value = (hmsint / 60);

			Double truncatedDoubleOfValue = BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP).doubleValue();

			if (time.before(dayStartTime)) {
				passedWorkingHr = 0.0;
			} else if (time.after(dayStartTime)) {
				passedWorkingHr = hmsDate.getHours() + truncatedDoubleOfValue;
			}
			if (passedWorkingHr > workingHr) {
				passedWorkingHr = workingHr;
			}
			if (endMinit < dayStartMinute) {
				hr_st_timehr = endHr - 1;
			} else {
				hr_st_timehr = endHr;
			}
			String hr_Start_Time = dateString + " " + Integer.toString(hr_st_timehr) + ":30:00";
			String day_start_time = dateString + " " + dayStartTimeString;
			
			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);
			
			adminService.getOrganizationList(orgId, orgList);
			
			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();
			
			for(Organization org: orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}
			
			
			String organization = StringUtils.join(orgIds, ',');
			
			
			String query = "SELECT hr_pro.hrProduction,cumo_production.cumelativeProduction,cumo_production.produceMinute,target.LineTarget,target.operators,target.helpers\r\n" + 
					"      FROM    (  \r\n" + 
					"                     SELECT 1 as sr,SUM(sample_size)AS hrProduction    from quality_transaction\r\n" + 
					"                       WHERE created_at  BETWEEN :hr_Start_Time AND :endTime  \r\n" + 
					"                     AND check_output='ok'    AND org_id IN ("+organization+")  \r\n" + 
					"            ) AS hr_pro    \r\n" + 
					"				LEFT JOIN    (  \r\n" + 
					"                     SELECT 1 as sr,sum(production_table.style_production) AS cumelativeProduction,\r\n" + 
					"									sum(production_table.style_production*smv_table.smv) AS produceMinute\r\n" + 
					"                       from    (  \r\n" + 
					"                     SELECT order_entity.style_id,SUM(sample_size) AS style_production  \r\n" + 
					"                     FROM quality_transaction,order_entity\r\n" + 
					"                     WHERE quality_transaction.created_at  BETWEEN :day_start_time AND :endTime   \r\n" + 
					"                     AND quality_transaction.orderentity_id=order_entity.id\r\n" + 
					"                     AND check_output='ok'    AND quality_transaction.\r\n" + 
					"							org_id IN ("+organization+")  \r\n" + 
					"                     GROUP BY style_id    ) AS production_table    LEFT JOIN  \r\n" + 
					"                     (    SELECT style_id, SUM(smv) AS smv  \r\n" + 
					"                     FROM operation_break_down    ) AS smv_table  \r\n" + 
					"                     ON (production_table.style_id=smv_table.style_id)       \r\n" + 
					"               ) AS cumo_production   \r\n" + 
					"					 ON(hr_pro.sr=cumo_production.sr)  \r\n" + 
					"                 LEFT JOIN    (  \r\n" + 
					"                     SELECT 1 as sr, productiontarget AS LineTarget, number_of_operator AS operators, number_of_helper AS helpers\r\n" + 
					"                       FROM target_and_manpower    WHERE DATE=:dateString  AND org_id IN ("+organization+")\r\n" + 
					"                    ) AS target    \r\n" + 
					"						  ON(hr_pro.sr=target.sr)";

			Query q = entityManager.createNativeQuery(query);

			q.setParameter("dateString", dateString);
			q.setParameter("day_start_time", day_start_time);
			q.setParameter("hr_Start_Time", hr_Start_Time);
			q.setParameter("endTime", endTime);

			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {

				productionDto.setHrProduction(obj[0] != null ? Integer.parseInt(obj[0].toString()) : 0);
				productionDto.setCumalativeProduction(obj[1] != null ? Integer.parseInt(obj[1].toString()) : 0);
				productionDto.setProduceMinute(obj[2] != null ? DecimalValueConversion.getTwoValueAfterDot(obj[2].toString()) : 0.0);
		 		productionDto.setLineTarget(obj[3] != null ? Integer.parseInt(obj[3].toString()) : 0);
				productionDto.setOperator(obj[4] != null ? Integer.parseInt(obj[4].toString()) : 0);
				productionDto.setHelper(obj[5] != null ? Integer.parseInt(obj[5].toString()) : 0);

			}
			productionDto.setOrganizationList(orgNames);
			productionDto.setCumalativeTarget((int)(passedWorkingHr * productionDto.getLineTarget()));
			productionDto.setDayTarget((int)(workingHr * productionDto.getLineTarget()));
		
			if (productionDto.getCumalativeTarget() > 0) {
				productionDto.setProductivity((productionDto.getCumalativeProduction() * 100)
						/ productionDto.getCumalativeTarget());
			}

			long sumOFOperatorAndHelper;
			sumOFOperatorAndHelper = productionDto.getOperator() + productionDto.getHelper();
			if (passedWorkingHr > 0 && sumOFOperatorAndHelper > 0) {
				
				productionDto.setEfficiency(
						(productionDto.getProduceMinute() * 100) / (60 * passedWorkingHr * sumOFOperatorAndHelper));
			}
			productionDto.setBalancingGap(100 - productionDto.getEfficiency());

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionDto, 0, String.format("All %ses Dash Board QC Pass", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getLineWiseProductionChart(Long orgId,String startDate) {
		try {

			List<ProductionChartDto> productionChartList = new ArrayList<ProductionChartDto>();

			productionChartList = SentezIntegration.api7(jdbcUrl, username, password,orgId, startDate);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionChartList, productionChartList.size(), String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getLineWiseHourlyProductionChart(Long orgId,String startDate) {
		try {

			List<ProductionChartDto> productionChartList = new ArrayList<ProductionChartDto>();

			productionChartList = SentezIntegration.api8(jdbcUrl, username, password,orgId, startDate);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, productionChartList, productionChartList.size(), String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	public Response getOperationAnalysisCd(double minRange,double maxRange) {
		try {

			List<OperationAnalysisCSDto> OperationAnalysisCDDtoList = new ArrayList<OperationAnalysisCSDto>();

			OperationAnalysisCDDtoList = SentezIntegration.api9(jdbcUrl, username, password,minRange,maxRange);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, OperationAnalysisCDDtoList, OperationAnalysisCDDtoList.size(), String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	public Response getEmployeePerformedProcessList(Long employeeId,double minRange,double maxRange) {
		try {

			List<EmployeeProfileFromCapacityDto> employeeProfileFromCapacityDtoList = new ArrayList<EmployeeProfileFromCapacityDto>();

			employeeProfileFromCapacityDtoList = SentezIntegration.api10(jdbcUrl, username, password,employeeId,minRange,maxRange);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, employeeProfileFromCapacityDtoList, employeeProfileFromCapacityDtoList.size(), String.format("All %ses", root));

		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getEmployeeProcessWiseCycleTimeTrend(Long employeeId,Long operationId,double minRange,double maxRange) {
		try {

			List<OperatorCycleTimeTrendsDto> OperationAnalysisCSDtoList = new ArrayList<OperatorCycleTimeTrendsDto>();

			OperationAnalysisCSDtoList = SentezIntegration.api11(jdbcUrl, username, password,employeeId,operationId,minRange,maxRange);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, OperationAnalysisCSDtoList, OperationAnalysisCSDtoList.size(), String.format("All %ses", root));

		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getHourlyProductionAndDhuMonitoringBoard(Long orgId) {
		try {
			List<HourlyMonitoringDto> hourlyMonitoringList = new ArrayList<HourlyMonitoringDto>();

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String startCurrentDateTime  = dtf.format(now)+" 00:00:00";
			String endCurrentDateTime  = dtf.format(now)+" 23:59:59";
			//System.out.println(endCurrentDateTime);
//			start hour, end hour, lunch start time, lunch duration
//			Time
//			String time1 = "12:00:00";
//			String time2 = "12:01:00";
//
//			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//			Date date1 = format.parse(time1);
//			Date date2 = format.parse(time2);
//			long diff = date2.getTime() - date1.getTime();
//			long diffSeconds = diff / 1000;
//			long diffMinutes = diff / (60 * 1000);
//			long diffHours = diff / (60 * 60 * 1000);
//			System.out.println("Time in seconds: " + diffSeconds + " seconds.");
//			System.out.println("Time in minutes: " + diffMinutes + " minutes.");
//			System.out.println("Time in hours: " + diffHours + " hours.");
//
//			int startHour = 8;
//			int endHour = 17;
//			String lunchHourStart = "01:00";
//			int duration = 60;
//			int planHour = 10;
//			int workinghour = 8;
//			int operator = 0;
//			int helper = 0;
//			int totalTarget = 0;

			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);

			adminService.getOrganizationList(orgId, orgList);

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			String organization = StringUtils.join(orgIds, ',');
			int workingHour = 8;
			int planHour = 10;

			String query ="select tab1.line,tab1.buyer,tab1.style,tab1.item,tab1.smv,tab1.planHour,tab1.workingHour,tab1.operator,tab1.helper,tab1.hourlyTarget,tab1.TotalTarget,tab1.hr,tab1.hrProduction,  \r\n" +
							" tab1.dhu,tab1.totalProduction,tab1.totalDhu,tab1.lineAchivment,tab1.produceMin,(tab1.workingHour*(tab1.operator+tab1.helper)*60) as 'availableMinute', \r\n" +
					" ((tab1.smv*SUM(tab1.hrProduction))/(tab1.workingHour*(tab1.operator+tab1.helper)*60)) as 'efficiency',tab1.parent_org as 'parentId',tab2.orgName as 'parentName',tab1.id as 'lineId' \r\n" +
					" from \r\n" +
					" (SELECT organizations.id,organizations.parent_org,organizations.`name` as 'line',customer.`name` as 'buyer',style.`name` as 'style',item.`name` as 'item',  \r\n" +
					" style.smv,@planHour\\:=10 as 'planHour',@workingHour\\:=8 as 'workingHour',target_and_manpower.number_of_operator as 'operator',  \r\n" +
					" target_and_manpower.number_of_helper as 'helper',target_and_manpower.productiontarget as 'hourlyTarget',  \r\n" +
					" (target_and_manpower.productiontarget*"+workingHour+") as 'TotalTarget',	\r\n" +
					" date_format(quality_transaction.created_at, '%H' ) AS hr,  \r\n" +
					" SUM(quality_transaction.sample_size)AS hrProduction,  \r\n" +
					" (100*SUM(quality_transaction.defective_item)/SUM(quality_transaction.sample_size)) as dhu,  \r\n" +
					" SUM(quality_transaction.sample_size)AS totalProduction,  \r\n" +
					" SUM(quality_transaction.defective_item) as totalDhu,  \r\n" +
					" (SUM(quality_transaction.sample_size)*100/target_and_manpower.productiontarget) as 'lineAchivment',   \r\n" +
					" (style.smv*SUM(quality_transaction.sample_size)) as 'produceMin'  \r\n" +
					" from quality_transaction,organizations,style,order_entity,customer,target_and_manpower,item   \r\n" +
					" where quality_transaction.org_id in ("+ organization +") \r\n" +
					" and quality_transaction.active = 1   \r\n" +
					" and quality_transaction.org_id = organizations.id  \r\n" +
					" and organizations.is_line = 1 \r\n" +
					" and organizations.active = 1  \r\n" +
					" and quality_transaction.style_id = style.id  \r\n" +
					" and style.active = 1  \r\n" +
					" and style.item_id = item.id   \r\n" +
					" and item.active = 1  \r\n" +
					" and quality_transaction.orderentity_id = order_entity.id   \r\n" +
					" and order_entity.active = 1   \r\n" +
					" and order_entity.customer_id = customer.id   \r\n" +
					" and customer.active = 1   \r\n" +
					" and quality_transaction.org_id = target_and_manpower.org_id   \r\n" +
					" and target_and_manpower.active = 1  \r\n" +
					" GROUP BY organizations.parent_org,organizations.`name`,customer.`name`,style.`name`,item.`name`, \r\n" +
					" style.smv,@planHour,@workingHour,target_and_manpower.number_of_operator,   \r\n" +
					" target_and_manpower.number_of_helper,target_and_manpower.productiontarget,date_format(quality_transaction.created_at, '%H' )) as tab1 \r\n" +
					" LEFT JOIN  \r\n" +
					" (select organizations.id,organizations.`name` as 'orgName' from organizations where organizations.active = 1) as tab2 \r\n" +
					" on \r\n" +
					" tab1.parent_org = tab2.id \r\n" +
					" GROUP BY \r\n" +
					" tab1.line,tab1.buyer,tab1.style,tab1.item,tab1.smv,tab1.planHour,tab1.workingHour,tab1.operator,tab1.helper,tab1.hourlyTarget,tab1.TotalTarget,tab1.hr,tab1.hrProduction,  \r\n" +
					" tab1.dhu,tab1.totalProduction,tab1.totalDhu,tab1.lineAchivment,tab1.produceMin,tab1.parent_org,tab2.orgName,tab1.id ";

				Query q = entityManager.createNativeQuery(query);
				List<Object[]> objects = q.getResultList();

				List<LinkedHashMap> unitWiseLinesMonitoringData = new ArrayList<LinkedHashMap>();
				List<LinkedHashMap> allLinesMonitoringData = new ArrayList<LinkedHashMap>();
				LinkedHashMap<String, String> rowObject = new LinkedHashMap<String, String>();

				List<LinkedHashMap> unitListMap = new ArrayList<LinkedHashMap>();

				HashMap<String, String> linesUnit = new HashMap<>();

				for (Object[] databaseRecord : objects) {
					boolean lineFound = false;
					boolean unitFound = false;

					LinkedHashMap<String, String> lineMonitoringData = null;
					LinkedHashMap<String, String> unitData = null;

					for (LinkedHashMap object : allLinesMonitoringData) {
						if (!object.get("Line").equals(databaseRecord[0].toString())) {
							lineMonitoringData = object;
							lineFound = true;
							break;
						}
					}
					if (!lineFound) {
						lineMonitoringData = new LinkedHashMap<String, String>();
						lineMonitoringData.put("Line", databaseRecord[0].toString());
						allLinesMonitoringData.add(lineMonitoringData);
					}

					for (Map.Entry<String, String> entry : linesUnit.entrySet()) {
						if (entry.getKey().equalsIgnoreCase(databaseRecord[21].toString())) {
							unitFound = true;
							break;
						}
					}
					if (!unitFound) {
						linesUnit.put(databaseRecord[21].toString(), databaseRecord[20].toString());
					}

					lineMonitoringData.put("Buyer", databaseRecord[1] != null ? databaseRecord[1].toString() : "");
					lineMonitoringData.put("Style", databaseRecord[2] != null ? databaseRecord[2].toString() : "");
					lineMonitoringData.put("Item", databaseRecord[3] != null ? databaseRecord[3].toString() : "");
					lineMonitoringData.put("SMV", databaseRecord[4] != null ? databaseRecord[4].toString() : "");
					lineMonitoringData.put("PLAN/HOUR", databaseRecord[5] != null ? databaseRecord[5].toString() : "");
					lineMonitoringData.put("WH", databaseRecord[6] != null ? databaseRecord[6].toString() : "");
					lineMonitoringData.put("OP", databaseRecord[7] != null ? databaseRecord[7].toString() : "");
					lineMonitoringData.put("HP", databaseRecord[8] != null ? databaseRecord[8].toString() : "");
					lineMonitoringData.put("HourlyTarget", databaseRecord[9] != null ? databaseRecord[9].toString() : "");
					lineMonitoringData.put("TotalTarget", databaseRecord[10] != null ? databaseRecord[10].toString() : "");
					lineMonitoringData.put("ProDhu", "Pro._DHU");
					lineMonitoringData.put(formatTime(databaseRecord[11].toString()), databaseRecord[12].toString() + "_" +  databaseRecord[13].toString());//pass the key to formatTime() and format as '7:00 - 8:00 AM'
					lineMonitoringData.put("Total", databaseRecord[14].toString()  + "_" +  databaseRecord[15].toString());
					lineMonitoringData.put("LINE ACV%", databaseRecord[16] != null ? databaseRecord[16].toString() : "");
					lineMonitoringData.put("Produce Minute", databaseRecord[17] != null ? databaseRecord[17].toString() : "");
					lineMonitoringData.put("Available Minute", databaseRecord[18] != null ? databaseRecord[18].toString() : "");
					lineMonitoringData.put("Efficiency", databaseRecord[19] != null ? databaseRecord[19].toString() : "");
					lineMonitoringData.put("DHU", databaseRecord[15] != null ? databaseRecord[15].toString() : "");

					lineMonitoringData.put("UnitId", databaseRecord[20] != null ? databaseRecord[20].toString() : "");
					lineMonitoringData.put("Hour", databaseRecord[11] != null ? databaseRecord[11].toString() : "");
					lineMonitoringData.put("LineHourlyProduction", databaseRecord[12] != null ? databaseRecord[12].toString() : "");
					lineMonitoringData.put("LineHourlyDhu", databaseRecord[13] != null ? databaseRecord[13].toString() : "");
				}

				for (Map.Entry<String, String> entry : linesUnit.entrySet()) {
					LinkedHashMap unitMap = new LinkedHashMap<String, String>();
					double unitSMV = 0;
					int unitPlanHour = 0;
					int unitWorkingHour = 0;
					int unitOperator = 0;
					int unitHelper = 0;
					int unitHourlyTarget = 0;
					int unitTotalTarget = 0;
					int unitHourlyProduction = 0;
					double unitHourlyDhu = 0.0;
					int unitHour = 0;

					for (LinkedHashMap object : allLinesMonitoringData) {
						//check the line exist in the unit
						if(entry.getValue().equals(object.get("UnitId"))) {
							unitWiseLinesMonitoringData.add(object);

							//calculate SMV and other values
							unitSMV += Double.parseDouble(object.get("SMV").toString());
							unitPlanHour += Integer.parseInt(object.get("PLAN/HOUR").toString());
							unitWorkingHour += Integer.parseInt(object.get("WH").toString());
							unitOperator += Integer.parseInt(object.get("OP").toString());
							unitHelper += Integer.parseInt(object.get("HP").toString());
							unitHourlyTarget += Integer.parseInt(object.get("HourlyTarget").toString());
							unitTotalTarget += Integer.parseInt(object.get("TotalTarget").toString());

							unitHourlyProduction += Integer.parseInt(object.get("LineHourlyProduction").toString());;
							unitHourlyDhu += Double.parseDouble(object.get("LineHourlyDhu").toString());
						}
					}
					unitMap.put("SMV", unitSMV);
					unitMap.put("PLAN/HOUR", unitPlanHour);
					unitMap.put("WH", unitWorkingHour);
					unitMap.put("OP", unitOperator);
					unitMap.put("HP", unitHelper);
					unitMap.put("HourlyTarget", unitHourlyTarget);
					unitMap.put("TotalTarget", unitWorkingHour);
					unitMap.put("ProDhu", "Pro._DHU");

					//set other values
					unitWiseLinesMonitoringData.add(unitMap);
				}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, unitWiseLinesMonitoringData,unitWiseLinesMonitoringData.size(),  String.format("All %ses", root));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getProductionDetailsReport(Long orderId,String option) {
		try {
			
			 String totalProduction = "";
			 String checkTypeCondition = "";
			 String heading = "";
			 String tail = "";
			 String TableName = "";
			
			 
			 
			if(option.equalsIgnoreCase("production")) {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += "and quality_transaction.check_output = 'ok' \r\n";
			}else if(option.equalsIgnoreCase("alter")) {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += "and quality_transaction.check_output = 'alter' \r\n";
			}else if(option.equalsIgnoreCase("reject")) {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += "and quality_transaction.check_output = 'reject' \r\n";
			}else if(option.equalsIgnoreCase("currentHour")) {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += " and quality_transaction.created_at  between DATE_FORMAT(NOW(), \"%y-%m-%d %h:00:00\") and DATE_FORMAT(NOW(), \"%y-%m-%d %h:59:59\")\r\n" + 
						              " and quality_transaction.check_output = 'ok' \r\n";
			}else if(option.equalsIgnoreCase("lastHour")) {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += " and quality_transaction.created_at >= DATE_SUB(CURDATE(), INTERVAL 1 HOUR)\r\n" + 
						"and quality_transaction.check_output = 'ok' \r\n";
			}else if(option.equalsIgnoreCase("currentDate")) {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += " and DATE(quality_transaction.created_at) = CURDATE() \r\n" + 
						"and quality_transaction.check_output = 'ok' \r\n";
			}else if(option.equalsIgnoreCase("lastDate")) {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += " and quality_transaction.created_at  BETWEEN CURDATE() - INTERVAL 1 DAY AND CURDATE()\r\n" + 
						"and quality_transaction.check_output = 'ok' \r\n";
			}else if(option.equalsIgnoreCase("alterPercentage")) {
				 totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				 heading += "select tableMain.orderentity_id,tableMain.organization,tableMain.order,tableMain.color,tableMain.quantity,\r\n" + 
				 		"  100 * tableMain.production / total.totalCheck AS alterPercent\r\n" + 
				 		"\r\n" + 
				 		"from\r\n" + 
				 		"\r\n" + 
				 		"(SELECT quality_transaction.orderentity_id,SUM(quality_transaction.sample_size) AS totalCheck\r\n" + 
				 		"FROM quality_transaction\r\n" + 
				 		"WHERE quality_transaction.active = 1 \r\n" + 
				 		"and quality_transaction.orderentity_id = "+orderId+" \r\n" + 
				 		"group by quality_transaction.orderentity_id) as total\r\n" + 
				 		"\r\n" + 
				 		"Left JOIN\r\n" + 
				 		"\r\n" + 
				 		"(  "; 
				  tail += ") as tableMain\r\n" + 
				 		"\r\n" + 
				 		"on tableMain.orderentity_id = total.orderentity_id";
				  
				  checkTypeCondition += "and quality_transaction.check_output = 'alter' \r\n";
			}else if(option.equalsIgnoreCase("rejectPercentage")) {
				 totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				 heading += "select tableMain.orderentity_id,tableMain.organization,tableMain.order,tableMain.color,tableMain.quantity,\r\n" + 
					 		"  100 * tableMain.production / total.totalCheck AS rejectPercent\r\n" + 
					 		"\r\n" + 
					 		"from\r\n" + 
					 		"\r\n" + 
					 		"(SELECT quality_transaction.orderentity_id,SUM(quality_transaction.sample_size) AS totalCheck\r\n" + 
					 		"FROM quality_transaction\r\n" + 
					 		"WHERE quality_transaction.active = 1 \r\n" + 
					 		"and quality_transaction.orderentity_id = "+orderId+" \r\n" + 
					 		"group by quality_transaction.orderentity_id) as total\r\n" + 
					 		"\r\n" + 
					 		"Left JOIN\r\n" + 
					 		"\r\n" + 
					 		"(  "; 
					  tail += ") as tableMain\r\n" + 
					 		"\r\n" + 
					 		"on tableMain.orderentity_id = total.orderentity_id";
					  
					  checkTypeCondition += "and quality_transaction.check_output = 'reject' \r\n";
			}else if(option.equalsIgnoreCase("defect")) {
				 totalProduction += " COUNT(*) AS production ";
				 TableName += " ,quality_defect ";
				 checkTypeCondition += " and quality_defect.qualitytransaction_id = quality_transaction.id    \r\n" + 
				 					   " AND quality_defect.active = 1  ";
			}else if(option.equalsIgnoreCase("defectPercentage")) {
				 heading += "select tableMain.orderentity_id,tableMain.organization,tableMain.order,tableMain.color,tableMain.quantity,\r\n" + 
					 		"  100 * tableMain.production / total.totalCheck AS defectPercentage\r\n" + 
					 		"\r\n" + 
					 		"from\r\n" + 
					 		"\r\n" + 
					 		"(SELECT quality_transaction.orderentity_id,SUM(quality_transaction.sample_size) AS totalCheck\r\n" + 
					 		"FROM quality_transaction\r\n" + 
					 		"WHERE quality_transaction.active = 1 \r\n" + 
					 		"and quality_transaction.orderentity_id = "+orderId+" \r\n" + 
					 		"group by quality_transaction.orderentity_id) as total\r\n" + 
					 		"\r\n" + 
					 		"Left JOIN\r\n" + 
					 		"\r\n" + 
					 		"(  "; 
					  tail += ") as tableMain\r\n" + 
					 		"\r\n" + 
					 		"on tableMain.orderentity_id = total.orderentity_id";
			     totalProduction += " COUNT(*) AS production ";
				 TableName += " ,quality_defect ";
				 checkTypeCondition += " and quality_defect.qualitytransaction_id = quality_transaction.id    \r\n" + 
						 			   " AND quality_defect.active = 1  ";	  
			}else {
				totalProduction += " SUM(quality_transaction.sample_size) AS production ";
				checkTypeCondition += "and quality_transaction.check_output = 'ok' \r\n";
			}
			
			 
			
			String query = heading+"select tab1.orderentity_id,tab1.organization,tab2.order,tab2.color,tab2.quantity,tab1.production\r\n" + 
					"FROM\r\n" + 
					"(select quality_transaction.orderentity_id,organizations.name as 'organization',"+totalProduction+" \r\n" + 
					"from quality_transaction,organizations "+TableName+" \r\n" + 
					"where quality_transaction.orderentity_id = "+orderId+" \r\n" + checkTypeCondition +
					"and quality_transaction.active = 1\r\n" + 
					"and quality_transaction.org_id = organizations.id\r\n" + 
					"and organizations.active = 1\r\n" + 
					"group by quality_transaction.orderentity_id,organizations.name) as tab1\r\n" + 
					"Left join \r\n" + 
					"(select order_entity.id,order_entity.name as 'order',varience.color,sum(varience.order_quantity) as quantity\r\n" + 
					"from varience,order_entity\r\n" + 
					"where order_entity.id = "+orderId+" \r\n" + 
					"and order_entity.active = 1\r\n" + 
					"and order_entity.id = varience.orderentity_id\r\n" + 
					"and varience.active = 1\r\n" + 
					"group by order_entity.id,order_entity.name,varience.color) as tab2\r\n" + 
					"ON\r\n" + 
					"tab1.orderentity_id = tab2.id" + tail;
			
			Query q = entityManager.createNativeQuery(query);
			List<Object[]> objects = q.getResultList();
			
			List<LinkedHashMap> orderDetailList = new ArrayList<LinkedHashMap>();
			int totalOrQuantity = 0;
			float quantitySum = (float) 0.0;
			
			
			LinkedHashMap<String, String> colorQuantity = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> totalOrderQuantity = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> totalLineProduction = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> lineWiseTotalProduction = new LinkedHashMap<String, String>();
			
			for (Object[] databaseRecord : objects) {
				LinkedHashMap<String, String> orderDetail = new LinkedHashMap<String, String>();
				
				orderDetail.put("Color",databaseRecord[3] != null ? databaseRecord[3].toString() : "");
				orderDetail.put("Quantity",databaseRecord[4] != null ? databaseRecord[4].toString() : "");
				orderDetail.put(databaseRecord[1] != null ? databaseRecord[1].toString() : "",databaseRecord[5] != null ? databaseRecord[5].toString() : "");
				
				if (!colorQuantity.containsKey(databaseRecord[3].toString())) {
					colorQuantity.put(databaseRecord[3].toString(), databaseRecord[4].toString());
				}
				if (!totalLineProduction.containsKey(orderDetail.get(databaseRecord[1].toString()))) {
					quantitySum += Float.parseFloat(orderDetail.get(databaseRecord[1].toString()));
					totalLineProduction.put("Total Production",String.valueOf(quantitySum));
				}
				if (!lineWiseTotalProduction.containsKey(orderDetail.get(databaseRecord[1].toString()))) {
					float totalLineQuantity = (float) 0.0;
					totalLineQuantity += Float.parseFloat(orderDetail.get(databaseRecord[1].toString()));
					lineWiseTotalProduction.put(databaseRecord[1].toString(),String.valueOf(totalLineQuantity));
				}
				
				orderDetailList.add(orderDetail);
			}
			
			for( Map.Entry<String,String> entry : colorQuantity.entrySet()){
				  if (!totalOrderQuantity.containsKey(entry.getKey())) {
						totalOrQuantity += Integer.parseInt(entry.getValue());
						totalOrderQuantity.put("Total", String.valueOf(totalOrQuantity));
					}
				}
			
			orderDetailList.add(totalOrderQuantity);
			orderDetailList.add(totalLineProduction);
			orderDetailList.add(lineWiseTotalProduction);
			
			
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, orderDetailList,orderDetailList.size(),  String.format("All %ses", root));

	} catch (Exception e) {
		e.printStackTrace();
		return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
	}
	}
	
	public Response getProductionDetailsGraphReport(Long orderId) {
		try {
			
			List<OrderDetailsGraphDto> orderDetailsGraphDtoList = new ArrayList<OrderDetailsGraphDto>();
			
			String query = "SELECT\r\n" + 
					"        main5.created_at,\r\n" + 
					"        main6.lineInvolve,\r\n" + 
					"        main5.production,\r\n" + 
					"        main5.productiontarget,\r\n" + 
					"        main5.achivement,\r\n" + 
					"        main5.efficiency,\r\n" + 
					"        main5.alterPercentage,\r\n" + 
					"        main5.rejectPercentage,\r\n" + 
					"        main5.defectPercentage\r\n" + 
					"from\r\n" + 
					"(SELECT\r\n" + 
					"		main3.orderentity_id,\r\n" + 
					"        main3.created_at,\r\n" + 
					"        main3.production,\r\n" + 
					"        main3.productiontarget,\r\n" + 
					"        main3.achivement,\r\n" + 
					"        main3.efficiency,\r\n" + 
					"        main3.totalAlter,\r\n" + 
					"        main3.totalReject,\r\n" + 
					"        main4.defect,\r\n" + 
					"        100*main3.totalAlter/main3.totalCheck as alterPercentage,\r\n" + 
					"        100*main3.totalReject/main3.totalCheck as rejectPercentage,\r\n" + 
					"        100*main4.defect/main3.totalCheck as defectPercentage\r\n" + 
					"FROM\r\n" + 
					"(SELECT\r\n" + 
					" 		main1.totalCheck,\r\n" + 
					" 		main1.orderentity_id,\r\n" + 
					"        main1.created_at,\r\n" + 
					"        main1.production,\r\n" + 
					"        main1.productiontarget,\r\n" + 
					"        main1.achivement,\r\n" + 
					"        main1.efficiency,\r\n" + 
					"        main1.totalAlter,\r\n" + 
					"        main2.totalReject\r\n" + 
					"    FROM\r\n" + 
					"        (\r\n" + 
					"        SELECT\r\n" + 
					"            total.totalCheck,\r\n" + 
					"            tab1.orderentity_id,\r\n" + 
					"            tab1.created_at,\r\n" + 
					"            tab1.production,\r\n" + 
					"            tab1.productiontarget,\r\n" + 
					"            tab1.achivement,\r\n" + 
					"            tab1.efficiency,\r\n" + 
					"            tab2.totalAlter\r\n" + 
					"        FROM\r\n" + 
					"            (\r\n" + 
					"            SELECT\r\n" + 
					"                quality_transaction.orderentity_id,\r\n" + 
					"                quality_transaction.created_at,\r\n" + 
					"                SUM(\r\n" + 
					"                    quality_transaction.sample_size\r\n" + 
					"                ) AS production,\r\n" + 
					"                target_and_manpower.productiontarget,\r\n" + 
					"                (\r\n" + 
					"                    SUM(\r\n" + 
					"                        quality_transaction.sample_size\r\n" + 
					"                    ) * 100 / target_and_manpower.productiontarget\r\n" + 
					"                ) AS achivement,\r\n" + 
					"                (\r\n" + 
					"                    (\r\n" + 
					"                        style.smv * SUM(\r\n" + 
					"                            quality_transaction.sample_size\r\n" + 
					"                        )\r\n" + 
					"                    ) /(\r\n" + 
					"                        @workingHour\\:=8 *(\r\n" + 
					"                            target_and_manpower.number_of_operator + target_and_manpower.number_of_helper\r\n" + 
					"                        ) * 60\r\n" + 
					"                    )\r\n" + 
					"                ) AS 'efficiency'\r\n" + 
					"            FROM\r\n" + 
					"                quality_transaction,\r\n" + 
					"                target_and_manpower,\r\n" + 
					"                style,\r\n" + 
					"                order_entity\r\n" + 
					"            WHERE\r\n" + 
					"                quality_transaction.orderentity_id = "+orderId+" AND quality_transaction.check_output = 'ok' AND quality_transaction.active = 1 AND quality_transaction.org_id = target_and_manpower.org_id AND target_and_manpower.active = 1 AND quality_transaction.orderentity_id = order_entity.id AND order_entity.active = 1 AND order_entity.style_id = style.id AND style.active = 1\r\n" + 
					"            GROUP BY\r\n" + 
					"                quality_transaction.created_at\r\n" + 
					"        ) AS tab1\r\n" + 
					"    LEFT JOIN(\r\n" + 
					"        SELECT\r\n" + 
					"            quality_transaction.orderentity_id,\r\n" + 
					"            quality_transaction.created_at,\r\n" + 
					"            SUM(\r\n" + 
					"                quality_transaction.sample_size\r\n" + 
					"            ) AS totalCheck\r\n" + 
					"        FROM\r\n" + 
					"            quality_transaction\r\n" + 
					"        WHERE\r\n" + 
					"            quality_transaction.active = 1 AND quality_transaction.orderentity_id = "+orderId+" \r\n" + 
					"        GROUP BY\r\n" + 
					"            quality_transaction.created_at\r\n" + 
					"    ) AS total\r\n" + 
					"ON\r\n" + 
					"    tab1.orderentity_id = total.orderentity_id\r\n" + 
					"LEFT JOIN(\r\n" + 
					"    SELECT\r\n" + 
					"        quality_transaction.orderentity_id,\r\n" + 
					"        quality_transaction.created_at,\r\n" + 
					"        SUM(\r\n" + 
					"            quality_transaction.sample_size\r\n" + 
					"        ) AS totalAlter\r\n" + 
					"    FROM\r\n" + 
					"        quality_transaction,\r\n" + 
					"        organizations\r\n" + 
					"    WHERE\r\n" + 
					"        quality_transaction.orderentity_id = "+orderId+" AND quality_transaction.check_output = 'alter' AND quality_transaction.active = 1\r\n" + 
					"    GROUP BY\r\n" + 
					"        quality_transaction.created_at\r\n" + 
					") AS tab2\r\n" + 
					"ON\r\n" + 
					"    total.orderentity_id = tab2.orderentity_id\r\n" + 
					"GROUP BY\r\n" + 
					"    tab1.created_at\r\n" + 
					"    ) AS main1\r\n" + 
					"LEFT JOIN(\r\n" + 
					"    SELECT\r\n" + 
					"        quality_transaction.orderentity_id,\r\n" + 
					"        quality_transaction.created_at,\r\n" + 
					"        SUM(\r\n" + 
					"            quality_transaction.sample_size\r\n" + 
					"        ) AS totalReject\r\n" + 
					"    FROM\r\n" + 
					"        quality_transaction\r\n" + 
					"    WHERE\r\n" + 
					"        quality_transaction.orderentity_id = "+orderId+" AND quality_transaction.check_output = 'reject' AND quality_transaction.active = 1\r\n" + 
					"    GROUP BY\r\n" + 
					"        quality_transaction.created_at\r\n" + 
					") AS main2\r\n" + 
					"ON\r\n" + 
					"    main1.orderentity_id = main2.orderentity_id\r\n" + 
					"GROUP BY\r\n" + 
					"    main1.created_at) as main3\r\n" + 
					"Left JOIN\r\n" + 
					"(SELECT quality_transaction.orderentity_id,quality_transaction.created_at,COUNT(*) AS defect    \r\n" + 
					"FROM quality_transaction,quality_defect      \r\n" + 
					"WHERE quality_transaction.orderentity_id = "+orderId+" \r\n" + 
					"and quality_defect.qualitytransaction_id = quality_transaction.id    \r\n" + 
					"AND quality_defect.active = 1\r\n" + 
					"group by quality_transaction.orderentity_id,quality_transaction.created_at) as main4\r\n" + 
					"ON\r\n" + 
					"main3.orderentity_id = main4.orderentity_id\r\n" + 
					"group by main3.created_at) as main5\r\n" + 
					"left JOIN\r\n" + 
					"(select quality_transaction.orderentity_id,count(DISTINCT quality_transaction.org_id) as lineInvolve\r\n" + 
					"from quality_transaction\r\n" + 
					"where quality_transaction.orderentity_id = "+orderId+"\r\n" + 
					"group by quality_transaction.orderentity_id) as main6\r\n" + 
					"ON\r\n" + 
					"main5.orderentity_id = main6.orderentity_id\r\n" + 
					"group by main5.created_at";
			
			Query q = entityManager.createNativeQuery(query);
			List<Object[]> objects = q.getResultList();
			for (Object[] databaseRecord : objects) {
				OrderDetailsGraphDto orderDetailsGraphDto = new OrderDetailsGraphDto();
				
				orderDetailsGraphDto.setNumberOfLineProduction(databaseRecord[1] == null ? "0" : databaseRecord[1].toString());
				orderDetailsGraphDto.setProduction(databaseRecord[2] == null ? "0" : databaseRecord[2].toString());
				orderDetailsGraphDto.setTarget(databaseRecord[3] == null ? "0" : databaseRecord[3].toString());
				orderDetailsGraphDto.setAchievement(databaseRecord[4] == null ? "0.0" : databaseRecord[4].toString());
				orderDetailsGraphDto.setEfficient(databaseRecord[5] == null ? "0.0" : databaseRecord[5].toString());
				orderDetailsGraphDto.setAlterParcentage(databaseRecord[6] == null ? "0.0" : databaseRecord[6].toString());
				orderDetailsGraphDto.setRejectParcentage(databaseRecord[7] == null ? "0.0" : databaseRecord[7].toString());
				orderDetailsGraphDto.setDefectParcentage(databaseRecord[8] == null ? "0.0" : databaseRecord[8].toString());
				
				orderDetailsGraphDtoList.add(orderDetailsGraphDto);
			}
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK,orderDetailsGraphDtoList,orderDetailsGraphDtoList.size(),  String.format("All %ses", root));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
		
	}

	private String formatTime(String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        int startHour = hour % 12;
        int endHour = startHour + 1;
        if (startHour == 0) startHour = 12;
        String amPm = (hour < 12) ? "AM" : "PM";
        if (endHour == 12) {
            if (amPm == "AM") amPm = "PM";
            else amPm = "AM";
        }
        return startHour + ":00 " + "- " + endHour + ":00 " + amPm;
    }

}
