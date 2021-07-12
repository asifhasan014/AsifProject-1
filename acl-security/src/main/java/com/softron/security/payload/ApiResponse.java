package com.softron.security.payload;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ApiResponse {

	private boolean success;

	private List<String> messages;

	public ApiResponse(boolean success) {
		super();
		this.success = success;
	}

	public void addMessage(String message) {
		if (messages == null) {
			messages = new ArrayList<>();
		}
		messages.add(message);
	}

}
