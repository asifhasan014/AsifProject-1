package com.softron.quality.dto;

import java.util.ArrayList;
import java.util.List;

import com.softron.masterdata.dto.WorkProcessDto;

import lombok.Data;
@Data
public class QualityTrendDto {

	private List<Double> alter=new ArrayList<Double>();	
	private List<Double> reject=new ArrayList<Double>();
	private List<Double> DHU=new ArrayList<Double>();
	

}
