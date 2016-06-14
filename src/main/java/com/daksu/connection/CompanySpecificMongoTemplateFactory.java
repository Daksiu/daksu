package com.daksu.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import com.daksu.connection.execption.CompanySpecificMongoTemplateExecption;
import com.daksu.provisioner.domain.Company;
import com.daksu.provisioner.service.ProvisioningService;
import com.daksu.security.filter.DaksuUserAuthenticationToken;
import com.daksu.utils.UserContext;
import com.mongodb.Mongo;

public class CompanySpecificMongoTemplateFactory {
	
	@Autowired
	private ProvisioningService provisioningService;
	
	@Autowired
	private UserContext userContext;
		 
	private Mongo mongo;
	
	
	public CompanySpecificMongoTemplateFactory(Mongo mongo) {
		super();
		this.mongo = mongo;
	}

	public MongoTemplate createMongoTemplate() {
		if(SecurityContextHolder.getContext().getAuthentication()!=null 
				&& SecurityContextHolder.getContext().getAuthentication() instanceof DaksuUserAuthenticationToken) {
		String companyId = ((DaksuUserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
				.getCompany();		
		Company company = provisioningService.getCompanyById(companyId);		
		return new MongoTemplate(mongo, company.getDatabase());
		} else if(userContext!=null && userContext.getCompany()!=null) {
			return new MongoTemplate(mongo, userContext.getCompany().getDatabase());
		} else {
			throw new CompanySpecificMongoTemplateExecption("Could not find company id from the context");
		}
		
	}
		
}
