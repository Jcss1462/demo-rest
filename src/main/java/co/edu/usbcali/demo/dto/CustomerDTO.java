package co.edu.usbcali.demo.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerDTO {

	// uso los datos simples de Customer
	@Column(name = "email", unique = true, nullable = false)
	// bean validation
	@NotNull
	@Email // valido que sea un correo
	@Size(min = 3, max = 255) // valido la longitud de carecteres
	@NotEmpty
	private String email;

	@Column(name = "address", nullable = false)
	// bean validation
	@NotNull
	@Size(min = 3, max = 255) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String address;

	@Column(name = "enable", nullable = false)
	// bean validation
	@NotNull
	@Size(min = 1, max = 1) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String enable;

	@Column(name = "name", nullable = false)
	// bean validation
	@NotNull
	@Size(min = 4, max = 255) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String name;

	@Column(name = "phone", nullable = false)
	// bean validation
	@NotNull
	@Size(min = 6, max = 255) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String phone;

	@Column(name = "token", nullable = false)
	// bean validation
	@NotNull
	@Size(max = 255) // valido la longitud de carecteres
	@NotEmpty // valido que no este vacio
	private String token;

	public CustomerDTO() {
		super();
	}

	public CustomerDTO(String email, String address, String enable, String name, String phone, String token) {
		super();
		this.email = email;
		this.address = address;
		this.enable = enable;
		this.name = name;
		this.phone = phone;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
