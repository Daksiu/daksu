package com.daksu.provisioner.service;

import java.util.List;

import com.daksu.provisioner.bean.RegistrationBean;
import com.daksu.provisioner.domain.Company;

public interface ProvisioningService {

	List<Company> listCompanies();

	void registerCompany(RegistrationBean registrationBean);

	Company getCompanyById(String companyId);

}
