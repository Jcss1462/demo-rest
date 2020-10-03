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

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.repository.ProductRepository;

@Service
@Scope("singleton")
public class ProductServiceImpl implements PrdouctService {

	// inyecto el repositorio
	@Autowired
	ProductRepository productRepository;

	// inyecto el validador
	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Product save(Product entity) throws Exception {

		// valido
		validate(entity);

		// compruebo, si existe lanza el error
		if (productRepository.existsById(entity.getProId())) {
			throw new Exception("El producto con id:" + entity.getProId() + " ya existe");
		}

		return productRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Product update(Product entity) throws Exception {
		// valido
		validate(entity);

		// si no existe lanza el error
		if (productRepository.existsById(entity.getProId()) == false) {
			throw new Exception("El producto con id:" + entity.getProId() + " no existe");
		}

		return productRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Product entity) throws Exception {
		// valido que se mandaron datos
		if (entity == null) {
			throw new Exception("El producto es nulo");
		}

		// si es nulo o esta en blanco
		if (entity.getProId() == null || entity.getProId().isBlank() == true) {
			throw new Exception("El proId es obligatorio");
		}

		// si no existe lanza el error
		if (productRepository.existsById(entity.getProId()) == false) {
			throw new Exception("El producto con proId:" + entity.getProId() + " no existe\nNo se puede borrar");
		}

		// valido que no tenga registro en shoping product
		productRepository.findById(entity.getProId()).ifPresent(product -> {
			if (product.getShoppingProducts() != null && product.getShoppingProducts().isEmpty() == false) {
				throw new RuntimeException(
						"El producto con proIdd:" + entity.getProId() + " tiene shoping products\nNo se puede borrar");
			}
		});

		productRepository.deleteById(entity.getProId());

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		// valido que vengan los datos
		if (id == null || id.isBlank() == true) {
			throw new Exception("El proId es obligatorio");
		}

		// si el id existe
		if (productRepository.existsById(id)) {
			// manda el objeto
			delete(productRepository.findById(id).get());
		} else {
			throw new Exception("El producto con proId:" + id + " no existe y no se puede borrar");
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(String id) throws Exception {
		return productRepository.findById(id);
	}

	@Override
	public void validate(Product entity) throws Exception {

		if (entity == null) {
			throw new Exception("El product es nulo");
		}

		// validator
		// retorna una lista de los constraint violados
		Set<ConstraintViolation<Product>> constrintViolation = validator.validate(entity);
		// si no esta vacia lanza el error
		if (constrintViolation.isEmpty() == false) {

			throw new ConstraintViolationException(constrintViolation);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return productRepository.count();
	}

}
