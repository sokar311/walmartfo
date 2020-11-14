package com.walmart.frontoffice.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764833899913381916L;
	
	private Integer id;
	private String brand;
	private String description;
	private String image;
	private Integer price;
	private Integer previusPrice;
	private Integer discountPercentage;
	
	public Integer getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(Integer discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPreviusPrice() {
		return previusPrice;
	}
	public void setPreviusPrice(Integer previusPrice) {
		this.previusPrice = previusPrice;
	}
	
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", brand=" + brand + ", description=" + description + ", image=" + image
				+ ", price=" + price + ", previusPrice=" + previusPrice + ", discountPercentage=" + discountPercentage
				+ "]";
	}	
}
