package com.daksu.user.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Password {

	private String salt;

	private String hash;
	
	public Password(String salt, String hash) {
		super();
		this.salt = salt;
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
