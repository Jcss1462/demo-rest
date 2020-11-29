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

import co.edu.usbcali.demo.domain.Customer;


@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class CustomerServiceTest {
	
	private final static Logger log=LoggerFactory.getLogger(CustomerServiceTest.class);
	
	private final static String email="jcss1462@gmail.com";
	
	//inyecto el servicio
	@Autowired
	CustomerService customerService;


	@Test
	@Order(1)
	//le digo que muestre las excepciones que trae el servicio
	void save() throws Exception{
		log.info("save");
		
		//creo un nuevo customer
		Customer customer= new Customer();
		customer.setAddress("Cra 80 A");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("Juan Camilo");
		customer.setPhone("3207357487");
		customer.setToken("D5BGD1485GH153F8");
		
		customerService.save(customer);
		
	}
	
	
	@Test
	@Order(2)
	//consulto si el cliente fue grabado
	void findById() throws Exception {
		//ver informacion en la consola
		log.info("findById");
		Optional<Customer> customerOptional=customerService.findById(email);
		//siga si es verdadero(existe)	
		assertTrue(customerOptional.isPresent(),"El customer no existe");
	}
	
	@Test
	@Order(3)
	void update() throws Exception {
		
		//ver informacion en la consola
		log.info("update");
		
		Optional<Customer> customerOptional=customerService.findById(email);
		
		//siga si es verdadero(existe)	
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		//obtengo el customer
		Customer customer=customerOptional.get();
		
		//modifico
		customer.setEnable("N");
		
		customerService.update(customer);
	}
	
	
	@Test
	@Order(4)
	void delete() throws Exception {
		
		//ver informacion en la consola
		log.info("delete");
		
		Optional<Customer> customerOptional=customerService.findById(email);
		//siga si es verdadero(existe)	
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		//obtengo el customer
		Customer customer=customerOptional.get();
		
		//borro
		customerService.delete(customer);
	}
	
	@Test
	@Order(5)
	void findAll() throws Exception {
		//ver informacion en la consola
		log.info("findAll");
		List<Customer> customerList=customerService.findAll();
		//siga si no esta vacio	
		assertFalse(customerList.isEmpty(),"No existen customers en la lista");
		
		//forma funcional
		customerList.forEach(customer->{
			log.info("name: "+customer.getName());
			log.info("Email: "+customer.getEmail());
		});
	}
	
	@Test
	@Order(6)
	void count() throws Exception {
		//ver informacion en la consola
		log.info("count");
		log.info("El numero de customers es= "+customerService.count());
		
	}
	
	@Test
	@Order(7)
	//le digo que muestre las excepciones que trae el servicio
	void findByEmailAndToken() throws Exception{
		log.info("findByEmailAndToken");
		
		Optional<Customer> customerOptional=customerService.findByEmailAndToken("jcss1462@gmail.com", "0l7AsDerYYMtk03wvkHbmGeV8IB2");
		//siga si es verdadero(existe)	
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
	}
	

}
