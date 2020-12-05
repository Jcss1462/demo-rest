package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.dto.ProductDTO;
import co.edu.usbcali.demo.mapper.ProductMapper;
import co.edu.usbcali.demo.service.PrdouctService;

@RestController
@RequestMapping("/api/product")
//cualquiera puede llamar el servcio
@CrossOrigin("*")
public class ProductController {

	private final static Logger log = LoggerFactory.getLogger(ProductController.class);

	// inyecto el servicio
	@Autowired
	PrdouctService productService;

	// inyecto el mapeador
	@Autowired
	ProductMapper productMapper;

	@PostMapping("/save")
	// envio los datos por el body de la peticion http
	public ResponseEntity<?> save(@Valid @RequestBody ProductDTO productDTO) throws Exception {
		log.info("save");
		// mapeo lo que recibo a product
		Product product = productMapper.toProduct(productDTO);
		// guardo el product
		product = productService.save(product);
		// convierto lo guardo a dto para retornarlo
		productDTO = productMapper.toProductDTO(product);

		return ResponseEntity.ok().body(productDTO);

	}

	@PutMapping("/update")
	// envio los datos por el body de la peticion http
	// @valid valida la entrada
	public ResponseEntity<?> update(@Valid @RequestBody ProductDTO productDTO) throws Exception {

		// mapeo lo que recibo a product
		Product product = productMapper.toProduct(productDTO);
		// guardo el product
		product = productService.update(product);
		// convierto lo guardo a dto para retornarlo
		productDTO = productMapper.toProductDTO(product);

		return ResponseEntity.ok().body(productDTO);

	}

	// Get http
	@GetMapping("/findAll")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findAll() throws Exception {

		// lista de products
		List<Product> products = productService.findAll();
		// uso el mapper par convertir la lista de products a los dtos
		List<ProductDTO> productDTOs = productMapper.toProductsDto(products);

		return ResponseEntity.ok().body(productDTOs);

	}

	// Get http
	@GetMapping("/findById/{proId}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findById(@PathVariable("proId") String proId) throws Exception {

		// obtengo el product
		Optional<Product> productOptional = productService.findById(proId);
		// si no se encuentra
		if (productOptional.isPresent() == false) {
			return ResponseEntity.ok().body("Product not found");
		}
		// extraigo el producto
		Product product = productOptional.get();

		// creo el DTO y uso el mapper para convertir el product a dto
		ProductDTO productDto = productMapper.toProductDTO(product);

		return ResponseEntity.ok().body(productDto);

	}

	// Get http
	@DeleteMapping("/delete/{proId}")
	// guardo lo mandado por el url en el parametro proId
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> delete(@PathVariable("proId") String proId) throws Exception {

		// borro
		productService.deleteById(proId);
		log.info(proId);

		return ResponseEntity.ok().build();

	}

	// Get http
	@GetMapping("/findAllEnable")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findAllEnable() throws Exception {

		// lista de products
		List<Product> products = productService.finByEnbleY();
		// uso el mapper par convertir la lista de products a los dtos
		List<ProductDTO> productDTOs = productMapper.toProductsDto(products);

		return ResponseEntity.ok().body(productDTOs);

	}

	// Get http
	@GetMapping("/findByName/{name}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findByName(@PathVariable("name") String name) throws Exception {

		// lista de products
		List<Product> products = productService.findByName(name);
		// uso el mapper par convertir la lista de products a los dtos
		List<ProductDTO> productDTOs = productMapper.toProductsDto(products);
		return ResponseEntity.ok().body(productDTOs);

	}

	// Get http
	@GetMapping("/findByDetail/{detail}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findByDetail(@PathVariable("detail") String detail) throws Exception {

		// lista de products
		List<Product> products = productService.findByDetail(detail);
		// uso el mapper par convertir la lista de products a los dtos
		List<ProductDTO> productDTOs = productMapper.toProductsDto(products);
		return ResponseEntity.ok().body(productDTOs);

	}

	// Get http
	@GetMapping("/findByPrice/{priceFrom}/{priceTo}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findByPrice(@PathVariable("priceFrom") Integer priceFrom, @PathVariable("priceTo") Integer priceTo) throws Exception {

		// lista de products
		List<Product> products = productService.findByPrice(priceFrom, priceTo);
		// uso el mapper par convertir la lista de products a los dtos
		List<ProductDTO> productDTOs = productMapper.toProductsDto(products);
		return ResponseEntity.ok().body(productDTOs);

	}

}
