package com.daksu.product.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daksu.product.converter.ProductReadConverter;
import com.daksu.product.converter.ProductWriteConverter;
import com.daksu.product.domain.Product;
import com.daksu.provisioner.domain.Company;
import com.daksu.provisioner.service.ProvisioningService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

@Service
public class BatchImportProductRepository {
	
	private Mongo mongo;
	
	@Autowired
	private ProvisioningService provisioningService;
	
	private String companyId;
	private String userName;
	
	public BatchImportProductRepository(Mongo mongo) {
		super();
		this.mongo = mongo;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public void setUsername(String username) {
		this.userName = username;
	}
	
	
	private final ProductWriteConverter writeConverter = new ProductWriteConverter();
	
	private final ProductReadConverter readConverter = new ProductReadConverter();
	
	@Transactional
	public void addProduct(Product product) {
		if (!getMongoTemplate().collectionExists(Product.COLLECTION_NAME)) {
			getMongoTemplate().createCollection(Product.COLLECTION_NAME);
		}	
		
		getMongoTemplate().getDb().getCollection(Product.COLLECTION_NAME).insert(writeConverter.convert(product));
	}
	
	@Transactional
	public void addProducts(List<? extends Product> products) {
		List<DBObject> list = new ArrayList<DBObject>();
		for(Product product : products) {
			list.add(writeConverter.convert(product));
		}
		getMongoTemplate().getDb().getCollection(Product.COLLECTION_NAME).insert(list);
	}
	
	public List<Product> listProducts() {
		List<Product> products = new ArrayList<Product>();
		DBCursor cursor = getMongoTemplate().getDb().getCollection(Product.COLLECTION_NAME).find();
		while(cursor.hasNext()) {
			DBObject object = cursor.next();
			products.add(readConverter.convert(object));
		}
		return products;
	}
	
	public Product findProductByExternalId(String externalId) {
		DBObject query = new BasicDBObject("externalId", externalId);
		DBObject result = getMongoTemplate().getDb().getCollection(Product.COLLECTION_NAME).findOne(query);
		return readConverter.convert(result);
	}

	@Transactional
	public void updateProducts(List<Product> productsToBeUpdated) {
		for(Product product : productsToBeUpdated) {
			DBObject query = new BasicDBObject("externalId", product.getExternalId());
			getMongoTemplate().getDb().getCollection(Product.COLLECTION_NAME).update(query, writeConverter.convert(product));
		}
	}
	
	public boolean isProductCollectionExists() {
		return getMongoTemplate().collectionExists(Product.COLLECTION_NAME);
	}
	
	public  void createProductCollection() {
		if (!getMongoTemplate().collectionExists(Product.COLLECTION_NAME)) {
			getMongoTemplate().createCollection(Product.COLLECTION_NAME);
		}	
	}
	
	private MongoTemplate getMongoTemplate() {
		Company company = provisioningService.getCompanyById(companyId);		
		return new MongoTemplate(mongo, company.getDatabase());
	}

}
