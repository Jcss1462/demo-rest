package co.edu.usbcali.demo.domain;

public class NewProduct {

	String proId;
	
	Integer cartId;

	public NewProduct() {
		super();
	}

	public NewProduct(String proId, Integer cartId) {
		super();
		this.proId = proId;
		this.cartId = cartId;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

}
