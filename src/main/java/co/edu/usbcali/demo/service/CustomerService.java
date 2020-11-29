package co.edu.usbcali.demo.service;

import java.util.Optional;

import co.edu.usbcali.demo.domain.Customer;

public interface CustomerService extends GenericService<Customer, String> {
	
	public Optional<Customer> findByEmailAndToken(String email, String token);

}
