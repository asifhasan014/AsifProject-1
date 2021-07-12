package com.softron.admin.transferobjects;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class MenuItem {

	@JsonIgnore
	private Long id;

	@JsonIgnore
	private Long parentId;

	private String title;

	private String icon;

	private String link;

	private boolean home;
	
	private boolean hidden;

	private List<MenuItem> children;
	
	private int sortOrder;

	public void addChild(MenuItem child) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
	}

}
