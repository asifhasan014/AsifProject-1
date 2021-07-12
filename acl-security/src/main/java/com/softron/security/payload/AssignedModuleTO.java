package com.softron.security.payload;

public class AssignedModuleTO {

	private Long id;

	private String name;

	private String contextPath;

	private String image;

	private AssignedModuleTO(Long id, String name, String contextPath, String image) {
		super();
		this.id = id;
		this.name = name;
		this.contextPath = contextPath;
		this.image = image;
	}

	public static AssignedModuleTO from(Long id, String name, String contextPath, String image) {
		return new AssignedModuleTO(id, name, contextPath, image);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
