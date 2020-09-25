package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.Product;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ProductServiceTest {

	private final static Logger log = LoggerFactory.getLogger(ProductServiceTest.class);

	private final static String proId = "MOM142";

	// llamo al servicio
	@Autowired
	PrdouctService productService;

	@Test
	// para poder hacer las transaccion con invercion de control
	@Order(1)
	void save() throws Exception {
		log.info("save");

		// creo un nuevo customer
		Product product = new Product();
		product.setProId(proId);
		product.setName("Moto One macro");
		product.setPrice(500000);
		product.setDetail("Celular nuevo");
		product.setImage("C:\\Users\\JUAN CAMILO\\Desktop\\9 Semestre\\DesarrolloEmpresariales\\imagenes\\mom.jpg");
		product.setEnable("Y");

		productService.save(product);

	}

	@Test
	@Order(2)
	// consulto si el cliente fue grabado
	void findById() throws Exception {
		// ver informacion en la consola
		log.info("findById");
		Optional<Product> productOptional = productService.findById(proId);
		// siga si es verdadero(existe)
		assertTrue(productOptional.isPresent(), "El producto no existe");
	}

	@Test
	@Order(3)
	void update() throws Exception {

		// ver informacion en la consola
		log.info("update");

		Optional<Product> productOptional = productService.findById(proId);

		// siga si es verdadero(existe)
		assertTrue(productOptional.isPresent(), "El producto no existe");

		// obtengo el producto
		Product product = productOptional.get();

		// modifico
		product.setEnable("N");

		productService.update(product);
	}

	@Test
	@Order(4)
	void delete() throws Exception {

		// ver informacion en la consola
		log.info("delete");

		Optional<Product> productOptional = productService.findById(proId);
		// siga si es verdadero(existe)
		assertTrue(productOptional.isPresent(), "El producto no existe");

		// obtengo el producto
		Product product = productOptional.get();

		// borro
		productService.delete(product);
	}

	@Test
	@Order(5)
	void findAll() throws Exception {
		// ver informacion en la consola
		log.info("findAll");
		List<Product> productList = productService.findAll();
		// siga si no esta vacio
		assertFalse(productList.isEmpty(), "No existen productos en la lista");

		// forma funcional
		productList.forEach(product -> {
			log.info("name: " + product.getName());
			log.info("Price: " + product.getPrice());
		});
	}

	@Test
	@Order(6)
	void count() throws Exception {
		// ver informacion en la consola
		log.info("count");
		log.info("El numero de productos es= " + productService.count());

	}

}
