package co.edu.usbcali.demo.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.NewProduct;
import co.edu.usbcali.demo.domain.PayCartData;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.CustomerDTO;
import co.edu.usbcali.demo.dto.ShopingCartDTO;
import co.edu.usbcali.demo.dto.SopingProductDTO;
import co.edu.usbcali.demo.mapper.ShopingCartMapper;
import co.edu.usbcali.demo.mapper.ShopingProductMapper;
import co.edu.usbcali.demo.service.CartService;

@RestController
@RequestMapping("/api/cart")
//cualquiera puede llamar el servcio
@CrossOrigin("*")
public class CartController {

	//private final static Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	CartService cartService;

	@Autowired
	ShopingProductMapper shopingProductMapper;

	@Autowired
	ShopingCartMapper shopingCartMapper;

	// Get http
	@GetMapping("/findShopingProductsByCartId/{carId}")
	public ResponseEntity<?> findShopingProductsByCartId(@PathVariable("carId") String carId) throws Exception {
		// lista de customers
		List<ShoppingProduct> products = cartService.findShoppingProductByShoppingCart(Integer.parseInt(carId));

		List<SopingProductDTO> productDto = shopingProductMapper.toShopingProductsDto(products);

		// log.info(productDto.get(0).getProduct().getName());

		return ResponseEntity.ok().body(productDto);

	}

	@GetMapping("/currentCart/{email}")
	public ResponseEntity<?> currentCart(@PathVariable("email") String email) throws Exception {
		// lista de customers
		ShoppingCart cart = cartService.currentCart(email);

		ShopingCartDTO cartDto = shopingCartMapper.toShopingCartDTO(cart);

		// log.info(productDto.get(0).getProduct().getName());

		return ResponseEntity.ok().body(cartDto);

	}

	@GetMapping("/historyCart/{email}")
	public ResponseEntity<?> historyCart(@PathVariable("email") String email) throws Exception {
		// lista de customers
		List<ShoppingCart> carts = cartService.historyCarts(email);

		List<ShopingCartDTO> cartsDto = shopingCartMapper.toShopingCartsDTO(carts);

		// log.info(productDto.get(0).getProduct().getName());
		return ResponseEntity.ok().body(cartsDto);
	}

	@PostMapping("/createCart")
	// envio los datos por el body de la peticion http
	// @valid valida la entrada
	public ResponseEntity<?> createCart(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {
		ShoppingCart shopingcart = cartService.createCart(customerDTO.getEmail());
		ShopingCartDTO cartDto = shopingCartMapper.toShopingCartDTO(shopingcart);
		return ResponseEntity.ok().body(cartDto);
	}

	@PostMapping("/addProduct")
	// envio los datos por el body de la peticion http
	// @valid valida la entrada
	public ResponseEntity<?> addProduct(@Valid @RequestBody NewProduct newProduct) throws Exception {

		ShoppingProduct shopingProduct = cartService.addProduct(newProduct.getCartId(), newProduct.getProId(), 1);

		SopingProductDTO shpopingProductDto = shopingProductMapper.toShopingProductDTO(shopingProduct);

		return ResponseEntity.ok().body(shpopingProductDto);

	}

	@GetMapping("/findCartById/{cartId}")
	public ResponseEntity<?> findCartById(@PathVariable("cartId") Integer cartId) throws Exception {
		// lista de customers
		ShoppingCart shoppingCart = cartService.findCartById(cartId);

		ShopingCartDTO cartDto = shopingCartMapper.toShopingCartDTO(shoppingCart);

		// log.info(productDto.get(0).getProduct().getName());
		return ResponseEntity.ok().body(cartDto);
	}

	// Get http
	@DeleteMapping("/removeProduct/{carId}/{proId}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> removeProduct(@PathVariable("carId") Integer carId,@PathVariable("proId") String proId) throws Exception {

		// borro
		cartService.removeProduct(carId, proId);

		return ResponseEntity.ok().build();

	}
	
	@PutMapping("/payCart")
	// envio los datos por el body de la peticion http
	//@valid valida la entrada
	public ResponseEntity<?> payCart(@Valid @RequestBody PayCartData payCartData) throws Exception {

		ShoppingCart shopingCart=cartService.payCart(payCartData.getCartId(), payCartData.getPayId(), payCartData.getCartNumber());
		
		ShopingCartDTO cartDto = shopingCartMapper.toShopingCartDTO(shopingCart);

		return ResponseEntity.ok().body(cartDto);

	}


}
