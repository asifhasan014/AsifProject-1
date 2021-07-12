package com.softron.quality.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.repository.OrderEntityRepository;
import com.softron.quality.dto.VarienceDto;
import com.softron.quality.repository.VarienceRepository;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.qualitymodule.entity.Varience;

@Service
public class VarienceService {

	@Autowired
	VarienceRepository varienceRepository;

	@Autowired
	OrderEntityRepository orderEntityRepository;

	@Autowired
	ModelMapper modelMapper;

	String root = "Varience";

	public Response create(List<VarienceDto> varienceDtoList) {
		try {
			for (VarienceDto varienceEachDto : varienceDtoList) {

				Varience varience = modelMapper.map(varienceEachDto, Varience.class);

				if (varienceEachDto.getOrderEntity() != null) {
					Optional<OrderEntity> orderEntity = orderEntityRepository
							.findById(varienceEachDto.getOrderEntity().getId());
					varience.setOrderEntity(orderEntity.get());
				}

				varience.getOrderEntity().setVarience(null);

				varienceRepository.save(varience);

				modelMapper.map(varience, varienceEachDto);

			}

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, varienceDtoList,
					String.format("%s created successfully", root));

		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
			List<Varience> varienceList = varienceRepository.findAllByActiveTrue();

			for (Varience varienceListEach : varienceList) {
				varienceListEach.getOrderEntity().setVarience(null);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getVarienceResponseDtoList(varienceList),
					varienceList.size(), String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getColorListByOrder(Long orderId) {
		try {
			List<Object> color = varienceRepository.getcolorByOrderId(orderId);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, color,
					String.format("Color List retrieved successfully"));

		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getVarienceListByOrderAndColor(Long orderId, String color) {
		try {
			List<Varience> varienceList = varienceRepository.getVarienceListByOrderAndColor(orderId, color);

			for (Varience varienceListItem : varienceList) {
				varienceListItem.getOrderEntity().setVarience(null);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(varienceList),
					String.format("Color List retrieved successfully"));

		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<VarienceDto> getResponseDtoList(List<Varience> variences) {
		List<VarienceDto> responseDtoList = new ArrayList<>();
		variences.forEach(varience -> {
			VarienceDto varienceDto = modelMapper.map(varience, VarienceDto.class);
			responseDtoList.add(varienceDto);
		});
		return responseDtoList;
	}
	
	private List<VarienceDto> getVarienceResponseDtoList(List<Varience> variences) {
		List<VarienceDto> responseDtoList = new ArrayList<>();
		variences.forEach(varience -> {
			varience.getOrderEntity().setOrganization(null);
			
			VarienceDto varienceDto = new VarienceDto(); 
			BeanUtils.copyProperties(varience, varienceDto);
			
			if(varience.getOrderEntity()!=null) {
				OrderEntityDto orderEntityDto = new OrderEntityDto();
				BeanUtils.copyProperties(varience.getOrderEntity(), orderEntityDto);
				varienceDto.setOrderEntity(orderEntityDto);
			}
			responseDtoList.add(varienceDto);
		});
		return responseDtoList;
	}

}
