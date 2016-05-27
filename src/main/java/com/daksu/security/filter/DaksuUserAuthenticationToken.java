package com.daksu.security.filter;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class DaksuUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;
	
	private String company;
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public DaksuUserAuthenticationToken(String company) {
		super(null, null);
		this.company = company;
	}
	
	public DaksuUserAuthenticationToken(Object principal, Object credentials, String company) {
		super(principal, credentials);
		this.company = company;
	}
	
	public DaksuUserAuthenticationToken(Object principal, Object credentials, String company, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.company = company;
	}

}
