package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.dto.OrderListDto;
import com.softron.masterdata.parameter_selection_pannel.dto.OrderWiseStyleDto;
import com.softron.masterdata.parameter_selection_pannel.dto.StyleWiseCustomerDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.CustomerRepository;
import com.softron.masterdata.repository.OrderEntityRepository;
import com.softron.masterdata.repository.StyleRepository;
import com.softron.production.dto.OrderSummeryDto;
import com.softron.quality.dto.VarienceDto;
import com.softron.quality.repository.VarienceRepository;
import com.softron.quality.service.VarienceService;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.qualitymodule.entity.Varience;

@Service
public class OrderEntityService extends Sentez {
	@Autowired
	OrderEntityRepository orderEntityRepository;

	@Autowired
	ModelMapper modelMapper;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	VarienceService varienceService;

	@Autowired
	StyleRepository styleRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	VarienceRepository varienceRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	AdminService adminService;

	String root = "OrderEntity";

	public Response create(OrderEntityDto orderEntityDto, Long orgId) {
		try {

//			OrderEntity orderEntity = modelMapper.map(orderEntityDto, OrderEntity.class);
			OrderEntity orderEntity = new OrderEntity();
			BeanUtils.copyProperties(orderEntityDto, orderEntity);

			if (orderEntityDto.getStyle() != null) {
				Optional<Style> style = styleRepository.findById(orderEntityDto.getStyle().getId());
				orderEntity.setStyle(style.get());
			}
			if (orderEntityDto.getCustomer() != null) {
				Optional<Customer> customer = customerRepository.findById(orderEntityDto.getCustomer().getId());
				orderEntity.setCustomer(customer.get());
			}

			if (orderEntityDto.getVarience() != null) {
				for (VarienceDto varienceEntityDto : orderEntityDto.getVarience()) {
					Varience varienceEntity = new Varience();
					BeanUtils.copyProperties(varienceEntityDto, varienceEntity);
					varienceEntity.setOrderEntity(orderEntity);

				}
			}

			Optional<Organization> organaization = organizationRepository.findById(orgId);
			orderEntity.setOrganization(organaization.get());

			orderEntityRepository.save(orderEntity);

			BeanUtils.copyProperties(orderEntity, orderEntityDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, orderEntityDto,
					String.format("%s created successfully", root));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll(Long orgId, String type) {
		try {
//			Organization organization = adminService.getOrgAsCompany(orgId);
//
//			List<Organization> orgList = new ArrayList<Organization>();
//			List<Organization> organizationDetail = organizationRepository.findAllById(organization.getId());
//			orgList.addAll(organizationDetail);
//
//			adminService.getOrganizationList(organization.getId(), orgList);
//
//			List<String> orgNames = new ArrayList<String>();
//			List<Long> orgIds = new ArrayList<Long>();
//
//			for (Organization org : orgList) {
//				orgIds.add(org.getId());
//				orgNames.add(org.getName());
//			}

			List<OrderEntity> orderEntity = getAllOrderEntity(orgId);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(orderEntity, type),
					orderEntity.size(), String.format("All %ses", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<OrderEntity> getAllOrderEntity(Long orgId) {

		List<OrderEntity> orderEntity = new ArrayList<OrderEntity>();
		try {
			Organization organization = adminService.getOrgAsCompany(orgId);

			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(organization.getId());
			orgList.addAll(organizationDetail);

			adminService.getOrganizationList(organization.getId(), orgList);

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			orderEntity = orderEntityRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);

		} catch (Exception e) {
			System.out.println(e);
		}
		return orderEntity;
	}

	public Response get(Long id) {
		try {
			OrderEntity orderEntity = orderEntityRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			OrderEntityDto orderEntityDto = new OrderEntityDto();
			modelMapper.map(orderEntity, orderEntityDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, orderEntityDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getOrderByCustomer(Long customerId) {
		try {

			List<OrderEntity> orderList = orderEntityRepository.findAllByCustomerOrderByStyle(customerId);
			List<OrderEntityDto> orderDtoList = new ArrayList<>();

			for (OrderEntity orderEntityEachItem : orderList) {
				OrderEntityDto orderEntityDto = new OrderEntityDto();
				modelMapper.map(orderEntityEachItem, orderEntityDto);
				orderDtoList.add(orderEntityDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, orderDtoList,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getOrderList(Long orgId) {
		try {
			OrderListDto orderListDto = new OrderListDto();

			Organization organization = adminService.getOrgAsCompany(orgId);

			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(organization.getId());
			orgList.addAll(organizationDetail);

			adminService.getOrganizationList(organization.getId(), orgList);

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			List<OrderEntity> orderEntityList = orderEntityRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);

			for (OrderEntity orderEntity : orderEntityList) {
				orderListDto.setOrderId(orderEntity.getId());
				orderListDto.setOrderName(orderEntity.getName());
				// List<OrderSummeryDto> orderSummeryDtoList=(List<OrderSummeryDto>)
				// getOrderSummery(orderEntity.getId().toString());
				orderListDto.setOrderSummeryDto(orderSummeryGetByOrderId(orderEntity.getId().toString()));
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, orderListDto,
					String.format("Order List With Summery retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getOrderSummery(String orderIds) {
		try {
			List<OrderSummeryDto> orderSummeryDtoList = orderSummeryGetByOrderId(orderIds);

			// List<Object[]> orderSummeryList =
			// orderEntityRepository.findAllOrderSummery(orderIds);
//			String query ="SELECT\r\n" + 
//					"    table1.orderId,\r\n" + 
//					"    table1.STATUS,\r\n" + 
//					"    table1.orderName,\r\n" + 
//					"    table1.buyer,\r\n" + 
//					"    table1.styleName,\r\n" + 
//					"    table1.quantity,\r\n" + 
//					"    table1.smv,\r\n" + 
//					"    table2.Production_Start_Date,\r\n" + 
//					"    table3.toatalProduction,\r\n" + 
//					"    table4.totalAlter,\r\n" + 
//					"    table5.totalReject,\r\n" + 
//					"    FORMAT(\r\n" + 
//					"        (\r\n" + 
//					"            (100 * table4.totalAlter) / NULLIF(table2.totalcheck,0)\r\n" + 
//					"        ),\r\n" + 
//					"        2\r\n" + 
//					"    ) AS alterPercentage,\r\n" + 
//					"    FORMAT(\r\n" + 
//					"        (\r\n" + 
//					"            (100 * table5.totalReject) / NULLIF(table2.totalcheck,0)\r\n" + 
//					"        ),\r\n" + 
//					"        2\r\n" + 
//					"    ) AS rejectPercentage,\r\n" + 
//					"    table6.defect,\r\n" + 
//					"    FORMAT(((100*table6.defect)/table2.totalcheck),2) as DHU,\r\n" + 
//					"    FORMAT(((table3.toatalProduction*table1.smv*100)/(60*1*NULLIF(table1.totalManpower,0))),2) as efficiency,\r\n" + 
//					"    table7.lineInvolve,\r\n" + 
//					"    table8.numerOfOperation,\r\n" + 
//					"    table9.avg_Hr_Target,\r\n" + 
//					"    table9.avgMp,\r\n" + 
//					"    table10.last_Day_Production,\r\n" + 
//					"    table11.Current_Day_Production,\r\n" + 
//					"    table12.last_Hr_Production,\r\n" + 
//					"    table12.Running_Line,\r\n" + 
//					"    table14.avgHourProduction,\r\n" + 
//					"    table13.Current_Hr_Production,\r\n" + 
//					"    (table14.avgHourProduction/NULLIF(table9.avg_Hr_Target,0)) as avgAchivement\r\n" + 
//					"FROM\r\n" + 
//					"    (\r\n" + 
//					"    SELECT\r\n" + 
//					"        order_entity.id AS orderId,\r\n" + 
//					"        order_entity.status AS\r\n" + 
//					"    STATUS\r\n" + 
//					"        ,\r\n" + 
//					"        order_entity.name AS orderName,\r\n" + 
//					"        customer.name AS buyer,\r\n" + 
//					"        style.name AS styleName,\r\n" + 
//					"        order_entity.quantity AS quantity,\r\n" + 
//					"        style.smv AS smv,\r\n" + 
//					"        SUM(target_and_manpower.number_of_helper+target_and_manpower.number_of_operator) as totalManpower\r\n" + 
//					"    FROM\r\n" + 
//					"        order_entity,\r\n" + 
//					"        customer,\r\n" + 
//					"        style,\r\n" + 
//					"        target_and_manpower\r\n" + 
//					"    WHERE\r\n" + 
//					"        order_entity.id IN("+orderIds+") AND order_entity.active = 1 AND order_entity.customer_id = customer.id AND customer.active = 1 AND order_entity.style_id = style.id AND style.active = 1\r\n" + 
//					"AND target_and_manpower.org_id = order_entity.org_id\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        order_entity.org_id\r\n" + 
//					") AS table1\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        SUM(\r\n" + 
//					"            quality_transaction.sample_size\r\n" + 
//					"        ) AS totalcheck,\r\n" + 
//					"        quality_transaction.id AS qualityId,\r\n" + 
//					"        quality_transaction.created_at AS Production_Start_Date\r\n" + 
//					"    FROM\r\n" + 
//					"        quality_transaction\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.active = 1 AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id\r\n" + 
//					"    ORDER BY\r\n" + 
//					"        quality_transaction.created_at ASC\r\n" + 
//					") AS table2\r\n" + 
//					"ON\r\n" + 
//					"    table1.orderId = table2.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        SUM(\r\n" + 
//					"            quality_transaction.sample_size\r\n" + 
//					"        ) AS toatalProduction\r\n" + 
//					"    FROM\r\n" + 
//					"        quality_transaction\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
//					"    AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id\r\n" + 
//					") AS table3\r\n" + 
//					"ON\r\n" + 
//					"    table2.orderId = table3.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        SUM(\r\n" + 
//					"            quality_transaction.sample_size\r\n" + 
//					"        ) AS totalAlter\r\n" + 
//					"    FROM\r\n" + 
//					"        quality_transaction\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.active = 1 AND quality_transaction.check_output = 'alter' \r\n" + 
//					"    AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id\r\n" + 
//					") AS table4\r\n" + 
//					"ON\r\n" + 
//					"    table3.orderId = table4.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        SUM(\r\n" + 
//					"            quality_transaction.sample_size\r\n" + 
//					"        ) AS totalReject\r\n" + 
//					"    FROM\r\n" + 
//					"        quality_transaction\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.active = 1 AND quality_transaction.check_output = 'reject' \r\n" + 
//					"    AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id\r\n" + 
//					") AS table5\r\n" + 
//					"ON\r\n" + 
//					"    table5.orderId = table4.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        COUNT(quality_defect.id) AS defect\r\n" + 
//					"    FROM\r\n" + 
//					"        quality_transaction,\r\n" + 
//					"        quality_defect\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.orderentity_id IN("+orderIds+") AND quality_defect.qualitytransaction_id = quality_transaction.id AND quality_transaction.active = 1\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id\r\n" + 
//					") AS table6\r\n" + 
//					"ON\r\n" + 
//					"    table5.orderId = table6.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        COUNT(\r\n" + 
//					"            DISTINCT quality_transaction.org_id\r\n" + 
//					"        ) AS lineInvolve\r\n" + 
//					"    FROM\r\n" + 
//					"        quality_transaction\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id\r\n" + 
//					") AS table7\r\n" + 
//					"ON\r\n" + 
//					"    table6.orderId = table7.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        order_entity.id AS orderId,\r\n" + 
//					"        COUNT(operation.id) AS numerOfOperation\r\n" + 
//					"    FROM\r\n" + 
//					"        order_entity,\r\n" + 
//					"        style,\r\n" + 
//					"        operation_break_down,\r\n" + 
//					"        operation\r\n" + 
//					"    WHERE\r\n" + 
//					"        order_entity.id IN("+orderIds+") AND order_entity.active = 1 AND order_entity.style_id = style.id AND style.active = 1 AND style.id = operation_break_down.style_id AND operation_break_down.active = 1 AND operation_break_down.operation_id = operation.id AND operation.active = 1\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        order_entity.id\r\n" + 
//					") AS table8\r\n" + 
//					"ON\r\n" + 
//					"    table7.orderId = table8.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        FORMAT(\r\n" + 
//					"            AVG(\r\n" + 
//					"                target_and_manpower.productiontarget\r\n" + 
//					"            ),\r\n" + 
//					"            2\r\n" + 
//					"        ) AS avg_Hr_Target,\r\n" + 
//					"        FORMAT(\r\n" + 
//					"            AVG(\r\n" + 
//					"                target_and_manpower.number_of_helper + target_and_manpower.number_of_operator\r\n" + 
//					"            ),\r\n" + 
//					"            2\r\n" + 
//					"        ) AS avgMp\r\n" + 
//					"    FROM\r\n" + 
//					"        `quality_transaction`,\r\n" + 
//					"        target_and_manpower\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.orderentity_id IN("+orderIds+") AND quality_transaction.org_id = target_and_manpower.org_id\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id\r\n" + 
//					") AS table9\r\n" + 
//					"ON\r\n" + 
//					"    table8.orderId = table9.orderId\r\n" + 
//					"LEFT JOIN(\r\n" + 
//					"    SELECT\r\n" + 
//					"        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"        SUM(\r\n" + 
//					"            quality_transaction.sample_size\r\n" + 
//					"        ) AS last_Day_Production\r\n" + 
//					"    FROM\r\n" + 
//					"        quality_transaction\r\n" + 
//					"    WHERE\r\n" + 
//					"        quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
//					"    AND quality_transaction.created_at BETWEEN CURDATE() - INTERVAL 1 DAY AND CURDATE() AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"    GROUP BY\r\n" + 
//					"        quality_transaction.orderentity_id) AS table10\r\n" + 
//					"    ON\r\n" + 
//					"        table9.orderId = table10.orderId\r\n" + 
//					"    LEFT JOIN(\r\n" + 
//					"        SELECT\r\n" + 
//					"            quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"            SUM(\r\n" + 
//					"                quality_transaction.sample_size\r\n" + 
//					"            ) AS Current_Day_Production\r\n" + 
//					"        FROM\r\n" + 
//					"            quality_transaction\r\n" + 
//					"        WHERE\r\n" + 
//					"            quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
//					"        AND DATE(quality_transaction.created_at) = CURDATE() AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"        GROUP BY\r\n" + 
//					"            quality_transaction.orderentity_id) AS table11\r\n" + 
//					"        ON\r\n" + 
//					"            table10.orderId = table11.orderId\r\n" + 
//					"        LEFT JOIN(\r\n" + 
//					"            SELECT\r\n" + 
//					"                quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"                SUM(\r\n" + 
//					"                    quality_transaction.sample_size\r\n" + 
//					"                ) AS last_Hr_Production,\r\n" + 
//					"                COUNT(quality_transaction.org_id) AS Running_Line\r\n" + 
//					"            FROM\r\n" + 
//					"                quality_transaction\r\n" + 
//					"            WHERE\r\n" + 
//					"                quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
//					"            AND quality_transaction.created_at BETWEEN(NOW() - INTERVAL 1 HOUR) AND NOW() AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"            GROUP BY\r\n" + 
//					"                quality_transaction.orderentity_id) AS table12\r\n" + 
//					"            ON\r\n" + 
//					"                table11.orderId = table12.orderId\r\n" + 
//					"            LEFT JOIN(\r\n" + 
//					"                SELECT\r\n" + 
//					"                    quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"                    SUM(\r\n" + 
//					"                        quality_transaction.sample_size\r\n" + 
//					"                    ) AS Current_Hr_Production\r\n" + 
//					"                FROM\r\n" + 
//					"                    quality_transaction\r\n" + 
//					"                WHERE\r\n" + 
//					"                    quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n" + 
//					"                BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') AND quality_transaction.orderentity_id IN("+orderIds+")\r\n" + 
//					"                GROUP BY\r\n" + 
//					"                    quality_transaction.orderentity_id) AS table13\r\n" + 
//					"                ON\r\n" + 
//					"                    table12.orderId = table13.orderId\r\n" + 
//					"                LEFT JOIN(\r\n" + 
//					"                    SELECT\r\n" + 
//					"                        quality_transaction.orderentity_id AS orderId,\r\n" + 
//					"                        AVG(\r\n" + 
//					"                            quality_transaction.sample_size\r\n" + 
//					"                        ) AS avgHourProduction\r\n" + 
//					"                    FROM\r\n" + 
//					"                        quality_transaction\r\n" + 
//					"                    WHERE\r\n" + 
//					"                        quality_transaction.orderentity_id IN("+orderIds+") AND quality_transaction.check_output = 'ok' \r\n" + 
//					"                    BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') AND quality_transaction.active = 1\r\n" + 
//					"                    GROUP BY\r\n" + 
//					"                        quality_transaction.orderentity_id) AS table14\r\n" + 
//					"                    ON\r\n" + 
//					"                        table13.orderId = table14.orderId";
//			
//			Query q = entityManager.createNativeQuery(query);
////			q.setParameter("orderIds", orderIds);
//			List<Object[]> orderSummeryList = q.getResultList();
//			
//			for(Object[] obj : orderSummeryList) {
//				OrderSummeryDto orderSummeryDto = new OrderSummeryDto();
//				
//				orderSummeryDto.setOrderStutus(obj[1] == null ? "" : obj[1].toString());
//				orderSummeryDto.setOrder(obj[2] == null ? "" : obj[2].toString());
//				orderSummeryDto.setBuyer(obj[3] == null ? "" : obj[3].toString());
//				orderSummeryDto.setStyle(obj[4] == null ? "" : obj[4].toString());
//				orderSummeryDto.setQuantity(obj[5] == null ? "0" : obj[5].toString());
//				orderSummeryDto.setSmv(obj[6] == null ? "0" : obj[6].toString());
//				orderSummeryDto.setProductionStartDate(obj[7] == null ? "0" : obj[7].toString());
//				orderSummeryDto.setTotalProduction(obj[8] == null ? "0" : obj[8].toString());
//				orderSummeryDto.setAlter(obj[9] == null ? "0" : obj[9].toString());
//				orderSummeryDto.setReject(obj[10] == null ? "0" : obj[10].toString());
//				orderSummeryDto.setAlterPercentage(obj[11] == null ? "0" : obj[11].toString());
//				orderSummeryDto.setRejectPercentage(obj[12] == null ? "0" : obj[12].toString());
//				orderSummeryDto.setDefect(obj[13] == null ? "0" : obj[13].toString());
//				orderSummeryDto.setDhu(obj[14] == null ? "0" : obj[14].toString());
//				orderSummeryDto.setEfficiency(obj[15] == null ? "0" : obj[15].toString());
//				orderSummeryDto.setLineInvole(obj[16] == null ? "0" : obj[16].toString());
//				orderSummeryDto.setNumberOfOperation(obj[17] == null ? "0" : obj[17].toString());
//				orderSummeryDto.setAvgHrTarget(obj[18] == null ? "0" : obj[18].toString());
//				orderSummeryDto.setAvgMp(obj[19] == null ? "0" : obj[19].toString());
//				orderSummeryDto.setLastDayProduction(obj[20] == null ? "0" : obj[20].toString());
//				orderSummeryDto.setCurrentDayProduction(obj[21] == null ? "0" : obj[21].toString());
//				orderSummeryDto.setLastHrProduction(obj[22] == null ? "0" : obj[22].toString());
//				orderSummeryDto.setRunningProductionLine(obj[23] == null ? "0" : obj[23].toString());
//				orderSummeryDto.setAvgHrProduction(obj[24] == null ? "0" : obj[24].toString());
//				orderSummeryDto.setCurrentHrProduction(obj[25] == null ? "0" : obj[25].toString());
//				orderSummeryDto.setAvgAchivement(obj[26] == null ? "0" : obj[26].toString());
//				
//				orderSummeryDtoList.add(orderSummeryDto);	
//			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, orderSummeryDtoList, orderSummeryDtoList.size(),
					String.format("Order Summery retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<OrderSummeryDto> orderSummeryGetByOrderId(String orderIds) {
		List<OrderSummeryDto> orderSummeryDtoList = new ArrayList<OrderSummeryDto>();
		try {
			String query = "SELECT\r\n" + "    table1.orderId,\r\n" + "    table1.STATUS,\r\n"
					+ "    table1.orderName,\r\n" + "    table1.buyer,\r\n" + "    table1.styleName,\r\n"
					+ "    table1.quantity,\r\n" + "    table1.smv,\r\n" + "    table2.Production_Start_Date,\r\n"
					+ "    table3.toatalProduction,\r\n" + "    table4.totalAlter,\r\n" + "    table5.totalReject,\r\n"
					+ "    FORMAT(\r\n" + "        (\r\n"
					+ "            (100 * table4.totalAlter) / NULLIF(table2.totalcheck,0)\r\n" + "        ),\r\n"
					+ "        2\r\n" + "    ) AS alterPercentage,\r\n" + "    FORMAT(\r\n" + "        (\r\n"
					+ "            (100 * table5.totalReject) / NULLIF(table2.totalcheck,0)\r\n" + "        ),\r\n"
					+ "        2\r\n" + "    ) AS rejectPercentage,\r\n" + "    table6.defect,\r\n"
					+ "    FORMAT(((100*table6.defect)/table2.totalcheck),2) as DHU,\r\n"
					+ "    FORMAT(((table3.toatalProduction*table1.smv*100)/(60*1*NULLIF(table1.totalManpower,0))),2) as efficiency,\r\n"
					+ "    table7.lineInvolve,\r\n" + "    table8.numerOfOperation,\r\n"
					+ "    table9.avg_Hr_Target,\r\n" + "    table9.avgMp,\r\n" + "    table10.last_Day_Production,\r\n"
					+ "    table11.Current_Day_Production,\r\n" + "    table12.last_Hr_Production,\r\n"
					+ "    table12.Running_Line,\r\n" + "    table14.avgHourProduction,\r\n"
					+ "    table13.Current_Hr_Production,\r\n"
					+ "    (table14.avgHourProduction/NULLIF(table9.avg_Hr_Target,0)) as avgAchivement\r\n" + "FROM\r\n"
					+ "    (\r\n" + "    SELECT\r\n" + "        order_entity.id AS orderId,\r\n"
					+ "        order_entity.status AS\r\n" + "    STATUS\r\n" + "        ,\r\n"
					+ "        order_entity.name AS orderName,\r\n" + "        customer.name AS buyer,\r\n"
					+ "        style.name AS styleName,\r\n" + "        order_entity.quantity AS quantity,\r\n"
					+ "        style.smv AS smv,\r\n"
					+ "        SUM(target_and_manpower.number_of_helper+target_and_manpower.number_of_operator) as totalManpower\r\n"
					+ "    FROM\r\n" + "        order_entity,\r\n" + "        customer,\r\n" + "        style,\r\n"
					+ "        target_and_manpower\r\n" + "    WHERE\r\n" + "        order_entity.id IN(" + orderIds
					+ ") AND order_entity.active = 1 AND order_entity.customer_id = customer.id AND customer.active = 1 AND order_entity.style_id = style.id AND style.active = 1\r\n"
					+ "AND target_and_manpower.org_id = order_entity.org_id\r\n" + "    GROUP BY\r\n"
					+ "        order_entity.org_id\r\n" + ") AS table1\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS totalcheck,\r\n"
					+ "        quality_transaction.id AS qualityId,\r\n"
					+ "        quality_transaction.created_at AS Production_Start_Date\r\n" + "    FROM\r\n"
					+ "        quality_transaction\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.active = 1 AND quality_transaction.orderentity_id IN(" + orderIds
					+ ")\r\n" + "    GROUP BY\r\n" + "        quality_transaction.orderentity_id\r\n"
					+ "    ORDER BY\r\n" + "        quality_transaction.created_at ASC\r\n" + ") AS table2\r\n"
					+ "ON\r\n" + "    table1.orderId = table2.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS toatalProduction\r\n"
					+ "    FROM\r\n" + "        quality_transaction\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n"
					+ "    AND quality_transaction.orderentity_id IN(" + orderIds + ")\r\n" + "    GROUP BY\r\n"
					+ "        quality_transaction.orderentity_id\r\n" + ") AS table3\r\n" + "ON\r\n"
					+ "    table2.orderId = table3.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS totalAlter\r\n" + "    FROM\r\n"
					+ "        quality_transaction\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.active = 1 AND quality_transaction.check_output = 'alter' \r\n"
					+ "    AND quality_transaction.orderentity_id IN(" + orderIds + ")\r\n" + "    GROUP BY\r\n"
					+ "        quality_transaction.orderentity_id\r\n" + ") AS table4\r\n" + "ON\r\n"
					+ "    table3.orderId = table4.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS totalReject\r\n"
					+ "    FROM\r\n" + "        quality_transaction\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.active = 1 AND quality_transaction.check_output = 'reject' \r\n"
					+ "    AND quality_transaction.orderentity_id IN(" + orderIds + ")\r\n" + "    GROUP BY\r\n"
					+ "        quality_transaction.orderentity_id\r\n" + ") AS table5\r\n" + "ON\r\n"
					+ "    table5.orderId = table4.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n"
					+ "        COUNT(quality_defect.id) AS defect\r\n" + "    FROM\r\n"
					+ "        quality_transaction,\r\n" + "        quality_defect\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.orderentity_id IN(" + orderIds
					+ ") AND quality_defect.qualitytransaction_id = quality_transaction.id AND quality_transaction.active = 1\r\n"
					+ "    GROUP BY\r\n" + "        quality_transaction.orderentity_id\r\n" + ") AS table6\r\n"
					+ "ON\r\n" + "    table5.orderId = table6.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n" + "        COUNT(\r\n"
					+ "            DISTINCT quality_transaction.org_id\r\n" + "        ) AS lineInvolve\r\n"
					+ "    FROM\r\n" + "        quality_transaction\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.orderentity_id IN(" + orderIds + ")\r\n" + "    GROUP BY\r\n"
					+ "        quality_transaction.orderentity_id\r\n" + ") AS table7\r\n" + "ON\r\n"
					+ "    table6.orderId = table7.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        order_entity.id AS orderId,\r\n" + "        COUNT(operation.id) AS numerOfOperation\r\n"
					+ "    FROM\r\n" + "        order_entity,\r\n" + "        style,\r\n"
					+ "        operation_break_down,\r\n" + "        operation\r\n" + "    WHERE\r\n"
					+ "        order_entity.id IN(" + orderIds
					+ ") AND order_entity.active = 1 AND order_entity.style_id = style.id AND style.active = 1 AND style.id = operation_break_down.style_id AND operation_break_down.active = 1 AND operation_break_down.operation_id = operation.id AND operation.active = 1\r\n"
					+ "    GROUP BY\r\n" + "        order_entity.id\r\n" + ") AS table8\r\n" + "ON\r\n"
					+ "    table7.orderId = table8.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n" + "        FORMAT(\r\n"
					+ "            AVG(\r\n" + "                target_and_manpower.productiontarget\r\n"
					+ "            ),\r\n" + "            2\r\n" + "        ) AS avg_Hr_Target,\r\n"
					+ "        FORMAT(\r\n" + "            AVG(\r\n"
					+ "                target_and_manpower.number_of_helper + target_and_manpower.number_of_operator\r\n"
					+ "            ),\r\n" + "            2\r\n" + "        ) AS avgMp\r\n" + "    FROM\r\n"
					+ "        `quality_transaction`,\r\n" + "        target_and_manpower\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.orderentity_id IN(" + orderIds
					+ ") AND quality_transaction.org_id = target_and_manpower.org_id\r\n" + "    GROUP BY\r\n"
					+ "        quality_transaction.orderentity_id\r\n" + ") AS table9\r\n" + "ON\r\n"
					+ "    table8.orderId = table9.orderId\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.orderentity_id AS orderId,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS last_Day_Production\r\n"
					+ "    FROM\r\n" + "        quality_transaction\r\n" + "    WHERE\r\n"
					+ "        quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n"
					+ "    AND quality_transaction.created_at BETWEEN CURDATE() - INTERVAL 1 DAY AND CURDATE() AND quality_transaction.orderentity_id IN("
					+ orderIds + ")\r\n" + "    GROUP BY\r\n"
					+ "        quality_transaction.orderentity_id) AS table10\r\n" + "    ON\r\n"
					+ "        table9.orderId = table10.orderId\r\n" + "    LEFT JOIN(\r\n" + "        SELECT\r\n"
					+ "            quality_transaction.orderentity_id AS orderId,\r\n" + "            SUM(\r\n"
					+ "                quality_transaction.sample_size\r\n"
					+ "            ) AS Current_Day_Production\r\n" + "        FROM\r\n"
					+ "            quality_transaction\r\n" + "        WHERE\r\n"
					+ "            quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n"
					+ "        AND DATE(quality_transaction.created_at) = CURDATE() AND quality_transaction.orderentity_id IN("
					+ orderIds + ")\r\n" + "        GROUP BY\r\n"
					+ "            quality_transaction.orderentity_id) AS table11\r\n" + "        ON\r\n"
					+ "            table10.orderId = table11.orderId\r\n" + "        LEFT JOIN(\r\n"
					+ "            SELECT\r\n" + "                quality_transaction.orderentity_id AS orderId,\r\n"
					+ "                SUM(\r\n" + "                    quality_transaction.sample_size\r\n"
					+ "                ) AS last_Hr_Production,\r\n"
					+ "                COUNT(quality_transaction.org_id) AS Running_Line\r\n" + "            FROM\r\n"
					+ "                quality_transaction\r\n" + "            WHERE\r\n"
					+ "                quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n"
					+ "            AND quality_transaction.created_at BETWEEN(NOW() - INTERVAL 1 HOUR) AND NOW() AND quality_transaction.orderentity_id IN("
					+ orderIds + ")\r\n" + "            GROUP BY\r\n"
					+ "                quality_transaction.orderentity_id) AS table12\r\n" + "            ON\r\n"
					+ "                table11.orderId = table12.orderId\r\n" + "            LEFT JOIN(\r\n"
					+ "                SELECT\r\n"
					+ "                    quality_transaction.orderentity_id AS orderId,\r\n"
					+ "                    SUM(\r\n" + "                        quality_transaction.sample_size\r\n"
					+ "                    ) AS Current_Hr_Production\r\n" + "                FROM\r\n"
					+ "                    quality_transaction\r\n" + "                WHERE\r\n"
					+ "                    quality_transaction.active = 1 AND quality_transaction.check_output = 'ok' \r\n"
					+ "                BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') AND quality_transaction.orderentity_id IN("
					+ orderIds + ")\r\n" + "                GROUP BY\r\n"
					+ "                    quality_transaction.orderentity_id) AS table13\r\n"
					+ "                ON\r\n" + "                    table12.orderId = table13.orderId\r\n"
					+ "                LEFT JOIN(\r\n" + "                    SELECT\r\n"
					+ "                        quality_transaction.orderentity_id AS orderId,\r\n"
					+ "                        AVG(\r\n"
					+ "                            quality_transaction.sample_size\r\n"
					+ "                        ) AS avgHourProduction\r\n" + "                    FROM\r\n"
					+ "                        quality_transaction\r\n" + "                    WHERE\r\n"
					+ "                        quality_transaction.orderentity_id IN(" + orderIds
					+ ") AND quality_transaction.check_output = 'ok' \r\n"
					+ "                    BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') AND quality_transaction.active = 1\r\n"
					+ "                    GROUP BY\r\n"
					+ "                        quality_transaction.orderentity_id) AS table14\r\n"
					+ "                    ON\r\n" + "                        table13.orderId = table14.orderId";

			Query q = entityManager.createNativeQuery(query);
//			q.setParameter("orderIds", orderIds);
			List<Object[]> orderSummeryList = q.getResultList();

			for (Object[] obj : orderSummeryList) {
				OrderSummeryDto orderSummeryDto = new OrderSummeryDto();
				if(obj[0] != null) {
				orderSummeryDto.setOrderId(Long.parseLong(obj[0].toString()));
				}
				orderSummeryDto.setOrderStutus(obj[1] == null ? "" : obj[1].toString());
				orderSummeryDto.setOrder(obj[2] == null ? "" : obj[2].toString());
				orderSummeryDto.setBuyer(obj[3] == null ? "" : obj[3].toString());
				orderSummeryDto.setStyle(obj[4] == null ? "" : obj[4].toString());
				orderSummeryDto.setQuantity(obj[5] == null ? "0" : obj[5].toString());
				orderSummeryDto.setSmv(obj[6] == null ? "0" : obj[6].toString());
				orderSummeryDto.setProductionStartDate(obj[7] == null ? "0" : obj[7].toString());
				orderSummeryDto.setTotalProduction(obj[8] == null ? "0" : obj[8].toString());
				orderSummeryDto.setAlter(obj[9] == null ? "0" : obj[9].toString());
				orderSummeryDto.setReject(obj[10] == null ? "0" : obj[10].toString());
				orderSummeryDto.setAlterPercentage(obj[11] == null ? "0" : obj[11].toString());
				orderSummeryDto.setRejectPercentage(obj[12] == null ? "0" : obj[12].toString());
				orderSummeryDto.setDefect(obj[13] == null ? "0" : obj[13].toString());
				orderSummeryDto.setDhu(obj[14] == null ? "0" : obj[14].toString());
				orderSummeryDto.setEfficiency(obj[15] == null ? "0" : obj[15].toString());
				orderSummeryDto.setLineInvole(obj[16] == null ? "0" : obj[16].toString());
				orderSummeryDto.setNumberOfOperation(obj[17] == null ? "0" : obj[17].toString());
				orderSummeryDto.setAvgHrTarget(obj[18] == null ? "0" : obj[18].toString());
				orderSummeryDto.setAvgMp(obj[19] == null ? "0" : obj[19].toString());
				orderSummeryDto.setLastDayProduction(obj[20] == null ? "0" : obj[20].toString());
				orderSummeryDto.setCurrentDayProduction(obj[21] == null ? "0" : obj[21].toString());
				orderSummeryDto.setLastHrProduction(obj[22] == null ? "0" : obj[22].toString());
				orderSummeryDto.setRunningProductionLine(obj[23] == null ? "0" : obj[23].toString());
				orderSummeryDto.setAvgHrProduction(obj[24] == null ? "0" : obj[24].toString());
				orderSummeryDto.setCurrentHrProduction(obj[25] == null ? "0" : obj[25].toString());
				orderSummeryDto.setAvgAchivement(obj[26] == null ? "0" : obj[26].toString());

				orderSummeryDtoList.add(orderSummeryDto);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return orderSummeryDtoList;
	}

	public Response delete(Long id) {
		try {
			OrderEntity orderEntity = orderEntityRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			orderEntity.setActive(false);
			orderEntityRepository.save(orderEntity);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, OrderEntityDto orderEntityDto) {
		try {

			Optional<Style> style = null;
			Optional<Customer> customer = null;
			Optional<Company> company = null;

			OrderEntity orderEntity = orderEntityRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			if (orderEntityDto.getStyle() != null) {
				style = styleRepository.findById(orderEntityDto.getStyle().getId());
			}
			if (orderEntityDto.getCustomer() != null) {
				customer = customerRepository.findById(orderEntityDto.getCustomer().getId());
			}

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

			PropertyMap<OrderEntityDto, OrderEntity> skipModifiedFieldsMap = new PropertyMap<OrderEntityDto, OrderEntity>() {
				protected void configure() {
					skip().setStyle(null);
					skip().setCustomer(null);
				}
			};
			this.modelMapper.addMappings(skipModifiedFieldsMap);

			modelMapper.map(orderEntityDto, orderEntity);

			if (orderEntity.getVarience() != null) {
				for (Varience varienceEntity : orderEntity.getVarience()) {
					varienceEntity.setOrderEntity(orderEntity);

				}
			}

			if (orderEntity.getStyle() != null) {
				orderEntity.setStyle(style.get());
			}
			if (orderEntity.getCustomer() != null) {
				orderEntity.setCustomer(customer.get());
			}

			orderEntityRepository.save(orderEntity);
			modelMapper.map(orderEntity, orderEntityDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, orderEntityDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getParameterSelectionPanel(Long orgId) {
		try {
			
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
			
			List<Customer> customers = customerRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
			
			List<StyleWiseCustomerDto> styleWiseCustomerDtoList = new ArrayList<StyleWiseCustomerDto>();
			
			for(Customer customer: customers) {
				StyleWiseCustomerDto styleWiseCustomer = new StyleWiseCustomerDto();
				
				styleWiseCustomer.setId(customer.getId());
				styleWiseCustomer.setName(customer.getName());
				
				List<Object[]> orderEntityListByCustomer = orderEntityRepository.findAllByActiveTrueAndCustomerId(customer.getId());
				
				List<OrderWiseStyleDto> styleList = new ArrayList<OrderWiseStyleDto>();
				
				for(Object[] obj : orderEntityListByCustomer) {
					OrderWiseStyleDto style = new OrderWiseStyleDto();
					if(obj[0] != null) {
					style.setId(Long.parseLong(obj[0].toString()));
					}
					style.setName(obj[1] != null? obj[1].toString() : "");
					
					List<OrderEntity> orderEntityListByStyleAndCustomer = orderEntityRepository.findAllByActiveTrueAndStyleIdAndCustomerId(style.getId(), customer.getId());
					List<OrderEntityDto> OrderEntityDtoList = new ArrayList<OrderEntityDto>();
					
					for(OrderEntity orderEntity : orderEntityListByStyleAndCustomer) {
						
						OrderEntityDto orderEntityDto = new OrderEntityDto();
						orderEntityDto.setId(orderEntity.getId());
						orderEntityDto.setName(orderEntity.getName());
						
						OrderEntityDtoList.add(orderEntityDto);
					}
					style.setOrder(OrderEntityDtoList);
					styleList.add(style);
				}
				styleWiseCustomer.setStyle(styleList);
				styleWiseCustomerDtoList.add(styleWiseCustomer);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, styleWiseCustomerDtoList,
					styleWiseCustomerDtoList.size(), String.format("All Buyer with Style and Order Information", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<OrderEntityDto> getResponseDtoList(List<OrderEntity> orderEntities, String type) {
		List<OrderEntityDto> responseDtoList = new ArrayList<>();
		orderEntities.forEach(orderEntity -> {
			OrderEntityDto orderEntityDto = new OrderEntityDto();
			orderEntity.getStyle().setOperationBreakDown(null);
			orderEntity.getStyle().setOrganization(null);
			orderEntity.setOrganization(null);
			if (type != null && type.equals("mobile")) {
				orderEntity.setVarience(null);
			}
			modelMapper.map(orderEntity, orderEntityDto);
			responseDtoList.add(orderEntityDto);
		});
		return responseDtoList;
	}

}
