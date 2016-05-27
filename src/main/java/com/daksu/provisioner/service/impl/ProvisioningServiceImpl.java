package com.daksu.provisioner.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.daksu.provisioner.bean.RegistrationBean;
import com.daksu.provisioner.domain.Company;
import com.daksu.provisioner.domain.CompanyType;
import com.daksu.provisioner.repository.CompanyRepository;
import com.daksu.provisioner.service.ProvisioningService;
import com.daksu.user.bean.UserBean;
import com.daksu.user.domain.Role;
import com.daksu.user.service.UserService;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment env;
	
	@Override
	public List<Company> listCompanies() {
		return companyRepository.listCompanies();
	}
	
	@Override
	public Company getCompanyById(String companyId) {
		return companyRepository.findCompanyById(companyId);
	}
	
	@Override
	public void registerCompany(RegistrationBean registrationBean) {
		
		Company company = new Company() ;
		company.setCompanyId(registrationBean.getCompanyId());
		company.setCompanyName(registrationBean.getCompanyName());		
		company.setCompanyType(CompanyType.valueOf(registrationBean.getCompanyType()));
		company.setDatabase(getDatabaseName(registrationBean));
		
		companyRepository.addCompany(company);
		
		UserBean userBean = new UserBean();
		userBean.setUsername(registrationBean.getEmail());
		userBean.setPassword(registrationBean.getPassword());
		Set<Role> roles = new HashSet<Role>();
		roles.add(Role.ADMIN);
		userBean.setRoles(roles);
		userService.addUser(userBean);
		
	}
	
	
	private String getDatabaseName(RegistrationBean registrationBean) {
		String schemaName= env.getProperty("mongo.dbname");
		//Move this constant to property file
		return "daksu_" + registrationBean.getCompanyId();
	}

}
