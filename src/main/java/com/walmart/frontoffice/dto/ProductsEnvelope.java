package com.walmart.frontoffice.dto;

import java.io.Serializable;
import java.util.List;

public class ProductsEnvelope implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -473690259158533106L;
	private List<ProductDTO> products;

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "ProductsEnvelope [products=" + products + "]";
	}
	
}
