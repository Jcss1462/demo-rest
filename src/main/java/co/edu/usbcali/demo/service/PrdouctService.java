package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.Product;

public interface PrdouctService extends GenericService<Product, String> {
	public List<Product> finByEnbleY();
	
	public List<Product> findByName(String name);
	
	public List<Product> findByDetail(String detail);
	
	public List<Product> findByPrice(Integer priceFrom, Integer priceTo);
}
