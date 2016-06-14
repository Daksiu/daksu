package com.daksu.provisioner.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.daksu.provisioner.domain.Company;

@Repository
public class CompanyRepository {

	@Autowired
	@Qualifier("daksuMongoTemplate")
	private MongoTemplate daksuMongoTemplate;
	

	
	public void addCompany(Company company) {
		if (!daksuMongoTemplate.collectionExists( Company.COLLECTION_NAME)) {
			daksuMongoTemplate.createCollection(Company.COLLECTION_NAME);
		}		
		daksuMongoTemplate.insert(company, Company.COLLECTION_NAME);
	}

	public Company findCompanyById(String companyId) {
		return daksuMongoTemplate.findOne(query(where("companyId").is(companyId)), Company.class);
	}
	
	public List<Company> listCompanies() {
		return daksuMongoTemplate.findAll(Company.class);
	}
}
