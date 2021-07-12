package com.softron.production.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.production.dto.ReportLayoutDto;
import com.softron.production.repository.ReportLayoutRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.production.ReportLayout;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.utils.DateTimeUtilConversionService;

@Service
public class ReportLayoutService {
	@Autowired
	ReportLayoutRepository reportLayoutRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OrganizationRepository organizationRepository;

	String root = "Report Layout";

	public Response create(ReportLayoutDto reportLayoutDto) {
		try {
			ReportLayout reportLayout = modelMapper.map(reportLayoutDto, ReportLayout.class);

			if(reportLayoutDto.getUser() != null) {
				Optional<UserEntity> userEntity = userRepository.findById(reportLayoutDto.getUser().getUserName());
				reportLayout.setUser(userEntity.get());
			}

//			if(reportLayoutDto.getOrganization() != null) {
//				Optional<Organization> organization = organizationRepository.findById(reportLayoutDto.getOrganization().getId());
//				reportLayout.setOrganization(organization.get());
//			}



			//reportLayoutRepository.save(reportLayout);
			String reportLayoutFromDate = reportLayoutDto.getFromDateString();
			String reportLayoutToDate = reportLayoutDto.getToDateString();

			Date fromDate = DateTimeUtilConversionService.getDateFromString(reportLayoutFromDate,"yyyy-MM-dd");
			reportLayout.setFromDate(fromDate);

			Date toDate = DateTimeUtilConversionService.getDateFromString(reportLayoutToDate,"yyyy-MM-dd");
			reportLayout.setToDate(toDate);

			reportLayoutRepository.save(reportLayout);

			BeanUtils.copyProperties(reportLayout, reportLayoutDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, reportLayoutDto, String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}


	public Response getAll() {
		try {
			List<ReportLayout> reportLayout = reportLayoutRepository.findAllByActiveTrue();
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(reportLayout), reportLayout.size(),
					String.format("All %ses", root));
		}
		catch (Exception e) {
			e.printStackTrace();
//			System.out.print(e.printStackTrace());
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			ReportLayout reportLayout = reportLayoutRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			ReportLayoutDto reportLayoutDto = new ReportLayoutDto();
			modelMapper.map(reportLayout, reportLayoutDto);

			if(reportLayout.getFromDate() != null){
				String fromDate = DateTimeUtilConversionService.getStringFromDate(reportLayout.getFromDate(),"yyyy-MM-dd'T'HH:mm:ss");
				reportLayoutDto.setFromDateString(fromDate);
			}

			if(reportLayout.getToDate() != null){
				String toDate = DateTimeUtilConversionService.getStringFromDate(reportLayout.getToDate(),"yyyy-MM-dd'T'HH:mm:ss");
				reportLayoutDto.setToDateString(toDate);
			}
			

			//BeanUtils.copyProperties(client, clientDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, reportLayoutDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAllByType(String type) {
		try {

			List<ReportLayout> reportLayoutList = reportLayoutRepository.findAllByTypeAndActiveTrue(type);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(reportLayoutList), reportLayoutList.size(),
					String.format("All %ses", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response delete(Long id) {
		try {
			ReportLayout reportLayout = reportLayoutRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			reportLayout.setActive(false);
			reportLayoutRepository.save(reportLayout);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, ReportLayoutDto reportLayoutDto) {
		try {
			ReportLayout reportLayout = reportLayoutRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(reportLayoutDto, reportLayout);
			reportLayoutRepository.save(reportLayout);

			modelMapper.map(reportLayout, reportLayoutDto);
			//BeanUtils.copyProperties(client, clientDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, reportLayoutDto,String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}



	private List<ReportLayoutDto> getResponseDtoList(List<ReportLayout> reportLayouts) {
		List<ReportLayoutDto> responseDtoList = new ArrayList<>();
		reportLayouts.forEach(reportLayout -> {
			ReportLayoutDto reportLayoutDto = new ReportLayoutDto();
			//modelMapper.map(reportLayout, reportLayoutDto);
			BeanUtils.copyProperties(reportLayout, reportLayoutDto);
			if(reportLayout.getFromDate() != null){
				String fromDate = DateTimeUtilConversionService.getStringFromDate(reportLayout.getFromDate(),"yyyy-MM-dd'T'HH:mm:ss");
				reportLayoutDto.setFromDateString(fromDate);
			}
			
			if(reportLayout.getToDate() != null){
				String toDate = DateTimeUtilConversionService.getStringFromDate(reportLayout.getToDate(),"yyyy-MM-dd'T'HH:mm:ss");
				reportLayoutDto.setToDateString(toDate);
			}

			responseDtoList.add(reportLayoutDto);
		});
		return responseDtoList;
	}


}
