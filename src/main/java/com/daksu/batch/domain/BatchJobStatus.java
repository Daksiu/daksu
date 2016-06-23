package com.daksu.batch.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dasku.json.utils.CustomDateSerializer;

@Document(collection = BatchJobStatus.COLLECTION_NAME)
public class BatchJobStatus implements Serializable {

public static final String COLLECTION_NAME = "batch_job_status";
	
	@Id
	@GeneratedValue
	private ObjectId id;
	
	private String jobId;
	
	private String jobName;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date jobCreatedDate;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date jobStartedDate;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date jobCompletedDate;
	
	private String jobCreatedBy;
	
	private String jobStatus;
	
	private String processedRecords;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Date getJobCreatedDate() {
		return jobCreatedDate;
	}

	public void setJobCreatedDate(Date jobCreatedDate) {
		this.jobCreatedDate = jobCreatedDate;
	}

	public Date getJobStartedDate() {
		return jobStartedDate;
	}

	public void setJobStartedDate(Date jobStartedDate) {
		this.jobStartedDate = jobStartedDate;
	}

	public Date getJobCompletedDate() {
		return jobCompletedDate;
	}

	public void setJobCompletedDate(Date jobCompletedDate) {
		this.jobCompletedDate = jobCompletedDate;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getProcessedRecords() {
		return processedRecords;
	}

	public void setProcessedRecords(String processedRecords) {
		this.processedRecords = processedRecords;
	}

	public String getJobCreatedBy() {
		return jobCreatedBy;
	}

	public void setJobCreatedBy(String jobCreatedBy) {
		this.jobCreatedBy = jobCreatedBy;
	}
	
	
}
