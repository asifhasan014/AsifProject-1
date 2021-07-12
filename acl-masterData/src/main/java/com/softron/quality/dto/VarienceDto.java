package com.softron.quality.dto;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.dto.WorkProcessDto;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.qualitymodule.entity.QualityTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class VarienceDto {
	
	private Long id;

	private String color;

	private String size;

	private long orderQuantity;

	@JsonIgnoreProperties({"customer","style"})
	private OrderEntityDto orderEntity;

	public VarienceDto() {
		//super();
	}
	
	//private List<QualityTransactionDto> qualityTransaction;
	
	
}
