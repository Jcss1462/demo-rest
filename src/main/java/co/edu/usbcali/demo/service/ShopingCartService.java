package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.ShoppingCart;

public interface ShopingCartService extends GenericService<ShoppingCart, Integer> {

	public List<ShoppingCart> ListShopingCartEnable(String email) throws Exception;
	
	public List<ShoppingCart> ListShopingCartDisable(String email) throws Exception;
	
}
