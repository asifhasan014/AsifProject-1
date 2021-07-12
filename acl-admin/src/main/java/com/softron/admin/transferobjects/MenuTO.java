package com.softron.admin.transferobjects;

import java.util.List;

import lombok.Data;

@Data
public class MenuTO {

	private String title;

	private String icon;

	private String link;

	private boolean home;

	private List<MenuTO> subMenu;

}
