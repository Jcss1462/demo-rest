package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.repository.CustomerRepository;

@Service
@Scope("singleton")
public class CustomerServiceImpl implements CustomerService {
	
	//inyecto el repositorio
	@Autowired
	CustomerRepository customerRepository;
	
	//inyecto el validador
	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly=true)
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer save(Customer entity) throws Exception {
		
		//valido
		validate(entity);
		
		//compruebo, si existe lanza el error
		if(customerRepository.existsById(entity.getEmail())) {
			throw new Exception("El customer con id:"+entity.getEmail()+" ya existe");
		}
		
		return customerRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer update(Customer entity) throws Exception {
		
		//valido
		validate(entity);
		
		//si no existe lanza el error
		if(customerRepository.existsById(entity.getEmail())==false) {
			throw new Exception("El customer con id:"+entity.getEmail()+" no existe");
		}
		
		return customerRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Customer entity) throws Exception {
		
		//valido que se mandaron datos
		if(entity==null) {
			throw new Exception("El customer es nulo");
		}
		
		//si es nulo o esta en blanco
		if(entity.getEmail()==null||entity.getEmail().isBlank()==true) {
			throw new Exception("El Email es obligatorio");
		}
		
		//si no existe lanza el error
		if(customerRepository.existsById(entity.getEmail())==false) {
			throw new Exception("El customer con id:"+entity.getEmail()+" no existe\nNo se puede borrar");
		}
		
		//valido que no tenga registro en shoping cart
		customerRepository.findById(entity.getEmail()).ifPresent(customer->{
			if(customer.getShoppingCarts()!=null && customer.getShoppingCarts().isEmpty()==false) {
				throw new RuntimeException("El customer con id:"+entity.getEmail()+" tiene shoping cart\nNo se puede borrar");
			}
		});
		
		customerRepository.deleteById(entity.getEmail());
		
		
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		
		//valido que vengan los datos
		if(id==null || id.isBlank()==true) {
			throw new Exception("El Email es obligatorio");
		}
		
		//si el id existe
		if(customerRepository.existsById(id)) {
			//manda el objeto
			delete(customerRepository.findById(id).get());
		}else {
			throw new Exception("El customer con id:"+id+" no existe");
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Customer> findById(String id) throws Exception {
		return customerRepository.findById(id);
	}

	@Override
	public void validate(Customer entity) throws Exception {
		
		if(entity==null) {
			throw new Exception("El customer es nulo");
		}
		
		
		//validator
		//retorna una lista de los constraint violados
		Set<ConstraintViolation<Customer>> constrintViolation= validator.validate(entity);
		//si no esta vacia lanza el error
		if(constrintViolation.isEmpty()==false) {
			
			throw new ConstraintViolationException(constrintViolation);
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public Long count() {
		return customerRepository.count();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Customer> findByEmailAndToken(String email, String token) {
		// TODO Auto-generated method stub
		return customerRepository.findByEmailAndToken(email, token);
	}

}
