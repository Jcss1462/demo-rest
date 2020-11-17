package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

@SpringBootTest
@Rollback(false)
class CartServiceTest {

	private final static Logger log = LoggerFactory.getLogger(CartServiceTest.class);

	// inyecto el servicio

	@Autowired
	CartService cartService;

	// ShopingCart
	@Test
	void debeCrearUnShopingCart() throws Exception {
		// Arrange (declarar)
		String email = "abaglowbn@furl.net";
		ShoppingCart shopingCart = null;
		// Act (actuar)
		shopingCart = cartService.createCart(email);
		// Assert (validar)
		assertNotNull(shopingCart);

	}

	@Test
	void noDebeCrearUnShoppingCartPorCustomerDisable() throws Exception {
		// Arrange
		String email = "ehutcheonh@multiply.com";

		// lanza falla si no hay excepcion
		assertThrows(Exception.class, () -> cartService.createCart(email));

	}

	@Test
	void noDebeCrearUnShoppingCartPorCustomerNull() throws Exception {
		// Arrange
		String email = null;

		// Act
		// lanza falla si hay excepcion
		assertThrows(Exception.class, () -> cartService.createCart(email));

	}

	@Test
	void noDebeCrearUnShoppingCartPorCustomerNoExiste() throws Exception {
		// Arrange
		String email = "jperez@vvv.com";

		// Act
		// lanza falla si hay excepcion
		assertThrows(Exception.class, () -> cartService.createCart(email));

	}

	// shoping product
	@Test
	void debeAgregarProductSgopingCart() throws Exception {
		// Arrange
		// Integer carId=15;
		Integer carId = 57;
		String proId = "APPL45";
		Integer quantity = 15;

		// creo el shopingProduct
		ShoppingProduct shopingproduct;

		// Act
		shopingproduct = cartService.addProduct(carId, proId, quantity);

		// Assert
		// sigo si todo sale bien
		assertNotNull(shopingproduct, "El ShopingProduct es nulo");
	}

	@Test
	void eliminarShopingProduct() throws Exception {
		// Arrange
		// Integer carId=15;
		Integer carId = 57;
		String proId = "APPL45";

		// Act
		cartService.removeProduct(carId, proId);

	}

	@Test
	@Transactional
	void clearCart() throws Exception {
		// Arrange
		// Integer carId=15;
		Integer carId = 57;

		// Act
		cartService.clearCart(carId);

	}

	@Test
	void showShoppingProductByShoppingCart() throws Exception {
		// Arrange
		// Integer carId=15;
		Integer carId = 12;

		// Act
		List<ShoppingProduct> listShopingProducts = cartService.findShoppingProductByShoppingCart(carId);

		// Assert
		// sigo si todo sale bien
		assertNotNull(listShopingProducts, "La lista esta vacia");
		
		listShopingProducts.forEach(item -> {
			log.info(item.getProduct().toString()+item.getTotal().toString());
		});
		
	}

}
