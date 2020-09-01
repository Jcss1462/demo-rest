package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;

//Le dice a spring que esto es una prueba de unida(spring conoce a junit)
@SpringBootTest
//desactivo el rollback de la prueba
@Rollback(false)
//ejecuto todos los metodos
@TestMethodOrder(OrderAnnotation.class)
class CustommerTest {
	
	private final static String email="jcss1462@gmail.com";
	
	private final static Logger log=LoggerFactory.getLogger(CustommerTest.class);
	
	//hago inyeccion de dependencia
	@Autowired
	EntityManager entityManager;

	@Test
	//para poder hacer las transaccion con invercion de control
	@Transactional
	@Order(1)
	void save() {
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		
		Customer customer=entityManager.find(Customer.class, email);
		
		//siga si es nulo
		assertNull(customer,"El cliente con email "+email+" ya existe");
		
		//creo un nuevo customer
		customer= new Customer();
		customer.setAddress("Cra 80 A");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("Juan Camilo");
		customer.setPhone("3207357487");
		customer.setToken("D5BGD1485GH153F8");
		
		entityManager.persist(customer);
		
	}
	
	
	@Test
	@Transactional
	@Order(2)
	//consulto si el cliente fue grabado
	void findById() {
		//revisar si el no es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		Customer customer=entityManager.find(Customer.class, email);
		//siga si es  no nulo
		assertNotNull(customer,"El cliente con email "+email+" no existe");
		
		//ver informacion en la consola
		log.info(customer.getName());
	}
	
	@Test
	@Transactional
	@Order(3)
	//consulto si el cliente fue grabado
	void update() {
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		Customer customer=entityManager.find(Customer.class, email);
		//siga si es no nulo
		assertNotNull(customer,"El cliente con email "+email+" no existe");
		
		//modifico
		customer.setEnable("N");
		
		entityManager.merge(customer);
	}
	
	@Test
	@Transactional
	@Order(4)
	//consulto si el cliente fue grabado
	void delete() {
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
		Customer customer=entityManager.find(Customer.class, email);
		//siga si es no nulo
		assertNotNull(customer,"El cliente con email "+email+" no existe");
		
		//borro
		entityManager.remove(customer);
	}
		
		
	

}
