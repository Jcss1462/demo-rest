package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.dto.ProductDTO;
import co.edu.usbcali.demo.mapper.ProductMapper;
import co.edu.usbcali.demo.repository.ProductRepository;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	private final static Logger log = LoggerFactory.getLogger(ProductController.class);

	// inyecto el repositorio
	@Autowired
	ProductRepository productRepository;

	// inyecto el mapeador
	@Autowired
	ProductMapper productMapper;
	
	
	
	@PostMapping("/save")
	//envio los datos por el body de la peticion http
	public  ResponseEntity<?> save(@RequestBody ProductDTO productDTO){
		try {
			//mapeo lo que recibo a product
			Product product= productMapper.toProduct(productDTO);
			//guardo el product
			product=productRepository.save(product);
			//convierto lo guardo a dto para retornarlo
			productDTO=productMapper.toProductDTO(product);
			
			return ResponseEntity.ok().body(productDTO);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	

	// Get http
	@GetMapping("/findAll")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findAll() {
		try {
			// lista de products
			List<Product> products = productRepository.findAll();
			// uso el mapper par convertir la lista de products a los dtos
			List<ProductDTO> productDTOs = productMapper.toProductsDto(products);

			return ResponseEntity.ok().body(productDTOs);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// Get http
	@GetMapping("/findById/{proId}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findById(@PathVariable("proId") String proId) {

		try {
			// obtengo el product
			Optional<Product> productOptional = productRepository.findById(proId);
			// si no se encuentra
			if (productOptional.isPresent() == false) {
				return ResponseEntity.ok().body("Product not found");
			}
			// extraigo el producto
			Product product = productOptional.get();

			// creo el DTO y uso el mapper para convertir el product a dto
			ProductDTO productDto = productMapper.toProductDTO(product);

			return ResponseEntity.ok().body(productDto);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
