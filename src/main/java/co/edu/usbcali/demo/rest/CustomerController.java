package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.dto.CustomerDTO;
import co.edu.usbcali.demo.mapper.CustomerMapper;
import co.edu.usbcali.demo.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	//private final static Logger log = LoggerFactory.getLogger(CustomerController.class);

	// inyecto el repositorio
	@Autowired
	CustomerService customerService;

	// inyecto el mapeador
	@Autowired
	CustomerMapper customerMapper;

	@PostMapping("/save")
	// envio los datos por el body de la peticion http
	//@valid valida la entrada
	public ResponseEntity<?> save(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {

		// mapeo lo que recibo a customer
		Customer customer = customerMapper.toCustomer(customerDTO);
		// guardo el customer
		customer = customerService.save(customer);
		// convierto lo guardo a dto para retornarlo
		customerDTO = customerMapper.toCustomerDTO(customer);

		return ResponseEntity.ok().body(customerDTO);

	}

	@PutMapping("/update")
	// envio los datos por el body de la peticion http
	//@valid valida la entrada
	public ResponseEntity<?> update(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {

		// mapeo lo que recibo a customer
		Customer customer = customerMapper.toCustomer(customerDTO);
		// guardo el customer
		customer = customerService.update(customer);
		// convierto lo guardo a dto para retornarlo
		customerDTO = customerMapper.toCustomerDTO(customer);

		return ResponseEntity.ok().body(customerDTO);

	}

	// Get http
	@GetMapping("/findAll")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findAll() throws Exception {

		// lista de customers
		List<Customer> customers = customerService.findAll();
		// uso el mapper par convertir la lista de custommers a los dtos
		List<CustomerDTO> customerDTOs = customerMapper.toCustomersDto(customers);

		return ResponseEntity.ok().body(customerDTOs);

	}

	// Get http
	@GetMapping("/findById/{email}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findById(@PathVariable("email") String email) throws Exception {

		// obtengo el customer
		Optional<Customer> customerOptional = customerService.findById(email);
		// si no se encuente
		if (customerOptional.isPresent() == false) {
			return ResponseEntity.ok().body(null);
		}
		Customer customer = customerOptional.get();

		// creo el DTO y uso el mapper para convertir el customer a dto
		CustomerDTO customerDto = customerMapper.toCustomerDTO(customer);

		return ResponseEntity.ok().body(customerDto);

	}

	// Get http
	@DeleteMapping("/delete/{email}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> delete(@PathVariable("email") String email) throws Exception {

		
		//borro
		customerService.deleteById(email);

		return ResponseEntity.ok().build();

	}
}
