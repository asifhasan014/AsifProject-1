package com.softron.masterdata.home.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.home.dto.HomeDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.CalenderCurrentDateTime;
import com.softron.utils.DateTimeUtilConversionService;
import com.softron.utils.DecimalValueConversion;

@Service
public class HomeService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	AdminService adminService;

	String root = "Home";

	public Response getHomeYearProduction(Long orgId, int dataSource) {
		try {

			Date currentDateTime = DateTimeUtilConversionService.getCurrentDateTime("yyyy-MM-dd'T'HH:mm:ss");
			String EndDate = DateTimeUtilConversionService.getStringFromDate(currentDateTime, "yyyy-MM-dd'T'HH:mm:ss");

			String[] splitEndDate = EndDate.split("T");
			String getYearAndDate = splitEndDate[0];
			String[] splitYearAndDate = getYearAndDate.split("-");
			String getOnlyYear = splitYearAndDate[0];

			String startDateAndTimeString = getOnlyYear + "-01-01T08:30:00";

			// System.out.println("start Date : " + startDateAndTimeString);

			Date startDateAndTime = DateTimeUtilConversionService.getDateFromString(startDateAndTimeString,
					"yyyy-MM-dd'T'HH:mm:ss");

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

			HomeDto homeDto = new HomeDto();

			String pv = "@pv\\:=";
			if (dataSource == 1) {
				String query = "SELECT SUM(quality_transaction.sample_size) FROM quality_transaction \r\n"
						+ " WHERE created_at BETWEEN :startDateAndTime AND :currentDateTime\r\n"
						+ "AND check_output='ok'  \r\n" + "AND quality_transaction.org_id IN (" + organization + ")";

//				System.out.println("SQL : "+query);
//				System.out.println(startDateAndTimeString);
//				System.out.println(EndDate);

				Query q = entityManager.createNativeQuery(query);
				// q.setParameter("orgId", orgId);
				q.setParameter("startDateAndTime", startDateAndTime);
				q.setParameter("currentDateTime", currentDateTime);

//				List<Object[]> objects = q.getResultList();

				Object obj = q.getResultList();

				homeDto.setYearProduction(obj);
				homeDto.setOrganizationList(orgNames);
//				for (Object[] obj : objects) {
//					//String yearProduction= obj[0] != null ? obj[0].toString() : "";
//					homeDto.setYearProduction( obj[0] != null ?  obj[0] : 0.0 );
//					
//				}
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDto, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getHomeYearProductionBar(Long orgId, int dataSource) {
		try {

			Date currentDateTime = DateTimeUtilConversionService.getCurrentDateTime("yyyy-MM-dd'T'HH:mm:ss");
			String EndDate = DateTimeUtilConversionService.getStringFromDate(currentDateTime, "yyyy-MM-dd'T'HH:mm:ss");

//			String EndDate = DateTimeUtilConversionService.getStringFromDate(currentDateTime, "yyyy-MM-dd HH:mm:ss");

			String[] splitEndDate = EndDate.split("T");
			String getYearAndDate = splitEndDate[0];
			String[] splitYearAndDate = getYearAndDate.split("-");
			String getOnlyYear = splitYearAndDate[0];

			String startDateAndTimeString = getOnlyYear + "-01-01T08:30:00";

			Date startDateAndTime = DateTimeUtilConversionService.getDateFromString(startDateAndTimeString,
					"yyyy-MM-dd'T'HH:mm:ss");

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

			List<HomeDto> homeDtoList = new ArrayList<HomeDto>();

			String pv = "@pv\\:=";
			if (dataSource == 1) {

				String query = "SELECT date_format( quality_transaction.created_at, '%Y-%m' )\r\n" + "\r\n"
						+ "AS month,date_format( quality_transaction.created_at, '%b' ) AS month_name, SUM(quality_transaction.sample_size) AS production\r\n"
						+ "\r\n" + "FROM quality_transaction\r\n" + "\r\n"
						+ "WHERE created_at BETWEEN :startDateAndTime AND :currentDateTime\r\n" + "\r\n"
						+ "AND check_output='ok'\r\n" + "\r\n" + "AND quality_transaction.org_id IN (" + organization
						+ ")\r\n" + "\r\n" + "group by month order by month";

				System.out.println("SQL query : " + query);

				Query q = entityManager.createNativeQuery(query);
				// q.setParameter("orgId", orgId);
				q.setParameter("startDateAndTime", startDateAndTime);
				q.setParameter("currentDateTime", currentDateTime);

//				q.setParameter("startDateAndTimeString", startDateAndTimeString);
//				q.setParameter("EndDate", EndDate);

				System.out.println();

				List<Object[]> objects = q.getResultList();

				for (Object[] obj : objects) {
					HomeDto homeDto = new HomeDto();
					homeDto.setMonthDate(obj[1] != null ? obj[1].toString() : "");
					homeDto.setYearProductionBar(obj[2] != null ? obj[2].toString() : "");
					homeDto.setYearProduction(null);
					homeDto.setOrganizationList(orgNames);
					homeDtoList.add(homeDto);
				}

			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDtoList, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getHomeYearProductionBuyer(Long orgId, int dataSource) {
		try {

			Date currentDateTime = DateTimeUtilConversionService.getCurrentDateTime("yyyy-MM-dd'T'HH:mm:ss");
			String EndDate = DateTimeUtilConversionService.getStringFromDate(currentDateTime, "yyyy-MM-dd'T'HH:mm:ss");

//			System.out.println("EndDate ==========" + EndDate);

			String[] splitEndDate = EndDate.split("T");
			String getYearAndDate = splitEndDate[0];
			String[] splitYearAndDate = getYearAndDate.split("-");
			String getOnlyYear = splitYearAndDate[0];

			String startDateAndTimeString = getOnlyYear + "-01-01T08:30:00";

			System.out.println("start Date : " + startDateAndTimeString);

			Date startDateAndTime = DateTimeUtilConversionService.getDateFromString(startDateAndTimeString,
					"yyyy-MM-dd'T'HH:mm:ss");

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

			List<HomeDto> homeDtoList = new ArrayList<HomeDto>();

			String pv = "@pv\\:=";
			if (dataSource == 1) {
				String query = "SELECT SUM(quality_transaction.sample_size) as customerSamplesizeSum,customer.name as buyerName\r\n"
						+ "FROM quality_transaction,order_entity,customer\r\n"
						+ "WHERE quality_transaction.created_at BETWEEN :startDateAndTime AND :currentDateTime\r\n"
						+ "AND check_output='ok'  AND order_entity.id=quality_transaction.orderentity_id\r\n"
						+ "AND customer.id=order_entity.customer_id\r\n" + "AND quality_transaction.org_id IN ("
						+ organization + ") GROUP BY customer.id";

				Query q = entityManager.createNativeQuery(query);
//				q.setParameter("orgId", orgId);
				q.setParameter("startDateAndTime", startDateAndTime);
				q.setParameter("currentDateTime", currentDateTime);

				List<Object[]> objects = q.getResultList();

				for (Object[] obj : objects) {

					HomeDto homeDto = new HomeDto();
					homeDto.setCustomerName((obj[1] != null ? obj[1].toString() : ""));
					homeDto.setThisyearCustomerProduction((obj[0] != null ? obj[0].toString() : "0.0"));
					homeDto.setOrganizationList(orgNames);

					homeDtoList.add(homeDto);
				}
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDtoList, homeDtoList.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getHomeMonthProduction(Long orgId, int dataSource, String startDateString, String endDateString) {
		try {

//			Date currentDateTime = DateTimeUtilConversionService.getCurrentDateTime("yyyy-MM-dd'T'HH:mm:ss");
//			String EndDate = DateTimeUtilConversionService.getStringFromDate(currentDateTime, "yyyy-MM-dd'T'HH:mm:ss");
//
//			System.out.println("EndDate ==========" + EndDate);
//
//			String[] splitEndDate = EndDate.split("T");
//			String getYearAndDate = splitEndDate[0];
//			String[] splitYearAndDate = getYearAndDate.split("-");
//			String getOnlyYear = splitYearAndDate[0];
//
//			String startDateAndTimeString = getOnlyYear + "-01-01T08:30:00";
//
//			System.out.println("start Date : " + startDateAndTimeString);
//
//			Date startDateAndTime = DateTimeUtilConversionService.getDateFromString(startDateAndTimeString,
//					"yyyy-MM-dd'T'HH:mm:ss");

			Date sartDate = DateTimeUtilConversionService.getDateFromString(startDateString, "yyyy-MM-dd'T'HH:mm:ss");
			Date endDate = DateTimeUtilConversionService.getDateFromString(endDateString, "yyyy-MM-dd'T'HH:mm:ss");

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

			HomeDto homeDto = new HomeDto();

			if (dataSource == 1) {
				String query = "SELECT SUM(quality_transaction.sample_size) FROM quality_transaction \r\n"
						+ "WHERE created_at BETWEEN :sartDate AND :endDate  AND check_output='ok'\r\n"
						+ "AND quality_transaction.org_id IN (" + organization + ")";

				Query q = entityManager.createNativeQuery(query);
//				q.setParameter("orgId", orgId);
				q.setParameter("sartDate", sartDate);
				q.setParameter("endDate", endDate);

				List<Object> objects = q.getResultList();

//				Object obj = q.getResultList();

				for (Object obj : objects) {
					HomeDto homedto = new HomeDto();
					homeDto.setThismonthProduction(obj != null ? obj.toString() : "0.0");
					homeDto.setOrganizationList(orgNames);

				}
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDto, String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getHomeMonthProgress(Long orgId, int dataSource, String startDateString, String endDateString) {
		try {

			Date sartDate = DateTimeUtilConversionService.getDateFromString(startDateString, "yyyy-MM-dd'T'HH:mm:ss");
			Date endDate = DateTimeUtilConversionService.getDateFromString(endDateString, "yyyy-MM-dd'T'HH:mm:ss");

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

			List<HomeDto> homeDtoList = new ArrayList<HomeDto>();

			if (dataSource == 1) {

				String query = "SELECT date_format( quality_transaction.created_at, '%M-%d' ) AS day,SUM(quality_transaction.sample_size) FROM quality_transaction \r\n"
						+ "WHERE created_at BETWEEN :sartDate AND :endDate  AND check_output='ok'\r\n"
						+ "AND quality_transaction.org_id IN (" + organization + ")\r\n"
						+ "group by day order by day";

				Query q = entityManager.createNativeQuery(query);
//				q.setParameter("orgId", orgId);
				q.setParameter("sartDate", sartDate);
				q.setParameter("endDate", endDate);

				List<Object[]> objects = q.getResultList();

//				Object obj = q.getResultList();

				for (Object[] obj : objects) {
					HomeDto homedto = new HomeDto();
					homedto.setMonthDate(obj[0] != null ? obj[0].toString() : "");
					homedto.setDayProduction(obj[1] != null ? obj[1].toString() : "0.0");
					homedto.setOrganizationList(orgNames);

					homeDtoList.add(homedto);
				}
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDtoList, homeDtoList.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getHomeMonthProductionBuyer(Long orgId, int dataSource, String startDateString,
			String endDateString) {
		try {

			Date sartDate = DateTimeUtilConversionService.getDateFromString(startDateString, "yyyy-MM-dd'T'HH:mm:ss");
			Date endDate = DateTimeUtilConversionService.getDateFromString(endDateString, "yyyy-MM-dd'T'HH:mm:ss");

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

			List<HomeDto> homeDtoList = new ArrayList<HomeDto>();

			String pv = "@pv\\:=";
			if (dataSource == 1) {
				String query = "SELECT customer.name as buyerName,SUM(quality_transaction.sample_size)\r\n"
						+ " FROM quality_transaction,order_entity,customer\r\n"
						+ " WHERE quality_transaction.created_at BETWEEN :sartDate AND :endDate\r\n"
						+ " AND check_output='ok'  \r\n" + " AND order_entity.id=quality_transaction.orderentity_id\r\n"
						+ " AND customer.id=order_entity.customer_id\r\n" + "AND quality_transaction.org_id IN ("
						+ organization + ")\r\n" + " GROUP BY customer.id";

				Query q = entityManager.createNativeQuery(query);
//				q.setParameter("orgId", orgId);
				q.setParameter("sartDate", sartDate);
				q.setParameter("endDate", endDate);

				List<Object[]> objects = q.getResultList();

//				Object obj = q.getResultList();

				for (Object[] obj : objects) {
					HomeDto homedto = new HomeDto();
					homedto.setCustomerName(obj[0] != null ? obj[0].toString() : "");
					homedto.setThismonthProduction(obj[1] != null ? obj[1].toString() : "0.0");
					homedto.setOrganizationList(orgNames);

					homeDtoList.add(homedto);
				}
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDtoList, homeDtoList.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getMonthEfficiency(Long orgId, int dataSource, String startDateString, String endDateString) {
		try {

			Date sartDate = DateTimeUtilConversionService.getDateFromString(startDateString, "yyyy-MM-dd'T'HH:mm:ss");
			Date endDate = DateTimeUtilConversionService.getDateFromString(endDateString, "yyyy-MM-dd'T'HH:mm:ss");

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

			List<HomeDto> homeDtoList = new ArrayList<HomeDto>();

			if (dataSource == 1) {
//				String query = "select sum(product_minute.hr_produce_minute) AS produce_minute,sum(manpower.mp*60) AS available_minute\r\n" + 
//						"\r\n" + 
//						"FROM\r\n" + 
//						"\r\n" + 
//						"(\r\n" + 
//						"\r\n" + 
//						"SELECT production_table.dt,production_table.hr,production_table.org_id, sum(production_table.style_production*smv_table.smv) AS hr_produce_minute\r\n" + 
//						"\r\n" + 
//						"FROM\r\n" + 
//						"\r\n" + 
//						"(\r\n" + 
//						"\r\n" + 
//						"SELECT date_format(created_at, '%Y-%m-%d' ) AS dt ,date_format(created_at, '%Y-%m-%d-%h' ) AS hr, org_id, style_id,SUM(sample_size) AS style_production\r\n" + 
//						"\r\n" + 
//						"FROM quality_transaction\r\n" + 
//						"\r\n" + 
//						"WHERE  created_at BETWEEN :sartDate AND :endDate\r\n" + 
//						"\r\n" + 
//						"AND check_output='ok'\r\n" + 
//						"\r\n" + 
//						"AND org_id IN ("+organization+")\r\n" + 
//						"\r\n" + 
//						"GROUP BY hr,org_id,style_id\r\n" + 
//						"\r\n" + 
//						") AS production_table\r\n" + 
//						"\r\n" + 
//						"LEFT JOIN\r\n" + 
//						"\r\n" + 
//						"(\r\n" + 
//						"\r\n" + 
//						"SELECT style_id, SUM(smv) AS smv\r\n" + 
//						"\r\n" + 
//						"FROM operation_break_down\r\n" + 
//						"\r\n" + 
//						") AS smv_table\r\n" + 
//						"\r\n" + 
//						"ON (smv_table.style_id=production_table.style_id)\r\n" + 
//						"\r\n" + 
//						"GROUP BY hr\r\n" + 
//						"\r\n" + 
//						") as product_minute\r\n" + 
//						"\r\n" + 
//						"LEFT JOIN\r\n" + 
//						"\r\n" + 
//						"(\r\n" + 
//						"\r\n" + 
//						"SELECT date_format(target_and_manpower.date, '%Y-%m-%d' ) AS dt, org_id, SUM((target_and_manpower.number_of_helper+target_and_manpower.number_of_operator)) AS mp\r\n" + 
//						"\r\n" + 
//						"FROM target_and_manpower\r\n" + 
//						"\r\n" + 
//						"GROUP BY dt,org_id\r\n" + 
//						"\r\n" + 
//						") as manpower\r\n" + 
//						"\r\n" + 
//						"ON(product_minute.dt=manpower.dt AND product_minute.org_id=manpower.org_id)";

				String query = "select sum(product_minute.hr_produce_minute) AS produce_minute,sum(manpower.mp*60) AS available_minute\r\n"
						+ "FROM\r\n" + "(	\r\n"
						+ "	SELECT production_table.dt,production_table.hr,production_table.org_id, \r\n"
						+ "	sum(production_table.style_production*smv_table.smv) AS hr_produce_minute\r\n"
						+ "	FROM\r\n" + "	(\r\n"
						+ "		SELECT date_format(quality_transaction.created_at, '%Y-%m-%d' ) AS dt ,date_format(quality_transaction.created_at, '%Y-%m-%d-%h' ) AS hr, \r\n"
						+ "		quality_transaction.org_id, order_entity.style_id,SUM(quality_transaction.sample_size) AS style_production\r\n"
						+ "		FROM quality_transaction,order_entity\r\n"
						+ "		WHERE  quality_transaction.created_at BETWEEN :sartDate AND :endDate\r\n"
						+ "		AND quality_transaction.orderentity_id=order_entity.id\r\n"
						+ "		AND quality_transaction.check_output='ok'\r\n"
						+ "		AND quality_transaction.org_id IN (" + organization + ")\r\n" + "		\r\n"
						+ "		GROUP BY hr,org_id,style_id\r\n" + "		\r\n" + "	) AS production_table\r\n"
						+ "	LEFT JOIN\r\n" + "	(\r\n" + "		SELECT style_id, SUM(smv) AS smv\r\n"
						+ "		FROM operation_break_down\r\n" + "	) AS smv_table\r\n"
						+ "	ON (smv_table.style_id=production_table.style_id)\r\n" + "	GROUP BY hr\r\n"
						+ ") as product_minute\r\n" + "LEFT JOIN\r\n" + "(\r\n"
						+ "	SELECT date_format(target_and_manpower.date, '%Y-%m-%d' ) AS dt, org_id, SUM((target_and_manpower.number_of_helper+target_and_manpower.number_of_operator)) AS mp\r\n"
						+ "	FROM target_and_manpower\r\n" + "	GROUP BY dt,org_id\r\n" + ") as manpower\r\n"
						+ "ON(product_minute.dt=manpower.dt AND product_minute.org_id=manpower.org_id)";

				Query q = entityManager.createNativeQuery(query);

				q.setParameter("sartDate", sartDate);
				q.setParameter("endDate", endDate);
				
				System.out.println(query);

				List<Object[]> objects = q.getResultList();

				for (Object[] obj : objects) {
					HomeDto homedto = new HomeDto();

					String produceMinuteString = obj[0] != null ? obj[0].toString() : "0";
					double produceMinute = DecimalValueConversion.getTwoValueAfterDot(produceMinuteString);
//					long produceMinute = Long.parseLong(produceMinuteString);

					String avaiableMinuteString = obj[1] != null ? obj[1].toString() : "0";
					double avaiableMinute = DecimalValueConversion.getTwoValueAfterDot(avaiableMinuteString);

					if (avaiableMinute > 0) {
						
						homedto.setEfficiency("0");
						double efficiency = DecimalValueConversion
								.getTwoValueAfterDot(String.valueOf(produceMinute / avaiableMinute));

						homedto.setEfficiency(String.valueOf(efficiency));

					} else {
						homedto.setEfficiency("0");
					}

					homedto.setOrganizationList(orgNames);
					homeDtoList.add(homedto);
				}
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDtoList, homeDtoList.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getMonthDhu(Long orgId, int dataSource, String startDateString, String endDateString) {
		try {

			Date sartDate = DateTimeUtilConversionService.getDateFromString(startDateString, "yyyy-MM-dd'T'HH:mm:ss");
			Date endDate = DateTimeUtilConversionService.getDateFromString(endDateString, "yyyy-MM-dd'T'HH:mm:ss");

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

			List<HomeDto> homeDtoList = new ArrayList<HomeDto>();

			String pv = "@pv\\:=";
			if (dataSource == 1) {
//				String query = "SELECT round(100*df/tc,2) AS DHU\r\n" + 
//						"\r\n" + 
//						"FROM\r\n" + 
//						"\r\n" + 
//						"(\r\n" + 
//						"\r\n" + 
//						"SELECT 1 as sr, COUNT(*) AS df\r\n" + 
//						"\r\n" + 
//						"FROM quality_transaction,quality_type,quality_defect\r\n" + 
//						"\r\n" + 
//						"WHERE quality_transaction.qualitytype_id=quality_type.id\r\n" + 
//						"\r\n" + 
//						"AND quality_type.type=1\r\n" + 
//						"\r\n" + 
//						"AND quality_transaction.created_at BETWEEN :sartDate AND :endDate\r\n" + 
//						"\r\n" + 
//						"AND quality_defect.qualitytransaction_id= quality_transaction.id\r\n" + 
//						"\r\n" + 
//						"AND quality_transaction.org_id IN ("+organization+"\r\n" + 
//						
//						"\r\n" + 
//						")\r\n" + 
//						"\r\n" + 
//						") AS defect\r\n" + 
//						"\r\n" + 
//						"LEFT JOIN\r\n" + 
//						"\r\n" + 
//						"(\r\n" + 
//						"\r\n" + 
//						"SELECT 1 as sr, SUM(quality_transaction.sample_size) AS tc\r\n" + 
//						"\r\n" + 
//						"FROM quality_transaction,quality_type\r\n" + 
//						"\r\n" + 
//						"WHERE quality_transaction.qualitytype_id=quality_type.id\r\n" + 
//						"\r\n" + 
//						"AND quality_type.type=1\r\n" + 
//						"\r\n" + 
//						"AND quality_transaction.created_at BETWEEN :sartDate AND :endDate\r\n" + 
//						"\r\n" + 
//						"AND quality_transaction.org_id IN ("+organization+"\r\n" + 
//						"\r\n" + 
//						")\r\n" + 
//						"\r\n" + 
//						") AS toatl_check\r\n" + 
//						"\r\n" + 
//						"ON (toatl_check.sr=defect.sr)";

				String query = "SELECT round(100*df/tc,2) AS DHU\r\n" + "FROM\r\n" + "(\r\n"
						+ "	SELECT 1 as sr, COUNT(*) AS df\r\n"
						+ "	FROM quality_transaction,quality_type,quality_defect\r\n"
						+ "	WHERE quality_transaction.qualitytype_id=quality_type.id\r\n"
						+ "	AND quality_type.type=1\r\n"
						+ "	AND quality_transaction.created_at BETWEEN :sartDate AND :endDate\r\n"
						+ "	AND quality_defect.qualitytransaction_id= quality_transaction.id\r\n"
						+ "	AND quality_transaction.org_id IN (" + organization + ")\r\n" + ") AS defect\r\n"
						+ "LEFT JOIN\r\n" + "(\r\n" + "	SELECT 1 as sr, SUM(quality_transaction.sample_size) AS tc\r\n"
						+ "	FROM quality_transaction,quality_type\r\n"
						+ "	WHERE quality_transaction.qualitytype_id=quality_type.id\r\n"
						+ "	AND quality_type.type=1\r\n"
						+ "	AND quality_transaction.created_at BETWEEN :sartDate AND :endDate \r\n"
						+ "	AND quality_transaction.org_id IN (" + organization + ")\r\n" + ") AS toatl_check\r\n"
						+ "ON (toatl_check.sr=defect.sr)\r\n" + "";

				Query q = entityManager.createNativeQuery(query);
//				q.setParameter("orgId", orgId);
				q.setParameter("sartDate", sartDate);
				q.setParameter("endDate", endDate);

				List<Object> objects = q.getResultList();

//				Object obj = q.getResultList();

				for (Object obj : objects) {
					HomeDto homedto = new HomeDto();

					homedto.setDhu(obj != null ? obj.toString() : "0");
					homedto.setOrganizationList(orgNames);
					homeDtoList.add(homedto);
				}
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, homeDtoList, homeDtoList.size(),
					String.format("All %ses DHU", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

}
