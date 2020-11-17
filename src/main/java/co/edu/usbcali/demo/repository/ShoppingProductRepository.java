package co.edu.usbcali.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface ShoppingProductRepository extends JpaRepository<ShoppingProduct, Integer> {
	// obtengo la suma de del total de todos los shoping products del shopingCart
	@Query("SELECT SUM(shpr.total) FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public Long totalShoppingProductByShoppingCart(Integer carId);

	// obtengo EL TOTAL DE ELEMENTOS
	@Query("SELECT SUM(shpr.quantity) FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public Integer totalItemsShopingCart(Integer carId);

	// obtengo EL TOTAL DE ELEMENTOS
	@Query("SELECT shpr FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId AND shpr.product.proId=:proId")
	public ShoppingProduct obtenerShopingProductFromShopingCart(Integer carId, String proId);

	// obtengo EL TOTAL DE ELEMENTOS
	@Query("SELECT shpr FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public List<ShoppingProduct> findByCartId(Integer carId);
}
