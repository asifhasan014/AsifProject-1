package com.softron.quality.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class TrendListDto {

	List<GraphDataDto> alterList = new ArrayList<GraphDataDto>();
	List<GraphDataDto> rejectList = new ArrayList<GraphDataDto>();
	List<GraphDataDto> dhuList = new ArrayList<GraphDataDto>();
	List<GraphDataDto> average = new ArrayList<GraphDataDto>();
	List<GraphDataDto> orgList = new ArrayList<GraphDataDto>();
}
