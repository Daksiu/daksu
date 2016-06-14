package com.daksu.product.converter;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import com.daksu.product.domain.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProductWriteConverter implements Converter<Product, DBObject> {

	  public DBObject convert(Product source) {
		  
		  if(source == null) {
				return null;
			}
		  
	    DBObject dbo = new BasicDBObject();
	    dbo.put("_id", source.getId());
	    dbo.put("externalId", source.getExternalId());
	    dbo.put("name", source.getName());
	    dbo.put("description", source.getDescription());
	    
	    for(Map.Entry<String, Object> entry : source.getProductFields().entrySet()) {
	    	dbo.put(entry.getKey(), entry.getValue());
	    }
	    
	    return dbo;
	  }

	}


