package com.softron.masterdata.service;

import java.sql.Date;
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
import com.softron.masterdata.dto.TargetAndManpowerDto;
import com.softron.masterdata.repository.SectionRepository;
import com.softron.masterdata.repository.TargetAndManpowerRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.DateTimeConversionService;
import com.softron.utils.SentezIntegrationForTargetAndManpower;

@Service
public class TargetAndManpowerService extends Sentez{
	
	@Autowired
	TargetAndManpowerRepository targetAndManpowerRepository;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AdminService adminService;

	String root = "TargetAndManpower";

	public Response create(TargetAndManpowerDto targetAndManpowerDto) {
		
		try {
		TargetAndManpower targetAndManpower = new TargetAndManpower();
		modelMapper.map(targetAndManpowerDto,targetAndManpower);
		
		if(targetAndManpowerDto.getOrganization()!=null) {
			Optional<Organization> organization = organizationRepository.findById(targetAndManpowerDto.getOrganization().getId());
			targetAndManpower.setOrganization(organization.get());
		}
		
		
		targetAndManpowerRepository.save(targetAndManpower);

		BeanUtils.copyProperties(targetAndManpower, targetAndManpowerDto);
		
		String dateString= DateTimeConversionService.getStringFromDate(targetAndManpowerDto.getDate(),"yyyy-MM-dd");
		targetAndManpowerDto.setDateString(dateString);
		targetAndManpowerDto.setDate(null);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, targetAndManpowerDto, String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
			try {
				
				List<TargetAndManpower> targetAndManpowerList = targetAndManpowerRepository.findAllByActiveTrue();
				return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(targetAndManpowerList), targetAndManpowerList.size(),
						String.format("All %ses", root));
				}
		
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			TargetAndManpower targetAndManpower = targetAndManpowerRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			TargetAndManpowerDto targetAndManpowerDto = new TargetAndManpowerDto();
			
			modelMapper.map(targetAndManpower, targetAndManpowerDto);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, targetAndManpowerDto, String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	

	public Response delete(Long id) {
		try {
			TargetAndManpower targetAndManpower = targetAndManpowerRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			targetAndManpower.setActive(false);
			targetAndManpowerRepository.save(targetAndManpower);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, TargetAndManpowerDto targetAndManpowerDto) {
		try {
			TargetAndManpower targetAndManpower = targetAndManpowerRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			
			modelMapper.map(targetAndManpowerDto, targetAndManpower);
			
			targetAndManpowerRepository.save(targetAndManpower);
			SentezIntegrationForTargetAndManpower.erpLineTargetUpdate(jdbcUrl, username, password, targetAndManpower);

			modelMapper.map(targetAndManpower, targetAndManpowerDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, targetAndManpowerDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
		
	}

	private List<TargetAndManpowerDto> getResponseDtoList(List<TargetAndManpower> targetAndManpowers){
		List<TargetAndManpowerDto> responseDtoList = new ArrayList<>();
		targetAndManpowers.forEach(targetAndManpower -> {
			TargetAndManpowerDto targetAndManpowerDto = new TargetAndManpowerDto();
			modelMapper.map(targetAndManpower, targetAndManpowerDto);
			
			responseDtoList.add(targetAndManpowerDto);
		});
		return responseDtoList;
	}

	
	

}
