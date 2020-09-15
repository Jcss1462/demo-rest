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

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.dto.CustomerDTO;
import co.edu.usbcali.demo.mapper.CustomerMapper;
import co.edu.usbcali.demo.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	private final static Logger log=LoggerFactory.getLogger(CustomerController.class);

	//inyecto el repositorio
	@Autowired
	CustomerRepository customerRepository;
	
	//inyecto el mapeador
	@Autowired
	CustomerMapper customerMapper;
	
	@PostMapping("/save")
	//envio los datos por el body de la peticion http
	public  ResponseEntity<?> save(@RequestBody CustomerDTO customerDTO){
		try {
			//mapeo lo que recibo a customer
			Customer customer= customerMapper.toCustomer(customerDTO);
			//guardo el customer
			customer=customerRepository.save(customer);
			//convierto lo guardo a dto para retornarlo
			customerDTO=customerMapper.toCustomerDTO(customer);
			
			return ResponseEntity.ok().body(customerDTO);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//Get http
	@GetMapping("/findAll")
	//guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findAll() {
		try {
			//lista de customers
			List<Customer> customers=customerRepository.findAll();
			//uso el mapper par convertir la lista de custommers a los dtos
			List<CustomerDTO> customerDTOs=customerMapper.toCustomersDto(customers);
		
			return ResponseEntity.ok().body(customerDTOs);
		
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	
	//Get http
	@GetMapping("/findById/{email}")
	//guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findById(@PathVariable("email") String email) {
		
		try {
			//obtengo el customer
			Optional<Customer> customerOptional=customerRepository.findById(email);
			//si no se encuente
			if(customerOptional.isPresent()==false) {
				return ResponseEntity.ok().body("Customer not found");
			}
			Customer customer=customerOptional.get();
			
			//creo el DTO y uso el mapper para convertir el customer a dto
			CustomerDTO customerDto=  customerMapper.toCustomerDTO(customer);
		
			return ResponseEntity.ok().body(customerDto);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
}
