package com.softron.utils;

import com.softron.masterdata.capacitystudy.dto.EmployeeProfileFromCapacityDto;
import com.softron.masterdata.capacitystudy.dto.OperationAnalysisCSDto;
import com.softron.masterdata.capacitystudy.dto.OperatorCycleTimeTrendsDto;
import com.softron.production.dto.ProductionChartDto;

import com.softron.production.dto.ProductionDto;
import com.softron.production.dto.ProductionReportDto;
import com.softron.quality.dto.GraphDataDto;
import com.softron.schema.admin.entity.masterdata.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Date;
//import java.sql.Date;  

public class SentezIntegration {
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public static void updateData(Connection connection, String sql) throws SQLException {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			}
		}
	}

	public static Connection getDatabaseConnection(String DB_URL, String USER, String PASS) {
		Connection connection = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeDatabaseConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ProductionDto api1(String jdbcUrl, String username, String password) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime nowDate = LocalDateTime.now();
		String instantDate = dtf.format(nowDate);
		String instantOnlyDate = df.format(nowDate);

		String splitInstantDate = instantDate.split(" ")[0];
		String startTime = " 8:30:00";
		String dayStartTime = splitInstantDate + startTime;

		int hour = nowDate.getHour();
		int minute = nowDate.getMinute();

		int HrTarget;
		int HrProduction;
		int CumalativeTarget;
		int CumalativeProduction;
		int DayTarget;
		int Production;
		double ProduceMinute, Productivity;
		int Operator;
		int Helper;
		String StyleName;
		double CumalativeWorkingHr = 0;
		int DayWorkingHr = 10;
		if (hour > 9) {
			CumalativeWorkingHr = (hour - 8) + (minute / 60);
		} else if (hour > 8 && minute > 30) {
			CumalativeWorkingHr = (minute / 60);
		}
		if (CumalativeWorkingHr > DayWorkingHr) {
			CumalativeWorkingHr = DayWorkingHr;
		}
		if (minute < 30) {
			hour -= 1;
		}
		String hourStartTime = instantOnlyDate + " " + hour + ":30:00";

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";

		ProductionDto productionDto = new ProductionDto();
//    	 System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//    	 System.out.println("dayStartTime :"+dayStartTime);
//    	 System.out.println("hourStartTime :"+hourStartTime);
//    	 System.out.println("instantDate :"+instantDate);
//    	 System.out.println("=============================================================================");

		/*
		 * try{ Class.forName(JDBC_DRIVER); connection =
		 * DriverManager.getConnection(DB_URL, USER, PASS); }catch(Exception e){
		 * e.printStackTrace(); }
		 */
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();
//            String SQL = "select * from Sentez.dbo.Erp_ProductivityTransaction where InsertedAt between '2019-12-12 9:30:30' and '2019-12-12 10:30:30';";
//            ResultSet rs = stmt.executeQuery(SQL);

//        	"set @day_st_time="+dayStartTime+"\r\n" +
//	 		"set @hr_st_time="+hourStartTime+"\r\n" +
//	 		"set @end_time="+instantDate+"\r\n" +

//        	"set @day_st_time='"+dayStartTime+"'\r\n" +
//	 		"set @hr_st_time='"+hourStartTime+"'\r\n" +
//	 		"set @end_time='"+instantDate+"'\r\n" +

			String tsql = "declare @date varchar(30), @line_no varchar(30)\r\n"
					+ "declare @day_st_time varchar(30),@hr_st_time varchar(30),@end_time varchar(30)\r\n"
					+ "set @day_st_time='2019-10-10 8:30:00'\r\n" + "set @hr_st_time='2019-10-10 15:30:00'\r\n"
					+ "set @end_time='2020-1-2 16:30:00'\r\n" + "set @line_no='LINE1'\r\n" + "\r\n"
					+ "select tar.LineTarget,ceiling(pro.HrProduction) as HrProduction,ceiling(pro.Production) as Production,round(pro.produceMinute,2) as produceMinute,pro.operator,tar.HelperNumber,style.InventoryName\r\n"
					+ "from\r\n"
					+ "(	select sum(t1.Production) as Production,sum(t2.HrProduction) as HrProduction,max(t3.op) as operator,sum(t4.SMV*t1.Production) as produceMinute\r\n"
					+ "                from\r\n" + "                    (\r\n"
					+ "                    select 1 as sr, Erp_ProductivityTransaction.WorkOrderItemId,sum(Erp_ProductivityTransaction.Quantity) as Production\r\n"
					+ "						from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_WorkOrderItem\r\n"
					+ "						where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "						and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n"
					+ "						 and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "						 and Erp_ProductivityTransaction.InsertedAt between @day_st_time and @end_time\r\n"
			
					+ "						and Erp_InventoryWorkStudy.ProductionProcessId=15\r\n" + "\r\n"
					+ "                    group by Erp_ProductivityTransaction.WorkOrderItemId,Erp_ProductivityTransaction.ProcessId\r\n"
					+ "                    )t1\r\n" + "                    left join\r\n" + "					(\r\n"
					+ "						select Erp_ProductivityTransaction.WorkOrderItemId,sum(Erp_ProductivityTransaction.Quantity) as HrProduction\r\n"
					+ "						from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_WorkOrderItem\r\n"
					+ "						where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "						and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n"
					+ "						and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "						and Erp_ProductivityTransaction.InsertedAt between @hr_st_time and @end_time\r\n"
								
					+ "						and Erp_InventoryWorkStudy.ProductionProcessId=15\r\n"
					+ "						group by Erp_ProductivityTransaction.WorkOrderItemId,Erp_ProductivityTransaction.ProcessId\r\n"
					+ "					)t2\r\n" + "					on(t1.WorkOrderItemId=t2.WorkOrderItemId)\r\n"
					+ "\r\n" + "                    left join(\r\n"
					+ "                    select Erp_ProductivityTransaction.WorkOrderItemId , count(distinct Erp_ProductivityTransaction.ResourceId) as op\r\n"
					+ "                    from Sentez.dbo.Erp_ProductivityTransaction\r\n"
					+ "                    where Erp_ProductivityTransaction.InsertedAt between @day_st_time and @end_time\r\n"
					+ "                    and Erp_ProductivityTransaction.ProductionLine=@line_no\r\n"
					+ "					group by Erp_ProductivityTransaction.WorkOrderItemId\r\n"
					+ "                    )t3\r\n"
					+ "                    on(t1.WorkOrderItemId =t3.WorkOrderItemId)\r\n"
					+ "                    left join\r\n" + "                    (\r\n"
					+ "                    select Erp_WorkOrderItem.RecId, sum(Erp_InventoryWorkStudy.StandartTime1) as SMV\r\n"
					+ "                        from Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_WorkOrderItem\r\n"
					+ "						where Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "                        group by Erp_WorkOrderItem.RecId\r\n" + "                    ) t4\r\n"
					+ "                    on(t1.WorkOrderItemId=t4.RecId)\r\n" + "				group by t1.sr	\r\n"
					+ ")pro\r\n" + "left join\r\n" + "(select Erp_LineTarget.LineTarget,Erp_LineTarget.HelperNumber\r\n"
					+ "from Sentez.dbo.Erp_LineTarget\r\n" + "where Erp_LineTarget.ProductionLine=@line_no\r\n"
					+ "  and Erp_LineTarget.TargetDate=@end_time\r\n" + ")tar\r\n" + "on(1=1)\r\n" + "left join\r\n"
					+ "(\r\n" + "	select top 1 Erp_Inventory.InventoryName\r\n"
					+ "	from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,\r\n"
					+ "			Sentez.dbo.Erp_WorkOrderItem, Sentez.dbo.Erp_Inventory\r\n"
					+ "	where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "		and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n"
					+ "		and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "		and Erp_WorkOrderItem.InventoryId=Erp_Inventory.RecId\r\n"
					+ "		and Erp_ProductivityTransaction.InsertedAt between @hr_st_time and @end_time\r\n"
				
					+ "		and Erp_InventoryWorkStudy.ProductionProcessId=15\r\n"
					+ "		order by Erp_ProductivityTransaction.InsertedAt desc\r\n" + ")style\r\n" + "on(1=1)";

			rs = stmt.executeQuery(tsql);

			// System.out.println("RS value :"+rs.getString(1));

			while (rs.next()) {
				// System.out.println(rs.getString(1));
				// ProductionDto productionDto = new ProductionDto();
				HrTarget = rs.getString(1) != null ? Integer.parseInt(rs.getString(1)) : 0;
				HrProduction = rs.getString(2) != null ? Integer.parseInt(rs.getString(2)) : 0;
				CumalativeTarget = (int) (HrTarget * CumalativeWorkingHr);
				CumalativeProduction = rs.getString(3) != null ? Integer.parseInt(rs.getString(3)) : 0; // Integer.parseInt(rs.getString(3));
				DayTarget = HrTarget * DayWorkingHr;
				Production = rs.getString(3) != null ? Integer.parseInt(rs.getString(3)) : 0; // Integer.parseInt(rs.getString(3));

				if (CumalativeTarget != 0) {
					Productivity = CumalativeProduction * 100 / CumalativeTarget;
				} else {
					Productivity = 0.0;
				}

				ProduceMinute = rs.getString(4) != null ? Double.parseDouble(rs.getString(4)) : 0; // ;
				Operator = rs.getString(5) != null ? Integer.parseInt(rs.getString(5)) : 0;
				Helper = rs.getString(6) != null ? Integer.parseInt(rs.getString(6)) : 0;

				double Efficiency = (ProduceMinute * 100 / (60 * CumalativeWorkingHr * (Operator + Helper)));

				StyleName = rs.getString(2);

				productionDto.setDayTarget(DayTarget);
				productionDto.setCumalativeProduction(CumalativeProduction);
				productionDto.setCumalativeTarget(CumalativeTarget);
				productionDto.setCumalativeWorkingHr(CumalativeWorkingHr);
				productionDto.setDayWorkingHr(DayWorkingHr);
				productionDto.setHrTarget(HrTarget);
				productionDto.setHrProduction(HrProduction);
				productionDto.setProduction(Production);
				productionDto.setStyleName(StyleName);
				productionDto.setOperator(Operator);
				productionDto.setDayWorkingHr(DayWorkingHr);
				productionDto.setHelper(Helper);
				productionDto.setProductivity(Productivity);
				productionDto.setEfficiency(Efficiency);

//            	private Long id;
//
//            	private int dayTarget;
//
//            	private Double dayProduction;
//
//            	private int CumalativeTarget;
//
//            	private int CumalativeProduction;
//
//            	private Double thisHourTarget;
//
//            	private int HrProduction;
//
//            	private Double activedQty=100.0;
//
//            	private Double planQty=1000.0;
//
//            	private Double productivity;
//
//            	private Double Efficiency;
//
//            	private String StyleName;

//            	Cumelative target = HrTarget*CumalativeWorkingHr;
//            	Day Target = HrTarget*DayWorkingHr;
//            	This Hr production >>>> Production_query(ThisHrstartDate,ThisHrEndDate,lineNo);
//            	Cumelative production >>>> Production_query(DayStartDate,ThisHrEndDate,lineNo);
//            	Day production >>>> Production_query(DayStartDate,DayEndDate,lineNo);
//
//            	Productivity= CumelativeProduction*100/ CumelativeTarget
//            	Efficiency=CumelativeProduction*100*SMV/(60*CumelativeHr*(operator+helper))
//
//            	PotentialProduction=(60*(operator+helper)/SMV)
//            	LineBalancingGap=PotentialProduction-ThisHrProduction;

			}

		} catch (SQLException e) {
			System.out.println("@@@SQLException@@@");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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
		return productionDto;

	}

	public static List<GraphDataDto> api2(String jdbcUrl, String username, String password, String endDate) {

		String splitStartDate = endDate.split(" ")[0];
		String startTime = " 8:30:00";
		String dayStartTime = splitStartDate + startTime;

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<GraphDataDto> productionBarChartList = new ArrayList<GraphDataDto>();

		// try (Connection con = DriverManager.getConnection(connectionUrl); Statement
		// stmt = con.createStatement();) {
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "declare @st_time varchar(30),@end_time varchar(30), @line_no varchar(30)\r\n"
					+ "		set @st_time='" + dayStartTime + "'" + "\r\n" + "	    set @end_time='" + endDate + "'"
					+ "\r\n" + "            set @line_no='LINE1'\r\n" + "            \r\n" + "           \r\n"
					+ "            \r\n" + "            select t3.Process,t1.Production\r\n" + "            from\r\n"
					+ "            (\r\n"
					+ "                select Erp_InventoryWorkStudy.ProcessId as ProcessId, Erp_InventoryWorkStudy.ItemOrderNo as OrderNo,\r\n"
					+ "                convert(int,Erp_InventoryWorkStudy.Quantity) as MC,\r\n"
					+ "                CONVERT(float(2),Erp_InventoryWorkStudy.StandartTime1) as SMV, Erp_InventoryWorkStudy.Segment as mc_type\r\n"
					+ "                from Sentez.dbo.Erp_InventoryWorkStudy\r\n"
					+ "                where (Erp_InventoryWorkStudy.Segment!='HP' or Erp_InventoryWorkStudy.Segment is NULL)\r\n"
					+ "                and Erp_InventoryWorkStudy.InventoryId in \r\n" + "                (\r\n"
					+ "                    SELECT distinct Erp_WorkOrderItem.InventoryId\r\n"
					+ "                    FROM Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_WorkOrderItem, Sentez.dbo.Erp_InventoryWorkStudy\r\n"
					+ "                    where Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n"
					+ "                    and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId \r\n"
					+ "                    and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "                    and Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "                   \r\n" + "                    and Erp_InventoryWorkStudy.ItemOrderNo IN \r\n"
					+ "                    ( select MAX(Erp_InventoryWorkStudy.ItemOrderNo) \r\n"
					+ "                    from Sentez.dbo.Erp_InventoryWorkStudy, Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_WorkOrderItem\r\n"
					+ "                    where Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n"
					+ "                    and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId \r\n"
					+ "                    and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "                    and Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "                    )\r\n" + "                )	\r\n" + "            ) t0\r\n"
					+ "            left join\r\n" + "            (\r\n"
					+ "                select Erp_ProductivityTransaction.WorkOrderItemId,Erp_ProductivityTransaction.ProcessId, \r\n"
					+ "                avg(Erp_InventoryWorkStudy.ItemOrderNo) as OrderNo,\r\n"
					+ "                count(distinct Erp_ProductivityTransaction.ResourceId) as MC,\r\n"
					+ "                convert(int,Sum(Erp_ProductivityTransaction.Quantity)) as Production,\r\n"
					+ "                DATEDIFF(minute,min(Erp_ProductivityTransaction.InsertedAt),MAX(Erp_ProductivityTransaction.InsertedAt)) as A_minute \r\n"
					+ "                from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_WorkOrderItem\r\n"
					+ "                where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "                and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n"
					+ "                and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "                and Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n"
					+ "                \r\n" + "                \r\n"
					+ "                group by Erp_ProductivityTransaction.WorkOrderItemId,Erp_ProductivityTransaction.ProcessId\r\n"
					+ "            ) t1\r\n" + "            on (t0.ProcessId=t1.ProcessId)\r\n"
					+ "            left join\r\n" + "            (	\r\n"
					+ "                select Erp_WorkOrderItem.RecId, Erp_Inventory.InventoryName as Style\r\n"
					+ "                from Sentez.dbo.Erp_WorkOrderItem,Sentez.dbo.Erp_Inventory\r\n"
					+ "                where Erp_WorkOrderItem.InventoryId=Erp_Inventory.RecId\r\n"
					+ "            )t2\r\n" + "            on(t1.WorkOrderItemId=t2.RecId)\r\n"
					+ "            Left join\r\n" + "            (	\r\n"
					+ "                select Erp_Process.RecId, Erp_Process.ProcessName as Process\r\n"
					+ "                from Sentez.dbo.Erp_Process\r\n" + "            )t3\r\n"
					+ "            on(t0.ProcessId=t3.RecId)\r\n" + "            order by t0.OrderNo desc";

			rs = stmt.executeQuery(tsql);

			while (rs.next()) {
				GraphDataDto graphDataDto = new GraphDataDto();

				graphDataDto.setOperationName(rs.getString(1) != null ? rs.getString(1) : "0");
				graphDataDto.setValue(rs.getString(2) != null ? rs.getString(2) : "0");

				productionBarChartList.add(graphDataDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return productionBarChartList;

	}

	public static List<GraphDataDto> api3(String jdbcUrl, String username, String password, String endDate) {

		String splitStartDate = endDate.split(" ")[0];
		String startTime = " 8:30:00";
		String dayStartTime = splitStartDate + startTime;

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDateDateType = null;
		Date endDateDateType = null;
		try {
			startDateDateType = formatter1.parse(dayStartTime);
			endDateDateType = formatter1.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar start = Calendar.getInstance();
		start.setTime(startDateDateType);
		Calendar end = Calendar.getInstance();
		end.setTime(endDateDateType);

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<GraphDataDto> productionBarChartList = new ArrayList<GraphDataDto>();
		int i = 1;

		// try (Connection con = DriverManager.getConnection(connectionUrl); Statement
		// stmt = con.createStatement();) {
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			for (Date date = start.getTime(); start.before(end); start.add(Calendar.HOUR, 1), date = start.getTime()) {

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDateStringType = dateFormat.format(date.getTime());
				String endDateStringType = dateFormat.format(end.getTime());

				String tsql = "declare @date varchar(30), @line_no varchar(30)\r\n"
						+ "declare @st_time varchar(30),@end_time varchar(30)\r\n" + "\r\n" + "set @st_time='"
						+ startDateStringType + "'" + "\r\n" + "set @end_time='" + endDateStringType + "'" + "\r\n"
						+ "set @line_no='LINE1'\r\n" + "\r\n"
						+ "select sum(Erp_ProductivityTransaction.Quantity) as Production\r\n"
						+ "from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_WorkOrderItem\r\n"
						+ "where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
						+ "and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n"
						+ "and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
						+ "and Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n"
						+ "and Erp_ProductivityTransaction.ProductionLine=@line_no\r\n"
						+ "and Erp_InventoryWorkStudy.ProductionProcessId=15";

				rs = stmt.executeQuery(tsql);

				while (rs.next()) {
					GraphDataDto graphDataDto = new GraphDataDto();

					graphDataDto.setProduction(rs.getString(1) != null ? rs.getString(1) : "0");
					// graphDataDto.setTime(startDateStringType!=null?startDateStringType:"0");
					graphDataDto.setTime(i + "h");

					productionBarChartList.add(graphDataDto);
				}
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return productionBarChartList;

	}

	public static GraphDataDto api4(String jdbcUrl, String username, String password, String startDate,
									String endDate) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

//    	 List<GraphDataDto> productioninfoList = new ArrayList<GraphDataDto>();
		GraphDataDto graphDataDto = new GraphDataDto();

		// try (Connection con = DriverManager.getConnection(connectionUrl); Statement
		// stmt = con.createStatement();) {

//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String startDateStringType = dateFormat.format(date.getTime());
//                String endDateStringType = dateFormat.format(end.getTime());
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "declare @date varchar(30), @line_no varchar(30)\r\n"
					+ "declare @st_time varchar(30),@end_time varchar(30)\r\n" + "\r\n" + "set @st_time='" + startDate
					+ "'" + "\r\n" + "set @end_time='" + endDate + "'" + "\r\n" + "set @line_no='LINE1'\r\n"
					+ "select sum(t_production.Production) as production, sum(t_production.Production*t_smv.smv) as produc_munite\r\n"
					+ "from \r\n" + "(\r\n"
					+ "	SELECT Erp_WorkOrderItem.InventoryId,sum(Erp_ProductivityTransaction.Quantity) as Production\r\n"
					+ "	from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_WorkOrderItem\r\n"
					+ "	where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "	and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n"
					+ "	and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "	and Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n"
					+ "	and Erp_ProductivityTransaction.ProductionLine=@line_no\r\n"
					+ "	and Erp_InventoryWorkStudy.ProductionProcessId=15\r\n"
					+ "	group by Erp_WorkOrderItem.InventoryId\r\n" + ") t_production\r\n" + "left join\r\n" + "(\r\n"
					+ "	select Erp_InventoryWorkStudy.InventoryId, sum(Erp_InventoryWorkStudy.StandartTime1) smv\r\n"
					+ "	from Sentez.dbo.Erp_InventoryWorkStudy \r\n"
					+ "	group by Erp_InventoryWorkStudy.InventoryId\r\n" + ") t_smv\r\n"
					+ "on (t_production.InventoryId=t_smv.InventoryId)\r\n" + "";

			rs = stmt.executeQuery(tsql);

			while (rs.next()) {

				graphDataDto.setProduction(rs.getString(1) != null ? rs.getString(1) : "0");
				// graphDataDto.setTime(startDateStringType!=null?startDateStringType:"0");
				graphDataDto.setProductionMinute(rs.getString(2) != null ? rs.getString(2) : "0");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return graphDataDto;

	}

	public static List<GraphDataDto> api5(String jdbcUrl, String username, String password, String startDate,
										  String endDate) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<GraphDataDto> productioninfoList = new ArrayList<GraphDataDto>();
		// GraphDataDto graphDataDto = new GraphDataDto();

		// try (Connection con = DriverManager.getConnection(connectionUrl); Statement
		// stmt = con.createStatement();) {

//               DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//               String startDateStringType = dateFormat.format(date.getTime());
//               String endDateStringType = dateFormat.format(end.getTime());
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "declare @date varchar(30), @line_no varchar(30)\r\n"
					+ "declare @st_time varchar(30),@end_time varchar(30)\r\n" + "set @st_time='2018-1-2 8:30:00'\r\n"
					+ "set @end_time='2020-1-2 16:30:00'\r\n" + "set @line_no='LINE1'\r\n" + "\r\n"
					+ "SELECT FORMAT(Erp_ProductivityTransaction.InsertedAt,'yyyy-MM')  AS Q_Month, sum(Erp_ProductivityTransaction.Quantity) as Production\r\n"
					+ "from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_WorkOrderItem\r\n"
					+ "where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n"
					+ "and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n"
					+ "and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n"
					+ "and Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n"
					+ "and Erp_ProductivityTransaction.ProductionLine=@line_no\r\n"
					+ "and Erp_InventoryWorkStudy.ProductionProcessId=15\r\n"
					+ "group by FORMAT(Erp_ProductivityTransaction.InsertedAt,'yyyy-MM')\r\n" + "";

			rs = stmt.executeQuery(tsql);

			Double result = 0.0;
			String value = null;
			while (rs.next()) {
				GraphDataDto graphDataDto = new GraphDataDto();

				graphDataDto.setTime(rs.getString(1) != null ? rs.getString(1) : "0");
				graphDataDto.setType("Production");
				result = Double.parseDouble(rs.getString(2));
				value = String.format("%.2f", result);
				graphDataDto.setValue(value != null ? value : "0");
//				graphDataDto.setValue(rs.getString(2)!=null?rs.getString(2):"0");

				productioninfoList.add(graphDataDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return productioninfoList;

	}

	public static List<ProductionReportDto> api6(String jdbcUrl, String username, String password, String startDate,
												 String endDate) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<ProductionReportDto> productioninfoList = new ArrayList<ProductionReportDto>();
		// GraphDataDto graphDataDto = new GraphDataDto();

		// try (Connection con = DriverManager.getConnection(connectionUrl); Statement
		// stmt = con.createStatement();) {

//               DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//               String startDateStringType = dateFormat.format(date.getTime());
//               String endDateStringType = dateFormat.format(end.getTime());
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "DECLARE @startHour DATETIME, @oneHour DATETIME         \r\n" +
					"SET @startHour = substring(convert(char(8),'8:30',108), 1, 5)\r\n" +
					"SET @oneHour = substring(convert(char(8),'1:00',108), 1, 5)\r\n" +
					"\r\n" +
					"select common.CompanyName,common.ProductionLine,common.WorkOrderItemId,common.dd,common.hr,common.pr,workOrderItem.WorkOrderNo,workOrderItem.InventoryName,workOrderItem.CurrentAccountName,inventoryWorkStudy.SMV\r\n" +
					"FROM \r\n" +
					"(SELECT Erp_Company.CompanyName,Erp_ProductivityTransaction.ProductionLine,Erp_ProductivityTransaction.WorkOrderItemId,CONVERT(DATE, Erp_ProductivityTransaction.InsertedAt) AS dd,Format(cast(substring(convert(char(8),Erp_ProductivityTransaction.InsertedAt,108), 1, 5) -@startHour +@oneHour AS dateTime ),'HH','en-us') as hr,sum(Erp_ProductivityTransaction.Quantity) AS pr\r\n" +
					"FROM\r\n" +
					"Sentez.dbo.Erp_ProductivityTransaction,Sentez.dbo.Erp_WorkOrderItem,Sentez.dbo.Erp_InventoryWorkStudy,Sentez.dbo.Erp_Company\r\n" +
					"WHERE Erp_ProductivityTransaction.CompanyId=Erp_Company.RecId AND Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId AND Erp_ProductivityTransaction.IsDeleted = 0 AND Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId AND Erp_WorkOrderItem.Status = 1 AND Erp_InventoryWorkStudy.ProcessId=Erp_ProductivityTransaction.ProcessId AND Erp_InventoryWorkStudy.ProductionProcessId=15 AND Erp_ProductivityTransaction.InsertedAt between '"+startDate+"' and '"+ endDate+"' \r\n" +
					"GROUP BY CONVERT(DATE, Erp_ProductivityTransaction.InsertedAt),Format(cast(substring(convert(char(8),Erp_ProductivityTransaction.InsertedAt,108), 1, 5) -@startHour +@oneHour AS dateTime ),'HH','en-us'),Erp_ProductivityTransaction.WorkOrderItemId,Erp_Company.CompanyName,Erp_ProductivityTransaction.ProductionLine\r\n" +
					") AS common\r\n" +
					"LEFT JOIN \r\n" +
					"(SELECT Erp_WorkOrderItem.RecId,Erp_WorkOrderItem.InventoryId,Erp_WorkOrder.WorkOrderNo,Erp_CurrentAccount.CurrentAccountName,Erp_Inventory.InventoryName\r\n" +
					"	FROM Erp_WorkOrderItem,Erp_WorkOrder,Erp_CurrentAccount,Erp_Inventory\r\n" +
					"	WHERE Erp_WorkOrderItem.WorkOrderId = Erp_WorkOrder.RecId AND Erp_WorkOrder.Status = 1	AND Erp_WorkOrder.CurrentAccountId = Erp_CurrentAccount.RecId	AND Erp_WorkOrderItem.InventoryId = Erp_Inventory.RecId	AND Erp_WorkOrderItem.Status = 1\r\n" +
					") AS workOrderItem\r\n" +
					"ON common.WorkOrderItemId = workOrderItem.RecId\r\n" +
					"LEFT JOIN\r\n" +
					"(SELECT Erp_InventoryWorkStudy.InventoryId,SUM(Erp_InventoryWorkStudy.StandartTime1) AS SMV FROM Erp_InventoryWorkStudy\r\n" +
					"	GROUP BY Erp_InventoryWorkStudy.InventoryId\r\n" +
					") AS inventoryWorkStudy\r\n" +
					"ON workOrderItem.InventoryId = inventoryWorkStudy.InventoryId\r\n" +
					"ORDER BY common.dd,common.hr";

			//System.out.println(tsql);
			rs = stmt.executeQuery(tsql);


			Double result = 0.0;
			String value = null;
			while (rs.next()) {
				ProductionReportDto productionDto = new ProductionReportDto();

				productionDto.setCompany(rs.getString(1) != null ? rs.getString(1) : "0");
				productionDto.setLine(rs.getString(2) != null ? rs.getString(2) : "0");
				productionDto.setProductionDate(rs.getString(4) != null ? rs.getString(4) : "0");
				productionDto.setProductionHour(rs.getString(5) != null ? rs.getString(5) : "0");
				productionDto.setProduction(rs.getString(6) != null ? rs.getString(6) : "0");
				productionDto.setOrderNo(rs.getString(7) != null ? rs.getString(7) : "0");
				productionDto.setStyleName(rs.getString(8) != null ? rs.getString(8) : "0");
				productionDto.setBuyer(rs.getString(9) != null ? rs.getString(9) : "0");

				productionDto.setSmv(rs.getString(10) != null ? rs.getString(10) : "0");





				productioninfoList.add(productionDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return productioninfoList;

	}

	public static List<ProductionChartDto> api7(String jdbcUrl, String username, String password, Long orgId,String startDate) {
		
		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<ProductionChartDto> ProductionChartList = new ArrayList<ProductionChartDto>();
		
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "SELECT Erp_Process.ProcessName,sum(Erp_ProductivityTransaction.Quantity) AS production \r\n" +
						  "FROM \r\n" +
						  "Sentez.dbo.Erp_ProductivityTransaction,Sentez.dbo.Erp_Company,Sentez.dbo.Erp_Process \r\n" +
						  "WHERE Erp_ProductivityTransaction.CompanyId="+orgId+" \r\n" +
						  "AND Erp_ProductivityTransaction.InsertedAt between '"+startDate+" 00:00:00' and '"+startDate+" 23:59:59' \r\n" +
						  "AND Erp_ProductivityTransaction.CompanyId=Erp_Company.RecId \r\n" +
						  "AND Erp_ProductivityTransaction.IsApproved = 1 \r\n" +
						  "AND Erp_ProductivityTransaction.IsDeleted = 0 \r\n" +
						  "AND Erp_ProductivityTransaction.ProcessId = Erp_Process.RecId \r\n" +
						  "GROUP BY Erp_Process.ProcessName";

			rs = stmt.executeQuery(tsql);

			while (rs.next()) {
				ProductionChartDto ProductionChartDto = new ProductionChartDto();

				ProductionChartDto.setOperationName(rs.getString(1) != null ? rs.getString(1) : "");
				ProductionChartDto.setProductionQuantity(rs.getString(2) != null ? Double.parseDouble(rs.getString(2)) : 0.0);

				ProductionChartList.add(ProductionChartDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return ProductionChartList;
	}
	
	public static List<ProductionChartDto> api8(String jdbcUrl, String username, String password, Long orgId,String startDate) {
		
		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<ProductionChartDto> ProductionChartList = new ArrayList<ProductionChartDto>();
		
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "DECLARE @startHour DATETIME, @oneHour DATETIME \r\n" +        
						"SET @startHour = substring(convert(char(8),'8:30',108), 1, 5) \r\n" +
						"SET @oneHour = substring(convert(char(8),'1:00',108), 1, 5) \r\n" +
						"SELECT Format(cast(substring(convert(char(8),Erp_ProductivityTransaction.InsertedAt,108), 1, 5) -@startHour +@oneHour AS dateTime ),'HH','en-us') as hr,sum(Erp_ProductivityTransaction.Quantity) AS production \r\n" +
						"FROM \r\n" +
						"Sentez.dbo.Erp_ProductivityTransaction,Sentez.dbo.Erp_Company,Sentez.dbo.Erp_Process \r\n" +
						"WHERE Erp_ProductivityTransaction.CompanyId="+orgId+" \r\n" +
						"AND Erp_ProductivityTransaction.InsertedAt between '"+startDate+" 08:30:00' and '"+startDate+" 16:30:59' \r\n" +
						"AND Erp_ProductivityTransaction.CompanyId=Erp_Company.RecId \r\n" +
						"AND Erp_ProductivityTransaction.IsApproved = 1 \r\n" +
						"AND Erp_ProductivityTransaction.IsDeleted = 0 \r\n" +
						"AND Erp_ProductivityTransaction.ProcessId = Erp_Process.RecId \r\n" +
						"GROUP BY Format(cast(substring(convert(char(8),Erp_ProductivityTransaction.InsertedAt,108), 1, 5) -@startHour +@oneHour AS dateTime ),'HH','en-us')";

			rs = stmt.executeQuery(tsql);

			while (rs.next()) {
				ProductionChartDto ProductionChartDto = new ProductionChartDto();

				ProductionChartDto.setHour(rs.getString(1) != null ? rs.getString(1) : "");
				ProductionChartDto.setProductionQuantity(rs.getString(2) != null ? Double.parseDouble(rs.getString(2)) : 0.0);

				ProductionChartList.add(ProductionChartDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return ProductionChartList;
	}
	
	public static List<OperationAnalysisCSDto> api9(String jdbcUrl, String username, String password,double minRange,double maxRange) {
		
		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<OperationAnalysisCSDto> OperationAnalysisCSDtoList = new ArrayList<OperationAnalysisCSDto>();
		
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "SELECT Erp_ProductivityTransaction.ProcessId as oerationId,Erp_Process.ProcessName as oerationName,Erp_WorkOrderItem.InventoryId AS styleId,Erp_Inventory.InventoryName AS styleName, \r\n" +
						  "Erp_Employee.EmployeeCode,Erp_Employee.EmployeeName,ROUND(AVG(Erp_ProductivityTransaction.StudyTime),2) AS operation_smv, \r\n" +
						  "ROUND((sum(convert(float,Erp_ProductivityTransaction.Time))*24*60/sum(Erp_ProductivityTransaction.Quantity)),2) as operator_cycle_time, \r\n" +
						  "round(100*avg(Erp_ProductivityTransaction.StudyTime)/(sum(convert(float,Erp_ProductivityTransaction.Time))*24*60/sum(Erp_ProductivityTransaction.Quantity)),2) AS efficiency, \r\n" +
						  "COUNT(*) AS data_number  \r\n" +
						  "FROM Erp_ProductivityTransaction,Erp_Process,Erp_WorkOrderItem,Erp_Inventory,Erp_Employee \r\n" +
						  "WHERE Erp_ProductivityTransaction.ProcessId = Erp_Process.RecId \r\n" +
						  "AND Erp_ProductivityTransaction.WorkOrderItemId = Erp_WorkOrderItem.RecId \r\n" +
						  "AND Erp_WorkOrderItem.InventoryId = Erp_Inventory.RecId \r\n" +
						  "AND Erp_ProductivityTransaction.EmployeeId = Erp_Employee.RecId \r\n" +
						  "AND Erp_ProductivityTransaction.Quantity > 0 \r\n" +
						  "AND (convert(float,Erp_ProductivityTransaction.Time)*24*60/Erp_ProductivityTransaction.Quantity) BETWEEN  "+minRange+"*Erp_ProductivityTransaction.StudyTime AND  "+maxRange+"*Erp_ProductivityTransaction.StudyTime \r\n" +
						  "GROUP BY Erp_ProductivityTransaction.ProcessId,Erp_Process.ProcessName,Erp_WorkOrderItem.InventoryId,Erp_Inventory.InventoryName, \r\n" +
								   "Erp_Employee.EmployeeCode,Erp_Employee.EmployeeName ";

			rs = stmt.executeQuery(tsql);

			while (rs.next()) {
				OperationAnalysisCSDto operationAnalysisCSDto = new OperationAnalysisCSDto();

				operationAnalysisCSDto.setOperationId(rs.getString(1) != null ? Long.parseLong(rs.getString(1).toString()) : 0);
				operationAnalysisCSDto.setOperationName(rs.getString(2) != null ? rs.getString(2): "");
				operationAnalysisCSDto.setStyleId(rs.getString(3) != null ? Long.parseLong(rs.getString(3).toString()) : 0);
				operationAnalysisCSDto.setStyleName(rs.getString(4) != null ? rs.getString(4) : "");
				operationAnalysisCSDto.setEmployeeCode(rs.getString(5) != null ? rs.getString(5) : "");
				operationAnalysisCSDto.setEmployeeName(rs.getString(6) != null ? rs.getString(6) : "");
				operationAnalysisCSDto.setOperationSmv(rs.getString(7) != null ? Double.parseDouble(rs.getString(7).toString()) : 0);
				operationAnalysisCSDto.setCycleTime(rs.getString(8) != null ? Double.parseDouble(rs.getString(8).toString()) : 0);
				operationAnalysisCSDto
						.setOperatorEfficiency(rs.getString(9) != null ? Double.parseDouble(rs.getString(9).toString()) : 0);
				operationAnalysisCSDto.setNumberOfData(rs.getString(10) != null ? Integer.parseInt(rs.getString(10).toString()) : 0);

				OperationAnalysisCSDtoList.add(operationAnalysisCSDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return OperationAnalysisCSDtoList;
	}
	
	public static List<EmployeeProfileFromCapacityDto> api10(String jdbcUrl, String username, String password,Long employeeId,double minRange,double maxRange) {
		
		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<EmployeeProfileFromCapacityDto> employeeProfileFromCapacityDtoList = new ArrayList<EmployeeProfileFromCapacityDto>();
		
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();
			
			String averageCapacityString = null;

			String tsql = "SELECT Erp_ProductivityTransaction.ProcessId as operationId,Erp_Process.ProcessName as operationName,Erp_WorkOrderItem.InventoryId AS styleId,Erp_Inventory.InventoryName AS styleName,Erp_ProductivityTransaction.StudyTime AS smv,60/(sum(convert(float,Erp_ProductivityTransaction.Time))*24*60/sum(Erp_ProductivityTransaction.Quantity)) AS averageCapacity \r\n" +
						  "FROM Erp_ProductivityTransaction,Erp_Process,Erp_WorkOrderItem,Erp_Inventory,Erp_InventoryWorkStudy \r\n" +
						  "WHERE Erp_ProductivityTransaction.EmployeeId = "+employeeId+" \r\n" +
						  "AND Erp_ProductivityTransaction.ProcessId = Erp_Process.RecId \r\n" +
						  "AND Erp_ProductivityTransaction.WorkOrderItemId = Erp_WorkOrderItem.RecId \r\n" +
						  "AND Erp_WorkOrderItem.InventoryId = Erp_Inventory.RecId \r\n" +
						  "AND Erp_ProductivityTransaction.ProcessId = Erp_InventoryWorkStudy.ProcessId \r\n" +
						  "AND Erp_ProductivityTransaction.Quantity > 0 \r\n" +
						  "AND (convert(float,Erp_ProductivityTransaction.Time)*24*60/Erp_ProductivityTransaction.Quantity) BETWEEN  "+minRange+"*Erp_ProductivityTransaction.StudyTime AND  "+maxRange+"*Erp_ProductivityTransaction.StudyTime \r\n" +
						  "GROUP BY Erp_ProductivityTransaction.ProcessId,Erp_Process.ProcessName,Erp_WorkOrderItem.InventoryId,Erp_Inventory.InventoryName,Erp_ProductivityTransaction.StudyTime ";

			rs = stmt.executeQuery(tsql);

			while (rs.next()) {
				EmployeeProfileFromCapacityDto employeeProfileFromCapacityDto = new EmployeeProfileFromCapacityDto();

				employeeProfileFromCapacityDto.setOperationId(rs.getString(1) != null ? Long.parseLong(rs.getString(1).toString()) : 0);
				employeeProfileFromCapacityDto.setOperation(rs.getString(2) != null ? rs.getString(2) : "");
				employeeProfileFromCapacityDto.setStyleId(rs.getString(3) != null ? Long.parseLong(rs.getString(3).toString()) : 0);
				employeeProfileFromCapacityDto.setStyleName(rs.getString(4) != null ? rs.getString(4) : "");
				employeeProfileFromCapacityDto.setSmv(rs.getString(5) != null ? Float.parseFloat(rs.getString(5)) : 0);
				//employeeProfileFromCapacityDto.setAverageCapacity(rs.getString(6) != null ? Float.parseFloat(rs.getString(6)) : 0);
				
				averageCapacityString = rs.getString(6) != null ? rs.getString(6) : "0";

				float averageCapacity = Float.parseFloat(averageCapacityString);
				employeeProfileFromCapacityDto.setAverageCapacity(averageCapacity);

				String efficiency = String.valueOf(employeeProfileFromCapacityDto.getSmv()
						* (employeeProfileFromCapacityDto.getAverageCapacity() / 60) * 100);

				float efficiencyfloat = (float) DecimalValueConversion.getTwoValueAfterDot(efficiency);
				employeeProfileFromCapacityDto.setEfficiency(efficiencyfloat);

				employeeProfileFromCapacityDtoList.add(employeeProfileFromCapacityDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return employeeProfileFromCapacityDtoList;
	}
	
	public static List<OperatorCycleTimeTrendsDto> api11(String jdbcUrl, String username, String password,Long employeeId,Long operationId,double minRange,double maxRange) {
		
		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		List<OperatorCycleTimeTrendsDto> OperationAnalysisCSDtoList = new ArrayList<OperatorCycleTimeTrendsDto>();
		
		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();
			

			String tsql = "SELECT FORMAT(Erp_ProductivityTransaction.InsertedAt,'yyyy-MMM-dd', 'en-us') AS study_date,ROUND((sum(convert(float,Erp_ProductivityTransaction.Time))*24*60/sum(Erp_ProductivityTransaction.Quantity)),2) as operator_cycle_time \r\n" +
						  "FROM Erp_ProductivityTransaction \r\n" +
						  "WHERE Erp_ProductivityTransaction.EmployeeId = "+employeeId+"  \r\n" +
						  "AND Erp_ProductivityTransaction.ProcessId = "+operationId+" \r\n" +
						  "AND Erp_ProductivityTransaction.Quantity > 0 \r\n" +
						  "AND (convert(float,Erp_ProductivityTransaction.Time)*24*60/Erp_ProductivityTransaction.Quantity) BETWEEN  "+minRange+"*Erp_ProductivityTransaction.StudyTime AND  "+maxRange+"*Erp_ProductivityTransaction.StudyTime \r\n" +
						  "GROUP BY FORMAT(Erp_ProductivityTransaction.InsertedAt,'yyyy-MMM-dd', 'en-us') ";

			rs = stmt.executeQuery(tsql);

			while (rs.next()) {
				OperatorCycleTimeTrendsDto operationAnalysisCSDto = new OperatorCycleTimeTrendsDto();

				operationAnalysisCSDto.setStudyDate(rs.getString(1) != null ? rs.getString(1).toString() : "");
				operationAnalysisCSDto.setOperatorCyleTime(rs.getString(2) != null ? Double.parseDouble(rs.getString(2).toString()) : 0);

				OperationAnalysisCSDtoList.add(operationAnalysisCSDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
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

		return OperationAnalysisCSDtoList;
	}

	public static void employeeCreation(String jdbcUrl, String username, String password, Employee employee, Long orgId) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		Connection con = null;
		Statement stmt = null;

		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_Employee ON\r\n" +
					"INSERT INTO Sentez.dbo.Erp_Employee(RecId,CompanyId,EmployeeCode,EmployeeName,EmployeeSurname,EmployeeType,ProxyNo)\r\n" +
					"VALUES ("+employee.getId()+","+orgId+",'"+employee.getEmployeeCode()+"','"+employee.getName()+"','"+employee.getSurName()+"',"+employee.getEmployeeType()+",'"+employee.getProxyCardNo()+"')\r\n" +
					"SET IDENTITY_INSERT Sentez.dbo.Erp_Employee OFF";
            
			stmt.executeUpdate(tsql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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
	
	public static void machineCreation(String jdbcUrl, String username, String password,Machine machine) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		Connection con = null;
		Statement stmt = null;

		try {
			System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_Resource ON\r\n" +
					"INSERT INTO Sentez.dbo.Erp_Resource(RecId,ResourceCode,Explanation)\r\n" +
					"VALUES ("+machine.getId()+",'"+machine.getCode()+"','"+machine.getDescription()+"')\r\n" +
					"SET IDENTITY_INSERT Sentez.dbo.Erp_Resource OFF";
            
			stmt.executeUpdate(tsql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

	public static void companyCreation(String jdbcUrl, String username, String password,Company company) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		//String connectionUrl ="jdbc:sqlserver://" + "Server=DIN0100005\\SQLEXPRESS;Database=Sentez;User Id=sa;Password=asi@321;" ;
		Connection con = null;
		Statement stmt = null;

		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();



			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_Company ON;" +
					" INSERT INTO Sentez.dbo.Erp_Company(RecId,CompanyName,CompanyCode) " +
					" VALUES ("+company.getId()+",'"+company.getName()+"','"+company.getCompanyCode()+"');"+
					" SET IDENTITY_INSERT Sentez.dbo.Erp_Company OFF; ";

			stmt.executeUpdate(tsql);



		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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
	
	public static void erpCurrentAccount(String jdbcUrl, String username, String password,Customer customer,Long orgId) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		//String connectionUrl ="jdbc:sqlserver://" + "Server=DIN0100005\\SQLEXPRESS;Database=Sentez;User Id=sa;Password=asi@321;" ;
		Connection con = null;
		Statement stmt = null;

		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();



			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_CurrentAccount ON;" +
					" INSERT INTO Sentez.dbo.Erp_CurrentAccount(RecId,CompanyId,CurrentAccountName) " +
					" VALUES ("+customer.getId()+",'"+orgId+"','"+customer.getName()+"');"+
					" SET IDENTITY_INSERT Sentez.dbo.Erp_CurrentAccount OFF; ";

			stmt.executeUpdate(tsql);



		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

	public static void orderCreation(String jdbcUrl, String username, String password,OrderEntity orderEntity) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		//String connectionUrl ="jdbc:sqlserver://" + "Server=DIN0100005\\SQLEXPRESS;Database=Sentez;User Id=sa;Password=asi@321;" ;
		Connection con = null;
		Statement stmt = null;

		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();



			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_WorkOrder ON\r\n" +
					"INSERT INTO Sentez.dbo.Erp_WorkOrder(RecId,CompanyId,WorkOrderType,\r\n" +
					"WorkOrderSubType,WorkOrderNo,ControlCode,\r\n" +
					"CurrentAccountId,Quantity,PackageQuantity,\r\n" +
					"Status,IsChecked,IsApproved,\r\n" +
					"IsLocked,InsertedBy,QuantityPerLot,\r\n" +
					"TotalAmount,PackingQuantity,CuttingExtra,ProxyNo)\r\n" +
					"VALUES ("+orderEntity.getId()+","+orderEntity.getOrganization().getId()+","+15+","+"\r\n" +
					0+",'"+orderEntity.getName()+"',"+0+",\r\n" +
					orderEntity.getCustomer().getId()+","+orderEntity.getQuantity()+","+1.00000000+",\r\n" +
					1+","+1+","+1+",\r\n" +
					0+","+1+","+orderEntity.getQuantity()+",\r\n" +
					0+","+0+","+5+",'"+orderEntity.getProxyCardNo()+"');"+"\r\n" +
					"SET IDENTITY_INSERT Sentez.dbo.Erp_WorkOrder OFF";

			System.out.println("Sql Query\n");
			System.out.println(tsql);

			stmt.executeUpdate(tsql);



		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

		//return ;

	}

	public static void operationCreation(String jdbcUrl, String username, String password,Operation operation,Long orgId) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		//String connectionUrl ="jdbc:sqlserver://" + "Server=DIN0100005\\SQLEXPRESS;Database=Sentez;User Id=sa;Password=asi@321;" ;
		Connection con = null;
		Statement stmt = null;

		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String ComingTransactionType1 = "Sağlam";
			String ComingTransactionType2 = "2k";
			String ComingTransactionType3 = "Defolu";
			String ComingTransactionType4 = "Lekeli";

			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_Process ON;\r\n" +
					"INSERT INTO Sentez.dbo.Erp_Process(RecId,CompanyId,ProcessCode,ProcessName,\r\n" +
					"UseManufacturing,UsePlanning,ProxyNo,StandartTime,\r\n" +
					"ComingTransactionType1,ComingTransactionType2,ComingTransactionType3,\r\n" +
					"ComingTransactionType4,InUse)\r\n" +
					"VALUES ("+operation.getId()+","+orgId+",'"+operation.getProcessCode()+"','"+operation.getName()+"',\r\n"+
					1+","+1+",'"+operation.getProcessCode()+"',"+operation.getSmv()+",'"+ComingTransactionType1+"',\r\n" +
					"'"+ComingTransactionType2+"','"+ComingTransactionType3+"','"+ComingTransactionType4+"',"+1+");\r\n" +
					"SET IDENTITY_INSERT Sentez.dbo.Erp_Process OFF;";

			stmt.executeUpdate(tsql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

	public static void erp_WorkOrderItemCreation(String jdbcUrl, String username, String password,OrderEntity orderEntity) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		//String connectionUrl ="jdbc:sqlserver://" + "Server=DIN0100005\\SQLEXPRESS;Database=Sentez;User Id=sa;Password=asi@321;" ;
		Connection con = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String InventoryVariantIds = "1";

			String tsql ="INSERT INTO Sentez.dbo.Erp_WorkOrderItem(WorkOrderId,WorkOrderSubType,\r\n" +
					"ItemOrderNo,SubNo,InventoryId,Quantity,ProxyNo,\r\n" +
					"Status,IsChecked,IsApproved,\r\n" +
					"InventoryVariantIds)\r\n" +
					"VALUES ("+orderEntity.getId()+","+1+","+1+","+1+","+orderEntity.getStyle().getId()+","+orderEntity.getQuantity()+",'"+orderEntity.getProxyCardNo()+"',\r\n"+
					1+","+1+","+1+",'"+InventoryVariantIds+"');";


			System.out.println("Querry value :\n");
			System.out.println(tsql);

			stmt.executeUpdate(tsql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

	public static void StyleCreationAsInventory(String jdbcUrl, String username, String password,Style style,Long orgId) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";

		Connection con = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

			String inventoryCode = style.getId().toString();


			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_Inventory ON;\r\n" +
					"INSERT INTO Sentez.dbo.Erp_Inventory(RecId,CompanyId,InventoryCode,InventoryName)\r\n" +
					"VALUES ("+style.getId()+","+orgId+",'"+inventoryCode+"','"+style.getName()+"');\r\n"+
					"SET IDENTITY_INSERT Sentez.dbo.Erp_Inventory OFF;";

			stmt.executeUpdate(tsql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

	public static void erpInventoryWorkStudyCreation(String jdbcUrl, String username, String password,OperationBreakDown operationBreakDown) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";

		Connection con = null;
		Statement stmt = null;

		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();


			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_InventoryWorkStudy ON;\r\n" +
					"INSERT INTO Sentez.dbo.Erp_InventoryWorkStudy(RecId,InventoryId,ProcessId,Quantity)\r\n" +
					"VALUES ("+operationBreakDown.getId()+","+operationBreakDown.getStyle().getId()+","+operationBreakDown.getOperation().getId()+","+operationBreakDown.getMachineQuantity()+");\r\n"+
					"SET IDENTITY_INSERT Sentez.dbo.Erp_InventoryWorkStudy OFF;";

			stmt.executeUpdate(tsql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

	public static void erpInventoryWorkStudyUpdate(String jdbcUrl, String username, String password,OperationBreakDown operationBreakDown) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";

		Connection con = null;
		Statement stmt = null;

		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();


			String tsql = "UPDATE Sentez.dbo.Erp_InventoryWorkStudy\r\n" +
					"SET InventoryId ="+operationBreakDown.getStyle().getId()+",ProcessId ="+operationBreakDown.getOperation().getId()+",Quantity="+operationBreakDown.getMachineQuantity()+"\r\n"+
					"WHERE RecId="+operationBreakDown.getId()+";";

			stmt.executeUpdate(tsql);



		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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

	public static ProductionDto gwtProductionDashBoard(String jdbcUrl, String username, String password,String currentDateAndTimeString) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		ProductionDto productionDto = new ProductionDto();

		try {

//			String currentDateAndTimeString = "2020-2-26 12:30:00";

			String dateString = currentDateAndTimeString.split(" ")[0];
			String timeString = currentDateAndTimeString.split(" ")[1];
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

//			System.out.println("time :" + time);
//			System.out.println();
//			System.out.println("dayStartTime :" + dayStartTime);
//			System.out.println();

			endHr = time.getHours();
			endMinit = time.getMinutes();

			dayStartMinute= dayStartTime.getMinutes();

//			System.out.println("end hour :" + endHr);
//			System.out.println("endMinit :" + endMinit);

			long timeDurationofworking = time.getTime() - dayStartTime.getTime();
			System.out.println("substruct two date :" + timeDurationofworking);

			String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeDurationofworking),
					TimeUnit.MILLISECONDS.toMinutes(timeDurationofworking)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDurationofworking)),
					TimeUnit.MILLISECONDS.toSeconds(timeDurationofworking)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDurationofworking)));
			//System.out.println("substruct two date in time :" + hms);

			Date hmsDate = sdf.parse(hms);

			double hmsint = hmsDate.getMinutes();
			double value = (hmsint / 60);

			Double truncatedDoubleOfValue = BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP).doubleValue();

			//System.out.println("Date type hms :" + hmsDate);



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
			String hr_Start_Time = Integer.toString(hr_st_timehr) + ":30:00";

			// System.out.println("passedWorkingHr :"+passedWorkingHr);

//			System.out.println("==============================================");
//
			System.out.println("dateString :" + dateString);
			System.out.println("dayStartTimeString :" + dayStartTimeString);
			// System.out.println("hr_st_time :"+dateString);
			System.out.println("timeString :" + timeString);
			System.out.println("passedWorkingHr :" + passedWorkingHr);
			System.out.println("hr_Start_Time :"+hr_Start_Time);
			System.out.println("truncatedDoubleOfValue :"+truncatedDoubleOfValue);

			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();


			String tsql = "DECLARE @getDate varchar(30), @st_time varchar(30),@end_time varchar(30), @line_no varchar(30),@hr_st_time varchar(30)\r\n" +
					"\r\n" +
					"SET @getDate='"+dateString+"'\r\n" +
					"\r\n" +
					"set @st_time='"+dayStartTimeString+"'\r\n" +
					"\r\n" +
					"set @hr_st_time='"+hr_Start_Time+"'\r\n" +
					"\r\n" +
					"set @end_time='"+timeString+"'\r\n" +
					"\r\n" +
					"SELECT hrProdTable.hrProduction,ProdMntTable.cumelativeProduction,ProdMntTable.produceMinute, target.LineTarget,\r\n" +
					"\r\n" +
					"(SELECT MAX(v) FROM (VALUES (target.OperatorNumber),(operator.op) ) AS value(v) ) AS operators, target.HelperNumber AS helpers\r\n" +
					"\r\n" +
					"FROM\r\n" +
					"\r\n" +
					"(\r\n" +
					"\r\n" +
					"SELECT 1 as sr, sum(prodTable.pro) AS cumelativeProduction, sum(prodTable.pro*smvTable.smv) AS produceMinute\r\n" +
					"\r\n" +
					"FROM\r\n" +
					"\r\n" +
					"(\r\n" +
					"\r\n" +
					"SELECT Erp_WorkOrderItem.InventoryId,\r\n" +
					"\r\n" +
					"convert(int,Sum(Erp_ProductivityTransaction.Quantity)) as pro\r\n" +
					"\r\n" +
					"from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,\r\n" +
					"\r\n" +
					"Sentez.dbo.Erp_WorkOrderItem\r\n" +
					"\r\n" +
					"where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n" +
					"\r\n" +
					"and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n" +
					"\r\n" +
					"and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n" +
					"\r\n" +
					"and Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n" +
					"\r\n" +
					"and Erp_InventoryWorkStudy.ProductionProcessId=15\r\n" +
					"\r\n" +
					"GROUP BY Erp_WorkOrderItem.InventoryId\r\n" +
					"\r\n" +
					") prodTable\r\n" +
					"\r\n" +
					"LEFT join\r\n" +
					"\r\n" +
					"(\r\n" +
					"\r\n" +
					"SELECT Erp_InventoryWorkStudy.InventoryId,sum(Erp_InventoryWorkStudy.StandartTime1) AS smv\r\n" +
					"\r\n" +
					"FROM Erp_InventoryWorkStudy GROUP BY Erp_InventoryWorkStudy.InventoryId\r\n" +
					"\r\n" +
					") smvTable\r\n" +
					"\r\n" +
					"ON(prodTable.InventoryId=smvTable.InventoryId)\r\n" +
					"\r\n" +
					")ProdMntTable\r\n" +
					"\r\n" +
					"LEFT JOIN\r\n" +
					"\r\n" +
					"(\r\n" +
					"\r\n" +
					"SELECT 1 as sr,\r\n" +
					"\r\n" +
					"convert(int,Sum(Erp_ProductivityTransaction.Quantity)) as hrProduction\r\n" +
					"\r\n" +
					"from Sentez.dbo.Erp_ProductivityTransaction, Sentez.dbo.Erp_InventoryWorkStudy,\r\n" +
					"\r\n" +
					"Sentez.dbo.Erp_WorkOrderItem\r\n" +
					"\r\n" +
					"where Erp_ProductivityTransaction.ProcessId=Erp_InventoryWorkStudy.ProcessId\r\n" +
					"\r\n" +
					"and Erp_ProductivityTransaction.WorkOrderItemId=Erp_WorkOrderItem.RecId\r\n" +
					"\r\n" +
					"and Erp_WorkOrderItem.InventoryId=Erp_InventoryWorkStudy.InventoryId\r\n" +
					"\r\n" +
					"and Erp_ProductivityTransaction.InsertedAt between @hr_st_time and @end_time\r\n" +
					"\r\n" +
					"and Erp_InventoryWorkStudy.ProductionProcessId=15\r\n" +
					"\r\n" +
					")hrProdTable\r\n" +
					"\r\n" +
					"ON(ProdMntTable.sr=hrProdTable.sr)\r\n" +
					"\r\n" +
					"LEFT join\r\n" +
					"\r\n" +
					"(\r\n" +
					"\r\n" +
					"SELECT 1 as sr, @getDate AS dt ,Erp_LineTarget.LineTarget,Erp_LineTarget.OperatorNumber,Erp_LineTarget.HelperNumber\r\n" +
					"\r\n" +
					"FROM Sentez.dbo.Erp_LineTarget\r\n" +
					"\r\n" +
					"WHERE Erp_LineTarget.TargetDate=@getDate\r\n" +
					"\r\n" +
					") target\r\n" +
					"\r\n" +
					"ON(hrProdTable.sr=target.sr)\r\n" +
					"\r\n" +
					"LEFT join\r\n" +
					"\r\n" +
					"(\r\n" +
					"\r\n" +
					"SELECT @getDate AS dt,count(distinct Erp_ProductivityTransaction.ResourceId) AS op\r\n" +
					"\r\n" +
					"FROM Sentez.dbo.Erp_ProductivityTransaction\r\n" +
					"\r\n" +
					"WHERE Erp_ProductivityTransaction.InsertedAt between @st_time and @end_time\r\n" +
					"\r\n" +
					") operator\r\n" +
					"\r\n" +
					"ON(target.dt=operator.dt)";

			//System.out.println(tsql);

			rs=stmt.executeQuery(tsql);


			while (rs.next()) {


				productionDto.setHrProduction(rs.getString(1) !=null?Integer.parseInt(rs.getString(1)):0);
				productionDto.setCumalativeProduction(rs.getString(2) !=null?Integer.parseInt(rs.getString(2)):0);
				productionDto.setProduceMinute(rs.getString(3) !=null?Double.parseDouble(rs.getString(3)):0.0);
				productionDto.setLineTarget(rs.getString(4) !=null?Integer.parseInt(rs.getString(4)):0);
				productionDto.setOperator(rs.getString(5) !=null?Integer.parseInt(rs.getString(5)):0);
				productionDto.setHelper(rs.getString(6) !=null?Integer.parseInt(rs.getString(6)):0);

			}

			productionDto.setCumalativeTargetDoubleValue(passedWorkingHr*productionDto.getLineTarget());
			productionDto.setDayTargetDoubleValue(workingHr*productionDto.getLineTarget());
			if(productionDto.getCumalativeTargetDoubleValue()>0) {
				productionDto.setProductivity((productionDto.getCumalativeProduction()*100)/productionDto.getCumalativeTargetDoubleValue());
			}

			long sumOFOperatorAndHelper;
			sumOFOperatorAndHelper = productionDto.getOperator()+productionDto.getHelper();
			if(passedWorkingHr>0 && sumOFOperatorAndHelper>0) {
				productionDto.setEfficiency((productionDto.getProduceMinute()*100)/(60*passedWorkingHr*sumOFOperatorAndHelper));
			}
			productionDto.setBalancingGap(100-productionDto.getEfficiency());


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
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
		return productionDto;
	}

}
