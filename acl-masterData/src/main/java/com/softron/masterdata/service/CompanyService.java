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
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.SentezIntegration;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.CompanyDto;
import com.softron.masterdata.dto.PlantDto;
import com.softron.masterdata.dto.SectionDto;
import com.softron.masterdata.repository.ClientRepository;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.PlantRepository;
import com.softron.masterdata.repository.SectionRepository;

@Service
public class CompanyService extends Sentez{
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	PlantRepository plantRepository;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	
	String root = "Company";

	public Response create(CompanyDto companyDto) {
		try {
		Company company = modelMapper.map(companyDto, Company.class);
//		Optional<Client> client = clientRepository.findById(companyDto.getClient().getId());
//		company.setClient(client.get());
		
		companyRepository.save(company);
		
		//Sentez sentez = new Sentez();
		
		SentezIntegration.companyCreation(jdbcUrl, username, password,company);
		
		BeanUtils.copyProperties(company, companyDto);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, companyDto, String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {

		
		List<Company> companys = companyRepository.findAllByActiveTrue();

		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(companys), companys.size(),String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Company company = companyRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			CompanyDto companyDto = new CompanyDto();
			
			
			modelMapper.map(company, companyDto);
			
			//BeanUtils.copyProperties(company, companyDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, companyDto, String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			Company company = companyRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			company.setActive(false);
			companyRepository.save(company);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response update(Long id, CompanyDto companyDto) {
		   try {
		      Company company = companyRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
//		      Optional<Client> client = clientRepository.findById(companyDto.getClient().getId());
		      modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());


		      modelMapper.map(companyDto,company);

		      //company.setId(id);

//		      company.setClient(client.get());
		      companyRepository.save(company);
		      modelMapper.map(company, companyDto);
		     
		      return ResponseUtils.getSuccessResponse(HttpStatus.OK, companyDto, String.format("%s updated successfully", root));
		   } catch (NoRecordExistsException e) {
		      return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		   } catch (Exception e) {
		      return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		   }
		}
	

	public Response getQualityTree() {
		try {
			List<Company> companyMainList= new ArrayList<Company>();	
			List<Company> companys = companyRepository.findAllByActiveTrue();

			List<CompanyDto> companyMainDtoList = getResponseDtoList(companys);
			
			for(CompanyDto companyDtoEach : companyMainDtoList) {
				companyDtoEach.setText(companyDtoEach.getName());

			}
			
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK,companyMainDtoList , companyMainDtoList.size(),String.format("All %ses", root));
			}
	catch (Exception e) {
		return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
	}
}

	
	private List<CompanyDto> getResponseDtoList(List<Company> companys){
		List<CompanyDto> responseDtoList = new ArrayList<>();
		companys.forEach(company -> {
			CompanyDto companyDto = new CompanyDto();
			modelMapper.map(company, companyDto);
			//BeanUtils.copyProperties(company, companyDto);
			responseDtoList.add(companyDto);
		});
		return responseDtoList;
	}
	
              
}
