package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.CustomerDto;
import com.softron.masterdata.dto.DefectDto;
import com.softron.masterdata.dto.DepartmentDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.DepartmentRepository;

@Service
public class DepartmentService {
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OrganizationRepository organizationRepository;

	String root = "Department";

	public Response create(DepartmentDto departmentDto) {
		try {
		Department department = modelMapper.map(departmentDto, Department.class);
		departmentRepository.save(department);

		BeanUtils.copyProperties(department, departmentDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, departmentDto,
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
			
		List<Department> department = departmentRepository.findAllByActiveTrue();
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(department), department.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Department department = departmentRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			DepartmentDto departmentDto = new DepartmentDto();
			modelMapper.map(department, departmentDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, departmentDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			Department department = departmentRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			department.setActive(false);
			departmentRepository.save(department);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, DepartmentDto departmentDto) {
		try {
			Department department = departmentRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(departmentDto, department);
			departmentRepository.save(department);

			modelMapper.map(department, departmentDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, departmentDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	private List<DepartmentDto> getResponseDtoList(List<Department> departments) {
		List<DepartmentDto> responseDtoList = new ArrayList<>();
		departments.forEach(department -> {
			DepartmentDto departmentDto = new DepartmentDto();
			modelMapper.map(department, departmentDto);
			responseDtoList.add(departmentDto);
		});
		return responseDtoList;
	}
}
