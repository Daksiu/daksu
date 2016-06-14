package com.daksu.batch.service;

import java.io.InputStream;

import com.daksu.batch.exception.DaksuImportException;

public interface BatchImportJobService {

	void importFile(InputStream uploadedInputStream, String fileName) throws DaksuImportException;

	String getDataDirectory();

	String getDataArchiveDirectory();

}
