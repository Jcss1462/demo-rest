package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import co.edu.usbcali.demo.domain.PaymentMethod;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class PaymentMethodServiceTest {

	private final static Logger log = LoggerFactory.getLogger(PaymentMethodServiceTest.class);

	// se usa estatico para que el valor se guarde durante toda la ejecucion
	private static Integer payId = null;

	// inyecto el servicio
	@Autowired
	PaymentMethodService paymentMethodService;

	@Test
	@Order(1)
	// le digo que muestre las excepciones que trae el servicio
	void save() throws Exception {
		log.info("save");

		// creo un nuevo paymentMethod
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setEnable("Y");
		paymentMethod.setName("BITCOIN");

		// lo mando a la bd y me retorna la informacion del objeto
		paymentMethod = paymentMethodService.save(paymentMethod);
		payId = paymentMethod.getPayId();

		// si el id es nulo lanza error
		assertNotNull(payId, "El payId es nulo");
		log.info("payId:" + payId);

	}
	
	@Test
	@Order(2)
	//consulto si el cliente fue grabado
	void findById() throws Exception {
		//ver informacion en la consola
		log.info("findById");
		Optional<PaymentMethod> paymentMethodOptional=paymentMethodService.findById(payId);
		//siga si es verdadero(existe)	
		assertTrue(paymentMethodOptional.isPresent(),"El paymentMethod no existe");
		log.info("Encontrado");
		log.info("name: "+paymentMethodOptional.get().getName());
	}
	
	
	@Test
	@Order(3)
	void update() throws Exception {
		
		//ver informacion en la consola
		log.info("update");
		
		Optional<PaymentMethod> paymentMethodOptional=paymentMethodService.findById(payId);
		
		//siga si es verdadero(existe)	
		assertTrue(paymentMethodOptional.isPresent(),"El paymetMethod no existe");
		
		//obtengo el paymentMethod
		PaymentMethod paymentMethod=paymentMethodOptional.get();
		
		//modifico
		paymentMethod.setEnable("N");
		
		paymentMethodService.update(paymentMethod);
	}
	
	@Test
	@Order(4)
	void delete() throws Exception {
		
		//ver informacion en la consola
		log.info("delete");
		
		Optional<PaymentMethod> paymentMethodOptional=paymentMethodService.findById(payId);
		
		//siga si es verdadero(existe)	
		assertTrue(paymentMethodOptional.isPresent(),"El paymentMethod no existe");
		
		//obtengo el paymentMethod
		PaymentMethod paymentMethod=paymentMethodOptional.get();
		
		//borro
		paymentMethodService.delete(paymentMethod);
	}
	
	@Test
	@Order(5)
	void findAll() throws Exception {
		//ver informacion en la consola
		log.info("findAll");
		List<PaymentMethod> paymentMethodList=paymentMethodService.findAll();
		//siga si no esta vacio	
		assertFalse(paymentMethodList.isEmpty(),"No existen paymentMethods en la lista");
		
		//forma funcional
		paymentMethodList.forEach(paymentMethod->{
			log.info("name: "+paymentMethod.getName());
		});
	}
	
	@Test
	@Order(6)
	void count() throws Exception {
		//ver informacion en la consola
		log.info("count");
		log.info("El numero de paymentMethods es= "+paymentMethodService.count());
		
	}
	

}
