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

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;


@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ShopingCartRepositoryTest {
	
	
	private final static Logger log=LoggerFactory.getLogger(ShopingCartRepositoryTest.class);
	
	//se usa estatico para que el  valor se guarde durante toda la ejecucion
	private static Integer carId=null;
	private static String email="abaglowbn@furl.net";
	private static Integer payId=1;

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	//traigo los repositorios de cuatomer y payment method para manipular los objetos de la bd
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PaymentMethodRepository paymentMthodRepository;
	

	@Test
	@Order(1)
	@Transactional
	void save() {
		
		ShoppingCart shoppingCart= new ShoppingCart();
		shoppingCart.setCarId(null);
		shoppingCart.setItems(2);
		shoppingCart.setTotal(15165156L);
		shoppingCart.setEnable("Y");
		
		//obtengo el customer y lo guardo en shoping cart
		Optional<Customer> customerOptional=customerRepository.findById(email);
		assertTrue(customerOptional.isPresent(),"El customer con el email "+email+" no existe");
		Customer customer=customerOptional.get();
		shoppingCart.setCustomer(customer);
		
		//obtengo el paymentMethod y lo guardo en shoping cart
		Optional<PaymentMethod> paymentMethodOptional=paymentMthodRepository.findById(payId);
		assertTrue(paymentMethodOptional.isPresent(),"El paymentMethod con el payId "+payId+" no existe");
		PaymentMethod paymentMethod=paymentMethodOptional.get();
		shoppingCart.setPaymentMethod(paymentMethod);
		
		//obtengo el id generado luego de la insecion
		shoppingCart=shoppingCartRepository.save(shoppingCart);
		carId=shoppingCart.getCarId();
		
		//siga si no es nulo
		assertNotNull(carId,"El carId es nulo");
		log.info("El carId es "+carId);
	}
	
	@Test
	@Order(2)
	@Transactional
	void findById() {
		Optional<ShoppingCart> shoppingCartOptional=shoppingCartRepository.findById(carId);
		assertTrue(shoppingCartOptional.isPresent(),"El shopping card con carId "+carId+" no existe");
	}
	
	@Test
	@Order(3)
	@Transactional
	void update() {
		Optional<ShoppingCart> shoppingCartOptional=shoppingCartRepository.findById(carId);
		assertTrue(shoppingCartOptional.isPresent(),"El shopping card con carId "+carId+" no existe");
		
		//obtengo el shoppingCart
		ShoppingCart shoppingCart=shoppingCartOptional.get();
		//actualizo
		shoppingCart.setEnable("N");
		//lo guardo
		shoppingCartRepository.save(shoppingCart);
	}

	@Test
	@Order(4)
	@Transactional
	void delete() {
		Optional<ShoppingCart> shoppingCartOptional=shoppingCartRepository.findById(carId);
		assertTrue(shoppingCartOptional.isPresent(),"El shopping card con carId "+carId+" no existe");
		//obtengo el shoppingCart
		ShoppingCart shoppingCart=shoppingCartOptional.get();
		//lo borro
		shoppingCartRepository.delete(shoppingCart);
	}
}
