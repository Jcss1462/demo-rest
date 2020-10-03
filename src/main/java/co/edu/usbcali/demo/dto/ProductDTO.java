package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDTO {

	// uso los datos simples de Product
	@NotNull
	@Size(min = 3, max = 255) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String proId;

	@NotNull
	@Size(max = 255) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String detail;

	@NotNull
	@Size(min = 1, max = 1)
	@NotEmpty // valido que no este vacio
	private String enable;

	private String image;

	@NotNull
	@Size(min = 4, max = 255)
	@NotEmpty // valido que no este vacio
	private String name;

	@NotNull
	private Integer price;

	public ProductDTO() {
		super();
	}

	public ProductDTO(String proId, String detail, String enable, String image, String name, Integer price) {
		super();
		this.proId = proId;
		this.detail = detail;
		this.enable = enable;
		this.image = image;
		this.name = name;
		this.price = price;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
