package com.daksu.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.daksu.product.domain.Product;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product product) throws Exception {

		return product;
	}
}
