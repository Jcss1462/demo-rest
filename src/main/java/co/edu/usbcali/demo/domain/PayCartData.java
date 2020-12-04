package co.edu.usbcali.demo.domain;

public class PayCartData {

	Integer cartId;
	Integer payId;
	Long cartNumber;

	public PayCartData() {
		super();
	}

	public PayCartData(Integer cartId, Integer payId, Long cartNumber) {
		super();
		this.cartId = cartId;
		this.payId = payId;
		this.cartNumber = cartNumber;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Long getCartNumber() {
		return cartNumber;
	}

	public void setCartNumber(Long cartNumber) {
		this.cartNumber = cartNumber;
	}

}
