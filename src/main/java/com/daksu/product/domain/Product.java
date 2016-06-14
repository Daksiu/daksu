package com.daksu.product.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.daksu.product.json.ProductSerializer;

@Document(collection = Product.COLLECTION_NAME)
@JsonSerialize(using = ProductSerializer.class)
public class Product {

	public static final String COLLECTION_NAME = "products";
	
	@Id
	@GeneratedValue
	private ObjectId id;
	
	@Column(unique = true, nullable = false)
	private String externalId;
	
	private String name;
	
	private String description;
	
	private Integer quantity;

	private Map<String, Object> productFields = new HashMap<String, Object>();

	public Map<String, Object> getProductFields() {
		return productFields;
	}

	public void setProductFields(Map<String, Object> productFields) {
		this.productFields = productFields;
	}

	public void addProductField(String fieldName, Object fieldValue) {
		productFields.put(fieldName, fieldValue);
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
