package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class ShopingCartDTO {
	
	private Integer carId;


	// bean validation
	@NotNull
	@PositiveOrZero
	private Long total;
	// bean validation
	
	@NotNull
	@Size(min = 1, max = 1) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String enable;
	
	// bean validation
	@NotNull
	@PositiveOrZero
	private Integer items;
	
	public ShopingCartDTO() {
		super();
	}

	public ShopingCartDTO(Integer carId, @NotNull @PositiveOrZero Long total,
			@NotNull @Size(min = 1, max = 1) @NotEmpty String enable, @NotNull @PositiveOrZero Integer items) {
		super();
		this.carId = carId;
		this.total = total;
		this.enable = enable;
		this.items = items;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public Integer getItems() {
		return items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	
	

}
