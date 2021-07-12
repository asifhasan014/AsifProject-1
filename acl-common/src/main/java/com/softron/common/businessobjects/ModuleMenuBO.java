package com.softron.common.businessobjects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class ModuleMenuBO {

	private String moduleName;	
	private List<MenuBO> menus;

}
