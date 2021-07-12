package com.softron.common.utils;

public final class UrlConstants {
	private UrlConstants(){
		
	}

	private static final String API = "/api";
	private static final String VERSION = "/v1";

	public static class Client{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/clients";
		public static final String GET_CLIENTSTIME = "/clientsTime";
		public static final String GET_CLIENTDASHBOARD =GET_ALL+"/getClientDashBoard";
		public static final String GET_CLIENTCHART =GET_ALL+"/getClientChart";
		public static final String GET = GET_ALL + "/{id}";
	}

	public static class Company{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/companies";
		public static final String GET_QUALITYTREE = GET_ALL+"/getQualityTree";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Customer{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/customers";
		public static final String GETCUSBYNAME = GET_ALL +"/getCustomerByName";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Defect{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/defects";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Department{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/departments";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Employee{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/employees";
		public static final String EMPLOYEEPROFILEINFO =GET_ALL+"/getEmployeeProfileInfo";
		public static final String EMPLOYEEPROFILECHART =GET_ALL+"/getEmployeeProfileChart";
		public static final String EMPLOYEE_REPORT =GET_ALL+"/getEmployeeReport"+"/{formate}";
		public static final String GET = GET_ALL + "/{id}";
		public static final String GET_ALL_CO_WORKER_CS = "/coWorkerCS";
		public static final String EMPLOYEE_CREATE_FROM_EXCEL =GET_ALL+"/employeeCreateFromExcel";
	}
	public static class Operation{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/operations";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Plant{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/plants";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Section{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/sections";
		public static final String GET_SUBSECTION = GET_ALL+"/getsubsection";
		public static final String GET_SECTION = GET_ALL+"/getsection";
		public static final String GET = GET_ALL + "/{id}";
		
		public static final String GET_ORGANIZATION_SECTION = GET_ALL+"/getOrganizationWiseSection";
	}
	public static class WorkProcess{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/workProcess";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class OrderEntity{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/orderEntities";
//		public static final String GET_STYLE = GET_ALL+"/customer";
		public static final String GET_ORDER = GET_ALL+"/customer";
		public static final String GET_ORDER_LIST = GET_ALL+"/getOrderList";
		public static final String GET_ORDER_SUMMERY = GET_ALL+"/getOrderSummery";
		public static final String GET_PARAMETER_SELECTION_PANEL = GET_ALL+"/getParameterSelectionPanel";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Style{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/styles";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class OperationBreakDown{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/operationBreakDowns";
		public static final String GET_OPBDBYSTYLE = GET_ALL+"/getByStyle";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class MachineType{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/machineTypes";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class QualityDefect{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/qualityDefects";
		public static final String TOPTHREEQDEFECT =GET_ALL+"/topThreeQualityDefects";
		public static final String GET = GET_ALL + "/{id}";
		public static final String GET_TOPQUALITYDEFECT =GET_ALL+"/getTopQualityDefects";
	}
	public static class QualityTransaction{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/qualityTransactions";
		public static final String GET_SUM = GET_ALL+"/getCheckValue";
		public static final String GET_TSL = GET_ALL+"/getTLSdashBoard";
		public static final String GET_TREND = GET_ALL+"/getTrendsdashBoard";
		public static final String GET_TRENDALLSEC = GET_ALL+"/getTrendsGraphValue";
		public static final String GET_BARCHART = GET_ALL+"/getBarChartValue";
		public static final String GET_TOPFIVEQUALITYDEFECT = GET_ALL+"/getTopFiveQualityDefect";
		public static final String GET = GET_ALL + "/{id}";
		public static final String GET_QUALITYBARCHART = GET_ALL+"/getQualityBarChart";
		public static final String GET_QUALITYTRENDALLSEC = GET_ALL+"/getQualityTrendsGraph";
		public static final String GET_TOPDEFECTIVEOPERATION = GET_ALL+"/getTopDefectiveOperation";
		public static final String GET_QCPASSREPORT = GET_ALL+"/getQcPassReport";
		public static final String GET_DEFECTANALYSISREPORT = GET_ALL+"/getDefectAnalysisReport";
		public static final String GET_REPORTSHARE = GET_ALL+"/getReportShare";
		public static final String GET_LINE_WISE_ORDER_INFO = GET_ALL+"/getLineWiseOrderInfo";
	}
	public static class QualityType{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/qualityTypes";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class OperationMachine{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/operationMachine";
		public static final String GET_ALL_OPMC = "/getAllOperationMachine";
		public static final String DELETE_BY_LIST = GET_ALL+"/deleteByIdList";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Production{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/productions";
		public static final String P_BAR_CHART = GET_ALL+"/getProductionBarChart";
		public static final String P_DASHBOARD = GET_ALL+"/ProductionDashBoard";
		public static final String P_HOURLY_GRAPH =GET_ALL+"/getProductionHourlyGraph";
		public static final String GET = GET_ALL + "/{id}";
		public static final String GET_PRODUCTIONREPORT = GET_ALL + "/getProductionReport";
		public static final String GET_PRODUCTION_REPORT_QC_PASS = GET_ALL + "/getProductionReportQcPass";
		public static final String P_DASHBOARD_QC_PASS = GET_ALL+"/ProductionDashBoardQcPass";
		public static final String LINE_WISE_PRODUCTION_CHART = GET_ALL+"/getLineWiseProductionChart";
		public static final String LINE_WISE_HOURLY_PRODUCTION_CHART = GET_ALL+"/getLineWiseHourlyProductionChart";
		public static final String OPERATION_ANALYSIS_COUTER_DEVICE = GET_ALL+"/getOperationAnalysisCd";
		public static final String EMPLOYEE_PERFORMED_PROCESS_LIST = GET_ALL+"/getEmployeePerformedProcessList";
		public static final String EMPLOYEE_PROCESS_WISE_CYCLE_TIME_TREND = GET_ALL+"/getEmployeeProcessWiseCycleTimeTrend";
		public static final String HOURLY_PRODUCTION_AND_DHU_MONITORING_BOARD = GET_ALL+"/getHourlyProductionAndDhuMonitoringBoard";
		public static final String PRODUCTION_DETAILS_REPORT = GET_ALL+"/getProductionDetailsReport";
		public static final String PRODUCTION_DETAILS_GRAPH_REPORT = GET_ALL+"/getProductionDetailsGraphReport";
	}
	
	public static class Varience{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/varience";
		public static final String GET_COLOR_BY_ORDER =GET_ALL+"/coloeListByOrder";
		public static final String GET_Varience_BY_ORDER_AND_COLOR =GET_ALL+"/varienceListByOrderAndColor";
	}
	public static class TargetAndManpower{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/targetAndManpower";
		public static final String GET_All_BETWEEN_TWO_DATE =GET_ALL+"/getTargetAndManpowerByStartDateAndEndDate";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class ReportLayout{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/reportLayout";
		public static final String GET = GET_ALL + "/{id}";
		public static final String GET_BY_TYPE = GET_ALL+"/getByType";
	}
//	public static class Email{
//		public static final String ROOT = API + VERSION;
//		public static final String GET_ALL = "/email";
////		public static final String GET = GET_ALL + "/{id}";
////		public static final String GET_BY_TYPE = GET_ALL+"/getByType";
//	}
	public static class CapacityStudy{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/capacityStudy";
		//public static final String GET = GET_ALL+"/update";
		public static final String GET_CAPACITY_GRAPH_DATA = GET_ALL+"/getGraphData"+"/{capacityId}";
		public static final String GET_CAPACITY_REPORT_DATA = GET_ALL+"/getReportData"+"/{capacityId}";
		public static final String GET_EMPLOYEE_PROFILE = GET_ALL+"/getEmployeeProfileFromCapacityStudy"+"/{employeeId}";
		public static final String GET_OPERATION_ANALYSIS_CS = GET_ALL+"/getOperationAnalysisCS";
		public static final String GET_CS_BY_STATUS_IS_COMPLETE = GET_ALL+"/getAllByStatusIsComplete";
		public static final String GET_CS_BY_STATUS_IS_PARTIAL = GET_ALL+"/getAllByStatusIsPartial";
		public static final String GET_RESUMELIST = GET_ALL+"/getResumeList";
		public static final String GET_OPERATION_ANALYSIS_TREND = GET_ALL+"/getOperatorCycleTimeTrends";
		public static final String WORK_STUDY_CAPACITY_GRAPH = GET_ALL+"/getCapacityStudyGraph";
		public static final String WORK_STUDY_CAPACITY_GRAPH_2 = GET_ALL+"/getCapacityStudyGraph2";
		public static final String GET_INCOMPLETE_CS_LIST = GET_ALL+"/getIncompleteCsList";
		public static final String GET_NEWS_FEED = GET_ALL+"/getNewsFeed";
		public static final String GET_NEWS_FEED_HEADER_INFO = GET_ALL+"/getNewsFeedHeaderInfo";
	}
	public static class Home{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/home";
		public static final String GET_THIS_YEAR_PRODUCTION = GET_ALL+"/organization";
		public static final String GET_THIS_YEAR_PRODUCTION_BAR = GET_ALL+"/productionBar";
		public static final String GET_THIS_YEAR_PRODUCTION_BUYER = GET_ALL+"/productionBuyer";
		public static final String GET_THIS_MONTH_PRODUCTION = GET_ALL+"/thismonthproduction";
		public static final String GET_THIS_MONTH_PROGRESS = GET_ALL+"/thismonthprogress";
		public static final String GET_THIS_MONTH_PRODUCTION_BUYER = GET_ALL+"/thismonthproductionbuyer";
		public static final String GET_THIS_MONTH_EFFICIENCY = GET_ALL+"/thismonthefficiency";
		public static final String GET_THIS_MONTH_DHU = GET_ALL+"/thismonthdhu";
		
	}
	public static class Device{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/device";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Machine{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/machine";
		public static final String GET = GET_ALL + "/{id}";
	}
	public static class Item{
		public static final String ROOT = API + VERSION;
		public static final String GET_ALL = "/items";
		public static final String GET = GET_ALL + "/{id}";
	}


}