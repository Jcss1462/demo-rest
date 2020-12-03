package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import co.edu.usbcali.demo.domain.Product;

public class SopingProductDTO {
	
	private Integer shprId;

	
	// bean validation
	@NotNull
	@PositiveOrZero
	private Integer quantity;
	
	@NotNull
	@PositiveOrZero
	private Long total;
	
	private Product product;
	
	private String proId;
	
	private String proName;
	
	private String proImg;
	
	private Integer price;

	public SopingProductDTO() {
		super();
	}

	
	public SopingProductDTO(Integer shprId, @NotNull @PositiveOrZero Integer quantity,
			@NotNull @PositiveOrZero Long total, Product product, String proId, String proName, String proImg,
			Integer price) {
		super();
		this.shprId = shprId;
		this.quantity = quantity;
		this.total = total;
		this.product = product;
		this.proId = proId;
		this.proName = proName;
		this.proImg = proImg;
		this.price = price;
	}


	public Integer getShprId() {
		return shprId;
	}

	public void setShprId(Integer shprId) {
		this.shprId = shprId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	public String getProId() {
		return product.getProId();
	}

	public void setProId(String proId) {
		this.proId = proId;
	}


	public String getProName() {
		return product.getName();
	}


	public void setProName(String proName) {
		this.proName = proName;
	}


	public String getProImg() {
		return product.getImage();
	}


	public void setProImg(String proImg) {
		this.proImg = proImg;
	}


	public Integer getPrice() {
		return product.getPrice();
	}


	public void setPrice(Integer price) {
		this.price = price;
	}




	

}
