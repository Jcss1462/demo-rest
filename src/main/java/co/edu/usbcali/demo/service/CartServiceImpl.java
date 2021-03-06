package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

@Service
@Scope("singleton")
public class CartServiceImpl implements CartService {

	//private final static Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	// inyecto los servicios que necesitar
	@Autowired
	CustomerService customerService;
	@Autowired
	ShopingCartService shopingCartService;
	@Autowired
	PrdouctService productService;
	@Autowired
	ShopingProductService shopingProductService;
	@Autowired
	PaymentMethodService paymentMthod;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingCart createCart(String email) throws Exception {
		Customer customer = null;

		// valido que el email venga valido
		if (email == null || email.isBlank() == true) {
			throw new Exception("El email del cilente es nulo");
		}

		// valido que el cliente exista
		Optional<Customer> customerOptional = customerService.findById(email);
		if (customerOptional.isPresent() == false) {
			throw new Exception("No existe el cliente con email: " + email);
		}

		// obtengo el customer
		customer = customerOptional.get();

		// valido que el customer esta habilitado
		if (customer.getEnable() == null || customer.getEnable().equals("N") == true) {
			throw new Exception("El cliente con email: " + email + " no esta habilitado");
		}

		// verifico si existe un shoping cart activo
		if (shopingCartService.ListShopingCartEnable(email).size() > 0) {
			throw new Exception("El cliente con email: " + email + " ya tiene un shopingCart activo");
		}

		// creo un nuevo shopingCart para el cliente y lo guardo
		ShoppingCart shoppingCart = new ShoppingCart(0, customer, null, 0, 0L, "Y", null, null);
		shoppingCart = shopingCartService.save(shoppingCart);

		return shoppingCart;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingProduct addProduct(Integer carId, String proId, Integer quantity) throws Exception {
		ShoppingCart shopingCart = null;
		Long totalShopingProduct = 0L;
		Long totalShopingCart = 0L;
		Integer totalShopinCartItems = 0;
		Product product = null;

		// valido
		if (carId == null || carId <= 0) {
			throw new Exception("El carId es nulo o menor a 0");
		}
		if (proId == null || proId.isBlank() == true) {
			throw new Exception("El proId es nulo o menor a 0");
		}
		if (quantity == null || quantity <= 0) {
			throw new Exception("La cantidad es nulo o menor a 0");
		}

		// valido si el shopingCart existe y si no lo hace lanzo excepcion
		if (shopingCartService.findById(carId).isPresent() == false) {
			throw new Exception("El shping cart con id:" + carId + " no existe");
		}

		shopingCart = shopingCartService.findById(carId).get();

		// valido que el carro este activo
		if (shopingCart.getEnable().equals("N") == true) {
			throw new Exception("El shoping cart esta inabilitado");
		}

		// valido que el product exista
		if (productService.findById(proId).isPresent() == false) {
			throw new Exception("El product no existe");
		}

		// guardo el product
		product = productService.findById(proId).get();

		// valido que el producto este habilitado
		if (product.getEnable().equals("N") == true) {
			throw new Exception("El product esta inabilitado");
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////
		// valido si el producto ya se encuentra en el shoping cart
		ShoppingProduct shopingProduct = shopingProductService.obtenerShopingProductFromShopingCart(carId, proId);

		// si el producto ya esta en el shopingcart
		if (shopingProduct != null) {

			// actualizo valores
			shopingProduct.setQuantity(shopingProduct.getQuantity() + quantity);
			shopingProduct.setTotal(Long.valueOf(shopingProduct.getQuantity() * product.getPrice()));

			shopingProductService.update(shopingProduct);
		} else {
			shopingProduct = new ShoppingProduct();
			// mando el id, pro igual se asignara en Bd el generado automaticamente
			shopingProduct.setShprId(0);
			shopingProduct.setProduct(product);
			shopingProduct.setQuantity(quantity);
			shopingProduct.setShoppingCart(shopingCart);
			// calculo el total del shoping product segun la cantidad
			totalShopingProduct = Long.valueOf(product.getPrice() * quantity);
			shopingProduct.setTotal(totalShopingProduct);

			// guardo el shopingProduct
			shopingProduct = shopingProductService.save(shopingProduct);

		}

		///////////////////////////////////////////////////////////////////////////////////////////////////
		// calculo el nuevo total de shopingCart
		totalShopingCart = shopingProductService.totalShopingProductByShopingCart(carId);
		// actualizo el valor
		shopingCart.setTotal(totalShopingCart);

		// calculo el total de items del shoping cart
		totalShopinCartItems = shopingProductService.totalItemsShopingCart(carId);
		// actualizo el valor
		shopingCart.setItems(totalShopinCartItems);

		shopingCartService.update(shopingCart);

		return shopingProduct;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void removeProduct(Integer carId, String proId) throws Exception {

		Long totalShopingCart = 0L;
		Integer totalShopinCartItems = 0;

		// valido
		if (carId == null || carId <= 0) {
			throw new Exception("El carId es nulo o menor a 0");
		}
		if (proId == null || proId.isBlank() == true) {
			throw new Exception("El proId es nulo o menor a 0");
		}

		// valido que el shopingCart exista
		if (shopingCartService.findById(carId).isPresent() == false) {
			throw new Exception("El shopingCart no existe");
		}

		// obtengo el shoping cart
		ShoppingCart shopingCart = shopingCartService.findById(carId).get();

		// valido que el carro este activo
		if (shopingCart.getEnable().equals("N") == true) {
			throw new Exception("El shoping cart esta pagado, no se puede eliminar el producto");
		}

		// valido que el producto exista
		if (productService.findById(proId).isPresent() == false) {
			throw new Exception("El product no existe");
		}

		/////////////////////////////////////////////////////////////////////////////////
		// valido que tenga el producto
		ShoppingProduct shopingProduct = shopingProductService.obtenerShopingProductFromShopingCart(carId, proId);

		if (shopingProduct == null) {
			throw new Exception("El shoping cart no contiene el producto con id: " + proId);
		}

		// borro el product
		shopingProductService.deleteById(shopingProduct.getShprId());

		//////////////////////////////////////////////////////////////////////////////////////////////////
		// obtengo el shoping cart
		shopingCart = shopingCartService.findById(carId).get();
		// calculo el nuevo total de shopingCart
		totalShopingCart = shopingProductService.totalShopingProductByShopingCart(carId) == null ? 0
				: shopingProductService.totalShopingProductByShopingCart(carId);
		// actualizo el valor
		shopingCart.setTotal(totalShopingCart);

		// calculo el total de items del shoping cart
		totalShopinCartItems = shopingProductService.totalItemsShopingCart(carId) == null ? 0
				: shopingProductService.totalItemsShopingCart(carId);
		// actualizo el valor
		shopingCart.setItems(totalShopinCartItems);

		shopingCartService.update(shopingCart);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void clearCart(Integer carId) throws Exception {
		// valido
		if (carId == null || carId <= 0) {
			throw new Exception("El carId es nulo o menor a 0");
		}

		// valido que el shopingCart exista
		if (shopingCartService.findById(carId).isPresent() == false) {
			throw new Exception("El shopingCart no existe");
		}

		// obtengo el shoping cart
		ShoppingCart shopingCart = shopingCartService.findById(carId).get();

		// si el shoping cart tiene pymentmethod no dejo eliminarlo
		if (shopingCart.getPaymentMethod() != null||shopingCart.getEnable().equals('N')) {
			throw new Exception("Este carro ya esta pagado, no se puede limpiar");
		}

		// reviso si el shoping cart tiene elementos
		List<ShoppingProduct> shopingProducts = shopingProductService.findByCartId(carId);

		// si el carro no contiene shoping products
		if (shopingProducts.isEmpty()) {
			throw new Exception("El shoping cart ya esta vacio");
		}

		// si tiene elementos recorro la lista de shopng products para eliminarlos
		for (ShoppingProduct item : shopingProducts) {
			shopingProductService.deleteById(item.getShprId());
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// modififico la cantidad de items y el total a 0
		shopingCart.setItems(0);
		shopingCart.setTotal(0L);
		shopingCartService.update(shopingCart);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShoppingProduct> findShoppingProductByShoppingCart(Integer carId) throws Exception {
		// valido
		if (carId == null || carId <= 0) {
			throw new Exception("El carId es nulo o menor a 0");
		}

		// valido que el shopingCart exista
		if (shopingCartService.findById(carId).isPresent() == false) {
			throw new Exception("El shopingCart no existe");
		}

		// obtengo el shoping cart
		ShoppingCart shopingCart = shopingCartService.findById(carId).get();
		List<ShoppingProduct> listaShopingProducts = shopingCart.getShoppingProducts();

		
		return listaShopingProducts;
	}

	@Override
	public ShoppingCart currentCart(String email) throws Exception {
		// valido que el email venga valido
		if (email == null || email.isBlank() == true) {
			throw new Exception("El email del cilente es nulo");
		}

		// valido que el cliente exista
		Optional<Customer> customerOptional = customerService.findById(email);
		if (customerOptional.isPresent() == false) {
			throw new Exception("No existe el cliente con email: " + email);
		}

		if (shopingCartService.ListShopingCartEnable(email).isEmpty()) {
			throw new Exception("Actualmente no hay carros activos");
		}

		// obtengo el carro actual
		return shopingCartService.ListShopingCartEnable(email).get(0);
	}

	@Override
	public List<ShoppingCart> historyCarts(String email) throws Exception {
		// valido que el email venga valido
		if (email == null || email.isBlank() == true) {
			throw new Exception("El email del cilente es nulo");
		}

		// valido que el cliente exista
		Optional<Customer> customerOptional = customerService.findById(email);
		if (customerOptional.isPresent() == false) {
			throw new Exception("No existe el cliente con email: " + email);
		}
		return shopingCartService.ListShopingCartDisable(email);
	}

	@Override
	public ShoppingCart findCartById(Integer cartId) throws Exception {

		if (cartId == null) {
			throw new Exception("El cartId es nulo");
		}

		return shopingCartService.findById(cartId).get();
	}

	@Override
	public ShoppingCart payCart(Integer cartId, Integer payId, Long cartNumber) throws Exception {
		// valido que los valores de ebtrada
		if (payId == null) {
			throw new Exception("El payId es nulo");
		}
		
		if (cartNumber == null) {
			throw new Exception("El cartNumber es nulo");
		}
		
		if (cartId == null) {
			throw new Exception("El cartId es nulo");
		}
		
		//reviso que exitsa el peymentMthod y qu eeste habilitdo
		if(paymentMthod.findById(payId).isPresent()==false) {
			throw new Exception("El paymentMethod no existe");
		}
		
		PaymentMethod paymentMethod=paymentMthod.findById(payId).get();
		
		if(paymentMethod.getEnable().equals("N")) {
			throw new Exception("El paymentMethod no esta disponible");
		}
		
		//reviso que el shoping cart exusta y sea valido
		
		if(shopingCartService.findById(cartId).isPresent()==false) {
			throw new Exception("El shopingCart no existe");
		}
		
		ShoppingCart shopingCart=shopingCartService.findById(cartId).get();
		
		if(shopingCart.getEnable().equals("N")) {
			throw new Exception("El shopingCart ya esta pago");
		}
		
		//reviso que el shopingCart tenga productos
		if(shopingCart.getShoppingProducts().isEmpty()) {
			throw new Exception("El shopingCart esta vacio");
		}
		
		
		//si todo sale bien
		//actualizo el shoping cart
		shopingCart.setCartNumber(cartNumber);
		shopingCart.setPaymentMethod(paymentMethod);
		shopingCart.setEnable("N");
		shopingCartService.update(shopingCart);
		
		//creo un nuevo carro
		createCart(shopingCart.getCustomer().getEmail());
		
		return shopingCart;
	}

}
