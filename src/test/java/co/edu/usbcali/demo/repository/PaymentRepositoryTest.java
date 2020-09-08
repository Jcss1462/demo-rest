package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

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

import co.edu.usbcali.demo.domain.PaymentMethod;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class PaymentRepositoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(PaymentRepositoryTest.class);
	
	//se usa estatico para que el  valor se guarde durante toda la ejecucion
	private static Integer payId=null;

	@Autowired
	PaymentMethodRepository paymentMethodRepository;
	
	@Test
	@Order(1)
	@Transactional
	void save() {
		
		PaymentMethod paymentMethod= new PaymentMethod();
		paymentMethod.setEnable("Y");
		paymentMethod.setName("EFECTY");
		
		//lo mando a la bd y me retorna la informacion del objeto
		paymentMethod=paymentMethodRepository.save(paymentMethod);
		payId=paymentMethod.getPayId();
		
		//si el id es nulo lanza error
		assertNotNull(payId,"El payId es nulo");
		log.info("payId:"+payId);
		
	}
	
	@Test
	@Order(2)
	@Transactional
	void findById() {
		//si el objeto con el id payId existe 
		assertTrue(paymentMethodRepository.findById(payId).isPresent());
		//traigo el objeto
		PaymentMethod paymentMethod= paymentMethodRepository.findById(payId).get();
		//si el paymentMethod es nulo lanza error
	    assertNotNull(paymentMethod,"El paymentntMethod no existe");
	}
	
	@Test
	@Order(3)
	@Transactional
	void update() {
		//si el objeto con el id payId existe 
		assertTrue(paymentMethodRepository.findById(payId).isPresent());
		//traigo el objeto
		PaymentMethod paymentMethod= paymentMethodRepository.findById(payId).get();
		//edito
	    paymentMethod.setEnable("N");
	    //envio
	    paymentMethodRepository.save(paymentMethod);
	}
	
	
	@Test
	@Order(4)
	@Transactional
	void delete() {
		//si existe el objeto lo traigo
		assertTrue(paymentMethodRepository.findById(payId).isPresent());
		PaymentMethod paymentMethod= paymentMethodRepository.findById(payId).get();
		
		//lo elimino
	    paymentMethodRepository.delete(paymentMethod);
	}

}
