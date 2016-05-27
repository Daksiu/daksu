package com.daksu.provisioner.bean;

import java.io.Serializable;

public class CompanyBean implements Serializable {
	
	private String companyName;
	private String companyId;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
