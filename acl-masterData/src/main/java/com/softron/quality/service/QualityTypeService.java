package com.softron.quality.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.DefectDto;
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.repository.DefectRepository;
import com.softron.masterdata.repository.WorkProcessRepository;
import com.softron.quality.dto.QualityTypeDto;
import com.softron.quality.repository.QualityTypeRepository;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.qualitymodule.entity.QualityType;

@Service
public class QualityTypeService {
	
	@Autowired
	QualityTypeRepository qualityTypeRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	WorkProcessRepository workProcessRepository;
	
	String root = "QualityType";

	public Response create(QualityTypeDto qualityTypeDto) {
		try {
		QualityType qualityType = modelMapper.map(qualityTypeDto, QualityType.class);
		
		Optional<WorkProcess> workProcess = workProcessRepository.findById(qualityTypeDto.getWorkProcess().getId());
		qualityType.setWorkProcess(workProcess.get());
		qualityTypeRepository.save(qualityType);
		
		qualityTypeDto = modelMapper.map(qualityType, QualityTypeDto.class);
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, qualityTypeDto, String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
		List<QualityType> qualityType = qualityTypeRepository.findAllByActiveTrue();
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(qualityType), qualityType.size(),String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {	
		try {
			QualityType qualityType = qualityTypeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			QualityTypeDto qualityTypeDto = modelMapper.map(qualityType, QualityTypeDto.class);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, qualityTypeDto, String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		
		try {
			QualityType qualityType = qualityTypeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			qualityType.setActive(false);
			qualityTypeRepository.save(qualityType);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, QualityTypeDto qualityTypeDto) {
		try {
			QualityType qualityType = qualityTypeRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			Optional<WorkProcess> workProcess = workProcessRepository.findById(qualityTypeDto.getWorkProcess().getId());
			 
			
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			
			PropertyMap<QualityTypeDto, QualityType> skipModifiedFieldsMap = new PropertyMap<QualityTypeDto, QualityType>() {
		         protected void configure() {
		            skip().setWorkProcess(null);
		         }
		      };
		      this.modelMapper.addMappings(skipModifiedFieldsMap);
		      qualityType.setWorkProcess(workProcess.get()); 
		      
			qualityTypeRepository.save(qualityType);
			modelMapper.map(qualityType,qualityTypeDto);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, qualityTypeDto, String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	private List<QualityTypeDto> getResponseDtoList(List<QualityType> qualityTypes){
		List<QualityTypeDto> responseDtoList = new ArrayList<>();
		qualityTypes.forEach(qualityType -> {
			QualityTypeDto qualityTypeDto = modelMapper.map(qualityType, QualityTypeDto.class);
			responseDtoList.add(qualityTypeDto);
		});
		return responseDtoList;
	}

}
