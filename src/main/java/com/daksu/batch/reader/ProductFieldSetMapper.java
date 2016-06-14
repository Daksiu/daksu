package com.daksu.batch.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.daksu.product.domain.Product;

/**
 * Builds a Product from a row in the CSV file (as a set of fields)
 */
public class ProductFieldSetMapper implements FieldSetMapper<Product> {
	@Override
	public Product mapFieldSet(FieldSet fieldSet) throws BindException {
		/*int fieldCount = fieldSet.getFieldCount();
		//Properties properties = fieldSet.getProperties();
		String[] values = fieldSet.getValues() ;
		boolean hasName = fieldSet.hasNames();*/
		
		String[] names = fieldSet.getNames();
		
		List<String> listOfNames = new ArrayList<String>(Arrays.asList(names));
		
		Product product = new Product();
		if (listOfNames.contains("externalId")) {
			product.setExternalId(fieldSet.readString("externalId"));
			listOfNames.remove("externalId");
		}
		if (listOfNames.contains("name")) {
			product.setName(fieldSet.readString("name"));
			listOfNames.remove("name");
		}
		if (listOfNames.contains("description")) {
			product.setDescription(fieldSet.readString("description"));
			listOfNames.remove("description");
		}
		if (listOfNames.contains("quantity")) {
			product.setQuantity(fieldSet.readInt("quantity"));
			listOfNames.remove("quantity");
		}
		
		
		for(String name : listOfNames) {
			product.addProductField(name, fieldSet.readString(name));
		}
		
		return product;
	}
}
