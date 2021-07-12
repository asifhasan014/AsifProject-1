package com.softron.masterdata.dto;

import java.util.List;

import com.softron.production.dto.OrderSummeryDto;

import lombok.Data;

@Data
public class OrderListDto {

	private Long orderId;
	
	private String orderName;
	
	private List<OrderSummeryDto> orderSummeryDto;
}
