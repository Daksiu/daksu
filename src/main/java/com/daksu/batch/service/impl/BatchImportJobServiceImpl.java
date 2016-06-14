package com.daksu.batch.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import com.daksu.batch.exception.DaksuImportException;
import com.daksu.batch.service.BatchImportJobService;
import com.daksu.product.repository.BatchImportProductRepository;
import com.daksu.utils.SpringPropertiesUtil;
import com.daksu.utils.UserContext;

import au.com.bytecode.opencsv.CSVReader;

public class BatchImportJobServiceImpl implements BatchImportJobService {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Autowired
	private UserContext userContext;
	
	@Override
	public void importFile(InputStream uploadedInputStream, String fileName) throws DaksuImportException {
	
		fileName = fileName.replace(".csv", ("-").concat(new SimpleDateFormat("yyyyMMddhhmmss'.csv'").format(new Date())));
		
		saveFile(uploadedInputStream, fileName);
		scheduleJob(fileName);
	}

	private void scheduleJob(String fileName) {
		try {
			String dataDir = getDataDirectory();
			String archiveDir = getDataArchiveDirectory();
			
			String fileDir = dataDir.concat(FileSystems.getDefault().getSeparator()).concat(fileName);
			
			String header = readCsvHeader(fileDir);
			
			JobParameters params = new JobParametersBuilder()
					.addString("inputFile", fileDir)
					.addString("inputFileHeader", header)
					.addString("companyId", userContext.getCompany().getCompanyId())
					.addString("username", userContext.getUser().getUsername())
					.addString("archiveDirectory", archiveDir)
					.addDate("date", new Date()).toJobParameters();

		
			jobLauncher.run(job, params);
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	@Override
	public String getDataDirectory() {
		String dataDir = SpringPropertiesUtil.getProperty("daksu.upload.dir");
		String company = userContext.getCompany().getCompanyId();
		String companyDataDir = dataDir + FileSystems.getDefault().getSeparator() + company;
		File theDir = new File(companyDataDir);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + companyDataDir);
			theDir.mkdir();
		}
		return companyDataDir;
	}

	@Override
	public String getDataArchiveDirectory() {
		return getDataDirectory() + FileSystems.getDefault().getSeparator() + "archive";
	}

	private void saveFile(InputStream uploadedInputStream, String fileName) throws DaksuImportException {
		try {
			String uploadedFileLocation = getDataDirectory().concat(FileSystems.getDefault().getSeparator())
					.concat(fileName);
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new DaksuImportException(e.getMessage());
		}
	}
	
	private String readCsvHeader(String file) throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new FileReader(file));
		String[] header = reader.readNext();
		reader.close();
		StringBuilder sb = new StringBuilder();
		int l = 1;
		for (String n : header) {			
			sb.append(n);
			if(header.length != l) {
				sb.append(",");
			}
			l++;
		}
		return sb.toString();
	}

}
