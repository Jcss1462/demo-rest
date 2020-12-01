package co.edu.usbcali.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

	
	@Query("SELECT shc FROM ShoppingCart shc WHERE shc.enable='Y' AND shc.customer.email=:email")
	public List<ShoppingCart> findShopingCartEnable(String email);
	
	@Query("SELECT shc FROM ShoppingCart shc WHERE shc.enable='N' AND shc.customer.email=:email")
	public List<ShoppingCart> findShopingCartDisable(String email);

	
}
