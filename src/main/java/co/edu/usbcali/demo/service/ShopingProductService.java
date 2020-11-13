package co.edu.usbcali.demo.service;

import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface ShopingProductService extends GenericService<ShoppingProduct, Integer> {
	public Long totalShopingProductByShopingCart(Integer carId);
}
