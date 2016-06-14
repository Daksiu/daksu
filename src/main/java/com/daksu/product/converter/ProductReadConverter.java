package com.daksu.product.converter;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;

import com.daksu.product.domain.Product;
import com.mongodb.DBObject;

public class ProductReadConverter implements Converter<DBObject, Product> {

	public Product convert(DBObject source) {
		if(source == null) {
			return null;
		}
		Product p = new Product();
		p.setId((ObjectId) source.get("_id"));
		
		p.setExternalId((String) source.get("externalId"));
		p.setName((String) source.get("name"));
		p.setDescription((String) source.get("description"));
		
		source.removeField("_id");
		source.removeField("externalId");
		source.removeField("name");
		source.removeField("description");
		
		for(String key: source.keySet()) {
			p.addProductField(key, source.get(key));
		}
		return p;
	}

}


