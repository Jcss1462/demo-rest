package co.edu.usbcali.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.Customer;

//le paso el nombre de la clase y el tipo de dato del id
//con esto ya tiene todos los metodos del entity manager Y NO SE TIENE QUE LLAMAR
public interface CustomerRepository extends JpaRepository<Customer, String> {

	//modo automatico (por nombre del metodo)
	public List<Customer> findByEnableAndEmail(String enable,String email);
	
	//modo manual
	@Query("SELECT cus FROM Customer cus WHERE cus.name LIKE 'Mar%'")
	public List<Customer> findCustomerLikemar();
	
	//modo automatico (por nombre del metodo)
	public Optional<Customer> findByEmailAndToken(String email,String token);
		

}
