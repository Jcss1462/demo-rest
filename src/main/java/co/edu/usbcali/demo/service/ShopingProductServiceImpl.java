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

import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.repository.ProductRepository;
import co.edu.usbcali.demo.repository.ShoppingCartRepository;
import co.edu.usbcali.demo.repository.ShoppingProductRepository;

@Service
@Scope("singleton")
public class ShopingProductServiceImpl implements ShopingProductService {

	// inyecto el repositorio
	@Autowired
	ShoppingProductRepository shoppingProductRepository;

	// traigo los repositorios de product y shopingCart method para manipular los
	// objetos de la bd
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	@Autowired
	ProductRepository productRepository;

	// inyecto el validador
	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<ShoppingProduct> findAll() {
		return shoppingProductRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingProduct save(ShoppingProduct entity) throws Exception {
		// valido
		validate(entity);

		// NO VERIFICO si existe EL shprId PORQUE ES AUTOINCREMENT

		return shoppingProductRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingProduct update(ShoppingProduct entity) throws Exception {

		// si es nulo o esta en blanco
		if (entity.getShprId() == null) {
			throw new Exception("El shprId es obligatorio");
		}

		// valido
		validate(entity);

		// si no existe lanza el error
		if (shoppingProductRepository.existsById(entity.getShprId()) == false) {
			throw new Exception("El shopingProduct con shprId:" + entity.getShprId() + " no existe");
		}

		return shoppingProductRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(ShoppingProduct entity) throws Exception {
		if (entity == null) {
			throw new Exception("El shopingProduct es nulo");
		}

		// si es nulo o esta en blanco
		if (entity.getShprId() == null) {
			throw new Exception("El shprId es obligatorio");
		}

		// si no existe lanza el error
		if (shoppingProductRepository.existsById(entity.getShprId()) == false) {
			throw new Exception(
					"El shopingProduct con shprId:" + entity.getShprId() + " no existe\nNo se puede borrar");
		}

		shoppingProductRepository.deleteById(entity.getShprId());

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		// valido que vengan los datos
		if (id == null) {
			throw new Exception("El shprId es obligatorio");
		}

		// si el id existe
		if (shoppingProductRepository.existsById(id)) {
			// manda el objeto
			delete(shoppingProductRepository.findById(id).get());
		}else {
			throw new Exception("El shopingProduct con shprId:"+id+" no existe");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ShoppingProduct> findById(Integer id) throws Exception {
		return shoppingProductRepository.findById(id);
	}

	@Override
	public void validate(ShoppingProduct entity) throws Exception {

		if (entity == null) {
			throw new Exception("El ShoppingProduct es nulo");
		}

		// verifico que el product exista
		if (productRepository.findById(entity.getProduct().getProId()).isPresent() == false) {
			throw new Exception("El producto no existe");
		}

		// verifico que el shopingCart exista
		if (shoppingCartRepository.findById(entity.getShoppingCart().getCarId()).isPresent() == false) {
			throw new Exception("El shopingCart no existe");
		}

		// validator
		// retorna una lista de los constraint violados
		Set<ConstraintViolation<ShoppingProduct>> constrintViolation = validator.validate(entity);
		// si no esta vacia lanza el error
		if (constrintViolation.isEmpty() == false) {

			throw new ConstraintViolationException(constrintViolation);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return shoppingProductRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public Long totalShopingProductByShopingCart(Integer carId) {
		
		return shoppingProductRepository.totalShoppingProductByShoppingCart(carId);
	}

	@Override
	public Integer totalItemsShopingCart(Integer carId) {
		return shoppingProductRepository.totalItemsShopingCart(carId);
	}

}
