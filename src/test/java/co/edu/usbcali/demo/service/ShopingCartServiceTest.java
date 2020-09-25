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

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ShopingCartServiceTest {

	private final static Logger log = LoggerFactory.getLogger(ShopingCartServiceTest.class);

	// se usa estatico para que el valor se guarde durante toda la ejecucion
	private static Integer carId = null;
	private static String email = "abaglowbn@furl.net";
	private static Integer payId = 1;

	// inyecto el servicio
	@Autowired
	ShopingCartService shopingCartService;

	// traigo los servicios de customer y payment method para manipular los
	// objetos de la bd
	@Autowired
	CustomerService customerService;
	@Autowired
	PaymentMethodService paymentMthodService;

	@Test
	@Order(1)
	// le digo que muestre las excepciones que trae el servicio
	void save() throws Exception {
		log.info("save");

		// creo un nuevoshoping cart
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setCarId(null);
		shoppingCart.setItems(2);
		shoppingCart.setTotal(15165156L);
		shoppingCart.setEnable("Y");

		// obtengo el customer y lo guardo en shoping cart
		Optional<Customer> customerOptional = customerService.findById(email);
		assertTrue(customerOptional.isPresent(), "El customer con el email " + email + " no existe");
		Customer customer = customerOptional.get();
		shoppingCart.setCustomer(customer);

		// obtengo el paymentMethod y lo guardo en shoping cart
		Optional<PaymentMethod> paymentMethodOptional = paymentMthodService.findById(payId);
		assertTrue(paymentMethodOptional.isPresent(), "El paymentMethod con el payId " + payId + " no existe");
		PaymentMethod paymentMethod = paymentMethodOptional.get();
		shoppingCart.setPaymentMethod(paymentMethod);
		

		// lo mando a la bd y me retorna la informacion del objeto
		shoppingCart = shopingCartService.save(shoppingCart);
		carId = shoppingCart.getCarId();

		// si el id es nulo lanza error
		assertNotNull(carId, "El carId es nulo");
		log.info("carId:" + carId);

	}
	
	@Test
	@Order(2)
	//consulto si el cliente fue grabado
	void findById() throws Exception {
		//ver informacion en la consola
		log.info("findById");
		Optional<ShoppingCart> ShopingCartOptional=shopingCartService.findById(carId);
		//siga si es verdadero(existe)
		assertTrue(ShopingCartOptional.isPresent(),"El shopingCart no existe");
		log.info("Encontrado");
	}
	
	@Test
	@Order(3)
	void update() throws Exception {
		
		//ver informacion en la consola
		log.info("update");
		
		Optional<ShoppingCart> ShopingCartOptional=shopingCartService.findById(carId);
		
		//siga si es verdadero(existe)	
		assertTrue(ShopingCartOptional.isPresent(),"El shopingCart no existe");
		
		//obtengo el shopingCart
		ShoppingCart shopingCart=ShopingCartOptional.get();
		
		//modifico
		shopingCart.setEnable("N");
		
		shopingCartService.update(shopingCart);
	}
	
	@Test
	@Order(4)
	void delete() throws Exception {
		
		//ver informacion en la consola
		log.info("delete");
		
		Optional<ShoppingCart> ShopingCartOptional=shopingCartService.findById(carId);
		
		//siga si es verdadero(existe)	
		assertTrue(ShopingCartOptional.isPresent(),"El shopingCart no existe");
		
		//obtengo el shopingCart
		ShoppingCart shopingCart=ShopingCartOptional.get();
		
		//borro
		shopingCartService.delete(shopingCart);
	}
	
	@Test
	@Order(5)
	void findAll() throws Exception {
		//ver informacion en la consola
		log.info("findAll");
		List<ShoppingCart> shpingCartList=shopingCartService.findAll();
		//siga si no esta vacio	
		assertFalse(shpingCartList.isEmpty(),"No existen shopingCarts en la lista");
		
		//forma funcional
		shpingCartList.forEach(shopingCart->{
			log.info("id: "+shopingCart.getCarId());
		});
	}
	
	@Test
	@Order(6)
	void count() throws Exception {
		//ver informacion en la consola
		log.info("count");
		log.info("El numero de shopingCarts es= "+shopingCartService.count());
		
	}

}
