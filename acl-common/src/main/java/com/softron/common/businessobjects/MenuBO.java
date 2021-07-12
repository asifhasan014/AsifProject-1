package com.softron.common.businessobjects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class MenuBO {

	private String title;
	private String icon;
	private String link;
	private boolean home;
	private int sortOrder;
	private boolean external;
	private boolean active;
	private boolean hidden;
	private List<MenuBO> children;

}
