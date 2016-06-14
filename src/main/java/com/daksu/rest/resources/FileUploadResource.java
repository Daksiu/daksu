package com.daksu.rest.resources;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.daksu.batch.exception.DaksuImportException;
import com.daksu.batch.service.BatchImportJobService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/upload")
public class FileUploadResource {

	@Autowired
	private BatchImportJobService batchImportJobService;

	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public String uploadFile(@Context HttpServletRequest request,
			@FormDataParam("file") FormDataContentDisposition dispostion,
			@FormDataParam("file") InputStream attachmentFile) {
		String fileName = dispostion.getFileName();
		try {
			batchImportJobService.importFile(attachmentFile, fileName);
		} catch (DaksuImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Import scheduled. Please check import reports";

	}
	


}
