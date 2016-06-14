package com.daksu.batch.tasklet;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * A tasklet that archives the input file
 */
public class ArchiveProductImportFileTasklet implements Tasklet {
	
	private String inputFile;
	
	private String archiveDirectory;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		// Make our destination directory and copy our input file to it
		
		inputFile = chunkContext.getStepContext().getStepExecution()
	      .getJobParameters().getString("inputFile");
		
		archiveDirectory = chunkContext.getStepContext().getStepExecution()
			      .getJobParameters().getString("archiveDirectory");
		
		File archiveDir = new File(archiveDirectory);
		FileUtils.forceMkdir(archiveDir);
		FileUtils.copyFileToDirectory(new File(inputFile), archiveDir);

		// We're done...
		return RepeatStatus.FINISHED;
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
}
