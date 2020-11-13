package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;


@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class CustomerRepositoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(CustomerRepositoryTest.class);
	
	private final static String email="jcss1462@gmail.com";
	
	//llamo al repositorio en lugar del entity manager
	@Autowired
	CustomerRepository cutomerRepository;

	@Test
	//para poder hacer las transaccion con invercion de control
	@Transactional
	@Order(1)
	void save() {
		log.info("save");
		Optional<Customer> customerOptional=cutomerRepository.findById(email);
		
		//siga si es falso(no existe)
		assertFalse(customerOptional.isPresent(),"El customer ya existe");
		
		//creo un nuevo customer
		Customer customer= new Customer();
		customer.setAddress("Cra 80 A");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("Juan Camilo");
		customer.setPhone("3207357487");
		customer.setToken("D5BGD1485GH153F8");
		
		cutomerRepository.save(customer);
		
	}
	

	@Test
	@Transactional
	@Order(2)
	//consulto si el cliente fue grabado
	void findById() {
		//ver informacion en la consola
		log.info("findById");
		Optional<Customer> customerOptional=cutomerRepository.findById(email);
		//siga si es verdadero(existe)	
		assertTrue(customerOptional.isPresent(),"El customer no existe");
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		
		//ver informacion en la consola
		log.info("update");
		
		Optional<Customer> customerOptional=cutomerRepository.findById(email);
		
		//siga si es verdadero(existe)	
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		//obtengo el customer
		Customer customer=customerOptional.get();
		
		//modifico
		customer.setEnable("N");
		
		cutomerRepository.save(customer);
	}
	
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		
		//ver informacion en la consola
		log.info("delete");
		
		Optional<Customer> customerOptional=cutomerRepository.findById(email);
		//siga si es verdadero(existe)	
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		//obtengo el customer
		Customer customer=customerOptional.get();
		
		//borro
		cutomerRepository.delete(customer);
	}
	
	
	@Test
	@Transactional
	@Order(5)
	void findAll() {
		
		//forma tradicional
		List<Customer> customers=cutomerRepository.findAll();
		for(Customer customer:customers){
			log.info("name: "+customer.getName());
			log.info("Email: "+customer.getEmail());
		}
		
		
		//forma funcional
		cutomerRepository.findAll().forEach(customer->{
			log.info("name: "+customer.getName());
			log.info("Email: "+customer.getEmail());
		});
	}
	
	@Test
	@Transactional
	@Order(6)
	void count() {
		log.info("Count: "+cutomerRepository.count());
	}
	
	@Test
	@Transactional
	@Order(7)
	void findByEnableAndEmail() {
		List<Customer> customers=cutomerRepository.findByEnableAndEmail("N", "ehutcheonh@multiply.com");
		//siga si encontro registro(no esta vacio)
		assertFalse(customers.isEmpty(),"Registro no enconrado");
		
		//forma funcional
		customers.forEach(customer->{
			log.info("name: "+customer.getName());
			log.info("Email: "+customer.getEmail());
		});
	}
	
	@Test
	@Transactional
	@Order(8)
	void findCustomerLikemar() {
		List<Customer> customers=cutomerRepository.findCustomerLikemar();
		//siga si encontro registro(no esta vacio)
		assertFalse(customers.isEmpty(),"Registro no enconrado");
		//forma funcional
		customers.forEach(customer->{
			log.info("name: "+customer.getName());
			log.info("Email: "+customer.getEmail());
		});
	}

}
