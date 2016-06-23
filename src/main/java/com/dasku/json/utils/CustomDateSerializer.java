package com.dasku.json.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date>{

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
	
	
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		gen.writeString(formatter.format(date));
	}

}
