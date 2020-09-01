package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;

//Le dice a spring que esto es una prueba de unida(spring conoce a junit)
@SpringBootTest
//desactivo el rollback de la prueba
@Rollback(false)
//ejecuto todos los metodos
@TestMethodOrder(OrderAnnotation.class)
class ProductTest {
	
	
	private final static String proId ="MOM142";

	private final static Logger log=LoggerFactory.getLogger(ProductTest.class);

	//hago inyeccion de dependencia
	@Autowired
	EntityManager entityManager;
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		
		Product product= entityManager.find(Product.class, proId);
		
		//siga si  es nulo
		assertNull(product,"El producto con id "+proId+" ya existe");
		
		product= new Product();
		product.setProId(proId);
		product.setName("Moto One macro");
		product.setPrice(500000);
		product.setDetail("Celular nuevo");
		product.setImage("C:\\Users\\JUAN CAMILO\\Desktop\\9 Semestre\\DesarrolloEmpresariales\\imagenes\\mom.jpg");
		product.setEnable("Y");
		
		entityManager.persist(product);
		
	}

	@Test
	@Transactional
	@Order(2)
	void findById() {
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		Product product= entityManager.find(Product.class, proId);
		//siga si es nulo
		assertNotNull(product,"El PRODUCTO con "+ proId+" no existe");
		log.info(product.getName());
	}
	
	@Test
	@Transactional
	@Order(3)
	//consulto si el producto fue grabado
	void update() {
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		Product product=entityManager.find(Product.class, proId);
		//siga si es no nulo
		assertNotNull(product,"El producto con id "+proId+" no existe");
		
		//modifico
		product.setEnable("N");
		
		entityManager.merge(product);
	}
	
	
	@Test
	@Transactional
	@Order(4)
	//consulto si el cliente fue grabado
	void delete() {
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		Product product=entityManager.find(Product.class, proId);
		//siga si es no nulo
		assertNotNull(product,"El producto con id "+proId+" no existe");
		
		//borro
		entityManager.remove(product);
	}

}
