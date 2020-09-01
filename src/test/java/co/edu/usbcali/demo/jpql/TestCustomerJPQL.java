package co.edu.usbcali.demo.jpql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import co.edu.usbcali.demo.domain.Customer;



@SpringBootTest
public class TestCustomerJPQL {
	
	private final static Logger log=LoggerFactory.getLogger(TestCustomerJPQL.class);
	
	//hago inyeccion de dependencia
	@Autowired
	EntityManager entityManager;
	
	
	//hago inversion de control
	@BeforeEach
	public void beforeEach() {
		log.info("beforeEach");
		//revisar si el entity manager es nulo
		assertNotNull(entityManager,"El entity manager es nulo");
	}
	
	@Test
	void selecWhereParam() {
		log.info("selecWhereParam");
		
		//pensar como aparece en el objeto y no en la tabla
		//traigo todos los clientes por una key
		String jpql="SELECT cus FROM Customer cus WHERE cus.enable=:enable AND cus.email=:email";
		List<Customer> customers=entityManager.
				createQuery(jpql).
				setParameter("enable", "Y").
				setParameter("email", "ftredwelldm@lulu.com").
				getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
			log.info(customer.getEnable());
		});
		
	}
	
	

	
	@Test
	void selecWhereEnable() {
		log.info("selecWhereEnable");
		
		//pensar como aparece en el objeto y no en la tabla
		//traigo todos los clientes por una key
		String jpql="SELECT cus FROM Customer cus WHERE cus.enable = 'Y' ORDER BY cus.email";
		List<Customer> customers=entityManager.createQuery(jpql).getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
			log.info(customer.getEnable());
		});
		
	}
	
	
	@Test
	void selectLike() {
		log.info("selectLike");
		
		//pensar como aparece en el objeto y no en la tabla
		//traigo todos los clientes por una key
		String jpql="SELECT cus FROM Customer cus WHERE cus.name LIKE 'Ma%'";
		List<Customer> customers=entityManager.createQuery(jpql).getResultList();
		
	
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
		});
		
	}
		
	
	
	@Test
	void selectAll() {
		log.info("selectAll");
		
		//pensar como aparece en el objeto y no en la tabla
		//traigo todos los clientes por una key
		String jpql="SELECT cus FROM Customer cus";
		List<Customer> customers=entityManager.createQuery(jpql).getResultList();
		
		//forma1
		
		for(Customer customer:customers) {
			log.info(customer.getEmail());
			log.info(customer.getName());
		}
		
		//forma2
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
		});
		
	}
		

}
