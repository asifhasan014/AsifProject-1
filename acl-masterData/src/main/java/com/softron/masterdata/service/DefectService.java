package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.CompanyDto;
import com.softron.masterdata.dto.DefectDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.DefectRepository;
import com.softron.masterdata.repository.WorkProcessRepository;

@Service
public class DefectService {
	@Autowired
	DefectRepository defectRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	WorkProcessRepository workProcessRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	AdminService adminService;

	String root = "Defect";

	public Response create(DefectDto defectDto, Long orgId) {
		try {
		Defect defect = modelMapper.map(defectDto, Defect.class);
		Optional<WorkProcess> workProcess = workProcessRepository.findById(defectDto.getWorkProcess().getId());
		defect.setWorkProcess(workProcess.get());
		Optional<Organization> organaization = organizationRepository.findById(orgId);
		defect.setOrganization(organaization.get());
		defectRepository.save(defect);

		BeanUtils.copyProperties(defect, defectDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, defectDto,
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll(Long orgId) {
		try {
			Organization organization = adminService.getOrgAsCompany(orgId);
			
			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(organization.getId());
			orgList.addAll(organizationDetail);
			
			adminService.getOrganizationList(organization.getId(), orgList);
			
			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();
			
			for(Organization org: orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}
			
		List<Defect> defects = defectRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(defects), defects.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Defect defect = defectRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			DefectDto defectDto = new DefectDto();
			modelMapper.map(defect, defectDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, defectDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {

		try {
			Defect defect = defectRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			defect.setActive(false);
			defectRepository.save(defect);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, DefectDto defectDto) {
		   try {
		      Defect defect = defectRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
		      Optional<WorkProcess> workProcess = workProcessRepository.findById(defectDto.getWorkProcess().getId());
		      modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		      PropertyMap<DefectDto, Defect> skipModifiedFieldsMap = new PropertyMap<DefectDto, Defect>() {
		         protected void configure() {
		            skip().setWorkProcess(null);
		         }
		      };
		      this.modelMapper.addMappings(skipModifiedFieldsMap);

		      modelMapper.map(defectDto,defect);

		      defect.setWorkProcess(workProcess.get());
		      defectRepository.save(defect);
		      modelMapper.map(defect, defectDto);
		     
		      return ResponseUtils.getSuccessResponse(HttpStatus.OK, defectDto, String.format("%s updated successfully", root));
		   } catch (NoRecordExistsException e) {
		      return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		   } catch (Exception e) {
		      return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		   }
		}

	private List<DefectDto> getResponseDtoList(List<Defect> defects) {
		List<DefectDto> responseDtoList = new ArrayList<>();
		defects.forEach(defect -> {
			DefectDto defectDto = new DefectDto();
			modelMapper.map(defect, defectDto);
			responseDtoList.add(defectDto);
		});
		return responseDtoList;
	}
}
