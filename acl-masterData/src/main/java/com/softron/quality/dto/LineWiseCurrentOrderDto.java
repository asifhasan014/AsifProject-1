package com.softron.quality.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class LineWiseCurrentOrderDto {
	
	private Long id;
	private Long orgId;
	private Long orderId;
	private String orderName;
	private Long style;
	private String styleName;
	private Long buyer;
	private String buyerName;
	private String layoutStartDate;
	private Long lineTerget;
	private int currentLineProduction;
	

}
