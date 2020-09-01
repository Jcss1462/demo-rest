package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
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

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ProductRepositoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(ProductRepositoryTest.class);
	
	private final static String proId="MOM142";
	
	//llamo al repositorio en lugar del entity manager
	@Autowired
	ProductRepository productRepository;

	@Test
	//para poder hacer las transaccion con invercion de control
	@Transactional
	@Order(1)
	void save() {
		log.info("save");
		Optional<Product> productOptional=productRepository.findById(proId);
		
		//siga si es falso(no existe)
		assertFalse(productOptional.isPresent(),"El producto ya existe");
		
		//creo un nuevo customer
		Product product= new Product();
		product.setProId(proId);
		product.setName("Moto One macro");
		product.setPrice(500000);
		product.setDetail("Celular nuevo");
		product.setImage("C:\\Users\\JUAN CAMILO\\Desktop\\9 Semestre\\DesarrolloEmpresariales\\imagenes\\mom.jpg");
		product.setEnable("Y");
		
		productRepository.save(product);
		
	}
	
	@Test
	@Transactional
	@Order(2)
	//consulto si el cliente fue grabado
	void findById() {
		//ver informacion en la consola
		log.info("findById");
		Optional<Product> productOptional=productRepository.findById(proId);
		//siga si es verdadero(existe)	
		assertTrue(productOptional.isPresent(),"El producto no existe");
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		
		//ver informacion en la consola
		log.info("update");
		
		Optional<Product> productOptional=productRepository.findById(proId);
		
		//siga si es verdadero(existe)	
		assertTrue(productOptional.isPresent(),"El producto no existe");
		
		//obtengo el producto
		Product product=productOptional.get();
		
		//modifico
		product.setEnable("N");
		
		productRepository.save(product);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {

		//ver informacion en la consola
		log.info("delete");
		
		Optional<Product> productOptional=productRepository.findById(proId);
		//siga si es verdadero(existe)	
		assertTrue(productOptional.isPresent(),"El producto no existe");
		
		//obtengo el customer
		Product product=productOptional.get();
		
		//borro
		productRepository.delete(product);
	}
	

	@Test
	@Transactional
	@Order(5)
	void findAll() {
		log.info("findAll");
		log.info("tradicional");
		//forma tradicional
		List<Product> products=productRepository.findAll();
		for(Product product:products){
			log.info("name: "+product.getName());
			log.info("precio: "+product.getPrice());
		}
		
		log.info("funcional");
		//forma funcional
		productRepository.findAll().forEach(product->{
			log.info("name: "+product.getName());
			log.info("precio: "+product.getPrice());
		});
	}
	
	
	@Test
	@Transactional
	@Order(6)
	void count() {
		log.info("Count: "+productRepository.count());
	}
	
	
	
	@Test
	@Transactional
	@Order(7)
	void filterPrice() {
		log.info("filterPrice");
		List<Product> products = productRepository.filterPrice(4440001,6440001);
		
		//siga si encontro registro(no esta vacio)
		assertFalse(products.isEmpty(),"Prductos no encontrados");
		
		products.forEach(product->{
			log.info("name: "+product.getName());
		});
		
	}
	
	@Test
	@Transactional
	@Order(8)
	void startWithA() {
		log.info("startWithA");
		List<Product> products = productRepository.startWithA();
		
		//siga si encontro registro(no esta vacio)
		assertFalse(products.isEmpty(),"Prductos no encontrados");
		
		products.forEach(product->{
			log.info("pro_id: "+product.getProId());
			log.info("name: "+product.getName());
		});
		
	}
	
	@Test
	@Transactional
	@Order(9)
	void OrderByPrice() {
		log.info("OrderByPrice");
		List<Product> products = productRepository.orderPrice();
		
		//siga si encontro registro(no esta vacio)
		assertFalse(products.isEmpty(),"Prductos no encontrados");
		
		products.forEach(product->{
			log.info("name: "+product.getName());
			log.info("price: "+product.getPrice());
		});
		
	}
	
	@Test
	@Transactional
	@Order(10)
	void IgnoreName() {
		log.info("IgnoreName");
		List<Product> products = productRepository.findByNameNot("iPad Pro");
		
		//siga si encontro registro(no esta vacio)
		assertFalse(products.isEmpty(),"Prductos no encontrados");
		
		products.forEach(product->{
			log.info("name: "+product.getName());
			log.info("price: "+product.getPrice());
		});
		
	}
	
	
	@Test
	@Transactional
	@Order(11)
	void pricesNotIn() {
		log.info("pricesNotIn");
		List<Integer> prices= new ArrayList<Integer>();
		prices.add(4500000);
		prices.add(500000);
		
		List<Product> products = productRepository.findByPriceNotIn(prices);
		
		//siga si encontro registro(no esta vacio)
		assertFalse(products.isEmpty(),"Prductos no encontrados");
		
		products.forEach(product->{
			log.info("name: "+product.getName());
			log.info("price: "+product.getPrice());
		});
		
	}
	
	
}
