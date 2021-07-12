package com.softron.quality.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GenerateBarChatDto {

	List<GraphDataDto> totalCheck = new ArrayList<GraphDataDto>();
	List<GraphDataDto> totalOk = new ArrayList<GraphDataDto>();
	List<GraphDataDto> totalAltert = new ArrayList<GraphDataDto>();
	List<GraphDataDto> totalReject = new ArrayList<GraphDataDto>();
	
}
