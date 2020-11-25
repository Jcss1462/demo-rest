package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;

public class SopingProductDTO {
	
	private Integer shprId;

	
	
	
	// bean validation
	@NotNull
	@PositiveOrZero
	private Integer quantity;
	
	@NotNull
	@PositiveOrZero
	private Long total;
	
	
	public SopingProductDTO() {
		super();
	}

	public SopingProductDTO(Integer shprId, 
			@NotNull @PositiveOrZero Integer quantity, @NotNull @PositiveOrZero Long total) {
		super();
		this.shprId = shprId;
		this.quantity = quantity;
		this.total = total;
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
	
	
	
	

}
