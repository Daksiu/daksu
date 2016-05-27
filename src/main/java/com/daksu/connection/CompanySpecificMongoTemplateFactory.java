package com.daksu.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import com.daksu.connection.execption.CompanySpecificMongoTemplateExecption;
import com.daksu.provisioner.domain.Company;
import com.daksu.provisioner.service.ProvisioningService;
import com.daksu.security.filter.DaksuUserAuthenticationToken;
import com.mongodb.Mongo;

public class CompanySpecificMongoTemplateFactory {
	
	@Autowired
	private ProvisioningService provisioningService;
		 
	private Mongo mongo;
	
	private MongoDbFactory mongoDbFactory;
	
	public CompanySpecificMongoTemplateFactory(Mongo mongo, MongoDbFactory mongoDbFactory) {
		super();
		this.mongo = mongo;
		this.mongoDbFactory = mongoDbFactory;
	}

	public MongoTemplate createMongoTemplate() {
		if(SecurityContextHolder.getContext().getAuthentication()!=null 
				&& SecurityContextHolder.getContext().getAuthentication() instanceof DaksuUserAuthenticationToken) {
		String companyId = ((DaksuUserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
				.getCompany();		
		Company company = provisioningService.getCompanyById(companyId);		
		return new MongoTemplate(mongo, company.getDatabase());
		} else {
			throw new CompanySpecificMongoTemplateExecption("Could not find company id from the context");
		}
		
	}
		
}
