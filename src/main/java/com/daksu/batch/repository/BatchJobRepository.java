package com.daksu.batch.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.daksu.batch.domain.BatchJobStatus;
import com.daksu.provisioner.domain.Company;
import com.daksu.provisioner.service.ProvisioningService;
import com.mongodb.Mongo;

@Service
public class BatchJobRepository {

	private Mongo mongo;
	
	private String companyId;
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Autowired
	private ProvisioningService provisioningService;
	
	public BatchJobRepository(Mongo mongo) {
		super();
		this.mongo = mongo;
	}
	
	private MongoTemplate getMongoTemplate() {
		Company company = provisioningService.getCompanyById(companyId);		
		return new MongoTemplate(mongo, company.getDatabase());
	}

	public void saveBatchJobStatus(BatchJobStatus status) {
		getMongoTemplate().save(status);
	}
	
	public BatchJobStatus findBatchJobStatusByJobId(String jobId) {
		return getMongoTemplate().findOne(query(where("jobId").is(jobId)), BatchJobStatus.class);
	}
}
