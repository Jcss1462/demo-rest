package co.edu.usbcali.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.Product;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
	
	@Query("SELECT pay FROM PaymentMethod pay WHERE pay.enable='Y'")
	public List<PaymentMethod> findByEnable();
	

}
