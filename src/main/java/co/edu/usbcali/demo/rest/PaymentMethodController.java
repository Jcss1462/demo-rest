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

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.dto.PaymentMethodDTO;
import co.edu.usbcali.demo.mapper.PaymentMethodMapper;
import co.edu.usbcali.demo.service.PaymentMethodService;

@RestController
@RequestMapping("/api/paymentMethod")
//cualquiera puede llamar el servcio
@CrossOrigin("*")
public class PaymentMethodController {

	private final static Logger log = LoggerFactory.getLogger(PaymentMethodController.class);

	// inyecto el servicio
	@Autowired
	PaymentMethodService paymentMethodService;

	// inyecto el mapeador
	@Autowired
	PaymentMethodMapper paymentMethodMapper;

	@PostMapping("/save")
	// envio los datos por el body de la peticion http
	public ResponseEntity<?> save(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) throws Exception {
		log.info("save");
		// mapeo lo que recibo a paymentMethod
		PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(paymentMethodDTO);
		// guardo el paymentMethod
		paymentMethod = paymentMethodService.save(paymentMethod);
		// convierto lo guardo a dto para retornarlo
		paymentMethodDTO = paymentMethodMapper.toPaymentMethodDTO(paymentMethod);

		return ResponseEntity.ok().body(paymentMethodDTO);

	}

	@PutMapping("/update")
	// envio los datos por el body de la peticion http
	// @valid valida la entrada
	public ResponseEntity<?> update(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) throws Exception {
		log.info("update");
		// mapeo lo que recibo a paymentMethod
		PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(paymentMethodDTO);
		// guardo el payentMethod
		paymentMethod = paymentMethodService.update(paymentMethod);
		// convierto lo guardo a dto para retornarlo
		paymentMethodDTO = paymentMethodMapper.toPaymentMethodDTO(paymentMethod);

		return ResponseEntity.ok().body(paymentMethodDTO);

	}

	// Get http
	@GetMapping("/findAll")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findAll() throws Exception {
		log.info("findAll");
		// lista de paymentMethods
		List<PaymentMethod> paymentMethods = paymentMethodService.findAll();
		// uso el mapper par convertir la lista de products a los dtos
		List<PaymentMethodDTO> paymentMethodDTOs = paymentMethodMapper.toPaymentMethodsDTO(paymentMethods);

		return ResponseEntity.ok().body(paymentMethodDTOs);

	}

	// Get http
	@GetMapping("/findById/{payId}")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findById(@PathVariable("payId") String payId) throws Exception {
		log.info("findById");
		// obtengo el paymentMethod
		Optional<PaymentMethod> paymentMethodOptional = paymentMethodService.findById(Integer.parseInt(payId));
		// si no se encuentra
		if (paymentMethodOptional.isPresent() == false) {
			return ResponseEntity.ok().body("PaymentMethod not found");
		}
		// extraigo el paymentMethod
		PaymentMethod paymentMethod = paymentMethodOptional.get();

		// creo el DTO y uso el mapper para convertir el paymentMethod a dto
		PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toPaymentMethodDTO(paymentMethod);

		return ResponseEntity.ok().body(paymentMethodDTO);

	}

	// Get http
	@DeleteMapping("/delete/{payId}")
	// guardo lo mandado por el url en el parametro proId
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> delete(@PathVariable("payId") String payId) throws Exception {

		// borro
		paymentMethodService.deleteById(Integer.parseInt(payId));
		log.info(payId);

		return ResponseEntity.ok().build();

	}

	// Get http
	@GetMapping("/findByEnable")
	// guardo lo mandado por el url en el parametro email
	// ? = puede retornar cualqier cosa
	public ResponseEntity<?> findByEnable() throws Exception {
		log.info("findAll");
		// lista de paymentMethods
		List<PaymentMethod> paymentMethods = paymentMethodService.findByEnable();
		// uso el mapper par convertir la lista de products a los dtos
		List<PaymentMethodDTO> paymentMethodDTOs = paymentMethodMapper.toPaymentMethodsDTO(paymentMethods);
		return ResponseEntity.ok().body(paymentMethodDTOs);
	}

}
