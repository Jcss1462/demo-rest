package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;


@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ShopingProductRepositoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(ShopingProductRepositoryTest.class);
	
	//se usa estatico para que el  valor se guarde durante toda la ejecucion
	private static Integer shprId=null;
	private static String proId="APPL45";
	private static Integer carId=12;
	
	@Autowired
	ShoppingProductRepository shopingProductRepository;
	
	//traigo los repositorios de cuatomer y payment method para manipular los objetos de la bd
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ShoppingCartRepository shoppingCartRepository;


	@Test
	@Order(1)
	@Transactional
	void save() {
		log.info("Save");
		ShoppingProduct shopingProduct= new ShoppingProduct();
		shopingProduct.setQuantity(15);
		shopingProduct.setTotal(25000L);
		
		//obtengo el producto y lo guardo en shoping product
		Optional<Product> productOptional=productRepository.findById(proId);
		assertTrue(productOptional.isPresent(),"El producto con el id "+proId+" no existe");
		Product product=productOptional.get();
		shopingProduct.setProduct(product);
		
		//obtengo el shpingCart y lo guardo en shoping product
		Optional<ShoppingCart> shopingCartOptional=shoppingCartRepository.findById(carId);
		assertTrue(shopingCartOptional.isPresent(),"El shoping cart con el id "+carId+" no existe");
		ShoppingCart shopingCart=shopingCartOptional.get();
		shopingProduct.setShoppingCart(shopingCart);
		
		//obtengo el id generado luego de la insecion
		shopingProduct=shopingProductRepository.save(shopingProduct);
		shprId=shopingProduct.getShprId();
		
		//siga si no es nulo
		assertNotNull(shprId,"El shprId es nulo");
		log.info("El shprId es "+shprId);
	}
	
	@Test
	@Order(2)
	@Transactional
	void findById() {
		log.info("findById");
		Optional<ShoppingProduct> shoppingProductOptional=shopingProductRepository.findById(shprId);
		assertTrue(shoppingProductOptional.isPresent(),"El shopping product con shprId "+shprId+" no existe");
	}
	
	@Test
	@Order(3)
	@Transactional
	void update() {
		log.info("update");
		Optional<ShoppingProduct> shoppingProductOptional=shopingProductRepository.findById(shprId);
		assertTrue(shoppingProductOptional.isPresent(),"El shopping product con shprId "+shprId+" no existe");
	
		//obtengo el shoppingProduct
		ShoppingProduct shoppingProduct=shoppingProductOptional.get();
		//actualizo
		shoppingProduct.setQuantity(1);
		shoppingProduct.setTotal(200L);
		//lo guardo
		shopingProductRepository.save(shoppingProduct);
	}
	
	@Test
	@Order(4)
	@Transactional
	void delete() {
		log.info("delete");
		Optional<ShoppingProduct> shoppingProductOptional=shopingProductRepository.findById(shprId);
		assertTrue(shoppingProductOptional.isPresent(),"El shopping product con shprId "+shprId+" no existe");

		//obtengo el shopingProduct
		ShoppingProduct shoppingProduct=shoppingProductOptional.get();
		//lo borro
		shopingProductRepository.delete(shoppingProduct);
	}

}
