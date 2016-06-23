package com.daksu.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.daksu.batch.domain.BatchJobStatus;
import com.daksu.batch.repository.BatchJobRepository;

public class ImportJobListener implements JobExecutionListener {
	
	@Autowired
	private BatchJobRepository batchjobRepository;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		BatchJobStatus status = new BatchJobStatus();
		status.setJobCreatedBy(getUsername(jobExecution));
		status.setJobCreatedDate(jobExecution.getCreateTime());
		status.setJobId(getJobId(jobExecution));
		status.setJobName(jobExecution.getJobInstance().getJobName());
		status.setJobStartedDate(jobExecution.getStartTime());
		status.setJobStatus(jobExecution.getStatus().toString());
		
		batchjobRepository.setCompanyId(getCompanyId(jobExecution));
		batchjobRepository.saveBatchJobStatus(status);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		
		BatchJobStatus status = batchjobRepository.findBatchJobStatusByJobId(getJobId(jobExecution));
		
		if(status!=null) {
			status.setJobCompletedDate(jobExecution.getEndTime());
			status.setJobStatus(jobExecution.getStatus().toString());
			batchjobRepository.setCompanyId(getCompanyId(jobExecution));
			batchjobRepository.saveBatchJobStatus(status);
		}
		
	}

	private String getCompanyId(JobExecution jobExecution) {
		return jobExecution.getJobParameters().getString("companyId");
	}

	private String getUsername(JobExecution jobExecution) {
		return jobExecution.getJobParameters().getString("username");
	}
	
	private String getJobId(JobExecution jobExecution) {
		return jobExecution.getJobParameters().getString("jobId");
	}
	
	
}
