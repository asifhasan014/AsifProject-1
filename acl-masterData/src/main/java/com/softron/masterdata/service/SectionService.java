package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;
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
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.CompanyDto;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.PlantDto;
import com.softron.masterdata.dto.SectionDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.PlantRepository;
import com.softron.masterdata.repository.SectionRepository;

@Service
public class SectionService {
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	String root = "Section";

	public Response create(SectionDto sectionDto) {
		try {
		Section section = modelMapper.map(sectionDto, Section.class);
		Optional<Organization> organization = organizationRepository.findById(sectionDto.getOrganization().getId());
		section.setOrganization(organization.get());
		
		if(section.getParent()!=null) {
			Optional<Section> parent = sectionRepository.findById(sectionDto.getParent().getId());
			section.setParent(parent.get());
		}
		sectionRepository.save(section);
		
		//BeanUtils.copyProperties(section, sectionDto);
		modelMapper.map(section,sectionDto);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, sectionDto, String.format("%s created successfully", root));
		}catch(Exception e) {
		  return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
//			List<Organization> orgList = new ArrayList<Organization>();
//			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
//			orgList.addAll(organizationDetail);
//			
//			adminService.getOrganizationList(orgId, orgList);
//			
//			List<String> orgNames = new ArrayList<String>();
//			List<Long> orgIds = new ArrayList<Long>();
//			
//			for(Organization org: orgList) {
//				orgIds.add(org.getId());
//				orgNames.add(org.getName());
//			}
			
		//List<Section> section = sectionRepository.findAllByActiveTrue();
		List<Section> section = sectionRepository.findSectionWithoutSubSection();
		return ResponseUtils.getSuccessResponse(HttpStatus.OK,getResponseDtoList(section), section.size(),String.format("All %ses", root));
		}catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Section section = sectionRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			if(section.getParent()!=null) {
			List<Section> subSection = sectionRepository.findAllByParentAndActiveTrue(section.getParent());
			section.getParent().setSubSection(subSection);
			}
			SectionDto sectionDto = new SectionDto();
			modelMapper.map(section,sectionDto);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, sectionDto, String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getSubSection(Long parentId) {
		try {
			//List<SectionDto> responseDtoList = new ArrayList<>();
			List<Section> subSection = sectionRepository.findAllByParentId(parentId);
			
			//modelMapper.map(section,responseDtoList);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(subSection), String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getSection() {
		try {
			//List<SectionDto> responseDtoList = new ArrayList<>();
			List<Section> section = sectionRepository.findSectionWithoutSubSection();
			
			//modelMapper.map(section,responseDtoList);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(section), String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	

	public Response delete(Long id) {
		try {
			Section section = sectionRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			section.setActive(false);
			sectionRepository.save(section);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, SectionDto sectionDto) {
		   try {
		      Section section = sectionRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
		      Optional<Organization> organization = organizationRepository.findById(sectionDto.getOrganization().getId());
		      Optional<Section> parent=null;
	          if(section.getParent()!=null) {
	    	      parent = sectionRepository.findById(sectionDto.getParent().getId());
//		    	  section.setParent(parent.get()); 
		      }
		      //Optional<Section> parent = sectionRepository.findById(sectionDto.getParent().getId());
		      modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		      PropertyMap<SectionDto, Section> skipModifiedFieldsMap = new PropertyMap<SectionDto, Section>() {
		         protected void configure() {
		            skip().setOrganization(null);
		            skip().setParent(null);
		         }
		      };
		      this.modelMapper.addMappings(skipModifiedFieldsMap);

		      modelMapper.map(sectionDto,section);

		      //company.setId(id);

		      section.setOrganization(organization.get());
		      if(parent!=null) {
		      section.setParent(parent.get());
		      }
		      sectionRepository.save(section);
		      modelMapper.map(section, sectionDto);
		     
		      return ResponseUtils.getSuccessResponse(HttpStatus.OK, sectionDto, String.format("%s updated successfully", root));
		   } catch (NoRecordExistsException e) {
		      return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		   } catch (Exception e) {
		      return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		   }
		}
	
	
	public Response getOrganizationWiseSection(Long parentId) {
		try {
			Long id = (long) 402;
			//List<Section> subSection = sectionRepository.findAll();
			List<Organization> organizationList = organizationRepository.findByParentId(id);
			String result;
			List<Section> sectionList = new ArrayList<Section>();
			getSectionList(parentId, sectionList);
			
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(sectionList), String.format("%s retrieved Successfully", root));
		 //return ResponseUtils.getSuccessResponse(HttpStatus.OK, sectionList, String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	private List<SectionDto> getResponseDtoList(List<Section> sections){
		List<SectionDto> responseDtoList = new ArrayList<>();
		sections.forEach(section -> {
			SectionDto sectionDto = new SectionDto();
			modelMapper.map(section,sectionDto);
			responseDtoList.add(sectionDto);
		});
		return responseDtoList;
	}
	
	public List<Section> getSectionList(Long orgId, List<Section> result){
		//List<Section> result = new ArrayList<Section>();
		try {
			List<Section> sectionList = sectionRepository.findAllByOrganizationId(orgId);
			result.addAll(sectionList);
			List<Organization> organizationChilds = organizationRepository.findByParentId(orgId);
			for(Organization org:organizationChilds) {
				getSectionList(org.getId(), result);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		

		return result;
	}
}
