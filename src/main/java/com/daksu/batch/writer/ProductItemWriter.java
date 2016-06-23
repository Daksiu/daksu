package com.daksu.batch.writer;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.daksu.product.domain.Product;
import com.daksu.product.repository.BatchImportProductRepository;

/**
 * Writes products to a database
 */
public class ProductItemWriter implements ItemWriter<Product> {

	@Autowired
	private BatchImportProductRepository batchImportProductRepository;
	
	private String companyId;
	private String username;

	@Override
	public void write(List<? extends Product> products) throws Exception {
		
		batchImportProductRepository.setCompanyId(companyId);
		batchImportProductRepository.setUsername(username);

		List<Product> productsToBeInserted = new ArrayList<Product>();
		List<Product> productsToBeUpdated = new ArrayList<Product>();

		for (Product product : products) {

			Product productInDB = batchImportProductRepository.findProductByExternalId(product.getExternalId());
			if (productInDB != null) {
				product.setId(productInDB.getId());
				product.setExternalId(productInDB.getExternalId());
				productsToBeUpdated.add(product);
			} else {
				product.setId(new ObjectId());
				productsToBeInserted.add(product);
			}

		}
		if (productsToBeInserted.size() > 0) {
			batchImportProductRepository.addProducts(productsToBeInserted);
		}

		if (productsToBeUpdated.size() > 0) {
			batchImportProductRepository.updateProducts(productsToBeUpdated);
		}
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}