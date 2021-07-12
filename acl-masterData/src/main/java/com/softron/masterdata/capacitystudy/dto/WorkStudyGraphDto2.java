package com.softron.masterdata.capacitystudy.dto;

import java.util.HashMap;

import lombok.Data;

@Data
public class WorkStudyGraphDto2 {

	HashMap<Integer, String> processes;
	HashMap<String, String> mc;
	HashMap<String, String> opt;
}
