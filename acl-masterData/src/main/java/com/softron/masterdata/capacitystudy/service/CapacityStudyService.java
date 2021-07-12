package com.softron.masterdata.capacitystudy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.capacitystudy.dto.CapacityGraphDto;
import com.softron.masterdata.capacitystudy.dto.CapacityReportDto;
import com.softron.masterdata.capacitystudy.dto.CapacityStudyDetailsDto;
import com.softron.masterdata.capacitystudy.dto.CapacityStudyDto;
import com.softron.masterdata.capacitystudy.dto.CompleteCSListDto;
import com.softron.masterdata.capacitystudy.dto.DailyHeaderInfoDto;
import com.softron.masterdata.capacitystudy.dto.EmployeeProfileFromCapacityDto;
import com.softron.masterdata.capacitystudy.dto.InCompleteCsDto;
import com.softron.masterdata.capacitystudy.dto.LabHistoryDto;
import com.softron.masterdata.capacitystudy.dto.NewsFeedHeaderInfoDto;
import com.softron.masterdata.capacitystudy.dto.OperationAnalysisCSDto;
import com.softron.masterdata.capacitystudy.dto.OperatorCycleTimeTrendsDto;
import com.softron.masterdata.capacitystudy.dto.OperatorDataDto;
import com.softron.masterdata.capacitystudy.dto.WorkStudyDto;
import com.softron.masterdata.capacitystudy.dto.WorkStudyGraphDto;
import com.softron.masterdata.capacitystudy.repository.CapacityStudyDetailsRepository;
import com.softron.masterdata.capacitystudy.repository.CapacityStudyRepository;
import com.softron.masterdata.capacitystudy.repository.LabHistoryRepository;
import com.softron.masterdata.capacitystudy.repository.WorkStudyRepository;
import com.softron.masterdata.dto.CustomerDto;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.dto.OperationMachineDto;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.dto.StyleDto;
import com.softron.masterdata.dto.UserDto;
import com.softron.masterdata.repository.EmployeeRepository;
import com.softron.masterdata.repository.OperationBreakDownRepository;
import com.softron.masterdata.repository.OperationMachineRepository;
import com.softron.masterdata.repository.OrderEntityRepository;
import com.softron.masterdata.repository.SectionRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.schema.capacitystudy.entity.CapacityStudy;
import com.softron.schema.capacitystudy.entity.CapacityStudyDetails;
import com.softron.schema.capacitystudy.entity.LabHistory;
import com.softron.schema.capacitystudy.entity.WorkStudy;
import com.softron.utils.DecimalValueConversion;
import com.softron.utils.LocalDateTimeConversion;

@Service
public class CapacityStudyService {

	@Autowired
	CapacityStudyRepository capacityStudyRepository;

	@Autowired
	CapacityStudyDetailsRepository capacityStudyDetailsRepository;

	@Autowired
	WorkStudyRepository workStudyRepository;

	@Autowired
	LabHistoryRepository labHistoryRepository;

	@Autowired
	OperationBreakDownRepository operationBreakDownRepository;

	@Autowired
	OperationMachineRepository operationMachineRepository;

	@Autowired
	OrderEntityRepository orderEntityRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	OrganizationRepository OrganizationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminService adminService;

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	SectionRepository sectionRepository;

	@Autowired
	ModelMapper modelMapper;

	@PersistenceContext
	private EntityManager entityManager;

	String root = "Capacity Study";

