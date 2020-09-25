package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.edu.usbcali.demo.domain.ShoppingProduct;

@Service
@Scope("singleton")
public class ShopingProductServiceImpl implements ShopingProductService{

	@Override
	public List<ShoppingProduct> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingProduct save(ShoppingProduct entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingProduct update(ShoppingProduct entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ShoppingProduct entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ShoppingProduct> findById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(ShoppingProduct entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

}
