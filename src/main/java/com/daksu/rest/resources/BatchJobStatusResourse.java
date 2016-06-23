package com.daksu.rest.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.daksu.batch.repository.ImportJobsRepository;

@Component
@Path("/job")
public class BatchJobStatusResourse {
	
	@Autowired
	private ImportJobsRepository importJobsRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(importJobsRepository.listBatchJobs());
	}

}