	public Response create(List<CapacityStudyDto> capacityStudyDtoList, String userName) {
		try {

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, save(capacityStudyDtoList, userName),
					String.format("%s created successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<CapacityStudyDto> save(List<CapacityStudyDto> capacityStudyDtoList, String userName) {
		List<CapacityStudyDto> capacityStudyDtoResponse = new ArrayList<CapacityStudyDto>();

		for (CapacityStudyDto capacityStudyDto : capacityStudyDtoList) {

			CapacityStudy capacityStudy = new CapacityStudy();

			if (capacityStudyDto.getId() != null) {
				capacityStudy = capacityStudyRepository.findById(capacityStudyDto.getId())
						.orElseThrow(() -> new NoRecordExistsException(
								String.format("%s doesn't exist for id %s", root, capacityStudyDto.getId())));
			}

			BeanUtils.copyProperties(capacityStudyDto, capacityStudy);
			capacityStudy.setUserName(userName);

			if (capacityStudyDto.getOrganization() != null) {
				Optional<Organization> organization = OrganizationRepository
						.findById(capacityStudyDto.getOrganization().getId());
				capacityStudy.setOrganization(organization.get());
			}

			if (capacityStudyDto.getSharedUser() != null) {
				Optional<UserEntity> sharedUser = userRepository
						.findByUserNameIgnoreCase(capacityStudyDto.getSharedUser().getUserName());
				capacityStudy.setSharedUser(sharedUser.get());
			}

			if (capacityStudyDto.getOrderEntityId() != null) {
				Optional<OrderEntity> orderEntity = orderEntityRepository.findById(capacityStudyDto.getOrderEntityId());
				capacityStudy.setOrderEntity(orderEntity.get());
			}

			List<CapacityStudyDetails> capacityStudyDetailsList = new ArrayList<CapacityStudyDetails>();

			if (capacityStudyDto.getCapacityStudyDetails() != null) {
				for (CapacityStudyDetailsDto capacityStudyDetailsDto : capacityStudyDto.getCapacityStudyDetails()) {

					CapacityStudyDetails capacityStudyDetails = new CapacityStudyDetails();

					if (capacityStudyDetailsDto.getId() != null) {
						capacityStudyDetails = capacityStudyDetailsRepository.findById(capacityStudyDetailsDto.getId())
								.orElseThrow(() -> new NoRecordExistsException(String
										.format("%s doesn't exist for id %s", root, capacityStudyDetailsDto.getId())));
					}

					BeanUtils.copyProperties(capacityStudyDetailsDto, capacityStudyDetails);

					OperationBreakDown operationBreakDown = operationBreakDownRepository
							.findById(capacityStudyDetailsDto.getOperationBreakDownId())
							.orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s",
									root, capacityStudyDetailsDto.getOperationBreakDownId())));

					operationBreakDown.setAllowance(capacityStudyDetailsDto.getAllowance());
					capacityStudyDetails.setOperationBreakDown(operationBreakDown);
					// operationBreakDownRepository.save(operationBreakDown);

					if (capacityStudyDetailsDto.getOperationMachine() != null) {

						OperationMachine operationMachine = new OperationMachine();

						if (capacityStudyDetailsDto.getOperationMachine().getId() != null) {
							operationMachine = operationMachineRepository
									.findById(capacityStudyDetailsDto.getOperationMachine().getId())
									.orElseThrow(() -> new NoRecordExistsException(
											String.format("%s doesn't exist for id %s", root,
													capacityStudyDetailsDto.getOperationMachine().getId())));
						}

						Optional<Employee> employeeInfo = Optional.empty();
						if (capacityStudyDetailsDto.getOperationMachine().getEmployee() != null) {
							employeeInfo = employeeRepository
									.findById(capacityStudyDetailsDto.getOperationMachine().getEmployee().getId());
							operationMachine.setEmployee(employeeInfo.get());
						}

						Optional<OperationBreakDown> operationBreakDownInfo = Optional.empty();
						if (capacityStudyDetailsDto.getOperationMachine().getOperationBreakDown() != null) {
							operationBreakDownInfo = operationBreakDownRepository.findById(
									capacityStudyDetailsDto.getOperationMachine().getOperationBreakDown().getId());
							operationMachine.setOperationBreakDown(operationBreakDownInfo.get());
						}
						if (capacityStudyDetailsDto.getOperationMachine().getOrderEntityId() != null) {
							Optional<OrderEntity> orferEntityInfo = orderEntityRepository
									.findById(capacityStudyDetailsDto.getOperationMachine().getOrderEntityId());
							operationMachine.setOrderEntity(orferEntityInfo.get());
						}
						if (capacityStudyDetailsDto.getOperationMachine().getOrganization() != null) {
							Optional<Organization> organizationInfo = OrganizationRepository
									.findById(capacityStudyDetailsDto.getOperationMachine().getOrganization().getId());
							operationMachine.setOrganization(organizationInfo.get());
						}

//								BeanUtils.copyProperties(capacityStudyDetailsDto.getOperationMachine(),
//										operationMachine);

						// operationMachineRepository.save(operationMachine);

						capacityStudyDetails.setOperationMachine(operationMachine);
//						capacityStudyDetails.setEmployee(employeeInfo.get());
//						capacityStudyDetails.setOperationBreakDown(operationBreakDownInfo.get());
						capacityStudyDetails.setEmployee(operationMachine.getEmployee());
						capacityStudyDetails.setOperationBreakDown(operationMachine.getOperationBreakDown());
					}

					capacityStudyDetails.setCapacityStudy(capacityStudy);

					if (capacityStudyDetailsDto.getWorkStudy() != null) {

						List<WorkStudy> workStudyList = new ArrayList<WorkStudy>();

						for (WorkStudyDto workStudyEachDto : capacityStudyDetailsDto.getWorkStudy()) {

							List<LabHistory> LabHistoryList = new ArrayList<LabHistory>();
							WorkStudy workStudy = new WorkStudy();
							if (workStudyEachDto.getId() != null) {
								workStudy = workStudyRepository.findById(workStudyEachDto.getId())
										.orElseThrow(() -> new NoRecordExistsException(String
												.format("%s doesn't exist for id %s", root, workStudyEachDto.getId())));
							}
							BeanUtils.copyProperties(workStudyEachDto, workStudy);
							for (LabHistoryDto labHistoryEachDto : workStudyEachDto.getLabHistory()) {
								LabHistory labHistory = new LabHistory();
								if (labHistoryEachDto.getId() != null) {
									labHistory = labHistoryRepository.findById(labHistoryEachDto.getId())
											.orElseThrow(() -> new NoRecordExistsException(String.format(
													"%s doesn't exist for id %s", root, labHistoryEachDto.getId())));
								}
								BeanUtils.copyProperties(labHistoryEachDto, labHistory);
								labHistory.setWorkStudy(workStudy);
								LabHistoryList.add(labHistory);
							}

							workStudy.setLabHistory(LabHistoryList);
							workStudy.setCapacityStudyDetails(capacityStudyDetails);
							workStudyList.add(workStudy);
						}
						capacityStudyDetails.setWorkStudy(workStudyList);
					}
					capacityStudyDetailsList.add(capacityStudyDetails);

				}

			}

			capacityStudy.setCapacityStudyDetails(capacityStudyDetailsList);

			if (capacityStudyDto.getLayoutStartDay() != null) {

				capacityStudy.setLayoutStartDay(LocalDateTimeConversion
						.getLocalDateTimeFromString(capacityStudyDto.getLayoutStartDay(), "yyyy-MM-dd'T'HH:mm:ss"));
			}

			capacityStudyRepository.save(capacityStudy);

			capacityStudy.setCapacityStudyNo(capacityStudy.getId().toString());

			capacityStudyRepository.save(capacityStudy);

//			BeanUtils.copyProperties(capacityStudy, capacityStudyDto);
//			capacityStudyDto.setCapacityStudyDetails(null);
//			CapacityStudyDtoResponse.add(capacityStudyDto);

			CapacityStudyDto responseCapacityStudyDto = getResponseDtoforSaveAndUpdate(capacityStudy);
			capacityStudyDtoResponse.add(responseCapacityStudyDto);

		}
		return capacityStudyDtoResponse;
	}

	public Response update(List<CapacityStudyDto> capacityStudyDtoList, String userName) {
		try {
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, save(capacityStudyDtoList, userName),
					String.format("%s updated successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll(Long orgId) {
		try {

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

			// List<CapacityStudy> CapacityStudyList =
			// capacityStudyRepository.findAllByActiveTrue();

			List<CapacityStudy> CapacityStudyList = capacityStudyRepository.findAllByActiveTrueAndOrganization(orgIds);

			for (CapacityStudy CapacityStudy : CapacityStudyList) {

				CapacityStudy.setCapacityStudyDetails(null);

				if (CapacityStudy.getOrderEntity() != null) {
					Optional<OrderEntity> orderEntity = orderEntityRepository
							.findById(CapacityStudy.getOrderEntity().getId());
					CapacityStudy.setOrderEntity(orderEntity.get());
				}

//			if(CapacityStudy.getSection() != null) {
//			Optional<Section> section = sectionRepository
//					.findById(CapacityStudy.getSection().getId());
//			CapacityStudy.setSection(section.get());
//			}

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(CapacityStudyList),
					CapacityStudyList.size(), String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAllByStatusIsComplete(Long orgId) {
		try {

			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = OrganizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);

			adminService.getOrganizationList(orgId, orgList);

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			List<CapacityStudy> CapacityStudyList = capacityStudyRepository.findAllCsByOrgIdAndStatusIsComplete(orgIds);

			for (CapacityStudy CapacityStudy : CapacityStudyList) {

				CapacityStudy.setCapacityStudyDetails(null);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseCompleteCSListDto(CapacityStudyList),
					CapacityStudyList.size(), String.format("All Complete %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAllByStatusIsPartial(String userName) {
		try {

			List<CapacityStudy> CapacityStudyList = capacityStudyRepository.findAllCsByUserAndStatusIsPartial(userName);

			for (CapacityStudy CapacityStudy : CapacityStudyList) {

				CapacityStudy.setCapacityStudyDetails(null);

				if (CapacityStudy.getOrderEntity() != null) {
					CapacityStudy.setOrderEntity(null);
				}
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(CapacityStudyList),
					CapacityStudyList.size(), String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

//	public Response getInCompleteCsList(String userName) {
//		try {
//
//			List<InCompleteCsDto> inCompleteCsList = new ArrayList<InCompleteCsDto>();
//			
//			String query = "SELECT capacity_study.id as id,organizations.name as performLine,customer.name as buyer,style.name as style,capacity_study.layout_start_day as layoutDate,capacity_study.user_name as studyBy,capacity_study.created_at as studyPerformDate,capacity_study.category as category FROM capacity_study,organizations,order_entity,customer,style\r\n" + 
//					"where \r\n" + 
//					"capacity_study.org_id=organizations.id\r\n" + 
//					"AND\r\n" + 
//					"capacity_study.orderentity_id=order_entity.id\r\n" + 
//					"AND\r\n" + 
//					"order_entity.customer_id=customer.id\r\n" + 
//					"AND\r\n" + 
//					"order_entity.style_id=style.id\r\n" + 
//					"AND\r\n" + 
//					"capacity_study.user_name=:userName\r\n" + 
//					"AND\r\n" + 
//					"capacity_study.status='partial'";
//
//			Query q = entityManager.createNativeQuery(query);
//			q.setParameter("userName", userName);
//
//			List<Object[]> objects = q.getResultList();
//
//			for (Object[] obj : objects) {
//				InCompleteCsDto inCompleteCsDto = new InCompleteCsDto();
//				BigInteger capacityStudyId = (BigInteger) obj[0];
//				
//				String query2 = "SELECT table2.opbdcount,table1.total\r\n" + 
//						"FROM\r\n" + 
//						"(SELECT COUNT(*) as total, capacity_study.id as id\r\n" + 
//						"FROM capacity_study,order_entity,operation_break_down\r\n" + 
//						"WHERE capacity_study.id=:capacityStudyId\r\n" + 
//						"AND\r\n" + 
//						"capacity_study.orderentity_id=order_entity.id\r\n" + 
//						"AND\r\n" + 
//						"operation_break_down.style_id=order_entity.style_id\r\n" + 
//						")as table1\r\n" + 
//						"LEFT JOIN\r\n" + 
//						"(\r\n" + 
//						"SELECT COUNT(DISTINCT capacity_study_details.operationbreakdown_id) as opbdcount,capacity_study_details.capacitystudy_id as id\r\n" + 
//						"FROM capacity_study_details\r\n" + 
//						"WHERE capacity_study_details.capacitystudy_id =:capacityStudyId\r\n" + 
//						"AND\r\n" + 
//						"capacity_study_details.cycle_time>0\r\n" + 
//						") as table2\r\n" + 
//						"ON table1.id = table2.id";
//
//				Query q2 = entityManager.createNativeQuery(query2);
//				q2.setParameter("capacityStudyId", capacityStudyId);
//
//				List<Object[]> objects2 = q2.getResultList();
//				
//				for(Object[] obj2 : objects2) {
//					int totalOpbdInCs=Integer.parseInt(obj2[0].toString()); 
//					int total=Integer.parseInt(obj2[1].toString());
//					
//				if(total>0) {
//						double percentOfComplete = DecimalValueConversion.getFormattedDoubleValue((double)(totalOpbdInCs*100)/total);
//						inCompleteCsDto.setPercentageOfComplete(String.valueOf(percentOfComplete));
//					}
//				}
//				
//				inCompleteCsDto.setPerformanceLine(obj[1] != null ? obj[1].toString() : "");
//				inCompleteCsDto.setBuyer(obj[2] != null ? obj[2].toString() : "");
//				inCompleteCsDto.setStyle(obj[3] != null ? obj[3].toString() : "");
//				inCompleteCsDto.setLayoutDate(obj[4] != null ? obj[4].toString() : "");
//				inCompleteCsDto.setStudyBy(obj[5] != null ? obj[5].toString() : "");
//				inCompleteCsDto.setStudyPerformDate(obj[6] != null ? obj[6].toString() : "");
//				inCompleteCsDto.setCategory(obj[7] != null ? obj[7].toString() : "");
//				
//				inCompleteCsList.add(inCompleteCsDto);
//			}
//			return ResponseUtils.getSuccessResponse(HttpStatus.OK, inCompleteCsList,
//					inCompleteCsList.size(), String.format("All incomplete %ses list", root));
//		} catch (Exception e) {
//			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
//		}
//	}

	public Response getInCompleteCsList(String userName) {
		try {

			List<CapacityStudy> CapacityStudyList = capacityStudyRepository.findAllCsForIncompleteList(userName);
			List<InCompleteCsDto> incompleteCsListDto = new ArrayList<InCompleteCsDto>();

			incompleteCsListDto = getResponseIncompleteCsDtoList(CapacityStudyList, userName);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, incompleteCsListDto, incompleteCsListDto.size(),
					String.format("All Incomplete %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getResumeList(String userName) {
		try {

			List<CapacityStudy> CapacityStudyList = capacityStudyRepository.findAllCsByUserAndStatusIsPartial(userName);
			List<CapacityStudyDto> CapacityStudyListDto = new ArrayList<CapacityStudyDto>();

			CapacityStudyListDto = getResponseDtoList(CapacityStudyList);

			for (CapacityStudyDto capacityStudyDtoEachInfo : CapacityStudyListDto) {

				capacityStudyDtoEachInfo.setOrderEntity(null);
				List<CapacityStudyDetailsDto> CapacityStudyDetailsDtoList = new ArrayList<CapacityStudyDetailsDto>();
				CapacityStudyDetailsDtoList = capacityStudyDtoEachInfo.getCapacityStudyDetails();

				for (CapacityStudyDetailsDto capacityStudyDetailsDtoEachInfo : CapacityStudyDetailsDtoList) {
					capacityStudyDetailsDtoEachInfo.setCapacityStudy(null);

					if (capacityStudyDetailsDtoEachInfo.getOperationBreakDown() != null) {
						float allowance = operationBreakDownRepository
								.findAllowanceById(capacityStudyDetailsDtoEachInfo.getOperationBreakDown().getId());
						capacityStudyDetailsDtoEachInfo.setAllowance(allowance);
					}

					for (WorkStudyDto workStudyDtoInfo : capacityStudyDetailsDtoEachInfo.getWorkStudy()) {
						workStudyDtoInfo.setCapacityStudyDetails(null);
					}
				}
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, CapacityStudyListDto, CapacityStudyListDto.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getCapacityGraphData(Long capacityId) {
		try {
			List<CapacityGraphDto> capacityGraphDtoList = new ArrayList<CapacityGraphDto>();

			String query = "SELECT capacity_study_details.capacity,employee.name as operator,operation.name as operation,machine_type.small_name as machineName,capacity_study_details.operationbreakdown_id as opbd  \r\n"
					+ "FROM   \r\n"
					+ "capacity_study,capacity_study_details,employee,operation_machine,operation_break_down,operation,machine_type  \r\n"
					+ "WHERE  capacity_study.id=:capacityId  AND  \r\n"
					+ "capacity_study_details.capacitystudy_id=capacity_study.id  AND  \r\n"
					+ "operation_machine.id=capacity_study_details.operationmachine_id  AND  \r\n"
					+ "employee.id=operation_machine.employee_id  AND  \r\n"
					+ "operation_break_down.id=operation_machine.operationbreakdown_id  AND  \r\n"
					+ "operation.id=operation_break_down.operation_id  \r\n"
					+ "AND machine_type.id=operation_break_down.machinetype_id  \r\n"
					+ "ORDER BY capacity_study_details.operationbreakdown_id ASC";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("capacityId", capacityId);

			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {
				CapacityGraphDto capacityGraphDto = new CapacityGraphDto();
				capacityGraphDto.setCapacity(obj[0] != null ? (int) obj[0] : 0);
				capacityGraphDto.setOperatorName(obj[1] != null ? obj[1].toString() : "");
				capacityGraphDto.setOperation(obj[2] != null ? obj[2].toString() : "");
				capacityGraphDto.setMachineName(obj[3] != null ? obj[3].toString() : "");

				capacityGraphDtoList.add(capacityGraphDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, capacityGraphDtoList, capacityGraphDtoList.size(),
					String.format("%s Graph Data Retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getCapacityReportData(Long capacityId) {
		try {
			List<CapacityReportDto> CapacityReportDtoList = new ArrayList<CapacityReportDto>();

			String opbdId = null;

			String query = "SELECT capacity_study_details.id as csdid,operation_machine.operationbreakdown_id as opbd,operation_break_down.allowance,operation.name as operationName,machine_type.small_name as machineName,SUM(capacity_study_details.capacity) as operationCapacity\r\n"
					+ "FROM capacity_study,capacity_study_details,operation_machine,operation_break_down,operation,machine_type\r\n"
					+ "WHERE\r\n" + "capacity_study.id=:capacityId\r\n"
					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id\r\n"
					+ "AND operation_machine.id=capacity_study_details.operationmachine_id\r\n"
					+ "AND operation_break_down.id=operation_machine.operationbreakdown_id\r\n"
					+ "AND operation.id=operation_break_down.operation_id\r\n"
					+ "AND machine_type.id=operation_break_down.machinetype_id\r\n"
					+ "GROUP BY operation_machine.operationbreakdown_id";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("capacityId", capacityId);

			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {
				CapacityReportDto capacityReportDto = new CapacityReportDto();

				opbdId = obj[1] != null ? obj[1].toString() : "";

				Long operationBreakDownId = Long.parseLong(opbdId);

				capacityReportDto.setOperation(obj[3] != null ? obj[3].toString() : "");
				capacityReportDto.setMachinetype(obj[4] != null ? obj[4].toString() : "");
				String operationcapacityString = obj[5] != null ? obj[5].toString() : "";
				int operationcapacityInt = Integer.parseInt(operationcapacityString);

				capacityReportDto.setOperationCapacity(operationcapacityInt);

				String queryTwo = "SELECT employee.name as operator,capacity_study_details.cycle_time,capacity_study_details.capacity,operation_break_down.allowance\r\n"
						+ "FROM operation_break_down,operation_machine,employee,capacity_study_details\r\n"
						+ "WHERE \r\n" + "operation_break_down.id=:operationBreakDownId\r\n" + "AND\r\n"
						+ "operation_machine.operationbreakdown_id=operation_break_down.id\r\n"
						+ "AND employee.id=operation_machine.employee_id\r\n"
						+ "AND capacity_study_details.operationmachine_id=operation_machine.id AND capacity_study_details.capacitystudy_id=:capacityId";

				Query qTwo = entityManager.createNativeQuery(queryTwo);
				qTwo.setParameter("operationBreakDownId", operationBreakDownId);
				qTwo.setParameter("capacityId", capacityId);

				List<Object[]> objectsForOperator = qTwo.getResultList();
				List<OperatorDataDto> operatorDataDtoList = new ArrayList<OperatorDataDto>();

				for (Object[] objForOperator : objectsForOperator) {
					OperatorDataDto operatorDataDto = new OperatorDataDto();

					operatorDataDto.setOperator(objForOperator[0] != null ? objForOperator[0].toString() : "");
					operatorDataDto.setAverage(objForOperator[1] != null ? (float) objForOperator[1] : 0);
					operatorDataDto.setCapacity(objForOperator[2] != null ? (int) objForOperator[2] : 0);
					operatorDataDto.setAllowance(objForOperator[3] != null ? (float) objForOperator[3] : 0);

					operatorDataDtoList.add(operatorDataDto);
				}

				capacityReportDto.setOperatorDataDto(operatorDataDtoList);
				CapacityReportDtoList.add(capacityReportDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, CapacityReportDtoList, CapacityReportDtoList.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getEmployeeProfileFromCapacityStudy(Long employeeId) {
		try {
			List<EmployeeProfileFromCapacityDto> employeeProfileFromCapacityDtoList = new ArrayList<EmployeeProfileFromCapacityDto>();

			String averageCapacityString = null;

			String query = "SELECT operation.id as operationId,operation.name as operationName,style.id as styleId,style.name as styleName,operation_break_down.smv,avg(capacity_study_details.capacity) as averageCapacity\r\n"
					+ "from capacity_study_details,operation_machine,operation_break_down,style,operation\r\n"
					+ "where capacity_study_details.operationbreakdown_id=operation_break_down.id\r\n"
					+ "and capacity_study_details.capacity!=0  \r\n"
					+ "and capacity_study_details.employee_id=:employeeId\r\n"
					+ "AND style.id=operation_break_down.style_id\r\n"
					+ "AND operation.id=operation_break_down.operation_id\r\n"
					+ "group by operation_break_down.operation_id,operation_break_down.style_id,operation_break_down.smv";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("employeeId", employeeId);

			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {
				EmployeeProfileFromCapacityDto employeeProfileFromCapacityDto = new EmployeeProfileFromCapacityDto();

				employeeProfileFromCapacityDto.setOperationId(obj[0] != null ? Long.parseLong(obj[0].toString()) : 0);
				employeeProfileFromCapacityDto.setOperation(obj[1] != null ? obj[1].toString() : "");
				employeeProfileFromCapacityDto.setStyleId(obj[2] != null ? Long.parseLong(obj[2].toString()) : 0);
				employeeProfileFromCapacityDto.setStyleName(obj[3] != null ? obj[3].toString() : "");
				employeeProfileFromCapacityDto.setSmv(obj[4] != null ? (float) obj[4] : 0);

				averageCapacityString = obj[5] != null ? obj[5].toString() : "0";

				float averageCapacity = Float.parseFloat(averageCapacityString);
				employeeProfileFromCapacityDto.setAverageCapacity(averageCapacity);

				String efficiency = String.valueOf(employeeProfileFromCapacityDto.getSmv()
						* (employeeProfileFromCapacityDto.getAverageCapacity() / 60) * 100);

				float efficiencyfloat = (float) DecimalValueConversion.getTwoValueAfterDot(efficiency);
				employeeProfileFromCapacityDto.setEfficiency(efficiencyfloat);

				employeeProfileFromCapacityDtoList.add(employeeProfileFromCapacityDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, employeeProfileFromCapacityDtoList,
					employeeProfileFromCapacityDtoList.size(),
					String.format("%s Graph Data Retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getOperationAnalysisCS(List<Long> orgIdList, List<String> orderEntityList) {
		try {
			List<OperationAnalysisCSDto> OperationAnalysisCSDtoList = new ArrayList<OperationAnalysisCSDto>();

			List<Organization> orgList = new ArrayList<Organization>();
			for (Long orgId : orgIdList) {
				List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
				orgList.addAll(organizationDetail);

				adminService.getOrganizationList(orgId, orgList);
			}

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			String organization = StringUtils.join(orgIds, ',');
			String orderEntityCondition = "";

			if (orderEntityList != null) {
				String orderList = orderEntityList.toString().replaceAll("(^\\[|\\]$)", "");
				String orderEntityConditionHead = " AND capacity_study.orderentity_id in (  ";
				String orderEntityConditionTail = "  ) ";
				orderEntityCondition += orderEntityConditionHead + orderList + orderEntityConditionTail;
			}

//			String query = "SELECT operation.id as oerationId, operation.name operation_name,style.id as styleId,style.name style_name,employee.employee_code ,employee.name as employee_name,\r\n"
//					+ "\r\n"
//					+ "round(avg(operation_break_down.smv),2) AS operation_smv,round(avg(capacity_study_details.cycle_time),2) AS operator_cycle_time,\r\n"
//					+ "\r\n"
//					+ "round(100*avg(operation_break_down.smv)/avg(capacity_study_details.cycle_time),2) AS efficiency, COUNT(*) AS data_number\r\n"
//					+ "\r\n"
//					+ "FROM capacity_study_details,operation_machine,operation,operation_break_down,style,employee\r\n"
//					+ "\r\n" + "WHERE capacity_study_details.operationmachine_id=operation_machine.id\r\n" + "\r\n"
//					+ "AND operation_machine.employee_id=employee.id\r\n" + "\r\n"
//					+ "AND operation_machine.operationbreakdown_id=operation_break_down.id\r\n" + "\r\n"
//					+ "AND operation_break_down.operation_id=operation.id\r\n" + "\r\n"
//					+ "AND operation_break_down.style_id=style.id\r\n" + "\r\n"
//					+ "AND capacity_study_details.cycle_time>0\r\n" + "\r\n" + "GROUP BY operation_break_down.id";

			String query = "SELECT operation.id as oerationId, operation.name operation_name,style.id as styleId,style.name style_name,employee.employee_code ,employee.name as employee_name, \r\n"
					+ "					  \r\n"
					+ "					 round(avg(operation_break_down.smv),2) AS operation_smv,round(avg(capacity_study_details.cycle_time),2) AS operator_cycle_time, \r\n"
					+ "					  \r\n"
					+ "					 round(100*avg(operation_break_down.smv)/avg(capacity_study_details.cycle_time),2) AS efficiency, COUNT(*) AS data_number \r\n"
					+ "					  \r\n"
					+ "					 FROM capacity_study,capacity_study_details,operation_machine,operation,operation_break_down,style,employee \r\n"
					+ "					 WHERE capacity_study.org_id in ( " + organization + " ) \r\n  "
					+ orderEntityCondition
					+ "                     AND capacity_study.id = capacity_study_details.capacitystudy_id\r\n"
					+ "                     AND capacity_study_details.operationmachine_id=operation_machine.id    \r\n"
					+ "					 AND operation_machine.employee_id=employee.id    \r\n"
					+ "					 AND operation_machine.operationbreakdown_id=operation_break_down.id    \r\n"
					+ "					 AND operation_break_down.operation_id=operation.id    \r\n"
					+ "					 AND operation_break_down.style_id=style.id    \r\n"
					+ "					 AND capacity_study_details.cycle_time>0      GROUP BY operation_break_down.id";

			Query q = entityManager.createNativeQuery(query);

			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {

				OperationAnalysisCSDto operationAnalysisCSDto = new OperationAnalysisCSDto();

				operationAnalysisCSDto.setOperationId(obj[0] != null ? Long.parseLong(obj[0].toString()) : 0);
				operationAnalysisCSDto.setOperationName(obj[1] != null ? obj[1].toString() : "");
				operationAnalysisCSDto.setStyleId(obj[2] != null ? Long.parseLong(obj[2].toString()) : 0);
				operationAnalysisCSDto.setStyleName(obj[3] != null ? obj[3].toString() : "");
				operationAnalysisCSDto.setEmployeeCode(obj[4] != null ? obj[4].toString() : "");
				operationAnalysisCSDto.setOperatorName(obj[5] != null ? obj[5].toString() : "");
				operationAnalysisCSDto.setOperationSmv(obj[6] != null ? Double.parseDouble(obj[6].toString()) : 0);
				operationAnalysisCSDto.setCycleTime(obj[7] != null ? Double.parseDouble(obj[7].toString()) : 0);
				operationAnalysisCSDto
						.setOperatorEfficiency(obj[8] != null ? Double.parseDouble(obj[8].toString()) : 0);
				operationAnalysisCSDto.setNumberOfData(obj[9] != null ? Integer.parseInt(obj[9].toString()) : 0);

				OperationAnalysisCSDtoList.add(operationAnalysisCSDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, OperationAnalysisCSDtoList,
					OperationAnalysisCSDtoList.size(), String.format("%s Graph Data Retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getOperatorCycleTimeTrends(Long emplyeeId, Long operationId, Long styleId) {
		try {
			List<OperatorCycleTimeTrendsDto> OperationAnalysisCSDtoList = new ArrayList<OperatorCycleTimeTrendsDto>();

			String query = "SELECT date_format(capacity_study_details.created_at, '%Y-%b-%d' ) AS study_date,\r\n"
					+ "round(avg(capacity_study_details.cycle_time),2) AS operator_cycle_time  \r\n"
					+ "FROM capacity_study_details,operation_break_down\r\n"
					+ "WHERE capacity_study_details.operationbreakdown_id=operation_break_down.id  \r\n"
					+ "AND capacity_study_details.employee_id=:emplyeeId \r\n"
					+ "AND operation_break_down.operation_id=:operationId\r\n"
					+ "AND operation_break_down.style_id=:styleId    \r\n" + "GROUP BY study_date";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("emplyeeId", emplyeeId);
			q.setParameter("operationId", operationId);
			q.setParameter("styleId", styleId);

			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {

				OperatorCycleTimeTrendsDto operationAnalysisCSDto = new OperatorCycleTimeTrendsDto();

				operationAnalysisCSDto.setStudyDate(obj[0] != null ? obj[0].toString() : "");
				operationAnalysisCSDto.setOperatorCyleTime(obj[1] != null ? Double.parseDouble(obj[1].toString()) : 0);

				OperationAnalysisCSDtoList.add(operationAnalysisCSDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, OperationAnalysisCSDtoList,
					OperationAnalysisCSDtoList.size(),
					String.format("%s OperatorCycleTimeTrends Data Retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getCapacityStudyGraph(Long capacityStudyId) {
		try {
			WorkStudyGraphDto workStudyGraphDto = getWorkStudyGraphDtoResponse(capacityStudyId);

//			String query = "SELECT table1.customer,table1.style,table1.linInfo,table1.Date,table1.days_Run,table1.graphInfo,table2.operator,table3.helper,\r\n"
//					+ "table1.TTLMP,table4.smv,table1.current_pcs,table1.studied_by,table1.cycle_time\r\n" + "FROM\r\n"
//					+ "(\r\n"
//					+ "SELECT capacity_study.id as csId, capacity_study.currentlineproduction as current_pcs,capacity_study.user_name as studied_by,capacity_study.created_at as Date,capacity_study.running_day as days_Run,\r\n"
//					+ "(\r\n" + "select COUNT(capacity_study.id) FROM capacity_study\r\n"
//					+ "WHERE capacity_study.created_at BETWEEN ( SELECT capacity_study.layout_start_date FROM capacity_study\r\n"
//					+ "WHERE capacity_study.id=:capacityStudyId )\r\n"
//					+ "AND ( SELECT capacity_study.created_at FROM capacity_study WHERE capacity_study.id=:capacityStudyId )\r\n"
//					+ "AND capacity_study.org_id = ( SELECT capacity_study.org_id FROM capacity_study WHERE capacity_study.id=:capacityStudyId )\r\n"
//					+ "\r\n"
//					+ ") as graphInfo,style.name as style,customer.name as customer,organizations.name as linInfo,COUNT(capacity_study_details.id) as TTLMP,FORMAT(SUM(capacity_study_details.cycle_time),2) as cycle_time\r\n"
//					+ "FROM capacity_study,order_entity,customer,style,organizations,capacity_study_details\r\n"
//					+ "WHERE capacity_study.id=:capacityStudyId\r\n"
//					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id\r\n"
//					+ "AND capacity_study.orderentity_id=order_entity.id\r\n" + "AND style.id=order_entity.style_id\r\n"
//					+ "AND customer.id=order_entity.customer_id\r\n" + "AND organizations.id=capacity_study.org_id\r\n"
//					+ "GROUP BY capacity_study_details.capacitystudy_id\r\n" + ") as table1\r\n" + "LEFT JOIN(\r\n"
//					+ "SELECT capacity_study.id as cs,COUNT(capacity_study_details.id) as operator,machine_type.small_name as machineName\r\n"
//					+ "FROM capacity_study,capacity_study_details,operation_break_down,machine_type\r\n"
//					+ "WHERE capacity_study.id=:capacityStudyId\r\n"
//					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id\r\n"
//					+ "AND operation_break_down.id=capacity_study_details.operationbreakdown_id\r\n"
//					+ "AND operation_break_down.machinetype_id=machine_type.id\r\n"
//					+ "AND machine_type.small_name NOT IN ('HP') ) as table2\r\n"
//					+ "ON table1.csId = table2.cs LEFT JOIN(\r\n"
//					+ "SELECT capacity_study.id as csid,COUNT(capacity_study_details.id) as helper\r\n"
//					+ "FROM capacity_study,capacity_study_details,operation_break_down,machine_type\r\n"
//					+ "WHERE capacity_study.id=:capacityStudyId\r\n"
//					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id\r\n"
//					+ "AND operation_break_down.id=capacity_study_details.operationbreakdown_id\r\n"
//					+ "AND operation_break_down.machinetype_id=machine_type.id\r\n"
//					+ "AND machine_type.small_name='HP' ) as table3 ON table3.csid=table1.csId\r\n" + "LEFT JOIN (\r\n"
//					+ "SELECT capacity_study.id as csId,FORMAT(SUM(operation_break_down.smv),2) as smv\r\n"
//					+ "FROM capacity_study,order_entity,style,operation_break_down WHERE\r\n"
//					+ "capacity_study.id=:capacityStudyId AND order_entity.id=capacity_study.orderentity_id\r\n"
//					+ "AND operation_break_down.style_id=style.id ) as table4\r\n" + "ON table4.csId=table1.csId\r\n"
//					+ "\r\n" + "\r\n" + "";
//
//			Query q = entityManager.createNativeQuery(query);
//			q.setParameter("capacityStudyId", capacityStudyId);
//
//			List<Object[]> objects = q.getResultList();
//
//			for (Object[] obj : objects) {
//
//				workStudyGraphDto.setBuyerName(obj[0] != null ? obj[0].toString() : "");
//				workStudyGraphDto.setStyleName(obj[1] != null ? obj[1].toString() : "");
//				workStudyGraphDto.setLineInfo(obj[2] != null ? obj[2].toString() : "");
//				workStudyGraphDto.setDate(obj[3] != null ? obj[3].toString() : "");
//				workStudyGraphDto.setDaysRun(obj[4] != null ? (int) obj[4] : 0);
//				workStudyGraphDto.setGraphInfo(obj[5] != null ? Integer.parseInt(obj[5].toString()) : 0);
//				workStudyGraphDto.setOperator(obj[6] != null ? Long.parseLong(obj[6].toString()) : 0);
//				workStudyGraphDto.setHelper(obj[7] != null ? Long.parseLong(obj[7].toString()) : 0);
//				workStudyGraphDto.setTtlMp(obj[8] != null ? Long.parseLong(obj[8].toString()) : 0);
//				workStudyGraphDto.setSmv(obj[9] != null ? Float.parseFloat(obj[9].toString()) : 0);
//				workStudyGraphDto.setCurrentPcs(obj[10] != null ? Integer.parseInt(obj[10].toString()) : 0);
//				workStudyGraphDto.setStudiedBy(obj[11] != null ? obj[11].toString() : "");
//				workStudyGraphDto.setCycleTime(obj[12] != null ? Float.parseFloat(obj[12].toString()) : 0);
//
//				if (workStudyGraphDto.getTtlMp() > 0) {
//					double bpt = DecimalValueConversion
//							.getFormattedDoubleValue(workStudyGraphDto.getCycleTime() / workStudyGraphDto.getTtlMp());
//					workStudyGraphDto.setBasicPitchTime(bpt);
//				} else {
//					workStudyGraphDto.setBasicPitchTime(0);
//				}
//
//				if (workStudyGraphDto.getBasicPitchTime() > 0) {
//					workStudyGraphDto.setWorkerPotential(Math.round(60 / workStudyGraphDto.getBasicPitchTime()));
//				} else {
//					workStudyGraphDto.setWorkerPotential(0);
//				}
//
//				if (workStudyGraphDto.getWorkerPotential() > 0) {
//					workStudyGraphDto.setProductivityGap(
//							Math.round((workStudyGraphDto.getCurrentPcs() - workStudyGraphDto.getWorkerPotential())
//									* 100 / workStudyGraphDto.getWorkerPotential()));
//				} else {
//					workStudyGraphDto.setProductivityGap(0);
//				}
//
//			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, workStudyGraphDto,
					String.format("%s Graph Data Retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private WorkStudyGraphDto getWorkStudyGraphDtoResponse(Long capacityStudyId) {
		WorkStudyGraphDto workStudyGraphDto = new WorkStudyGraphDto();
		try {
			String query = "SELECT table1.customer,table1.style,table1.linInfo,table1.Date,table1.days_Run,table1.graphInfo,table2.operator,table3.helper,\r\n"
					+ "table1.TTLMP,table4.smv,table1.current_pcs,table1.studied_by,table1.cycle_time\r\n" + "FROM\r\n"
					+ "(\r\n"
					+ "SELECT capacity_study.id as csId, capacity_study.currentlineproduction as current_pcs,capacity_study.user_name as studied_by,capacity_study.created_at as Date,capacity_study.running_day as days_Run,\r\n"
					+ "(\r\n" + "select COUNT(capacity_study.id) FROM capacity_study\r\n"
					+ "WHERE capacity_study.created_at BETWEEN ( SELECT capacity_study.layout_start_day FROM capacity_study\r\n"
					+ "WHERE capacity_study.id=:capacityStudyId )\r\n"
					+ "AND ( SELECT capacity_study.created_at FROM capacity_study WHERE capacity_study.id=:capacityStudyId )\r\n"
					+ "AND capacity_study.org_id = ( SELECT capacity_study.org_id FROM capacity_study WHERE capacity_study.id=:capacityStudyId )\r\n"
					+ "\r\n"
					+ ") as graphInfo,style.name as style,customer.name as customer,organizations.name as linInfo,COUNT(capacity_study_details.id) as TTLMP,FORMAT(SUM(capacity_study_details.cycle_time),2) as cycle_time\r\n"
					+ "FROM capacity_study,order_entity,customer,style,organizations,capacity_study_details\r\n"
					+ "WHERE capacity_study.id=:capacityStudyId\r\n"
					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id\r\n"
					+ "AND capacity_study.orderentity_id=order_entity.id\r\n" + "AND style.id=order_entity.style_id\r\n"
					+ "AND customer.id=order_entity.customer_id\r\n" + "AND organizations.id=capacity_study.org_id\r\n"
					+ "GROUP BY capacity_study_details.capacitystudy_id\r\n" + ") as table1\r\n" + "LEFT JOIN(\r\n"
					+ "SELECT capacity_study.id as cs,COUNT(capacity_study_details.id) as operator,machine_type.small_name as machineName\r\n"
					+ "FROM capacity_study,capacity_study_details,operation_break_down,machine_type\r\n"
					+ "WHERE capacity_study.id=:capacityStudyId\r\n"
					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id\r\n"
					+ "AND operation_break_down.id=capacity_study_details.operationbreakdown_id\r\n"
					+ "AND operation_break_down.machinetype_id=machine_type.id\r\n"
					+ "AND machine_type.small_name NOT IN ('HP') ) as table2\r\n"
					+ "ON table1.csId = table2.cs LEFT JOIN(\r\n"
					+ "SELECT capacity_study.id as csid,COUNT(capacity_study_details.id) as helper\r\n"
					+ "FROM capacity_study,capacity_study_details,operation_break_down,machine_type\r\n"
					+ "WHERE capacity_study.id=:capacityStudyId\r\n"
					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id\r\n"
					+ "AND operation_break_down.id=capacity_study_details.operationbreakdown_id\r\n"
					+ "AND operation_break_down.machinetype_id=machine_type.id\r\n"
					+ "AND machine_type.small_name='HP' ) as table3 ON table3.csid=table1.csId\r\n" + "LEFT JOIN (\r\n"
					+ "SELECT capacity_study.id as csId,FORMAT(SUM(operation_break_down.smv),2) as smv\r\n"
					+ "FROM capacity_study,order_entity,operation_break_down WHERE\r\n"
					+ "capacity_study.id=:capacityStudyId AND order_entity.id=capacity_study.orderentity_id\r\n"
					+ "AND operation_break_down.style_id=order_entity.style_id) as table4\r\n"
					+ "ON table4.csId=table1.csId\r\n" + "\r\n" + "\r\n" + "";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("capacityStudyId", capacityStudyId);

			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {

				workStudyGraphDto.setBuyerName(obj[0] != null ? obj[0].toString() : "");
				workStudyGraphDto.setStyleName(obj[1] != null ? obj[1].toString() : "");
				workStudyGraphDto.setLineInfo(obj[2] != null ? obj[2].toString() : "");
				workStudyGraphDto.setDate(obj[3] != null ? obj[3].toString() : "");
				workStudyGraphDto.setDaysRun(obj[4] != null ? (int) obj[4] : 0);
				workStudyGraphDto.setGraphInfo(obj[5] != null ? Integer.parseInt(obj[5].toString()) : 0);
				workStudyGraphDto.setOperator(obj[6] != null ? Long.parseLong(obj[6].toString()) : 0);
				workStudyGraphDto.setHelper(obj[7] != null ? Long.parseLong(obj[7].toString()) : 0);
				workStudyGraphDto.setTtlMp(obj[8] != null ? Long.parseLong(obj[8].toString()) : 0);
				workStudyGraphDto.setSmv(obj[9] != null ? Float.parseFloat(obj[9].toString()) : 0);
				workStudyGraphDto.setCurrentPcs(obj[10] != null ? Integer.parseInt(obj[10].toString()) : 0);
				workStudyGraphDto.setStudiedBy(obj[11] != null ? obj[11].toString() : "");
				workStudyGraphDto.setCycleTime(obj[12] != null ? Float.parseFloat(obj[12].toString()) : 0);

				if (workStudyGraphDto.getTtlMp() > 0) {
					double bpt = DecimalValueConversion
							.getFormattedDoubleValue(workStudyGraphDto.getCycleTime() / workStudyGraphDto.getTtlMp());
					workStudyGraphDto.setBasicPitchTime(bpt);
				} else {
					workStudyGraphDto.setBasicPitchTime(0);
				}

				if (workStudyGraphDto.getBasicPitchTime() > 0) {
					workStudyGraphDto.setWorkerPotential(Math.round(60 / workStudyGraphDto.getBasicPitchTime()));
				} else {
					workStudyGraphDto.setWorkerPotential(0);
				}

				if (workStudyGraphDto.getWorkerPotential() > 0) {
					workStudyGraphDto.setProductivityGap(
							Math.round((workStudyGraphDto.getCurrentPcs() - workStudyGraphDto.getWorkerPotential())
									* 100 / workStudyGraphDto.getWorkerPotential()));
				} else {
					workStudyGraphDto.setProductivityGap(0);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return workStudyGraphDto;
	}

	public Response getNewsFeed(Long orgId) {
		try {
			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = OrganizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);

			adminService.getOrganizationList(orgId, orgList);

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			List<CapacityStudy> completeCapacityStudy = capacityStudyRepository
					.findAllCsByOrgIdAndStatusIsComplete(orgIds);

			List<WorkStudyGraphDto> workStudyGraphDtoList = new ArrayList<WorkStudyGraphDto>();

			for (CapacityStudy capacityStudy : completeCapacityStudy) {
				WorkStudyGraphDto workStudyGraphDto = getWorkStudyGraphDtoResponse(capacityStudy.getId());
				workStudyGraphDtoList.add(workStudyGraphDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, workStudyGraphDtoList, workStudyGraphDtoList.size(),
					String.format("%s News Feed Data Retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getNewsFeedHeaderInfo(Long orgId) {
		try {
			List<NewsFeedHeaderInfoDto> newsFeedHeaderInfoDtoList = new ArrayList<NewsFeedHeaderInfoDto>();
			List<DailyHeaderInfoDto> dailyHeaderInfoDtoList = new ArrayList<DailyHeaderInfoDto>();
			NewsFeedHeaderInfoDto newsFeedHeaderInfoDto = new NewsFeedHeaderInfoDto();

			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = OrganizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);

			adminService.getOrganizationList(orgId, orgList);

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			String query = "select tab1.full_name,tab1.user_name,tab1.date,date_format(tab1.date,'%M , %Y') as monthData,max(tab1.total) as totalCount,date_format(tab1.date,'%U') as weekData,date_format(tab1.date,'%m') as monthNumber  \r\n"
					+ "from  \r\n"
					+ "(select date_format(capacity_study.created_at,'%Y-%m-%d') as date,capacity_study.user_name,users.full_name,count(*) as total  \r\n"
					+ "from capacity_study,users  \r\n" + "where capacity_study.`status` = 'complete'  \r\n"
					+ "and capacity_study.org_id in(7,9)  \r\n" + "and capacity_study.active = 1  \r\n"
					+ "and capacity_study.user_name = users.user_id  \r\n"
					+ "GROUP BY date,capacity_study.user_name,users.full_name   \r\n"
					+ "order by date,total DESC) as tab1  \r\n" + "GROUP BY tab1.date  \r\n" + "order by date DESC";

			Query q = entityManager.createNativeQuery(query);
			List<Object[]> objects = q.getResultList();

			String monthName = "";
			int previousMonthNumber = 0;
			Map<String, Integer> monthPerformer = new HashMap<>();
			int monthCount = 0;
			int i = 0;
			int objSize = objects.size();
			// List<LinkedHashMap> allMonthDataList = new ArrayList<LinkedHashMap>();
//			LinkedHashMap<String, String> monthData = null;
			for (Object[] obj : objects) {
//				DailyHeaderInfoDto monthHeaderInfoDto = new DailyHeaderInfoDto();
				DailyHeaderInfoDto dailyHeaderInfoDto = new DailyHeaderInfoDto();

				if (Integer.parseInt(obj[6].toString()) == previousMonthNumber) {
					if (monthPerformer.containsKey(obj[1].toString())) {
						monthPerformer.put(obj[1].toString(),
								monthPerformer.get(obj[1].toString()) + (Integer.parseInt(obj[4].toString())));
					} else {
						monthPerformer.put(obj[1].toString(), Integer.parseInt(obj[4].toString()));
					}
				} else {
					if (previousMonthNumber != 0) {
						DailyHeaderInfoDto monthHeaderInfoDto = new DailyHeaderInfoDto();

//						monthHeaderInfoDto.setName(monthName);
//						monthHeaderInfoDto.setName(monthPerformer.get);
						Map.Entry<String, Integer> maxEntry = null;
						int sum = 0;
						for (Map.Entry<String, Integer> entry : monthPerformer.entrySet()) {

							if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
								maxEntry = entry;
							}
							sum += entry.getValue();
						}

//						monthHeaderInfoDto.setUserName(maxEntry.getKey()+" # "+maxEntry.getValue());
						monthHeaderInfoDto.setUserName(maxEntry.getKey());
//						monthHeaderInfoDto.setDateData(monthName);
//						monthHeaderInfoDto.setDailyTotal(sum);
						monthHeaderInfoDto.setMonthData(monthName);
						monthHeaderInfoDto.setMonthTotal(sum);

						dailyHeaderInfoDtoList.add(monthHeaderInfoDto);
					}

					monthPerformer.clear();
					monthPerformer.put(obj[1].toString(), Integer.parseInt(obj[4].toString()));

				}

				previousMonthNumber = Integer.parseInt(obj[6].toString());
				monthName = obj[3].toString();
				i++;

				if (objSize == i) {
					DailyHeaderInfoDto monthHeaderInfoDto = new DailyHeaderInfoDto();

//					monthHeaderInfoDto.setName(monthName);
//					monthHeaderInfoDto.setName(obj[0].toString());
					Map.Entry<String, Integer> maxEntry = null;
					int sum = 0;
					for (Map.Entry<String, Integer> entry : monthPerformer.entrySet()) {

						if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
							maxEntry = entry;
						}
						sum += entry.getValue();
					}

//					monthHeaderInfoDto.setUserName(maxEntry.getKey()+" # "+maxEntry.getValue());
					monthHeaderInfoDto.setUserName(maxEntry.getKey());
//					monthHeaderInfoDto.setDateData(monthName);
//					monthHeaderInfoDto.setDailyTotal(sum);
					monthHeaderInfoDto.setMonthData(monthName);
					monthHeaderInfoDto.setMonthTotal(sum);

					dailyHeaderInfoDtoList.add(monthHeaderInfoDto);
				}

				// DailyHeaderInfoDto dailyHeaderInfoDto = new DailyHeaderInfoDto();

//				LinkedHashMap<String, String> monthData = new LinkedHashMap<String, String>();
//				
//				monthData.put(obj[3].toString(), obj[4].toString());

//				dailyHeaderInfoDto.setName(obj[0] != null ? obj[0].toString() : "");
				dailyHeaderInfoDto.setUserName(obj[1] != null ? obj[1].toString() : "");
				dailyHeaderInfoDto.setDateData(obj[2] != null ? obj[2].toString() : "");
				dailyHeaderInfoDto.setDailyTotal(Integer.parseInt(obj[4].toString()));

				dailyHeaderInfoDtoList.add(dailyHeaderInfoDto);
				// allMonthDataList.add(monthData);

			}
			// newsFeedHeaderInfoDto.setDailyHeaderInfoDtoList(dailyHeaderInfoDtoList);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, dailyHeaderInfoDtoList,
					dailyHeaderInfoDtoList.size(), String.format("%s News Feed Data Retrieved Successfully", root));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getCapacityStudyGraph2(Long capacityStudyId) {
		try {
			Integer tgt = capacityStudyRepository.findTargetById(capacityStudyId);
			// System.out.println(tgt.toString());

			String query = "SELECT table1.operationName,table1.machineName,table2.operator,table2.cycle_time,table2.capacity  \r\n"
					+ "FROM(        \r\n"
					+ "SELECT capacity_study.id as csId, capacity_study_details.id as csdid,capacity_study_details.operationbreakdown_id as opbd,operation.name as operationName,machine_type.small_name as machineName   \r\n"
					+ "FROM capacity_study,capacity_study_details,operation_break_down,operation,machine_type    \r\n"
					+ "WHERE capacity_study.id=:capacityStudyId \r\n"
					+ "AND capacity_study_details.capacitystudy_id=capacity_study.id    \r\n"
					+ "AND operation_break_down.id=capacity_study_details.operationbreakdown_id    \r\n"
					+ "AND operation.id=operation_break_down.operation_id    \r\n"
					+ "AND machine_type.id=operation_break_down.machinetype_id  \r\n"
					+ "GROUP BY capacity_study_details.operationbreakdown_id  \r\n"
					+ "ORDER BY capacity_study_details.operationbreakdown_id ASC) as table1  \r\n"
					+ "LEFT JOIN(        \r\n"
					+ "SELECT capacity_study_details.operationbreakdown_id as opbd,employee.name as operator,capacity_study_details.cycle_time as cycle_time,capacity_study_details.capacity as capacity  \r\n"
					+ "FROM operation_break_down,employee,capacity_study_details    WHERE     \r\n"
					+ "capacity_study_details.operationbreakdown_id=operation_break_down.id    \r\n"
					+ "AND employee.id=capacity_study_details.employee_id    \r\n"
					+ "AND capacity_study_details.capacitystudy_id=:capacityStudyId       \r\n"
					+ ") as table2  ON table1.opbd = table2.opbd";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("capacityStudyId", capacityStudyId);

			List<Object[]> objects = q.getResultList();

			LinkedHashMap<String, String> operationMap = new LinkedHashMap<String, String>();
			operationMap.put("Header", "Operation");

			LinkedHashMap<String, String> tgtMap = new LinkedHashMap<String, String>();
			tgtMap.put("Header", "TGT");

			LinkedHashMap<String, String> serialMap = new LinkedHashMap<String, String>();
			serialMap.put("Header", "SL");

			LinkedHashMap<String, String> machineMap = new LinkedHashMap<String, String>();
			machineMap.put("Header", "MC");

			List<LinkedHashMap<String, String>> operatorMap = new ArrayList<>();
			List<LinkedHashMap<String, String>> cycleTimeMap = new ArrayList<>();
			List<LinkedHashMap<String, String>> capacityMap = new ArrayList<>();
			List<HashMap> list = new ArrayList<HashMap>();
			int i = 0, j = 0, k = 0, l = 0;

			HashMap<String, String> operationTestMap = new HashMap<String, String>();

			for (Object[] obj : objects) {

				if (!operationMap.containsKey(obj[0].toString())) {
					j++;
					operationMap.put(obj[0].toString(), obj[0].toString());
					serialMap.put(obj[0].toString(), "" + j);
				}

				if (!machineMap.containsKey(obj[0].toString())) {
					machineMap.put(obj[0].toString(), obj[1].toString());
				}
				if (!tgtMap.containsKey(obj[0].toString())) {
					tgtMap.put(obj[0].toString(), tgt.toString());
				}

				boolean operatorFound = false;
				boolean cycleTimeFound = false;
				boolean capacityFound = false;

				for (LinkedHashMap<String, String> om : operatorMap) {
					if (!om.containsKey(obj[0].toString())) {
						om.put(obj[0].toString(), obj[2].toString());
						operatorFound = true;
						break;
					}
				}
				if (!operatorFound) {
					i++;
					LinkedHashMap<String, String> op = new LinkedHashMap<String, String>();
					op.put("Header", "OPT" + i);
					op.put(obj[0].toString(), obj[2].toString());
					operatorMap.add(op);
				}

				for (LinkedHashMap<String, String> om : cycleTimeMap) {
					if (!om.containsKey(obj[0].toString())) {
						om.put(obj[0].toString(), obj[3].toString());
						cycleTimeFound = true;
						break;
					}
				}
				if (!cycleTimeFound) {
					k++;
					LinkedHashMap<String, String> ct = new LinkedHashMap<String, String>();
					ct.put("Header", "CT" + k);
					ct.put(obj[0].toString(), obj[3].toString());
					cycleTimeMap.add(ct);
				}

				for (LinkedHashMap<String, String> om : capacityMap) {
					if (!om.containsKey(obj[0].toString())) {
						om.put(obj[0].toString(), obj[4].toString());
						capacityFound = true;
						break;
					}
				}
				if (!capacityFound) {
					l++;
					LinkedHashMap<String, String> cph = new LinkedHashMap<String, String>();
					cph.put("Header", "CPH" + l);
					cph.put(obj[0].toString(), obj[4].toString());
					capacityMap.add(cph);
				}

			}

			list.add(serialMap);
			list.add(tgtMap);

			Collections.reverse(capacityMap);
			Collections.reverse(cycleTimeMap);
			Collections.reverse(operatorMap);

			for (LinkedHashMap obj : capacityMap) {
				list.add(obj);
			}
			for (LinkedHashMap obj : cycleTimeMap) {
				list.add(obj);
			}
			for (LinkedHashMap obj : operatorMap) {
				list.add(obj);
			}
			list.add(machineMap);
			list.add(operationMap);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, list,
					String.format("%s Graph Data Retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<InCompleteCsDto> getResponseIncompleteCsDtoList(List<CapacityStudy> capacityStudys, String userName) {

		List<InCompleteCsDto> incompleteCsResponseDtoList = new ArrayList<>();

		for (CapacityStudy capacityStudy : capacityStudys) {

			InCompleteCsDto incompleteCsDto = new InCompleteCsDto();

			BeanUtils.copyProperties(capacityStudy, incompleteCsDto);

			if (capacityStudy.getOrderEntity() != null) {
				OrderEntityDto orderEntityDto = new OrderEntityDto();
				BeanUtils.copyProperties(capacityStudy.getOrderEntity(), orderEntityDto);
				orderEntityDto.setOrganization(null);
				incompleteCsDto.setOrderEntity(orderEntityDto);
			}

			if (capacityStudy.getSharedUser() != null) {
				incompleteCsDto.setSharedUser(capacityStudy.getSharedUser().getUserName());
			}

			List<Object[]> objects = capacityStudyRepository.findExtraInfoOfIncompleteCs(incompleteCsDto.getId());

			for (Object[] obj : objects) {
				StyleDto style = new StyleDto();
				incompleteCsDto.setPerformanceLine(obj[0].toString());
				incompleteCsDto.setBuyer(obj[1].toString());
				style.setName(obj[2].toString());
				incompleteCsDto.setStudyBy(obj[3].toString());
				style.setId(Long.parseLong(obj[4].toString()));
				incompleteCsDto.setStyle(style);
			}

			List<Object[]> objects2 = capacityStudyRepository.findPercentageOfComplete(incompleteCsDto.getId());

			for (Object[] obj2 : objects2) {
				int totalOpbdInCs = 0;
				int total = 0;
				if (obj2[0] != null) {
					totalOpbdInCs = Integer.parseInt(obj2[0].toString());
				}
				if (obj2[1] != null) {
					total = Integer.parseInt(obj2[1].toString());
				}

				if (total > 0) {
					double percentOfComplete = DecimalValueConversion
							.getFormattedDoubleValue((double) (totalOpbdInCs * 100) / total);
					incompleteCsDto.setPercentageOfComplete(String.valueOf(percentOfComplete));
				}
			}

			if (capacityStudy.getCapacityStudyDetails() != null) {
				List<CapacityStudyDetailsDto> capacityStudyDetailsDtoList = new ArrayList<CapacityStudyDetailsDto>();

				for (CapacityStudyDetails capacityStudyDetails : capacityStudy.getCapacityStudyDetails()) {
					CapacityStudyDetailsDto capacityStudyDetailsDto = new CapacityStudyDetailsDto();
					BeanUtils.copyProperties(capacityStudyDetails, capacityStudyDetailsDto);

					if (capacityStudyDetails.getOperationBreakDown() != null) {
						float allowance = operationBreakDownRepository
								.findAllowanceById(capacityStudyDetails.getOperationBreakDown().getId());
						capacityStudyDetailsDto.setAllowance(allowance);
					}

					if (capacityStudyDetails.getOperationMachine() != null) {
						OperationMachineDto operationMachineDto = new OperationMachineDto();
						EmployeeDto employeeDto = new EmployeeDto();
						OperationBreakDownDto operationBreakDownDto = new OperationBreakDownDto();

						BeanUtils.copyProperties(capacityStudyDetails.getOperationMachine(), operationMachineDto);
						BeanUtils.copyProperties(capacityStudyDetails.getOperationMachine().getEmployee(), employeeDto);
						BeanUtils.copyProperties(capacityStudyDetails.getOperationMachine().getOperationBreakDown(),
								operationBreakDownDto);

						operationMachineDto.setEmployee(employeeDto);
						operationMachineDto.setOperationBreakDown(operationBreakDownDto);
						operationMachineDto.setOrganization(null);
						capacityStudyDetailsDto.setOperationMachine(operationMachineDto);
					}

					if (capacityStudyDetails.getWorkStudy() != null) {
						List<WorkStudyDto> workStudyDtoDtoList = new ArrayList<WorkStudyDto>();

						for (WorkStudy workStudy : capacityStudyDetails.getWorkStudy()) {
							WorkStudyDto workStudyDto = new WorkStudyDto();
							BeanUtils.copyProperties(workStudy, workStudyDto);

							if (workStudy.getLabHistory() != null) {
								List<LabHistoryDto> labHistoryDtoDtoList = new ArrayList<LabHistoryDto>();

								for (LabHistory labHistory : workStudy.getLabHistory()) {
									LabHistoryDto labHistoryDto = new LabHistoryDto();
									BeanUtils.copyProperties(labHistory, labHistoryDto);
									labHistoryDtoDtoList.add(labHistoryDto);
								}
								workStudyDto.setLabHistory(labHistoryDtoDtoList);
							}
							workStudyDtoDtoList.add(workStudyDto);
						}
						capacityStudyDetailsDto.setWorkStudy(workStudyDtoDtoList);
					}
					capacityStudyDetailsDtoList.add(capacityStudyDetailsDto);
				}
				incompleteCsDto.setCapacityStudyDetails(capacityStudyDetailsDtoList);
			}

			String studyDate = LocalDateTimeConversion.getStringFromLocalDateTime(capacityStudy.getCreatedAt(),
					"yyyy-MM-dd'T'HH:mm:ss");
			incompleteCsDto.setStudyDate(studyDate);

			if (capacityStudy.getLayoutStartDay() != null) {
				incompleteCsDto.setLayoutStartDay(LocalDateTimeConversion
						.getStringFromLocalDateTime(capacityStudy.getLayoutStartDay(), "yyyy-MM-dd'T'HH:mm:ss"));
			}

			incompleteCsResponseDtoList.add(incompleteCsDto);

		}

		return incompleteCsResponseDtoList;
	}

	private List<CapacityStudyDto> getResponseDtoList(List<CapacityStudy> capacityStudys) {

		List<CapacityStudyDto> responseDtoList = new ArrayList<>();
		capacityStudys.forEach(capacityStudy -> {
			CapacityStudyDto capacityStudyDto = new CapacityStudyDto();

			if (capacityStudy.getOrderEntity() != null) {
				capacityStudy.getOrderEntity().setStyle(null);
				capacityStudy.getOrderEntity().setCustomer(null);
				capacityStudy.getOrderEntity().setVarience(null);
				capacityStudy.getOrderEntity().setOrganization(null);
			}

			modelMapper.map(capacityStudy, capacityStudyDto);
			// BeanUtils.copyProperties(capacityStudy, capacityStudyDto);

			String studyDate = LocalDateTimeConversion.getStringFromLocalDateTime(capacityStudy.getCreatedAt(),
					"yyyy-MM-dd'T'HH:mm:ss");
			capacityStudyDto.setStudyDate(studyDate);
			responseDtoList.add(capacityStudyDto);

		});

		return responseDtoList;
	}

	private CapacityStudyDto getResponseDtoforSaveAndUpdate(CapacityStudy capacityStudy) {

		CapacityStudyDto capacityStudyDto = new CapacityStudyDto();

		BeanUtils.copyProperties(capacityStudy, capacityStudyDto);

		if (capacityStudy.getOrderEntity() != null) {
			OrderEntityDto orderEntityDto = new OrderEntityDto();
			BeanUtils.copyProperties(capacityStudy.getOrderEntity(), orderEntityDto);
			orderEntityDto.setOrganization(null);
			capacityStudyDto.setOrderEntity(orderEntityDto);
		}

		if (capacityStudy.getSharedUser() != null) {
			UserDto userDto = new UserDto();
			userDto.setUserName(capacityStudy.getSharedUser().getUserName());
			capacityStudyDto.setSharedUser(userDto);
		}

		if (capacityStudy.getCapacityStudyDetails() != null) {
			List<CapacityStudyDetailsDto> capacityStudyDetailsDtoList = new ArrayList<CapacityStudyDetailsDto>();

			for (CapacityStudyDetails capacityStudyDetails : capacityStudy.getCapacityStudyDetails()) {
				CapacityStudyDetailsDto capacityStudyDetailsDto = new CapacityStudyDetailsDto();
				BeanUtils.copyProperties(capacityStudyDetails, capacityStudyDetailsDto);

				if (capacityStudyDetails.getOperationMachine() != null) {
					OperationMachineDto operationMachineDto = new OperationMachineDto();
					EmployeeDto employeeDto = new EmployeeDto();
					OperationBreakDownDto operationBreakDownDto = new OperationBreakDownDto();

					BeanUtils.copyProperties(capacityStudyDetails.getOperationMachine(), operationMachineDto);
					BeanUtils.copyProperties(capacityStudyDetails.getOperationMachine().getEmployee(), employeeDto);
					BeanUtils.copyProperties(capacityStudyDetails.getOperationMachine().getOperationBreakDown(),
							operationBreakDownDto);

					operationMachineDto.setEmployee(employeeDto);
					operationMachineDto.setOperationBreakDown(operationBreakDownDto);
					operationMachineDto.setOrganization(null);
					capacityStudyDetailsDto.setOperationMachine(operationMachineDto);
				}

				if (capacityStudyDetails.getWorkStudy() != null) {
					List<WorkStudyDto> workStudyDtoDtoList = new ArrayList<WorkStudyDto>();

					for (WorkStudy workStudy : capacityStudyDetails.getWorkStudy()) {
						WorkStudyDto workStudyDto = new WorkStudyDto();
						BeanUtils.copyProperties(workStudy, workStudyDto);

						if (workStudy.getLabHistory() != null) {
							List<LabHistoryDto> labHistoryDtoDtoList = new ArrayList<LabHistoryDto>();

							for (LabHistory labHistory : workStudy.getLabHistory()) {
								LabHistoryDto labHistoryDto = new LabHistoryDto();
								BeanUtils.copyProperties(labHistory, labHistoryDto);
								labHistoryDtoDtoList.add(labHistoryDto);
							}
							workStudyDto.setLabHistory(labHistoryDtoDtoList);
						}
						workStudyDtoDtoList.add(workStudyDto);
					}
					capacityStudyDetailsDto.setWorkStudy(workStudyDtoDtoList);
				}
				capacityStudyDetailsDtoList.add(capacityStudyDetailsDto);
			}
			capacityStudyDto.setCapacityStudyDetails(capacityStudyDetailsDtoList);
		}

		String studyDate = LocalDateTimeConversion.getStringFromLocalDateTime(capacityStudy.getCreatedAt(),
				"yyyy-MM-dd'T'HH:mm:ss");
		capacityStudyDto.setStudyDate(studyDate);

		if (capacityStudy.getLayoutStartDay() != null) {
			capacityStudyDto.setLayoutStartDay(LocalDateTimeConversion
					.getStringFromLocalDateTime(capacityStudy.getLayoutStartDay(), "yyyy-MM-dd'T'HH:mm:ss"));
		}

		return capacityStudyDto;
	}

	private List<CompleteCSListDto> getResponseCompleteCSListDto(List<CapacityStudy> capacityStudys) {

		List<CompleteCSListDto> responseDtoList = new ArrayList<>();
		capacityStudys.forEach(capacityStudy -> {
			CompleteCSListDto completeCSListDto = new CompleteCSListDto();

			if (capacityStudy.getOrderEntity() != null) {
				capacityStudy.getOrderEntity().setVarience(null);
				capacityStudy.getOrderEntity().setOrganization(null);
			}

			BeanUtils.copyProperties(capacityStudy, completeCSListDto);

			if (capacityStudy.getOrderEntity() != null) {
				OrderEntityDto orderEntityDto = new OrderEntityDto();
				BeanUtils.copyProperties(capacityStudy.getOrderEntity(), orderEntityDto);

				if (capacityStudy.getOrderEntity().getStyle() != null) {
					StyleDto styleDto = new StyleDto();
					styleDto.setName(capacityStudy.getOrderEntity().getStyle().getName());
					orderEntityDto.setStyle(styleDto);
				}
				if (capacityStudy.getOrderEntity().getCustomer() != null) {
					CustomerDto customerDto = new CustomerDto();
					customerDto.setName(capacityStudy.getOrderEntity().getCustomer().getName());
					orderEntityDto.setCustomer(customerDto);
				}
				completeCSListDto.setOrderEntity(orderEntityDto);
			}

//			modelMapper.map(capacityStudy, completeCSListDto);
			// BeanUtils.copyProperties(capacityStudy, capacityStudyDto);

			String studyDate = LocalDateTimeConversion.getStringFromLocalDateTime(capacityStudy.getCreatedAt(),
					"yyyy-MM-dd'T'HH:mm:ss");
			completeCSListDto.setStudyDate(studyDate);
			responseDtoList.add(completeCSListDto);

		});

		return responseDtoList;
	}

}
