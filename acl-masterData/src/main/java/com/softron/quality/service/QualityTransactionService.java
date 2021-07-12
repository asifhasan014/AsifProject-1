package com.softron.quality.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.admin.service.FileUploadService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.dto.OperationMachineDto;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.repository.ClientRepository;
import com.softron.masterdata.repository.DefectRepository;
import com.softron.masterdata.repository.EmployeeRepository;
import com.softron.masterdata.repository.OperationBreakDownRepository;
import com.softron.masterdata.repository.OperationMachineRepository;
import com.softron.masterdata.repository.OperationRepository;
import com.softron.masterdata.repository.OrderEntityRepository;
import com.softron.masterdata.repository.SectionRepository;
import com.softron.masterdata.repository.StyleRepository;
import com.softron.masterdata.repository.TargetAndManpowerRepository;
import com.softron.masterdata.repository.WorkProcessRepository;
import com.softron.quality.dto.DefectAnalysisReportDto;
import com.softron.quality.dto.GraphDataDto;
import com.softron.quality.dto.LineWiseCurrentOrderDto;
import com.softron.quality.dto.QcPassReportDto;
import com.softron.quality.dto.QualityDefectDto;
import com.softron.quality.dto.QualityDefectDto2;
import com.softron.quality.dto.QualityTransactionDto;
import com.softron.quality.dto.QualityTrendDto;
import com.softron.quality.dto.TrendListDto;
import com.softron.quality.repository.QualityDefectRepository;
import com.softron.quality.repository.QualityTransactionRepository;
import com.softron.quality.repository.QualityTypeRepository;
import com.softron.quality.repository.VarienceRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.qualitymodule.entity.QualityDefect;
import com.softron.schema.qualitymodule.entity.QualityTransaction;
import com.softron.schema.qualitymodule.entity.QualityType;
import com.softron.schema.qualitymodule.entity.Varience;
import com.softron.utils.LocalDateTimeConversion;

