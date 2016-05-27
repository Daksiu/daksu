package com.daksu.rest.transfer;

import java.util.Map;

public class UserTransfer {

	private final String name;
	
	private final String company;

	public String getCompany() {
		return company;
	}

	private final Map<String, Boolean> roles;

	public UserTransfer(String userName, String company, Map<String, Boolean> roles) {
		this.name = userName;
		this.company = company;
		this.roles = roles;
	}

	public String getName() {
		return this.name;
	}

	public Map<String, Boolean> getRoles() {
		return this.roles;
	}

}