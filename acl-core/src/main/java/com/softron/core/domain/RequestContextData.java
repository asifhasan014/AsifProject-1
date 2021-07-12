package com.softron.core.domain;

import java.util.Optional;

public class RequestContextData {

	private Long moduleId;

	public Optional<Long> getModuleId() {
		return Optional.ofNullable(this.moduleId);
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

}
