package com.daksu.provisioner.domain;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Company.COLLECTION_NAME)
public class Company implements Serializable {

	public static final String COLLECTION_NAME = "companies";
	
	@Id
	@GeneratedValue
	private BigInteger id;
	
	private String companyId;

	private String companyName;
	
	private String database;

	private boolean emailValidated;
	
	private boolean status;

	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isEmailValidated() {
		return emailValidated;
	}

	public void setEmailValidated(boolean emailValidated) {
		this.emailValidated = emailValidated;
	}

	private CompanyType companyType;

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	

}
