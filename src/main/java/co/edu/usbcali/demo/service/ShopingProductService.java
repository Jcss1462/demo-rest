package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface ShopingProductService extends GenericService<ShoppingProduct, Integer> {
	public Long totalShopingProductByShopingCart(Integer carId);
	
	public Integer totalItemsShopingCart(Integer carId);
	
	public ShoppingProduct obtenerShopingProductFromShopingCart(Integer carId,String proId);
	
	public List<ShoppingProduct> findByCartId(Integer carId);
}
