package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

@SpringBootTest
@Rollback(false)
class CartServiceTest {
	
	//inyecto el servicio
	
	@Autowired
	CartService cartService;

	@Test
	void debeCrearUnShopingCart() throws Exception {
		//Arrange (declarar)
		String email="abaglowbn@furl.net";
		ShoppingCart shopingCart=null;
		//Act (actuar)
		shopingCart=cartService.createCart(email);
		//Assert (validar)
		assertNotNull(shopingCart);
		
	}
	
	@Test
	void noDebeCrearUnShoppingCartPorCustomerDisable()throws Exception {
		//Arrange
		String email="ehutcheonh@multiply.com";
		

		//lanza falla si no hay excepcion
		assertThrows(Exception.class, ()->cartService.createCart(email));
		
	}
	
	@Test
	void noDebeCrearUnShoppingCartPorCustomerNull()throws Exception {
		//Arrange
		String email=null;
		
		//Act
		//lanza falla si hay excepcion
		assertThrows(Exception.class, ()->cartService.createCart(email));
		
	}
	
	@Test
	void noDebeCrearUnShoppingCartPorCustomerNoExiste()throws Exception {
		//Arrange
		String email="jperez@vvv.com";
		
		//Act
		//lanza falla si hay excepcion
		assertThrows(Exception.class, ()->cartService.createCart(email));
		
	}
	
	@Test
	void debeAgregarProductSgopingCart()throws Exception {
		//Arrange
		Integer carId=15;
		String proId="APPL90";
		Integer quantity=10;
		
		//creo el shopingProduct
		ShoppingProduct shopingproduct;
		
		//Act
		shopingproduct=cartService.addProduct(carId, proId, quantity);
		
		//Assert
		//sigo si todo sale bien
		assertNotNull(shopingproduct,"El ShopingProduct es nulo");
	}
	
}
