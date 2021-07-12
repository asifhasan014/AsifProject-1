package com.softron.admin.transferobjects;

import com.softron.schema.admin.entity.masterdata.QualityType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RawMenu {

	private Long id;

	private Long parentId;
	
	private String title;

	private String icon;

	private String link;

	private boolean home;
	
	private boolean hidden;
	
	@Builder.Default
	private int sortOrder = 99;

	private Long detailId;

}
