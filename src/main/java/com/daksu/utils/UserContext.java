package com.daksu.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.daksu.provisioner.domain.Company;
import com.daksu.provisioner.repository.CompanyRepository;
import com.daksu.security.filter.DaksuUserAuthenticationToken;
import com.daksu.user.domain.User;
import com.daksu.user.repositoty.UserRepository;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext implements InitializingBean {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;

	private User user;

	private Company company;

	@Override
	public void afterPropertiesSet() throws Exception {
		Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();

		if (authenticationToken != null && authenticationToken instanceof DaksuUserAuthenticationToken) {
			String company = ((DaksuUserAuthenticationToken) authenticationToken).getCompany();
			this.user = (User)((DaksuUserAuthenticationToken) authenticationToken).getPrincipal();

			this.company = companyRepository.findCompanyById(company);
		}
	}
	
	public User getUser() {
		return user;
	}

	public Company getCompany() {
		return company;
	}

}
