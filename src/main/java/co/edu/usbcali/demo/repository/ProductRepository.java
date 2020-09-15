package co.edu.usbcali.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.demo.domain.Product;

//le paso el nombre de la clase y el tipo de dato del id
//con esto ya tiene todos los metodos del entity manager Y NO SE TIENE QUE LLAMA
public interface ProductRepository extends JpaRepository<Product, String> {
	
	//modo manual
	@Query("SELECT pro FROM Product pro WHERE pro.price BETWEEN :p1 AND :p2")
	public List<Product> filterPrice(@Param ("p1") Integer p1, @Param("p2") Integer p2);
	
	@Query("SELECT pro FROM Product pro WHERE pro.proId LIKE 'A%'")
	public List<Product> startWithA();
	
	@Query("SELECT pro FROM Product pro ORDER BY pro.price DESC")
	public List<Product> orderPrice();
	
	//modo automatico 
	public List<Product> findByNameNot(@Param ("name") String name);
	
	
	public List<Product> findByPriceNotIn(@Param ("price") List<Integer> prices);


}
