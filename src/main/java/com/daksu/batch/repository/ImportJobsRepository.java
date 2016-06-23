package com.daksu.batch.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.daksu.batch.domain.BatchJobStatus;

@Service
public class ImportJobsRepository {
	
	@Autowired
    private WebApplicationContext context;

	
	public List<BatchJobStatus> listBatchJobs() {
		
		Query query = new Query();
		query.limit(10);
		query.with(new Sort(Sort.Direction.DESC, "jobCreatedDate"));
		
		return getMongoTemplate().find(query, BatchJobStatus.class);
	}
	
	
	
	private MongoTemplate getMongoTemplate() {
		return (MongoTemplate) context.getBean("mongoTemplate");
	}

}