@Service
public class QualityTransactionService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	QualityTransactionRepository qualityTransactionRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	WorkProcessRepository workProcessRepository;

	@Autowired
	SectionRepository sectionRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	QualityTypeRepository qualityTypeRepository;

	@Autowired
	StyleRepository styleRepository;

	@Autowired
	DefectRepository defectRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	OperationRepository operationRepository;

	@Autowired
	VarienceRepository varienceRepository;

	@Autowired
	OperationBreakDownRepository operationBreakDownRepository;

	@Autowired
	QualityDefectRepository qualityDefectRepository;

	@Autowired
	OperationMachineRepository operationMachineRepository;

	@Autowired
	OrderEntityRepository orderEntityRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	AdminService adminService;

	@Autowired
	TargetAndManpowerRepository targetAndManpowerRepository;
	
	@Autowired
	FileUploadService fileUploadService;

	String root = "QualityTransaction";

	public Response create(List<QualityTransactionDto> qualityTransactionDtoList,String userName) {
		try {
			List<Long> qualityTransactionIdList = new ArrayList<Long>();
			int tlsStatus = 0;

			for (QualityTransactionDto qualityTransactionDto : qualityTransactionDtoList) {

				QualityTransaction qualityTransaction = new QualityTransaction();

				BeanUtils.copyProperties(qualityTransactionDto, qualityTransaction);

				if (qualityTransactionDto.getWorkProcess() != null) {
					Optional<WorkProcess> workProcess = workProcessRepository
							.findById(qualityTransactionDto.getWorkProcess().getId());
					qualityTransaction.setWorkProcess(workProcess.get());
				}

				if (qualityTransactionDto.getQualityType() != null) {
					Optional<QualityType> qualityType = qualityTypeRepository
							.findById(qualityTransactionDto.getQualityType().getId());
					qualityTransaction.setQualityType(qualityType.get());
				}

				if (qualityTransactionDto.getOrderEntity() != null) {
					Optional<OrderEntity> orderEntity = orderEntityRepository
							.findById(qualityTransactionDto.getOrderEntity().getId());
					qualityTransaction.setOrderEntity(orderEntity.get());
				}

				if (qualityTransactionDto.getOperation() != null) {
					Optional<Operation> operation = operationRepository
							.findById(qualityTransactionDto.getOperation().getId());
					qualityTransaction.setOperation(operation.get());

				}
				if (qualityTransactionDto.getEmployee() != null) {
					Optional<Employee> employee = employeeRepository
							.findById(qualityTransactionDto.getEmployee().getId());

					Employee emploueeObj = employee.get();
					tlsStatus = emploueeObj.getTlsStatus();

					if (qualityTransaction.getQualityType().getType() == 3) {
						if (qualityTransactionDto.getDefectiveItem() > 0 && tlsStatus < 2) {
							tlsStatus = tlsStatus + 1;
							emploueeObj.setTlsStatus(tlsStatus);
						}
						if (qualityTransactionDto.getDefectiveItem() == 0 && tlsStatus > 0) {
							tlsStatus = tlsStatus - 1;
							emploueeObj.setTlsStatus(tlsStatus);
						}
					}

					qualityTransaction.setEmployee(emploueeObj);
				}

				if (qualityTransactionDto.getVarience() != null) {
					Optional<Varience> varience = varienceRepository
							.findById(qualityTransactionDto.getVarience().getId());
					qualityTransaction.setVarience(varience.get());
				}

				List<QualityDefect> qualityDefectList = new ArrayList<QualityDefect>();

				if (qualityTransactionDto.getNewQualityDefect() != null) {
					for (QualityDefectDto2 qualityDefectDto2 : qualityTransactionDto.getNewQualityDefect()) {
						QualityDefect qualityDefect = new QualityDefect();
						if (qualityDefectDto2.getDefect() != null) {
							Optional<Defect> defect = defectRepository.findById(qualityDefectDto2.getDefect().getId());
							qualityDefect.setDefect(defect.get());
						}
						if (qualityDefectDto2.getOperation() != null) {
							Optional<Operation> operation = operationRepository
									.findById(qualityDefectDto2.getOperation().getId());
							qualityDefect.setOperation(operation.get());
						}
						if (qualityDefectDto2.getOrganization() != null) {
							Optional<Organization> organization = organizationRepository
									.findById(qualityDefectDto2.getOrganization().getId());
							qualityDefect.setOrganization(organization.get());
						}

						qualityDefect.setQualityTransaction(qualityTransaction);
						qualityDefectList.add(qualityDefect);
					}
					qualityTransaction.setQualityDefect(qualityDefectList);
				}

				if (qualityTransactionDto.getOrganization() != null) {
					Optional<Organization> organization = organizationRepository
							.findById(qualityTransactionDto.getOrganization().getId());
					qualityTransaction.setOrganization(organization.get());

				}

				qualityTransaction.setCreatedUser(userName);
				qualityTransactionRepository.save(qualityTransaction);

				if (qualityTransactionDto.getNewCreateAtString() != null) {
					LocalDateTime createAt = LocalDateTimeConversion.getLocalDateTimeFromString(
							qualityTransactionDto.getNewCreateAtString(), "yyyy-MM-dd'T'HH:mm:ss");
					qualityTransaction.setCreatedAt(createAt);
					qualityTransactionRepository.save(qualityTransaction);
				}

				if (qualityTransactionDto.getOrganization() != null) {
					Organization org = organizationRepository
							.findByIdAndActiveTrue(qualityTransactionDto.getOrganization().getId());
					org.setIsLine(true);
					organizationRepository.save(org);
				}

				qualityTransactionIdList.add(qualityTransaction.getId());

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, qualityTransactionIdList,
					String.format("%s created successfully", root));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
			List<QualityTransaction> qualityTransaction = qualityTransactionRepository.findAllByActiveTrue();
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(qualityTransaction),
					qualityTransaction.size(), String.format("All %ses", root));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			QualityTransaction qualityTransaction = qualityTransactionRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			QualityTransactionDto qualityTransactionDto = new QualityTransactionDto();
			System.out.println(qualityTransaction.getDateTime());
			BeanUtils.copyProperties(qualityTransaction, qualityTransactionDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, qualityTransactionDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {

		try {
			QualityTransaction qualityTransaction = qualityTransactionRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			qualityTransaction.setActive(false);
			for (QualityDefect qualityDefect : qualityTransaction.getQualityDefect()) {
				qualityDefect.setActive(false);
			}
			qualityTransactionRepository.save(qualityTransaction);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, QualityTransactionDto qualityTransactionDto) {
		try {
			QualityTransaction qualityTransaction = qualityTransactionRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			if (qualityTransactionDto.getWorkProcess() != null) {
				WorkProcess workProcessValue = new WorkProcess();
				BeanUtils.copyProperties(qualityTransactionDto.getWorkProcess(), workProcessValue);
				qualityTransaction.setWorkProcess(workProcessValue);
			}

			if (qualityTransactionDto.getOrderEntity() != null) {
				OrderEntity orderEntitty = new OrderEntity();
				BeanUtils.copyProperties(qualityTransactionDto.getOrderEntity(), orderEntitty);
				qualityTransaction.setOrderEntity(orderEntitty);
			}

			if (qualityTransactionDto.getOperation() != null) {
				Operation operationValue = new Operation();
				BeanUtils.copyProperties(qualityTransactionDto.getOperation(), operationValue);
				qualityTransaction.setOperation(operationValue);
			}

			if (qualityTransactionDto.getEmployee() != null) {
				Employee employeeValue = new Employee();
				BeanUtils.copyProperties(qualityTransactionDto.getEmployee(), employeeValue);
				qualityTransaction.setEmployee(employeeValue);
			}

			if (qualityTransactionDto.getVarience() != null) {
				Varience varienceValue = new Varience();
				BeanUtils.copyProperties(qualityTransactionDto.getVarience(), varienceValue);
				qualityTransaction.setVarience(varienceValue);
			}

			if (qualityTransactionDto.getQualityType() != null) {
				QualityType qualityTypeValue = new QualityType();
				BeanUtils.copyProperties(qualityTransactionDto.getQualityType(), qualityTypeValue);
				qualityTransaction.setQualityType(qualityTypeValue);
			}

			List<QualityDefect> QualityDefectListValue = new ArrayList<QualityDefect>();
			if (qualityTransactionDto.getQualityDefect() != null) {
				for (QualityDefectDto qualityDefectDtoValue : qualityTransactionDto.getQualityDefect()) {
					QualityDefect qualityDefectValue = new QualityDefect();
					BeanUtils.copyProperties(qualityDefectDtoValue, qualityDefectValue);
					QualityDefectListValue.add(qualityDefectValue);
				}

				qualityTransaction.setQualityDefect(QualityDefectListValue);
			}

			if (qualityTransaction.getWorkProcess() != null) {
				Optional<WorkProcess> workProcess = workProcessRepository
						.findById(qualityTransactionDto.getWorkProcess().getId());
				qualityTransaction.setWorkProcess(workProcess.get());
			}

			if (qualityTransaction.getQualityType() != null) {
				Optional<QualityType> qualityType = qualityTypeRepository
						.findById(qualityTransactionDto.getQualityType().getId());
				qualityTransaction.setQualityType(qualityType.get());
			}

			if (qualityTransaction.getOrderEntity() != null) {
				Optional<OrderEntity> orderEntity = orderEntityRepository
						.findById(qualityTransactionDto.getOrderEntity().getId());
				qualityTransaction.setOrderEntity(orderEntity.get());
			}

			if (qualityTransaction.getOperation() != null) {
				Optional<Operation> operation = operationRepository
						.findById(qualityTransactionDto.getOperation().getId());
				qualityTransaction.setOperation(operation.get());

			}
			if (qualityTransaction.getEmployee() != null) {
				Optional<Employee> employee = employeeRepository.findById(qualityTransactionDto.getEmployee().getId());
				qualityTransaction.setEmployee(employee.get());
			}

			if (qualityTransaction.getVarience() != null) {
				Optional<Varience> varience = varienceRepository.findById(qualityTransactionDto.getVarience().getId());
				qualityTransaction.setVarience(varience.get());
			}
			if (qualityTransaction.getQualityDefect() != null) {
				for (QualityDefect qualityDefectList : qualityTransaction.getQualityDefect()) {
					qualityDefectList.setQualityTransaction(qualityTransaction);
				}
			}

			if (qualityTransaction.getOrganization() != null) {
				Optional<Organization> organization = organizationRepository
						.findById(qualityTransactionDto.getOrganization().getId());
				qualityTransaction.setOrganization(organization.get());
			}

			qualityTransaction.setSampleSize(qualityTransactionDto.getSampleSize());
			qualityTransaction.setDefectiveItem(qualityTransaction.getDefectiveItem());

			qualityTransactionRepository.save(qualityTransaction);

			if (qualityTransactionDto.getOrganization() != null) {
				Organization org = organizationRepository
						.findByIdAndActiveTrue(qualityTransactionDto.getOrganization().getId());
				org.setIsLine(true);
				organizationRepository.save(org);
			}

			BeanUtils.copyProperties(qualityTransaction, qualityTransactionDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, qualityTransactionDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response totalCheck(Date startDate, Date endDate, Long orgId) {
		try {
			QualityTransactionDto qualityTransactionDto = new QualityTransactionDto();

			Long totalCheckValue, qcPassValue, alterValue, rejectValue;
			totalCheckValue = qcPassValue = alterValue = rejectValue = null;

			if (orgId == null) {

				totalCheckValue = qualityTransactionRepository.totalCheck(startDate, endDate);
				qcPassValue = qualityTransactionRepository.totalQcPass(startDate, endDate);
				alterValue = qualityTransactionRepository.totalAlter(startDate, endDate);
				rejectValue = qualityTransactionRepository.totalReject(startDate, endDate);

				qualityTransactionDto.setTotalCheck(totalCheckValue != null ? totalCheckValue : 0);
				qualityTransactionDto.setTotalQcPass(qcPassValue != null ? qcPassValue : 0);
				qualityTransactionDto.setTotalAlter(alterValue != null ? alterValue : 0);
				qualityTransactionDto.setTotalReject(rejectValue != null ? rejectValue : 0);

			} else {

				totalCheckValue = qualityTransactionRepository.totalCheck(startDate, endDate, orgId);
				qcPassValue = qualityTransactionRepository.totalQcPass(startDate, endDate, orgId);
				alterValue = qualityTransactionRepository.totalAlter(startDate, endDate, orgId);
				rejectValue = qualityTransactionRepository.totalReject(startDate, endDate, orgId);

				qualityTransactionDto.setTotalCheck(totalCheckValue != null ? totalCheckValue : 0);
				qualityTransactionDto.setTotalQcPass(qcPassValue != null ? qcPassValue : 0);
				qualityTransactionDto.setTotalAlter(alterValue != null ? alterValue : 0);
				qualityTransactionDto.setTotalReject(rejectValue != null ? rejectValue : 0);

			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, qualityTransactionDto,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getTrends(String startDate, String endDate, String timeUnit, Long orgId) {
		try {

			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date startDateDateType = null;
			Date endDateDateType = null;
			try {
				startDateDateType = formatter1.parse(startDate);
				endDateDateType = formatter1.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Long totalCheckValue, alterValue, rejectValue, dhuCount;
			totalCheckValue = alterValue = rejectValue = dhuCount = null;
			QualityTrendDto trendResult = new QualityTrendDto();

			List<Double> alterPercentageList = new ArrayList<Double>();
			List<Double> rejectPercentList = new ArrayList<Double>();
			List<Double> dhuPercentList = new ArrayList<Double>();

			Calendar start = Calendar.getInstance();
			start.setTime(startDateDateType);
			Calendar end = Calendar.getInstance();
			end.setTime(endDateDateType);

			int condition;
			Double allresult = 0.0;
			switch (timeUnit) {
			case "month":
				condition = Calendar.MONTH;
				break;
			case "date":
				condition = Calendar.DATE;
				break;
			case "hour":
				condition = Calendar.HOUR;
				break;
			default:
				condition = 0;
				break;
			}

			if (condition > 0) {

				if (orgId != null) {

					for (Date date = start.getTime(); start.before(end); start.add(condition,
							1), date = start.getTime()) {
						totalCheckValue = qualityTransactionRepository.totalCheck(date, endDateDateType);
						alterValue = qualityTransactionRepository.totalAlter(date, endDateDateType);
						rejectValue = qualityTransactionRepository.totalReject(date, endDateDateType);
						dhuCount = qualityTransactionRepository.dhuCount(date, endDateDateType);

						if (totalCheckValue != null && alterValue != null) {
							Double result = (double) (alterValue * 100 / totalCheckValue);
							alterPercentageList.add(result);
						} else {
							alterPercentageList.add(allresult);
						}
						if (totalCheckValue != null && rejectValue != null) {
							Double result2 = (double) (rejectValue * 100 / totalCheckValue);
							rejectPercentList.add(result2);
						} else {
							rejectPercentList.add(allresult);
						}
						if (totalCheckValue != null && dhuCount != null) {
							Double result3 = (double) (dhuCount * 100 / totalCheckValue);
							dhuPercentList.add(result3);
						} else {
							dhuPercentList.add(allresult);
						}

					}

					trendResult.setAlter(alterPercentageList);
					trendResult.setReject(rejectPercentList);
					trendResult.setDHU(dhuPercentList);

				} else {
					for (Date date = start.getTime(); start.before(end); start.add(condition,
							1), date = start.getTime()) {
						totalCheckValue = qualityTransactionRepository.totalCheck(date, endDateDateType, orgId);
						alterValue = qualityTransactionRepository.totalAlter(date, endDateDateType, orgId);
						rejectValue = qualityTransactionRepository.totalReject(date, endDateDateType, orgId);
						dhuCount = qualityTransactionRepository.dhuCount(date, endDateDateType, orgId);

						if (totalCheckValue != null && alterValue != null) {
							Double result = (double) (alterValue * 100 / totalCheckValue);
							alterPercentageList.add(result);
						} else {
							alterPercentageList.add(allresult);
						}
						if (totalCheckValue != null && rejectValue != null) {
							Double result2 = (double) (rejectValue * 100 / totalCheckValue);
							rejectPercentList.add(result2);
						} else {
							rejectPercentList.add(allresult);
						}
						if (totalCheckValue != null && dhuCount != null) {
							Double result3 = (double) (dhuCount * 100 / totalCheckValue);
							dhuPercentList.add(result3);
						} else {
							dhuPercentList.add(allresult);
						}

					}

					trendResult.setAlter(alterPercentageList);
					trendResult.setReject(rejectPercentList);
					trendResult.setDHU(dhuPercentList);
				}

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, trendResult,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getTrendsGraphValue(String startDate, String endDate, String timeUnit) {
		try {

			String condition;
			Double allresult = 0.0;
			switch (timeUnit) {
			case "month":
				condition = "%Y-%m";
				break;
			case "date":
				condition = "%Y-%m-%d";
				break;
			case "hour":
				condition = "%Y-%m-%d-%h";
				break;
			default:
				condition = "%Y-%m-%d-%h";
				break;
			}

			TrendListDto trend = new TrendListDto();

			String query = "SELECT t_section.name,  ROUND(100*t_ok.total_ok/total.total_check, 2) as ok_per , ROUND(100*t_alter.total_alter/total.total_check, 2) as alter_per , ROUND(100*t_reject.total_reject/total.total_check, 2) as reject_per,   \r\n"
					+ "							ROUND(100*t_dhu.dhu/total.total_check, 2) as dhu_per, total.Q_date   \r\n"
					+ "							from  \r\n" + "							(  \r\n"
					+ "							    SELECT date_format( quality_transaction.created_at, :condition ) AS Q_date , quality_transaction.section_id, SUM(quality_transaction.sample_size) as total_check  \r\n"
					+ "							    FROM quality_transaction,quality_type  \r\n"
					+ "							    WHERE quality_type.type=1    \r\n"
					+ "							    AND quality_transaction.qualitytype_id=quality_type.id  \r\n"
					+ "							    AND quality_transaction.created_at   \r\n"
					+ "							    BETWEEN :startDate  AND :endDate     \r\n"
					+ "							    GROUP BY Q_date,quality_transaction.section_id  \r\n"
					+ "							) as total  \r\n" + "							left JOIN  \r\n"
					+ "							(  \r\n"
					+ "							    SELECT date_format( quality_transaction.created_at, :condition ) AS Q_date , quality_transaction.section_id, SUM(quality_transaction.sample_size)  as total_ok  \r\n"
					+ "							    FROM quality_transaction,quality_type  \r\n"
					+ "							    WHERE quality_type.type=1    \r\n"
					+ "							    AND quality_transaction.qualitytype_id=quality_type.id  \r\n"
					+ "							    AND quality_transaction.created_at   \r\n"
					+ "							    BETWEEN :startDate  AND :endDate     \r\n"
					+ "							    AND quality_transaction.check_output='ok'   \r\n"
					+ "							    GROUP BY Q_date,quality_transaction.section_id  \r\n"
					+ "							      \r\n" + "							 ) as t_ok  \r\n"
					+ "							 on total.section_id=t_ok.section_id  AND total.Q_date=t_ok.Q_date \r\n"
					+ "							 LEFT JOIN  \r\n" + "							 (\r\n"
					+ "							 	SELECT date_format( quality_transaction.created_at, :condition ) AS Q_date , quality_transaction.section_id, SUM(quality_transaction.sample_size)  as total_alter  \r\n"
					+ "							    FROM quality_transaction,quality_type  \r\n"
					+ "							    WHERE quality_type.type=1    \r\n"
					+ "							    AND quality_transaction.qualitytype_id=quality_type.id  \r\n"
					+ "							    AND quality_transaction.created_at   \r\n"
					+ "							    BETWEEN :startDate  AND :endDate     \r\n"
					+ "							    AND quality_transaction.check_output='alter'   \r\n"
					+ "							    GROUP BY Q_date,quality_transaction.section_id  \r\n"
					+ "							 ) as t_alter  \r\n"
					+ "							 on total.section_id=t_alter.section_id  AND total.Q_date=t_alter.Q_date\r\n"
					+ "							 LEFT JOIN  \r\n" + "							 (\r\n"
					+ "							 	SELECT date_format( quality_transaction.created_at, :condition ) AS Q_date , quality_transaction.section_id, SUM(quality_transaction.sample_size)  as total_reject  \r\n"
					+ "							    FROM quality_transaction,quality_type  \r\n"
					+ "							    WHERE quality_type.type=1    \r\n"
					+ "							    AND quality_transaction.qualitytype_id=quality_type.id  \r\n"
					+ "							    AND quality_transaction.created_at   \r\n"
					+ "							    BETWEEN :startDate  AND :endDate     \r\n"
					+ "							    AND quality_transaction.check_output='reject'   \r\n"
					+ "							    GROUP BY Q_date,quality_transaction.section_id  \r\n"
					+ "							 ) as t_reject  \r\n"
					+ "							 on total.section_id=t_reject.section_id  AND total.Q_date=t_reject.Q_date\r\n"
					+ "							  LEFT JOIN  \r\n" + "							  (\r\n"
					+ "							 	SELECT date_format( quality_transaction.created_at, :condition ) AS Q_date , quality_transaction.section_id,  COUNT(*) as dhu   \r\n"
					+ "							    from quality_transaction,quality_defect,quality_type   \r\n"
					+ "							    where quality_transaction.id=quality_defect.qualitytransaction_id  \r\n"
					+ "							    AND quality_type.type=1   \r\n"
					+ "							    AND quality_transaction.qualitytype_id=quality_type.id  \r\n"
					+ "							    and quality_transaction.created_at BETWEEN :startDate  AND :endDate   \r\n"
					+ "							    GROUP BY Q_date,quality_transaction.section_id    \r\n"
					+ "							 ) as t_dhu  \r\n"
					+ "							 on total.section_id=t_dhu.section_id  AND total.Q_date=t_dhu.Q_date\r\n"
					+ "							 LEFT JOIN  \r\n" + "							 (\r\n"
					+ "							 	SELECT section.id, section.name  \r\n"
					+ "							    FROM section  \r\n"
					+ "							 ) as t_section   \r\n"
					+ "							 on total.section_id=t_section.id";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			q.setParameter("condition", condition);
			List<Object[]> objects = q.getResultList();

			Map<String, Double> alter_average = new HashMap<String, Double>();
			Map<String, Double> reject_average = new HashMap<String, Double>();
			Map<String, Double> dhu_average = new HashMap<String, Double>();
			String TimeValue;

			for (Object[] obj : objects) {
				GraphDataDto alter = new GraphDataDto();
				GraphDataDto reject = new GraphDataDto();
				GraphDataDto dhu = new GraphDataDto();

				alter.setSectionName(obj[0] == null ? "" : obj[0].toString());
				reject.setSectionName(obj[0] == null ? "" : obj[0].toString());
				dhu.setSectionName(obj[0] == null ? "" : obj[0].toString());

				TimeValue = obj[5] == null ? "" : obj[5].toString();

				alter.setTime(TimeValue);
				reject.setTime(TimeValue);
				dhu.setTime(TimeValue);

				alter.setValue(obj[2] == null ? "0" : obj[2].toString());
				reject.setValue(obj[3] == null ? "0" : obj[3].toString());
				dhu.setValue(obj[4] == null ? "0" : obj[4].toString());

				trend.getAlterList().add(alter);
				trend.getRejectList().add(reject);
				trend.getDhuList().add(dhu);

				if (alter_average.containsKey(TimeValue)) {
					alter_average.put(TimeValue, (alter_average.get(TimeValue)
							+ (obj[2] == null ? alter_average.get(TimeValue) : Double.parseDouble(obj[2].toString())))
							/ 2);
				} else {
					alter_average.put(TimeValue, obj[2] == null ? 0.0 : Double.parseDouble(obj[2].toString()));
				}

				if (reject_average.containsKey(TimeValue)) {
					reject_average.put(TimeValue, (reject_average.get(TimeValue)
							+ (obj[3] == null ? reject_average.get(TimeValue) : Double.parseDouble(obj[3].toString())))
							/ 2);
				} else {
					reject_average.put(TimeValue, obj[3] == null ? 0.0 : Double.parseDouble(obj[3].toString()));
				}

				if (dhu_average.containsKey(TimeValue)) {
					dhu_average.put(TimeValue, (dhu_average.get(TimeValue)
							+ (obj[4] == null ? dhu_average.get(TimeValue) : Double.parseDouble(obj[4].toString())))
							/ 2);
				} else {
					dhu_average.put(TimeValue, obj[4] == null ? 0.0 : Double.parseDouble(obj[4].toString()));
				}

			}

			alter_average.forEach((k, v) -> {
				GraphDataDto average = new GraphDataDto();
				average.setAlterAverage(Double.toString(alter_average.get(k)));
				average.setRejctAverage(Double.toString(reject_average.get(k)));
				average.setDhuAverage(Double.toString(dhu_average.get(k)));
				average.setTime(k);
				trend.getAverage().add(average);
			});

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, trend,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getQualityTrendsGraph(Long orgId, String startDate, String endDate, String timeUnit) {
		try {

			String condition;
			Double allresult = 0.0;
			switch (timeUnit) {
			case "month":
				condition = "%Y-%m";
				break;
			case "date":
				condition = "%Y-%m-%d";
				break;
			case "hour":
				condition = "%Y-%m-%d-%H";
				break;
			default:
				condition = "%Y-%m-%d-%H";
				break;
			}

			TrendListDto trend = new TrendListDto();
			
			Optional<Organization> organizationOptionalValue = organizationRepository.findById(orgId);
			Organization organizationValue = organizationOptionalValue.get();

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

			String query = "SELECT\r\n" + 
					"    ROUND(\r\n" + 
					"        100 * t_ok.total_ok / total.total_check,\r\n" + 
					"        2\r\n" + 
					"    ) AS ok_per,\r\n" + 
					"    ROUND(\r\n" + 
					"        100 * t_alter.total_alter / total.total_check,\r\n" + 
					"        2\r\n" + 
					"    ) AS alter_per,\r\n" + 
					"    ROUND(\r\n" + 
					"        100 * t_reject.total_reject / total.total_check,\r\n" + 
					"        2\r\n" + 
					"    ) AS reject_per,\r\n" + 
					"    ROUND(\r\n" + 
					"        100 * t_dhu.dhu / total.total_check,\r\n" + 
					"        2\r\n" + 
					"    ) AS dhu_per,\r\n" + 
					"    total.Q_date\r\n" + 
					"FROM\r\n" + 
					"    (\r\n" + 
					"    SELECT\r\n" + 
					"        DATE_FORMAT(\r\n" + 
					"            quality_transaction.created_at,\r\n" + 
					"            :condition\r\n" + 
					"        ) AS Q_date,\r\n" + 
					"        SUM(\r\n" + 
					"            quality_transaction.sample_size\r\n" + 
					"        ) AS total_check\r\n" + 
					"    FROM\r\n" + 
					"        quality_transaction,\r\n" + 
					"        quality_type\r\n" + 
					"    WHERE\r\n" + 
					"        quality_type.type = 1 AND quality_transaction.qualitytype_id = quality_type.id AND quality_transaction.created_at BETWEEN :startDate AND :endDate  and quality_transaction.org_id in (\r\n" + 
					organization+")\r\n" + 
					"    GROUP BY\r\n" + 
					"        Q_date\r\n" + 
					") AS total\r\n" + 
					"LEFT JOIN(\r\n" + 
					"    SELECT\r\n" + 
					"        DATE_FORMAT(\r\n" + 
					"            quality_transaction.created_at,\r\n" + 
					"            :condition\r\n" + 
					"        ) AS Q_date,(quality_transaction.org_id) As srId,\r\n" + 
					"        SUM(\r\n" + 
					"            quality_transaction.sample_size\r\n" + 
					"        ) AS total_ok\r\n" + 
					"    FROM\r\n" + 
					"        quality_transaction,\r\n" + 
					"        quality_type\r\n" + 
					"    WHERE\r\n" + 
					"        quality_type.type = 1 AND quality_transaction.qualitytype_id = quality_type.id AND quality_transaction.created_at BETWEEN :startDate AND :endDate AND quality_transaction.check_output = 'ok'  and quality_transaction.org_id in (\r\n" + 
					organization+")\r\n" + 
					"    GROUP BY\r\n" + 
					"        Q_date\r\n" + 
					") AS t_ok\r\n" + 
					"ON\r\n" + 
					"    total.Q_date = t_ok.Q_date \r\n" + 
					"LEFT JOIN(\r\n" + 
					"    SELECT DATE_FORMAT(\r\n" + 
					"            quality_transaction.created_at,\r\n" + 
					"            :condition\r\n" + 
					"        ) AS Q_date,\r\n" + 
					"        SUM(\r\n" + 
					"            quality_transaction.sample_size\r\n" + 
					"        ) AS total_alter\r\n" + 
					"    FROM\r\n" + 
					"        quality_transaction,\r\n" + 
					"        quality_type\r\n" + 
					"    WHERE\r\n" + 
					"        quality_type.type = 1 AND quality_transaction.qualitytype_id = quality_type.id AND quality_transaction.created_at BETWEEN :startDate AND :endDate AND quality_transaction.check_output = 'alter'  and quality_transaction.org_id in (\r\n" + 
					organization+")\r\n" + 
					"    GROUP BY\r\n" + 
					"        Q_date\r\n" + 
					") AS t_alter\r\n" + 
					"ON\r\n" + 
					"    total.Q_date = t_alter.Q_date\r\n" + 
					"LEFT JOIN(\r\n" + 
					"    SELECT DATE_FORMAT(\r\n" + 
					"            quality_transaction.created_at,\r\n" + 
					"            :condition\r\n" + 
					"        ) AS Q_date,\r\n" + 
					"        SUM(\r\n" + 
					"            quality_transaction.sample_size\r\n" + 
					"        ) AS total_reject\r\n" + 
					"    FROM\r\n" + 
					"        quality_transaction,\r\n" + 
					"        quality_type\r\n" + 
					"    WHERE\r\n" + 
					"        quality_type.type = 1 AND quality_transaction.qualitytype_id = quality_type.id AND quality_transaction.created_at BETWEEN :startDate AND :endDate AND quality_transaction.check_output = 'reject'  and quality_transaction.org_id in (\r\n" + 
					organization+")\r\n" + 
					"    GROUP BY\r\n" + 
					"        Q_date\r\n" + 
					") AS t_reject\r\n" + 
					"ON\r\n" + 
					"    total.Q_date = t_reject.Q_date\r\n" + 
					"LEFT JOIN(\r\n" + 
					"    SELECT\r\n" + 
					"        DATE_FORMAT(\r\n" + 
					"            quality_transaction.created_at,\r\n" + 
					"            :condition\r\n" + 
					"        ) AS Q_date,\r\n" + 
					"        COUNT(*) AS dhu\r\n" + 
					"    FROM\r\n" + 
					"        quality_transaction,\r\n" + 
					"        quality_type,\r\n" + 
					"        quality_defect\r\n" + 
					"    WHERE\r\n" + 
					"        quality_type.type = 1 AND quality_transaction.qualitytype_id = quality_type.id AND quality_transaction.created_at BETWEEN :startDate AND :endDate AND quality_transaction.id = quality_defect.qualitytransaction_id  and quality_transaction.org_id in (\r\n" + 
					organization+")\r\n" + 
					"    GROUP BY\r\n" + 
					"        Q_date\r\n" + 
					") AS t_dhu\r\n" + 
					"ON\r\n" + 
					"    total.Q_date = t_dhu.Q_date";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			q.setParameter("condition", condition);
			List<Object[]> objects = q.getResultList();
			
//			System.out.println(query);

			Map<String, Double> alter_average = new HashMap<String, Double>();
			Map<String, Double> reject_average = new HashMap<String, Double>();
			Map<String, Double> dhu_average = new HashMap<String, Double>();
			String TimeValue;

			for (Object[] obj : objects) {
				GraphDataDto alter = new GraphDataDto();
				GraphDataDto reject = new GraphDataDto();
				GraphDataDto dhu = new GraphDataDto();

				alter.setOrganization(organizationValue.getName());
				reject.setOrganization(organizationValue.getName());
				dhu.setOrganization(organizationValue.getName());

				TimeValue = obj[4] == null ? "" : obj[4].toString();

				alter.setTime(TimeValue);
				reject.setTime(TimeValue);
				dhu.setTime(TimeValue);

				alter.setValue(obj[1] == null ? "0" : obj[1].toString());
				reject.setValue(obj[2] == null ? "0" : obj[2].toString());
				dhu.setValue(obj[3] == null ? "0" : obj[3].toString());

				trend.getAlterList().add(alter);
				trend.getRejectList().add(reject);
				trend.getDhuList().add(dhu);

//				if (alter_average.containsKey(TimeValue)) {
//					alter_average.put(TimeValue, (alter_average.get(TimeValue)
//							+ (obj[2] == null ? alter_average.get(TimeValue) : Double.parseDouble(obj[2].toString())))
//							/ 2);
//				} else {
//					alter_average.put(TimeValue, obj[2] == null ? 0.0 : Double.parseDouble(obj[2].toString()));
//				}
//
//				if (reject_average.containsKey(TimeValue)) {
//					reject_average.put(TimeValue, (reject_average.get(TimeValue)
//							+ (obj[3] == null ? reject_average.get(TimeValue) : Double.parseDouble(obj[3].toString())))
//							/ 2);
//				} else {
//					reject_average.put(TimeValue, obj[3] == null ? 0.0 : Double.parseDouble(obj[3].toString()));
//				}
//
//				if (dhu_average.containsKey(TimeValue)) {
//					dhu_average.put(TimeValue, (dhu_average.get(TimeValue)
//							+ (obj[4] == null ? dhu_average.get(TimeValue) : Double.parseDouble(obj[4].toString())))
//							/ 2);
//				} else {
//					dhu_average.put(TimeValue, obj[4] == null ? 0.0 : Double.parseDouble(obj[4].toString()));
//				}

			}

//			alter_average.forEach((k, v) -> {
//				GraphDataDto average = new GraphDataDto();
//				average.setAlterAverage(Double.toString(alter_average.get(k)));
//				average.setRejctAverage(Double.toString(reject_average.get(k)));
//				average.setDhuAverage(Double.toString(dhu_average.get(k)));
//				average.setTime(k);
//				trend.getAverage().add(average);
//			});

			trend.setOrgList(null);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, trend,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getQualityBarChart(Long orgId, String startDate, String endDate) {
		try {

			List<GraphDataDto> getBarChatList = new ArrayList<GraphDataDto>();

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

			String query = "SELECT\r\n" + "    t_name.name,t_name.root_path,\r\n" + "    total.total_check,\r\n"
					+ "    t_ok.total_ok,\r\n" + "    t_alter.total_alter,\r\n" + "    t_reject.total_reject\r\n"
					+ "FROM\r\n" + "    (\r\n" + "    SELECT\r\n" + "        quality_transaction.org_id,\r\n"
					+ "        SUM(\r\n" + "            quality_transaction.sample_size\r\n"
					+ "        ) AS total_check\r\n" + "    FROM\r\n" + "        quality_transaction,\r\n"
					+ "        quality_type\r\n" + "    WHERE\r\n" + "        quality_type.type = 1 \r\n"
					+ "        AND quality_transaction.qualitytype_id = quality_type.id \r\n"
					+ "        AND quality_transaction.created_at BETWEEN :startDate AND :endDate\r\n"
					+ "        and quality_transaction.org_id in (\r\n" + organization + ")\r\n" + "    GROUP BY\r\n"
					+ "        quality_transaction.org_id\r\n" + ") AS total\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.org_id,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS total_ok\r\n" + "    FROM\r\n"
					+ "        quality_transaction,\r\n" + "        quality_type\r\n" + "    WHERE\r\n"
					+ "        quality_type.type = 1 \r\n"
					+ "    AND quality_transaction.qualitytype_id = quality_type.id \r\n"
					+ "    AND quality_transaction.check_output = 'ok' \r\n"
					+ "    AND quality_transaction.created_at BETWEEN :startDate AND :endDate\r\n"
					+ "    and quality_transaction.org_id in (\r\n" + organization +

					")\r\n" + "    GROUP BY\r\n" + "        quality_transaction.org_id\r\n" + ") AS t_ok\r\n" + "ON\r\n"
					+ "    total.org_id = t_ok.org_id\r\n" + "LEFT JOIN(\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.org_id,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS total_alter\r\n"
					+ "    FROM\r\n" + "        quality_transaction,\r\n" + "        quality_type\r\n" + "    WHERE\r\n"
					+ "        quality_type.type = 1 \r\n"
					+ "    AND quality_transaction.qualitytype_id = quality_type.id \r\n"
					+ "    AND quality_transaction.check_output = 'alter' \r\n"
					+ "    AND quality_transaction.created_at BETWEEN :startDate AND :endDate\r\n"
					+ "    and quality_transaction.org_id in (\r\n" + organization +

					")\r\n" + "    GROUP BY\r\n" + "        quality_transaction.org_id\r\n" + ") AS t_alter\r\n"
					+ "ON\r\n" + "    total.org_id = t_alter.org_id\r\n" + "LEFT JOIN(\r\n"
					+ "    SELECT quality_transaction.org_id,\r\n" + "        SUM(\r\n"
					+ "            quality_transaction.sample_size\r\n" + "        ) AS total_reject\r\n"
					+ "    FROM\r\n" + "        quality_transaction,\r\n" + "        quality_type\r\n" + "    WHERE\r\n"
					+ "        quality_type.type = 1 \r\n"
					+ "    AND quality_transaction.qualitytype_id = quality_type.id \r\n"
					+ "    AND quality_transaction.check_output = 'reject' \r\n"
					+ "    AND quality_transaction.created_at BETWEEN :startDate AND :endDate\r\n"
					+ "    and quality_transaction.org_id in (\r\n" + organization +

					")\r\n" + "    GROUP BY\r\n" + "        quality_transaction.org_id\r\n" + ") AS t_reject\r\n"
					+ "ON\r\n" + "    total.org_id = t_reject.org_id\r\n"
					+ "LEFT JOIN (SELECT id, name,root_path FROM organizations ) as t_name on total.org_id=t_name.id";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {

				GraphDataDto TotalCheck = new GraphDataDto();
				GraphDataDto TotalOk = new GraphDataDto();
				GraphDataDto TotalAlter = new GraphDataDto();
				GraphDataDto TotalReject = new GraphDataDto();
				GraphDataDto listOrg = new GraphDataDto();

				TotalCheck.setOrganization(obj[0] == null ? "" : obj[0].toString());
				TotalCheck.setType("Total Check");
				TotalCheck.setValue(obj[2] == null ? "0" : obj[2].toString());

				TotalOk.setOrganization(obj[0] == null ? "" : obj[0].toString());
				TotalOk.setType("QC Pass");
				TotalOk.setValue(obj[3] == null ? "0" : obj[3].toString());

				TotalAlter.setOrganization(obj[0] == null ? "" : obj[0].toString());
				TotalAlter.setType("Alter");
				TotalAlter.setValue(obj[4] == null ? "0" : obj[4].toString());

				TotalReject.setOrganization(obj[0] == null ? "" : obj[0].toString());
				TotalReject.setType("Reject");
				TotalReject.setValue(obj[5] == null ? "0" : obj[5].toString());

				listOrg.setOrganizationList(orgNames);

				getBarChatList.add(TotalCheck);
				getBarChatList.add(TotalOk);
				getBarChatList.add(TotalAlter);
				getBarChatList.add(TotalReject);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getBarChatList,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getBarChartValue(String startDate, String endDate) {
		try {

			List<GraphDataDto> getBarChatList = new ArrayList<GraphDataDto>();

			String query = "select t_section.name, total.total_check,t_ok.total_ok, t_alter.total_alter, t_reject.total_reject   \r\n"
					+ "							 from     \r\n" + "							(   \r\n"
					+ "							     SELECT quality_transaction.section_id, SUM(quality_transaction.sample_size) as total_check     \r\n"
					+ "							     FROM quality_transaction,quality_type     \r\n"
					+ "							     WHERE quality_type.type=1   \r\n"
					+ "                                AND quality_transaction.qualitytype_id=quality_type.id \r\n"
					+ "                                AND quality_transaction.created_at     \r\n"
					+ "							     BETWEEN :startDate AND :endDate       \r\n"
					+ "							     GROUP BY quality_transaction.section_id     \r\n"
					+ "							) as total     \r\n" + "							 left JOIN     \r\n"
					+ "							(   \r\n"
					+ "							     SELECT quality_transaction.section_id, SUM(quality_transaction.sample_size)  as total_ok     \r\n"
					+ "							     FROM quality_transaction,quality_type     \r\n"
					+ "							     WHERE quality_type.type=1   \r\n"
					+ "                                AND quality_transaction.qualitytype_id=quality_type.id \r\n"
					+ "                                AND quality_transaction.created_at     \r\n"
					+ "							     BETWEEN :startDate AND :endDate       \r\n"
					+ "							     AND quality_transaction.check_output='ok'      \r\n"
					+ "							     GROUP BY quality_transaction.section_id     \r\n"
					+ "							          \r\n" + "							  ) as t_ok     \r\n"
					+ "							  on total.section_id=t_ok.section_id     \r\n"
					+ "							  LEFT JOIN     \r\n" + "							  (  \r\n"
					+ "							  	SELECT quality_transaction.section_id, SUM(quality_transaction.sample_size)  as total_alter     \r\n"
					+ "							     FROM quality_transaction,quality_type     \r\n"
					+ "							     WHERE quality_type.type=1   \r\n"
					+ "                                AND quality_transaction.qualitytype_id=quality_type.id \r\n"
					+ "                                AND quality_transaction.created_at     \r\n"
					+ "							     BETWEEN :startDate AND :endDate       \r\n"
					+ "							     AND quality_transaction.check_output='alter'      \r\n"
					+ "							     GROUP BY quality_transaction.section_id     \r\n"
					+ "							  ) as t_alter     \r\n"
					+ "							  on total.section_id=t_alter.section_id     \r\n"
					+ "							  LEFT JOIN     \r\n" + "							  (   \r\n"
					+ "							  	SELECT quality_transaction.section_id, SUM(quality_transaction.sample_size)  as total_reject     \r\n"
					+ "							     FROM quality_transaction,quality_type     \r\n"
					+ "							     WHERE quality_type.type=1   \r\n"
					+ "                                AND quality_transaction.qualitytype_id=quality_type.id \r\n"
					+ "                                AND quality_transaction.created_at     \r\n"
					+ "							     BETWEEN :startDate AND :endDate       \r\n"
					+ "							     AND quality_transaction.check_output='reject'      \r\n"
					+ "							     GROUP BY quality_transaction.section_id     \r\n"
					+ "							  ) as t_reject     \r\n"
					+ "							  on total.section_id=t_reject.section_id         \r\n"
					+ "							  LEFT JOIN     \r\n" + "							  (\r\n"
					+ "							  	SELECT section.id, section.name     \r\n"
					+ "							     FROM section     \r\n"
					+ "							  ) as t_section      \r\n"
					+ "							  on total.section_id=t_section.id";

			Query q = entityManager.createNativeQuery(query);
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {

				GraphDataDto TotalCheck = new GraphDataDto();
				GraphDataDto TotalOk = new GraphDataDto();
				GraphDataDto TotalAlter = new GraphDataDto();
				GraphDataDto TotalReject = new GraphDataDto();

				TotalCheck.setSectionName(obj[0] == null ? "" : obj[0].toString());
				TotalCheck.setType("Total Check");
				TotalCheck.setValue(obj[1] == null ? "0" : obj[1].toString());

				TotalOk.setSectionName(obj[0] == null ? "" : obj[0].toString());
				TotalOk.setType("QC Pass");
				TotalOk.setValue(obj[2] == null ? "0" : obj[2].toString());

				TotalAlter.setSectionName(obj[0] == null ? "" : obj[0].toString());
				TotalAlter.setType("Alter");
				TotalAlter.setValue(obj[3] == null ? "0" : obj[3].toString());

				TotalReject.setSectionName(obj[0] == null ? "" : obj[0].toString());
				TotalReject.setType("Reject");
				TotalReject.setValue(obj[4] == null ? "0" : obj[4].toString());

				getBarChatList.add(TotalCheck);
				getBarChatList.add(TotalOk);
				getBarChatList.add(TotalAlter);
				getBarChatList.add(TotalReject);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getBarChatList,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getTopFiveQualityDefect() {
		try {

			List<GraphDataDto> topFiveQualityDefectList = new ArrayList<GraphDataDto>();

			String query = "SELECT t_style.name AS style,t_operation.name AS Operation,t_defect.defect\r\n" + "\r\n"
					+ "from\r\n" + "\r\n" + "(\r\n" + "\r\n"
					+ "SELECT quality_transaction.style_id, quality_defect.operation_id, COUNT(*) AS defect\r\n"
					+ "\r\n" + "FROM quality_transaction,quality_defect\r\n" + "\r\n"
					+ "WHERE quality_defect.qualitytransaction_id=quality_transaction.id\r\n" + "\r\n"
					+ "AND quality_transaction.style_id IS NOT NULL\r\n" + "\r\n"
					+ "AND quality_defect.operation_id IS NOT NULL\r\n" + "\r\n"
					+ "GROUP BY quality_transaction.style_id, quality_defect.operation_id\r\n" + "\r\n"
					+ "ORDER BY defect DESC LIMIT 5\r\n" + "\r\n" + ") as t_defect\r\n" + "\r\n" + "LEFT JOIN\r\n"
					+ "\r\n" + "(\r\n" + "\r\n" + "SELECT id,NAME FROM style\r\n" + "\r\n" + ") as t_style\r\n" + "\r\n"
					+ "ON t_defect.style_id=t_style.id\r\n" + "\r\n" + "LEFT JOIN\r\n" + "\r\n" + "(\r\n" + "\r\n"
					+ "SELECT id,NAME FROM operation\r\n" + "\r\n" + ") as t_operation\r\n" + "\r\n"
					+ "ON t_defect.operation_id=t_operation.id";

			Query q = entityManager.createNativeQuery(query);
			List<Object[]> gettopFiveDefectResultList = q.getResultList();

			for (Object[] topFiveDefectResult : gettopFiveDefectResultList) {

				GraphDataDto topFiveQualityDefect = new GraphDataDto();

				topFiveQualityDefect.setStyle(topFiveDefectResult[0] == null ? "" : topFiveDefectResult[0].toString());
				topFiveQualityDefect
						.setOperationName(topFiveDefectResult[1] == null ? "0" : topFiveDefectResult[1].toString());
				topFiveQualityDefect
						.setDefectQuantity(topFiveDefectResult[2] == null ? "0" : topFiveDefectResult[2].toString());

				topFiveQualityDefectList.add(topFiveQualityDefect);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, topFiveQualityDefectList,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getTopDefectiveOperation(Long orgId, String startDate, String endDate) {
		try {

			List<GraphDataDto> topFiveQualityDefectList = new ArrayList<GraphDataDto>();

			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date startDateDateType = null;
			Date endDateDateType = null;
			try {
				startDateDateType = formatter1.parse(startDate);
				endDateDateType = formatter1.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

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

			String query = "SELECT\r\n" + "    t_style.name AS style,\r\n" + "    t_operation.name AS Operation,\r\n"
					+ "    t_defect.defect\r\n" + "FROM\r\n" + "    (\r\n" + "    SELECT\r\n"
					+ "        quality_transaction.style_id,\r\n" + "        quality_defect.operation_id,\r\n"
					+ "        COUNT(*) AS defect\r\n" + "    FROM\r\n" + "        quality_transaction,\r\n"
					+ "        quality_defect\r\n" + "    WHERE\r\n"
					+ "         quality_defect.qualitytransaction_id = quality_transaction.id AND quality_transaction.style_id IS NOT NULL AND quality_defect.operation_id IS NOT NULL and quality_defect.created_at BETWEEN :startDateDateType AND :endDateDateType and quality_transaction.org_id in (\r\n"
					+ organization + " )   GROUP BY\r\n" + "        quality_transaction.style_id,\r\n"
					+ "        quality_defect.operation_id\r\n" + "    ORDER BY\r\n" + "        defect\r\n"
					+ "    DESC\r\n" + "LIMIT 5\r\n" + ") AS t_defect\r\n" + "LEFT JOIN(\r\n" + "SELECT\r\n"
					+ "    id,\r\n" + "    NAME\r\n" + "FROM\r\n" + "    style\r\n" + ") AS t_style\r\n" + "ON\r\n"
					+ "    t_defect.style_id = t_style.id\r\n" + "LEFT JOIN(\r\n" + "SELECT\r\n" + "    id,\r\n"
					+ "    NAME\r\n" + "FROM\r\n" + "    operation\r\n" + ") AS t_operation\r\n" + "ON\r\n"
					+ "    t_defect.operation_id = t_operation.id";

			Query q = entityManager.createNativeQuery(query);

			q.setParameter("startDateDateType", startDateDateType);
			q.setParameter("endDateDateType", endDateDateType);

			List<Object[]> gettopFiveDefectResultList = q.getResultList();

			for (Object[] topFiveDefectResult : gettopFiveDefectResultList) {

				GraphDataDto topFiveQualityDefect = new GraphDataDto();

				topFiveQualityDefect.setStyle(topFiveDefectResult[0] == null ? "" : topFiveDefectResult[0].toString());
				topFiveQualityDefect
						.setOperationName(topFiveDefectResult[1] == null ? "0" : topFiveDefectResult[1].toString());
				topFiveQualityDefect
						.setDefectQuantity(topFiveDefectResult[2] == null ? "0" : topFiveDefectResult[2].toString());
				topFiveQualityDefect.setOrganizationList(orgNames);

				topFiveQualityDefectList.add(topFiveQualityDefect);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, topFiveQualityDefectList,
					topFiveQualityDefectList.size(), String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getTLSdashBoard(Long orgId) {
		try {
			Query query = null;
			String sql = "SELECT order_entity.* FROM quality_transaction,order_entity WHERE quality_transaction.org_id=:orgId"
					+ " AND quality_transaction.active=true and quality_transaction.orderentity_id=order_entity.id"
					+ " and order_entity.active=true order by quality_transaction.created_at desc LIMIT 1";

			query = entityManager.createNativeQuery(sql, OrderEntity.class);
			query.setParameter("orgId", orgId);

			Optional<Organization> organization = organizationRepository.findById(orgId);

			List<OrderEntity> orderEntityList = query.getResultList();
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			OrderEntityDto orderEntityDto = new OrderEntityDto();

			if (orderEntityList.size() != 0) {

				Style style = orderEntityList.get(0).getStyle();
				List<OperationBreakDown> operationBreakDownList = operationBreakDownRepository
						.findAllByStyleAndActiveTrue(style);

				for (OperationBreakDown operationBreakDownEach : operationBreakDownList) {

					List<OperationMachine> operationMachineList = operationMachineRepository
							.findAllByOperationBreakDownAndActiveTrueAndOrganizationId(operationBreakDownEach, orgId);

					operationBreakDownEach.setOperationMachine(operationMachineList);

				}

				orderEntityList.get(0).getStyle().setOperationBreakDown(operationBreakDownList);

				modelMapper.map(orderEntityList.get(0), orderEntityDto);

				Long orderentityID = orderEntityDto.getId();
				List<OperationBreakDownDto> operationBreakDownDtoList = new ArrayList<>();
				operationBreakDownDtoList = orderEntityDto.getStyle().getOperationBreakDown();

				for (OperationBreakDownDto opbd : operationBreakDownDtoList) {
					List<OperationMachineDto> operationMachineDtoList = new ArrayList<>();
					operationMachineDtoList = opbd.getOperationMachine();
					Long operationId = opbd.getOperation().getId();
					opbd.setStyle(null);
					opbd.getOperation().setOrganization(null);
					for (OperationMachineDto opmc : operationMachineDtoList) {
						opmc.setOperationBreakDown(null);
						opmc.setOrganization(null);
						if (opmc.getOrganization() != null && !opmc.getOrganization().getId().equals(orgId)) {
							opmc.setOrganization(null);
							break;
						}
						opmc.setEmployeeImage(fileUploadService.getEmployeeImage(opmc.getEmployee().getId(), ""));
						// opmc.setTLSstatus(getStatus(opmc, orderentityID, operationId, orgId));
					}
				}
			}

			orderEntityDto.setLineName(organization.get().getName());

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, orderEntityDto,
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public int getStatus(OperationMachineDto opmc, Long orderentityID, Long operationId, Long orgId) {
		int status = 0;
		EmployeeDto employeeDto = opmc.getEmployee();
		if (employeeDto != null) {
			Long employeeId = employeeDto.getId();

			List<QualityTransaction> getDefectItemList = qualityTransactionRepository
					.getDefectiveItemList(orderentityID, operationId, employeeId, orgId);//// nw//////

			int length = getDefectItemList.size();

			if (!getDefectItemList.isEmpty()) {
				int a = 0, b = 0, c = 0;
				if (length > 0 && getDefectItemList.get(0) != null) {

					a = getDefectItemList.get(0).getDefectiveItem();
				}
				if (length > 1 && getDefectItemList.get(1) != null) {

					b = getDefectItemList.get(1).getDefectiveItem();
				}
				if (length > 2 && getDefectItemList.get(2) != null) {

					c = getDefectItemList.get(2).getDefectiveItem();
				}
				if (a > 0) {
					status = 1;
					if (b > 0) {
						status = 2;
					}
				} else {
					if (b > 0 && c > 0) {
						status = 1;
					}
				}
			}
		}
		return status;
	}

	private List<QualityTransactionDto> getResponseDtoList(List<QualityTransaction> qualityTransactions) {
		List<QualityTransactionDto> responseDtoList = new ArrayList<>();
		qualityTransactions.forEach(qualityTransaction -> {
			QualityTransactionDto qualityTransactionDto = new QualityTransactionDto();
			BeanUtils.copyProperties(qualityTransaction, qualityTransactionDto);
			responseDtoList.add(qualityTransactionDto);
		});
		return responseDtoList;
	}

	public Response getQCPassReport(List<Long> orgIdList,List<String> orderEntityList, String startDate, String endDate, String timeUnit) {
		try {

			String condition;
			Double allresult = 0.0;
			switch (timeUnit) {
			case "month":
				condition = "%Y-%m";
				break;
			case "date":
				condition = "%Y-%m-%d";
				break;
			case "hour":
				condition = "%Y-%m-%d:%H";
				break;
			default:
				condition = "%Y-%m-%d:%H";
				break;
			}
			List<QcPassReportDto> getQcPassReportList = new ArrayList<QcPassReportDto>();

			List<Organization> orgList = new ArrayList<Organization>();
			for(Long orgId : orgIdList) {
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
			
			
			
			if(orderEntityList != null) {
				String orderList = orderEntityList.toString().replaceAll("(^\\[|\\]$)", "");
				String orderEntityConditionHead = " and quality_transaction.orderentity_id in (  ";
				String orderEntityConditionTail = "  ) ";
				orderEntityCondition += orderEntityConditionHead + orderList + orderEntityConditionTail;
			}

			String query = "SELECT\r\n" + "\r\n" + "total.qDate,\r\n" + "\r\n" + "total.root_path as line,\r\n" + "\r\n"
					+ "total.totalCheck,\r\n" + "\r\n" + "total_qcPass.qcPass,\r\n" + "\r\n"
					+ "100 * total_qcPass.qcPass / total.totalCheck AS qcPassPercent,\r\n" + "\r\n"
					+ "total_alter.totalAlter,\r\n" + "\r\n"
					+ "100 * total_alter.totalAlter / total.totalCheck AS alterPercent,\r\n" + "\r\n"
					+ "total_reject.reject,\r\n" + "\r\n"
					+ "100 * total_reject.reject / total.totalCheck AS rejectPercent,\r\n" + "\r\n"
					+ "defect_quantity.defect,\r\n" + "\r\n"
					+ "100 * defect_quantity.defect / total.totalCheck AS dhu\r\n" + "\r\n" + "FROM\r\n" + "\r\n"
					+ "(\r\n" + "\r\n" + "SELECT DATE_FORMAT(\r\n" + "\r\n" + "quality_transaction.created_at,\r\n"
					+ "\r\n" + ":condition\r\n" + "\r\n" + ") AS qDate,\r\n" + "\r\n"
					+ "quality_transaction.org_id,\r\n" + "\r\n" + "organizations.root_path,\r\n" + "\r\n" + "SUM(\r\n"
					+ "\r\n" + "quality_transaction.sample_size\r\n" + "\r\n" + ") AS totalCheck\r\n" + "\r\n"
					+ "FROM\r\n" + "\r\n" + "quality_transaction,\r\n" + "\r\n" + "quality_type,\r\n" + "\r\n"
					+ "organizations\r\n" + "\r\n" + "WHERE\r\n" + "\r\n"
					+ "quality_transaction.qualitytype_id = quality_type.id\r\n" + "\r\n"
					+ "AND quality_type.type = 1\r\n" + "\r\n" + "AND quality_transaction.active = 1\r\n" + "\r\n"
					+ "AND quality_transaction.created_at BETWEEN :startDate  AND :endDate\r\n" + "\r\n"
					+ "AND quality_transaction.org_id=organizations.id\r\n" + "\r\n"
					+ "and quality_transaction.org_id in (\r\n" + "\r\n" + organization + "\r\n" + "\r\n" + ")\r\n"
					+ "\r\n" + orderEntityCondition + "  GROUP BY\r\n" + "\r\n" + "qDate,\r\n" + "\r\n" + "quality_transaction.org_id\r\n"
					+ "\r\n" + ") AS total\r\n" + "\r\n" + " \r\n" + "\r\n" + "LEFT JOIN(\r\n" + "\r\n"
					+ "SELECT DATE_FORMAT(\r\n" + "\r\n" + "quality_transaction.created_at,\r\n" + "\r\n"
					+ ":condition\r\n" + "\r\n" + ") AS qDate,\r\n" + "\r\n" + "quality_transaction.org_id,\r\n"
					+ "\r\n" + "SUM(\r\n" + "\r\n" + "quality_transaction.sample_size\r\n" + "\r\n" + ") AS qcPass\r\n"
					+ "\r\n" + "FROM\r\n" + "\r\n" + "quality_transaction,\r\n" + "\r\n" + "quality_type\r\n" + "\r\n"
					+ "WHERE\r\n" + "\r\n"
					+ "quality_transaction.qualitytype_id = quality_type.id AND quality_type.type = 1 AND quality_transaction.check_output = 'ok' AND quality_transaction.active = 1 AND quality_transaction.created_at BETWEEN :startDate  AND :endDate and quality_transaction.org_id in (\r\n"
					+ "\r\n" + organization + "\r\n" + "\r\n" + ")\r\n" + "\r\n" + orderEntityCondition +"  GROUP BY\r\n" + "\r\n"
					+ "qDate,\r\n" + "\r\n" + "quality_transaction.org_id\r\n" + "\r\n" + ") AS total_qcPass\r\n"
					+ "\r\n" + "ON\r\n" + "\r\n"
					+ "total.qDate = total_qcPass.qDate AND total.org_id = total_qcPass.org_id\r\n" + "\r\n"
					+ "LEFT JOIN(\r\n" + "\r\n" + "SELECT DATE_FORMAT(\r\n" + "\r\n"
					+ "quality_transaction.created_at,\r\n" + "\r\n" + ":condition\r\n" + "\r\n" + ") AS qDate,\r\n"
					+ "\r\n" + "quality_transaction.org_id,\r\n" + "\r\n" + "SUM(\r\n" + "\r\n"
					+ "quality_transaction.sample_size\r\n" + "\r\n" + ") AS totalAlter\r\n" + "\r\n" + "FROM\r\n"
					+ "\r\n" + "quality_transaction,\r\n" + "\r\n" + "quality_type\r\n" + "\r\n" + "WHERE\r\n" + "\r\n"
					+ "quality_transaction.qualitytype_id = quality_type.id AND quality_type.type = 1 AND quality_transaction.check_output = 'alter' AND quality_transaction.active = 1 AND quality_transaction.created_at BETWEEN :startDate  AND :endDate and quality_transaction.org_id in (\r\n"
					+ "\r\n" + organization + "\r\n" + "\r\n" + ")\r\n" + "\r\n" + orderEntityCondition + "  GROUP BY\r\n" + "\r\n"
					+ "qDate,\r\n" + "\r\n" + "quality_transaction.org_id\r\n" + "\r\n" + ") AS total_alter\r\n"
					+ "\r\n" + "ON\r\n" + "\r\n"
					+ "total.qDate = total_alter.qDate AND total.org_id = total_alter.org_id\r\n" + "\r\n"
					+ "LEFT JOIN(\r\n" + "\r\n" + "SELECT DATE_FORMAT(\r\n" + "\r\n"
					+ "quality_transaction.created_at,\r\n" + "\r\n" + ":condition\r\n" + "\r\n" + ") AS qDate,\r\n"
					+ "\r\n" + "quality_transaction.org_id,\r\n" + "\r\n" + "SUM(\r\n" + "\r\n"
					+ "quality_transaction.sample_size\r\n" + "\r\n" + ") AS reject\r\n" + "\r\n" + "FROM\r\n" + "\r\n"
					+ "quality_transaction,\r\n" + "\r\n" + "quality_type\r\n" + "\r\n" + "WHERE\r\n" + "\r\n"
					+ "quality_transaction.qualitytype_id = quality_type.id AND quality_type.type = 1 AND quality_transaction.check_output = 'reject' AND quality_transaction.active = 1 AND quality_transaction.created_at BETWEEN :startDate  AND :endDate and quality_transaction.org_id in (\r\n"
					+ "\r\n" + organization + "\r\n" + "\r\n" + ")\r\n" + "\r\n" + orderEntityCondition + "  GROUP BY\r\n" + "\r\n"
					+ "qDate,\r\n" + "\r\n" + "quality_transaction.org_id\r\n" + "\r\n" + ") AS total_reject\r\n"
					+ "\r\n" + "ON\r\n" + "\r\n"
					+ "total.qDate = total_reject.qDate AND total.org_id = total_reject.org_id\r\n" + "\r\n"
					+ "LEFT JOIN(\r\n" + "\r\n" + "SELECT DATE_FORMAT(\r\n" + "\r\n"
					+ "quality_transaction.created_at,\r\n" + "\r\n" + ":condition\r\n" + "\r\n" + ") AS qDate,\r\n"
					+ "\r\n" + "quality_transaction.org_id,\r\n" + "\r\n" + "COUNT(*) AS defect\r\n" + "\r\n"
					+ "FROM\r\n" + "\r\n" + "quality_transaction,\r\n" + "\r\n" + "quality_type,\r\n" + "\r\n"
					+ "quality_defect\r\n" + "\r\n" + "WHERE\r\n" + "\r\n"
					+ "quality_transaction.qualitytype_id = quality_type.id\r\n" + "\r\n"
					+ "AND quality_type.type = 1\r\n" + "\r\n"
					+ "AND quality_defect.qualitytransaction_id = quality_transaction.id\r\n" + "\r\n"
					+ "AND quality_defect.active = 1\r\n" + "\r\n"
					+ "AND quality_transaction.created_at BETWEEN :startDate  AND :endDate and quality_transaction.org_id in (\r\n"
					+ "\r\n" + organization + "\r\n" + "\r\n" + ")\r\n" + "\r\n" + orderEntityCondition + "  GROUP BY\r\n" + "\r\n"
					+ "qDate,\r\n" + "\r\n" + "quality_transaction.org_id\r\n" + "\r\n" + ") AS defect_quantity\r\n"
					+ "\r\n" + "ON\r\n" + "\r\n"
					+ "total.qDate = defect_quantity.qDate AND total.org_id = defect_quantity.org_id";

			Query q = entityManager.createNativeQuery(query);

			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			q.setParameter("condition", condition);
			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {
				QcPassReportDto qcPassReportDto = new QcPassReportDto();

				String dateTime = obj[0] == null ? "" : obj[0].toString();
				String date = dateTime.split(":")[0] == null ? "" : dateTime.split(":")[0];
				qcPassReportDto.setDate(date);

				if (dateTime.split(":").length > 1 && dateTime.split(":")[1] != null) {
					qcPassReportDto.setHour(dateTime.split(":")[1]);
				}

				qcPassReportDto.setRootPath(obj[1] == null ? "" : obj[1].toString());
				qcPassReportDto.setTotalCheck(obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
				qcPassReportDto.setQcPass(obj[3] == null ? 0 : Integer.parseInt(obj[3].toString()));
				qcPassReportDto.setQcPassPercent(obj[4] == null ? 0.0 : Double.parseDouble(obj[4].toString()));
				qcPassReportDto.setTotalAlter(obj[5] == null ? 0 : Integer.parseInt(obj[5].toString()));
				qcPassReportDto.setAlterPercent(obj[6] == null ? 0.0 : Double.parseDouble(obj[6].toString()));
				qcPassReportDto.setReject(obj[7] == null ? 0 : Integer.parseInt(obj[7].toString()));
				qcPassReportDto.setRejectPercent(obj[8] == null ? 0.0 : Double.parseDouble(obj[8].toString()));
				qcPassReportDto.setDefect(obj[9] == null ? 0 : Integer.parseInt(obj[9].toString()));
				qcPassReportDto.setDhu(obj[10] == null ? 0.0 : Double.parseDouble(obj[10].toString()));
				qcPassReportDto.setOrganizationList(orgNames);

				getQcPassReportList.add(qcPassReportDto);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getQcPassReportList, getQcPassReportList.size(),
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getDefectAnalysisReport(List<Long> orgIdList,List<String> orderEntityList ,String startDate, String endDate, String timeUnit) {
		try {

			String condition;
			Double allresult = 0.0;
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
			List<DefectAnalysisReportDto> getDefectAnalysisReportList = new ArrayList<DefectAnalysisReportDto>();

			List<Organization> orgList = new ArrayList<Organization>();
			
			for(Long orgId : orgIdList) {
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
			
			if(orderEntityList != null) {
				String orderList = orderEntityList.toString().replaceAll("(^\\[|\\]$)", "");
				String orderEntityConditionHead = " AND quality_transaction.orderentity_id in (  ";
				String orderEntityConditionTail = "  ) ";
				orderEntityCondition += orderEntityConditionHead + orderList + orderEntityConditionTail;
			}

//			String query = " SELECT\r\n" + "    total.Q_date,\r\n" + "    total.root_path,\r\n" + "    total.style,\r\n"
//					+ "    total.buyer,\r\n" + "    total.orderItem,\r\n" + "    total.operation,\r\n"
//					+ "    total.employee,\r\n" + "    total.qualityCheckType,\r\n" + "    total.defect,\r\n"
//					+ "    total.quantity\r\n" + "FROM(\r\n"
//					+ " select DATE_FORMAT(quality_transaction.created_at,:condition) AS Q_date,COUNT(quality_defect.id) AS quantity,organizations.root_path,style.name AS style,customer.name AS buyer,\r\n"
//					+ "        order_entity.name AS orderItem,quality_type.name AS qualityCheckType,operation.name AS operation,employee.name AS employee, defect.name AS defect\r\n"
//					+ " \r\n"
//					+ " FROM quality_transaction,quality_defect,organizations,style, customer,order_entity,quality_type,operation,employee,defect\r\n"
//					+ " \r\n" + " WHERE quality_transaction.created_at BETWEEN :startDate  AND :endDate \r\n"
//					+ " AND quality_defect.qualitytransaction_id= quality_transaction.id AND quality_defect.active = 1\r\n"
//					+ " AND quality_transaction.org_id in (" + organization + ") \r\n"
//					+ " And quality_transaction.org_id=organizations.id\r\n" + " AND organizations.active = 1\r\n"
//					+ " AND quality_transaction.orderentity_id = order_entity.id AND order_entity.active = 1  \r\n"
//					+ " AND order_entity.customer_id = customer.id AND customer.active = 1 \r\n"
//					+ " AND order_entity.style_id = style.id  AND style.active = 1 \r\n"
//					+ " AND quality_transaction.qualitytype_id = quality_type.id AND quality_type.active = 1 \r\n"
//					+ " AND quality_transaction.operation_id = operation.id AND operation.active = 1 \r\n"
//					+ " AND quality_transaction.employee_id = employee.id AND employee.active = 1\r\n"
//					+ " AND quality_defect.defect_id = defect.id AND defect.active = 1\r\n" + " \r\n"
//					+ " GROUP BY Q_date,organizations.root_path,style.name,customer.name,\r\n"
//					+ "        order_entity.name,quality_type.name,operation.name,employee.name,defect.name\r\n"
//					+ ") AS total";
			
			
			String query = "SELECT total.Q_date ,total.root_path , total.style , total.buyer ,total.orderItem ,optTab.operationName, empTab.employeeName ,total.qualityCheckType ,total.defect , total.quantity \r\n"
					+ "	FROM  \r\n"
					+ "			( SELECT DATE_FORMAT(quality_transaction.created_at ,:condition) AS Q_date,COUNT(quality_defect.id) AS quantity,organizations.root_path,style. NAME AS style,customer. NAME AS buyer , order_entity. NAME AS orderItem, \r\n"
					+ "					quality_type. NAME AS qualityCheckType,quality_transaction.operation_id,quality_transaction.employee_id,defect. NAME AS defect \r\n"
					+ "				FROM \r\n"
					+ "					quality_transaction,quality_defect,organizations,style,customer,order_entity,quality_type,defect \r\n"
					+ "				WHERE quality_transaction.created_at BETWEEN :startDate  AND :endDate  AND  quality_defect.qualitytransaction_id = quality_transaction.id \r\n"
					+ "				AND quality_defect.active = 1 \r\n"
					+ "				AND quality_transaction.org_id IN (" + organization + ") \r\n"
					+ "				AND quality_transaction.org_id = organizations.id \r\n"
					+ "				AND organizations.active = 1 \r\n  " +orderEntityCondition
					+ "				AND quality_transaction.orderentity_id = order_entity.id \r\n"
					+ "				AND order_entity.active = 1 \r\n"
					+ "				AND order_entity.customer_id = customer.id \r\n"
					+ "				AND customer.active = 1 \r\n"
					+ "				AND order_entity.style_id = style.id \r\n"
					+ "				AND style.active = 1 \r\n"
					+ "				AND quality_transaction.qualitytype_id = quality_type.id \r\n"
					+ "				AND quality_type.active = 1 \r\n"
					+ "				AND quality_defect.defect_id = defect.id \r\n"
					+ "				AND defect.active = 1 \r\n"
					+ "				GROUP BY \r\n"
					+ "					Q_date,organizations.root_path,style. NAME,customer. NAME , order_entity. NAME,quality_type. NAME,quality_transaction.operation_id,quality_transaction.employee_id,defect. NAME ) AS total	 \r\n"
					+ "				LEFT JOIN ( \r\n"
					+ "					SELECT operation.id,operation.name AS operationName \r\n"
					+ "					FROM operation \r\n"
					+ "					WHERE operation.active = 1 \r\n"
					+ "				) AS optTab \r\n"
					+ "				ON total.operation_id = optTab.id \r\n"
					+ "				LEFT JOIN 	 \r\n"
					+ "				(SELECT employee.id,employee.name AS employeeName  \r\n"
					+ "					FROM employee  \r\n"
					+ "					WHERE employee.active = 1 \r\n"
					+ "				) AS empTab  \r\n"
					+ "				ON total.employee_id = empTab.id";

			Query q = entityManager.createNativeQuery(query);

			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			q.setParameter("condition", condition);
			List<Object[]> objects = q.getResultList();

			for (Object[] obj : objects) {
				DefectAnalysisReportDto defectAnalysisReportDto = new DefectAnalysisReportDto();

				String dateTime = obj[0] == null ? "" : obj[0].toString();
				String date = dateTime.split(":")[0] == null ? "" : dateTime.split(":")[0];
				defectAnalysisReportDto.setDate(date);

				if (dateTime.split(":").length > 1 && dateTime.split(":")[1] != null) {
					defectAnalysisReportDto.setHour(dateTime.split(":")[1]);
				}

				defectAnalysisReportDto.setRootPath(obj[1] == null ? "" : obj[1].toString());
				defectAnalysisReportDto.setStyle(obj[2] == null ? "" : obj[2].toString());
				defectAnalysisReportDto.setBuyer(obj[3] == null ? "" : obj[3].toString());
				defectAnalysisReportDto.setOrderItem(obj[4] == null ? "" : obj[4].toString());
				defectAnalysisReportDto.setOperation(obj[5] == null ? "" : obj[5].toString());
				defectAnalysisReportDto.setEmployee(obj[6] == null ? "" : obj[6].toString());
				defectAnalysisReportDto.setQualityCheckType(obj[7] == null ? "" : obj[7].toString());
				defectAnalysisReportDto.setDefect(obj[8] == null ? "" : obj[8].toString());
				defectAnalysisReportDto.setQuantity(obj[9] == null ? 0 : Integer.parseInt(obj[9].toString()));
				defectAnalysisReportDto.setOrganizationList(orgNames);

				getDefectAnalysisReportList.add(defectAnalysisReportDto);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getDefectAnalysisReportList,getDefectAnalysisReportList.size(),
					String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public List<Object[]> getOrganizationList(Long orgId) {
		String query = "SELECT id FROM (SELECT * FROM organizations ORDER BY parent_org,id) org,\r\n"
				+ "(SELECT @pv\\:= '" + orgId + "') initialisation\r\n"
				+ "WHERE FIND_IN_SET(parent_org, @pv) AND LENGTH(@pv\\:= CONCAT(@pv, ',', id))";

		Query q = entityManager.createNativeQuery(query);
		List<Object[]> objects = q.getResultList();

		return objects;

	}

	public Response getLineWiseOrderInfo(Long orgId) {
		try {
//			LineWiseCurrentOrderDto lineWiseCurrentDto = new LineWiseCurrentOrderDto();
			List<LineWiseCurrentOrderDto> lineWiseCurrentOrderList = new ArrayList<LineWiseCurrentOrderDto>();
//		if(orgId != null){

		List<Organization> organizationList = organizationRepository.findAllActive();
		List<Organization> leaves = adminService.getLeaves(orgId, organizationList, new ArrayList<Organization>());
		for (Organization obj : leaves) {
			if(obj != null){
				QualityTransaction qualityTransaction = qualityTransactionRepository.findByOrganization(obj.getId());
				//QualityTransaction qualityTransaction = qualityTransactionRepository.findByOrganization(orgId);
				LineWiseCurrentOrderDto lineWiseCurrentDto = new LineWiseCurrentOrderDto();			
				if(qualityTransaction != null){
					lineWiseCurrentDto.setId(qualityTransaction.getId());
					lineWiseCurrentDto.setOrgId(obj.getId());
					lineWiseCurrentDto.setOrderId(qualityTransaction.getOrderEntity() == null ? 0 : qualityTransaction.getOrderEntity().getId());
					lineWiseCurrentDto.setOrderName(qualityTransaction.getOrderEntity() == null ? "" : qualityTransaction.getOrderEntity().getName());
					lineWiseCurrentDto.setLayoutStartDate(qualityTransaction.getCreatedAt().toString());
					
					if(qualityTransaction.getOrderEntity() != null) {
						Optional<OrderEntity> orderEntity = orderEntityRepository.findById(qualityTransaction.getOrderEntity().getId());
						lineWiseCurrentDto.setBuyer(orderEntity.get().getCustomer() == null ? 0 : orderEntity.get().getCustomer().getId());
						lineWiseCurrentDto.setBuyerName(orderEntity.get().getCustomer() == null ? "" : orderEntity.get().getCustomer().getName());
						lineWiseCurrentDto.setStyle(orderEntity.get().getStyle() == null ? 0 : orderEntity.get().getStyle().getId());
						lineWiseCurrentDto.setStyleName(orderEntity.get().getStyle() == null ? "" : orderEntity.get().getStyle().getName());
//									lineWiseCurrentDto.setLineTerget(orderEntity.get().getQuantity());
					
				//   }
					
					SimpleDateFormat dateFormate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date currentDate = new Date();
			

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
					String formate="yyyy-MM-dd'T'HH:mm:ss";
					LocalDateTime now = LocalDateTime.now();

					String dateString = LocalDateTimeConversion.getStringFromLocalDateTime(now,formate);
					String[] tmp = dateString.split("T");
					String presentDate = tmp[0].toString();
					
					//Long orgIds = Long.valueOf(obj.getId());
					TargetAndManpower targetAndManPower = targetAndManpowerRepository.findByOrganizationAndDate(obj.getId(),presentDate);
					
					if(targetAndManPower != null){
						lineWiseCurrentDto.setLineTerget(targetAndManPower.getProductiontarget());
					}
					
					
					String endDate = dateFormate.format(currentDate);
					
					Calendar c = Calendar.getInstance();
					c.setTime(currentDate);
					c.add(Calendar.HOUR, -1);
					
					Date oneHourBeforeDateTime = c.getTime();
					String startDate = dateFormate.format(oneHourBeforeDateTime);
					Long OrderId = qualityTransaction.getOrderEntity().getId();
					
					String query = "SELECT SUM(quality_transaction.sample_size) AS qcPass\r\n" + 
							"FROM quality_transaction,quality_type\r\n" + 
							"WHERE\r\n" + 
							"    quality_transaction.qualitytype_id = quality_type.id  AND quality_transaction.check_output = 'ok' AND quality_transaction.active = 1 AND quality_transaction.created_at BETWEEN :startDate AND :endDate AND quality_transaction.org_id = "+obj.getId()+"  AND quality_transaction.orderentity_id = "+OrderId;
					Query q = entityManager.createNativeQuery(query);
			
					q.setParameter("startDate", startDate);
					q.setParameter("endDate", endDate);
					List<Object[]> objects = q.getResultList();
					
					
					for (Object[] obje : objects) {
						if(obje != null) {
							lineWiseCurrentDto.setCurrentLineProduction(Integer.parseInt(obje[0].toString()));
						}
			
					}	
					
				  }
					lineWiseCurrentOrderList.add(lineWiseCurrentDto);
				}
			}
			
		}
		
			
		//}
		
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, lineWiseCurrentOrderList,lineWiseCurrentOrderList.size(),
				String.format("%s retrieved Successfully", root));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public List<Organization> getOrganizationList(Long orgId, List<Organization> result) {

		try {
			List<Organization> organizationChilds = organizationRepository.findByParentId(orgId);
			result.addAll(organizationChilds);
			for (Organization org : organizationChilds) {
				getOrganizationList(org.getId(), result);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

}
