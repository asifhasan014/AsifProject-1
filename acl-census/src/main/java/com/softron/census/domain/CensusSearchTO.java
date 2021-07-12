package com.softron.census.domain;

import lombok.Data;

@Data
public class CensusSearchTO {

	private String period;
	private String orgType;
	private Long orgId;
	private long ministry;

}
