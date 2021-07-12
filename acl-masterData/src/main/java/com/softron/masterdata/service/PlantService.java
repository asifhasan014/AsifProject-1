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
import com.softron.masterdata.dto.PlantDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.PlantRepository;
import com.softron.masterdata.repository.SectionRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.repository.OrganizationRepository;

@Service
public class PlantService {
	@Autowired
	PlantRepository plantRepository;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	String root = "Plant";

	public Response create(PlantDto plantDto) {
		try {
		Plant plant = modelMapper.map(plantDto, Plant.class);
		Optional<Company> company = companyRepository.findById(plantDto.getCompany().getId());
		plant.setCompany(company.get());;
		plantRepository.save(plant);
		
		BeanUtils.copyProperties(plant, plantDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, plantDto, String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
			
		List<Plant> plantMainList = new ArrayList<Plant>(); 
		List<Plant> plant = plantRepository.findAllByActiveTrue();
		for(Plant plantMain : plant) {
			List<Section> sectionList = sectionRepository.findAllByPlantAndActiveTrue(plantMain);
			plantMainList.add(plantMain);
		}
		return ResponseUtils.getSuccessResponse(HttpStatus.OK,getResponseDtoList(plantMainList), plant.size(),String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {	
		try {
			Plant plant = plantRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			PlantDto plantDto = new PlantDto();
			
			List<Section> sectionList = sectionRepository.findAllByPlantAndActiveTrue(plant);
			modelMapper.map(plant, plantDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, plantDto, String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			Plant plant = plantRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			plant.setActive(false);
			plantRepository.save(plant);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, PlantDto plantDto) {
		   try {
		      Plant plant = plantRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
		      Optional<Company> company  = companyRepository.findById(plantDto.getCompany().getId());
		      modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		      PropertyMap<PlantDto, Plant> skipModifiedFieldsMap = new PropertyMap<PlantDto, Plant>() {
		         protected void configure() {
		            skip().setCompany(null);
		         }
		      };
		      this.modelMapper.addMappings(skipModifiedFieldsMap);

		      modelMapper.map(plantDto,plant);

		      plant.setCompany(company.get());
		      plantRepository.save(plant);
		      modelMapper.map(plant, plantDto);
		     
		      return ResponseUtils.getSuccessResponse(HttpStatus.OK, plantDto, String.format("%s updated successfully", root));
		   } catch (NoRecordExistsException e) {
		      return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		   } catch (Exception e) {
		      return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		   }
		}
	
	
	private List<PlantDto> getResponseDtoList(List<Plant> plants){
		List<PlantDto> responseDtoList = new ArrayList<>();
		plants.forEach(plant -> {
			PlantDto plantDto = new PlantDto();
			modelMapper.map(plant, plantDto);
			responseDtoList.add(plantDto);
		});
		return responseDtoList;
	}
	
}
