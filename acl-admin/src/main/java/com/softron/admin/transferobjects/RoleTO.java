package com.softron.admin.transferobjects;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleTO {

	private Long id;

	@NotBlank(message = "Role name can't be blank.")
	private String name;

	private Long moduleId;

	private boolean active;

	private List<Long> menuIds;

}
