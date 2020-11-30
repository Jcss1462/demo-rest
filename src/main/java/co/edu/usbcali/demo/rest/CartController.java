package co.edu.usbcali.demo.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.SopingProductDTO;
import co.edu.usbcali.demo.mapper.ShopingProductMapper;
import co.edu.usbcali.demo.service.CartService;

@RestController
@RequestMapping("/api/cart")
//cualquiera puede llamar el servcio
@CrossOrigin("*")
public class CartController {
	
	private final static Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	CartService cartService;
	
	@Autowired
	ShopingProductMapper shopingProductMapper;

	// Get http
	@GetMapping("/findById/{carId}")
	public ResponseEntity<?> findById(@PathVariable("carId") String carId) throws Exception {
		// lista de customers
		List<ShoppingProduct> products = cartService.findShoppingProductByShoppingCart(Integer.parseInt(carId));
		
		List<SopingProductDTO> productDto=shopingProductMapper.toShopingProductsDto(products);
		
		//log.info(productDto.get(0).getProduct().getName());
		
		return ResponseEntity.ok().body(productDto);

	}
	
	

}
