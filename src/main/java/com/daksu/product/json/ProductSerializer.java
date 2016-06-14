package com.daksu.product.json;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.daksu.product.domain.Product;

public class ProductSerializer extends JsonSerializer<Product> {

	@Override
	public void serialize(Product product, JsonGenerator jgen, SerializerProvider provided)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeStringField("id", product.getId().toHexString());
		jgen.writeStringField("name", product.getName());
		jgen.writeStringField("description", product.getDescription());

		for (Map.Entry<String, Object> entry : product.getProductFields().entrySet()) {
			jgen.writeStringField(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
		}

		jgen.writeEndObject();

	}

}
