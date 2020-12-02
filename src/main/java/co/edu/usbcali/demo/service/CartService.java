package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface CartService {
	
	public ShoppingCart createCart(String email) throws Exception;
	
	public ShoppingProduct addProduct(Integer carId, String proId, Integer quantity) throws Exception;

	public void removeProduct(Integer carId, String proId) throws Exception;
	
	public void clearCart(Integer carId) throws Exception;
	
	public List<ShoppingProduct> findShoppingProductByShoppingCart(Integer carId)throws Exception;

	
	public ShoppingCart currentCart(String email)throws Exception;
	
	public List<ShoppingCart> historyCarts(String email)throws Exception;

	
}
