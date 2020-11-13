package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
@Rollback(false)
class ShopingProductServiceTest {
	
	@Autowired
	ShopingProductServiceImpl shopingProductService;

	@Test
	void test() {
		
		//Arrange
		Long total=0L;
		Integer carId=15;
		
		//Act
		//obtengo la suma de del total de todos los shoping products del shopingCart
		total=shopingProductService.totalShopingProductByShopingCart(carId);
		
		//Assert
		//siga si es verdadero
		assertTrue(total>0);
	}

}
