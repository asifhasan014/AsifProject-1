package com.softron.masterdata.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.catalina.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softron.admin.controller.FileUploadController;
import com.softron.admin.service.AdminService;
import com.softron.admin.service.FileUploadService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.schema.admin.entity.FileUpload;
import com.softron.schema.admin.entity.Module;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.admin.repository.FileUploadRepository;
import com.softron.schema.admin.repository.ModuleRepository;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.utils.SentezIntegration;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.CoWorkerCSDto;
import com.softron.masterdata.dto.CompanyDto;
import com.softron.masterdata.dto.DefectDto;
import com.softron.masterdata.dto.DepartmentDto;
import com.softron.mailer.domain.Attachment;
import com.softron.mailer.domain.AttachmentEmail;
import com.softron.mailer.sender.impl.AttachmentEmailSender;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.WorkProcessDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.DepartmentRepository;
import com.softron.masterdata.repository.EmployeeRepository;
import com.softron.masterdata.repository.PlantRepository;
import com.softron.masterdata.repository.WorkProcessRepository;
import com.softron.quality.dto.GraphDataDto;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.utils.SentezIntegration;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EmployeeService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	WorkProcessRepository workProcessRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	PlantRepository plantRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	AttachmentEmailSender attachmentEmailSender;

	@Autowired
	AdminService adminService;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	FileUploadRepository fileUploadRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	UserRepository userRepository;

	String root = "Employee";

	@Value("${spring.datasource2.jdbcUrl}")
	public String jdbcUrl;

	@Value("${spring.datasource2.username}")
	public String username;

	@Value("${spring.datasource2.password}")
	public String password;

	@Value("${legal.file.upload.path}")
	private String FILE_DIRECTORY_PATH;

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	public Response create(EmployeeDto employeeDto, Long orgId, MultipartFile[] files) throws IOException {
		try {
			// EmployeeDto employeeDto = new ObjectMapper().readValue(employeeDtoString,
			// EmployeeDto.class);

			Employee employee = modelMapper.map(employeeDto, Employee.class);

			if (employee.getDepartment() != null) {
				Optional<Department> department = departmentRepository.findById(employeeDto.getDepartment().getId());
				employee.setDepartment(department.get());
			}

			if (employee.getWorkProcess() != null) {
				Optional<WorkProcess> workProcess = workProcessRepository
						.findById(employeeDto.getWorkProcess().getId());
				employee.setWorkProcess(workProcess.get());
			}

			Optional<Organization> organaization = organizationRepository.findById(orgId);
			employee.setOrganization(organaization.get());

			employeeRepository.save(employee);

			Organization org = adminService.getOrgAsCompany(employee.getOrganization().getId());

			// SentezIntegration.employeeCreation(jdbcUrl, username, password, employee
			// ,org.getId());

			BeanUtils.copyProperties(employee, employeeDto);

//			if(files != null) {
//				String fileName = fileUploadService.processFiles(files);
//				FileUpload fileUpload = new FileUpload();
//				fileUpload.setFilePath(fileName);
//				fileUpload.setEmployee(employee);
//				fileUploadRepository.save(fileUpload);
//			
//			}
			if (files != null) {
				imgUpload(employee.getId(), files);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, employeeDto,
					String.format("%s created successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response employeeCreateFromExcel(Long orgId, MultipartFile[] files) throws IOException {
		try {

//			Employee employee = new Employee();
			List<Employee> employeeEntityList = new ArrayList<Employee>();
			List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();

			if (files != null) {
				for (MultipartFile file : files) {
					String extension = FilenameUtils.getExtension(file.getOriginalFilename());
					if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
						employeeEntityList = readDataFromExcel(file,orgId);
					}
				}
			}

			for (Employee employee : employeeEntityList) {
//				Optional<Organization> organaization = organizationRepository.findById(orgId);
//				employee.setOrganization(organaization.get());

				employeeRepository.save(employee);

//				Organization org = adminService.getOrgAsCompany(employee.getOrganization().getId());

				// SentezIntegration.employeeCreation(jdbcUrl, username, password, employee
				// ,org.getId());

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED,
					getResponseDtoListUsingBeanUtil(employeeEntityList),
					String.format("%s created from excel file is successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<Employee> readDataFromExcel(MultipartFile file,Long orgId) {
		Workbook workbook = getworkBook(file);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();

		List<Employee> employeeEntityList = new ArrayList<Employee>();

		while (rows.hasNext()) {
			Row row = rows.next();
			Employee employee = new Employee();

			if (row.getCell(0) != null) {
				employee.setName(row.getCell(0).getStringCellValue());
			}
			if (row.getCell(1) != null) {
				employee.setSurName(row.getCell(1).getStringCellValue());
			}
			if (row.getCell(2) != null) {
				employee.setEmployeeCode(row.getCell(2).getStringCellValue());
			}
			if (row.getCell(3) != null) {
				employee.setDesignation(row.getCell(3).getStringCellValue());
			}
			if (row.getCell(4) != null) {
				employee.setGrade(row.getCell(4).getStringCellValue());
			}
			if (row.getCell(5) != null) {
				long employeeType = (long)row.getCell(5).getNumericCellValue();
				employee.setEmployeeType((int)employeeType);
			}
			if (row.getCell(6) != null) {
				employee.setProxyCardNo(row.getCell(6).getStringCellValue());
			}
			if (row.getCell(7) != null) {
				long tlsStatus = (long)row.getCell(7).getNumericCellValue();
				employee.setTlsStatus((int)tlsStatus);
			}
			if (row.getCell(8) != null) {
				employee.setLine(row.getCell(8).getStringCellValue());
			}
			if (row.getCell(9) != null) {
				Long departmentId = (long) row.getCell(9).getNumericCellValue();
				Optional<Department> department = departmentRepository.findById(departmentId);
				if(department.isPresent()) {
				employee.setDepartment(department.get());
				}
			}
			if (row.getCell(10) != null) {
				Long WorkProcessId = (long) row.getCell(10).getNumericCellValue();
				Optional<WorkProcess> workProcess = workProcessRepository.findById(WorkProcessId);
				if(workProcess.isPresent()) {
				employee.setWorkProcess(workProcess.get());
				}
			}
			
			Optional<Organization> organaization = organizationRepository.findById(orgId);
			employee.setOrganization(organaization.get());
			
			employeeEntityList.add(employee);
		}
		return employeeEntityList;
	}

	private Workbook getworkBook(MultipartFile file) {
		Workbook workbook = null;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			if (extension.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			} else if (extension.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file.getInputStream());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
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

			List<Employee> employee = employeeRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(employee), employee.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAllForMobile(Long orgId) {
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

			List<Employee> employee = employeeRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getMobileResponseDtoList(employee), employee.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAllCoWorkerCs(Long orgId) {
		try {
			Organization loggedOrganization = organizationRepository.findOrganizationById(orgId);
			List<Organization> organizationList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
			organizationList.addAll(organizationDetail);
			adminService.getOrganizationList(orgId, organizationList);

			Module module = moduleRepository.findByName("Work Study App");
			List<UserEntity> users = userRepository.findAllByOrganizationIn(organizationList);

			String query = "select * from users_assigned_modules where users_assigned_modules.assigned_modules_id = "
					+ module.getId();
			Query q = entityManager.createNativeQuery(query);
			List<Object[]> objects = q.getResultList();

			List<CoWorkerCSDto> coWorkerDtoList = new ArrayList<>();

			for (Object[] obj : objects) {

				for (UserEntity user : users) {
					if (user.getOrganization().getId() != orgId) {
						if (user.getUserName().equals(obj[0].toString())) {

							CoWorkerCSDto coWorkerDto = new CoWorkerCSDto();

							coWorkerDto.setUserId(user.getUserName());
							coWorkerDto.setFullName(user.getFullName());
							coWorkerDto.setEmail(user.getEmail());
							coWorkerDto.setMobile(user.getMobile());
							coWorkerDto.setOrganization(user.getOrganization());

							coWorkerDtoList.add(coWorkerDto);
						}

					}

				}

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, coWorkerDtoList, coWorkerDtoList.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Employee employee = employeeRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			EmployeeDto employeeDto = new EmployeeDto();
			modelMapper.map(employee, employeeDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, employeeDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getEmployeeReport(String reportFormat) {
		try {

			String path = "D:\\userData_asif\\Desktop\\report";
			List<Employee> employees = employeeRepository.findAllByActiveTrue();
			// load file and compile it
			File file = ResourceUtils.getFile("C:\\Users\\asif\\JaspersoftWorkspace\\MyReports\\employees.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("createdBy", "Asif Techie");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

			if (reportFormat.equalsIgnoreCase("html")) {
				JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\employees.html");
			}
			if (reportFormat.equalsIgnoreCase("pdf")) {

				JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\employees.pdf");

				File rportFile = ResourceUtils.getFile(path + "\\employees.pdf");
				Attachment attachment = new Attachment(path, rportFile);
				List<String> emailaddress = new ArrayList<String>();
				emailaddress.add("asifhasan014@gmail.com");
				emailaddress.add("mozahid@intellier.com");
				emailaddress.add("mohsiu@intellier.com");
				AttachmentEmail attachmentEmail = new AttachmentEmail(emailaddress, "report", "hi", attachment);
				attachmentEmailSender.send(attachmentEmail);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, "report generated in path : " + path,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			Employee employee = employeeRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			employee.setActive(false);
			employeeRepository.save(employee);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getEmployeeProfileInfo(Long id) {
		try {

			GraphDataDto employeeProfileInfo = new GraphDataDto();

			String qualityInfoSql = "SELECT SUM(quality_transaction.sample_size) AS total_check, \r\n"
					+ "SUM(quality_transaction.defective_item) AS defective_item\r\n" + "FROM quality_transaction \r\n"
					+ "WHERE quality_transaction.employee_id=:id";

			Query qualityInfoSqlResult = entityManager.createNativeQuery(qualityInfoSql);
			qualityInfoSqlResult.setParameter("id", id);

			Object[] result = (Object[]) qualityInfoSqlResult.getSingleResult();

			employeeProfileInfo.setTotalcheck(result[0] == null ? "0" : result[0].toString());
			employeeProfileInfo.setDefectQuantity(result[1] == null ? "0" : result[1].toString());

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, employeeProfileInfo,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response getEmployeeProfileChart(Long id) {
		try {

			List<GraphDataDto> employeeProfileChart = new ArrayList<GraphDataDto>();

			String qualityInfoSql = "SELECT date_format( quality_transaction.created_at, '%Y-%m-%d' ) AS Q_date, \r\n"
					+ "100*(1-(SUM(quality_transaction.defective_item)/SUM(quality_transaction.sample_size))) AS Quality \r\n"
					+ "FROM quality_transaction \r\n" + "WHERE quality_transaction.employee_id=:id\r\n"
					+ "GROUP BY Q_date";

			Query qualityInfoSqlResult = entityManager.createNativeQuery(qualityInfoSql);
			qualityInfoSqlResult.setParameter("id", id);

			List<Object[]> getemployeeChartobjects = qualityInfoSqlResult.getResultList();

			for (Object[] getemployeeChart : getemployeeChartobjects) {
				GraphDataDto getEmployeeProfileChartRowResult = new GraphDataDto();

				getEmployeeProfileChartRowResult
						.setQcDate(getemployeeChart[0] == null ? "0" : getemployeeChart[0].toString());
				getEmployeeProfileChartRowResult
						.setQuality(getemployeeChart[1] == null ? "0" : getemployeeChart[1].toString());

				employeeProfileChart.add(getEmployeeProfileChartRowResult);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, employeeProfileChart,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, EmployeeDto employeeDto, MultipartFile[] files) throws IOException {
		try {
			Employee employee = employeeRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			Optional<WorkProcess> workProcess = workProcessRepository.findById(employeeDto.getWorkProcess().getId());
			Optional<Department> department = departmentRepository.findById(employeeDto.getDepartment().getId());
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

			PropertyMap<EmployeeDto, Employee> skipModifiedFieldsMap = new PropertyMap<EmployeeDto, Employee>() {
				protected void configure() {
					skip().setWorkProcess(null);
					skip().setDepartment(null);
				}
			};
			this.modelMapper.addMappings(skipModifiedFieldsMap);

			modelMapper.map(employeeDto, employee);

			employee.setWorkProcess(workProcess.get());
			employee.setDepartment(department.get());
			employeeRepository.save(employee);
			modelMapper.map(employee, employeeDto);

			if (files != null) {
				imgUpload(employee.getId(), files);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, employeeDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<EmployeeDto> getResponseDtoList(List<Employee> employees) {
		List<EmployeeDto> responseDtoList = new ArrayList<>();
		employees.forEach(employee -> {
			EmployeeDto employeeDto = new EmployeeDto();
			modelMapper.map(employee, employeeDto);
			employeeDto.setEmployeeImage(fileUploadService.getEmployeeImage(employee.getId(), ""));
			responseDtoList.add(employeeDto);
		});
		return responseDtoList;
	}

	private List<EmployeeDto> getResponseDtoListUsingBeanUtil(List<Employee> employees) {
		List<EmployeeDto> responseDtoList = new ArrayList<>();
		for (Employee employee : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			BeanUtils.copyProperties(employee, employeeDto);

			if (employee.getDepartment() != null) {
				DepartmentDto departmentDto = new DepartmentDto();
				BeanUtils.copyProperties(employee.getDepartment(), departmentDto);
				employeeDto.setDepartment(departmentDto);
			}
			if (employee.getWorkProcess() != null) {
				WorkProcessDto workProcessDto = new WorkProcessDto();
				BeanUtils.copyProperties(employee.getWorkProcess(), workProcessDto);
				employeeDto.setWorkProcess(workProcessDto);
			}

			employeeDto.setEmployeeImage(fileUploadService.getEmployeeImage(employee.getId(), ""));
			responseDtoList.add(employeeDto);
		}
		return responseDtoList;
	}

	private List<EmployeeDto> getMobileResponseDtoList(List<Employee> employees) {
		List<EmployeeDto> responseDtoList = new ArrayList<>();
		employees.forEach(employee -> {
			EmployeeDto employeeDto = new EmployeeDto();
			modelMapper.map(employee, employeeDto);
			responseDtoList.add(employeeDto);
		});
		return responseDtoList;
	}

	private void imgUpload(Long employeeId, MultipartFile[] files) throws IOException {
		Optional<FileUpload> fileUploadValue = fileUploadRepository.findByEmployeeId(employeeId);

		String folder = null;

		if (fileUploadValue.isPresent()) {
			FileUpload fileUpload = fileUploadValue.get();
			String folderStr = fileUpload.getFilePath();
			String[] folderSplitStr = folderStr.split("/");
			folder = folderSplitStr[0].toString();// 1234/rahim.jpg
		}
		Map<String, String> fileMap = fileUploadService.processFiles(files, folder);

		if (fileUploadValue.isPresent()) {
			FileUpload fileUpload = fileUploadValue.get();
			fileUpload.setFilePath(fileMap.get("filePath"));
			fileUpload.setThumbnailPath(fileMap.get("thumbnailPath"));
			fileUploadRepository.save(fileUpload);
		} else {
			FileUpload fileUpload = new FileUpload();
			fileUpload.setFilePath(fileMap.get("filePath"));
			fileUpload.setThumbnailPath(fileMap.get("thumbnailPath"));
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, employeeId)));
			fileUpload.setEmployee(employee);
			fileUploadRepository.save(fileUpload);
		}
	}
}
