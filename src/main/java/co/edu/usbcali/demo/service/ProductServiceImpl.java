package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

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

		// valido que no tenga registro en shoping cart
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
		// si es nulo o esta en blanco
		if (entity.getDetail() == null || entity.getDetail().isBlank() == true) {
			throw new Exception("El Detail es obligatorio");
		}

		// si es nulo o esta en blanco
		if (entity.getEnable() == null || entity.getEnable().isBlank() == true) {
			throw new Exception("El Enable es obligatorio");
		}

		// si es nulo o esta en blanco
		if (entity.getImage() == null || entity.getImage().isBlank() == true) {
			throw new Exception("El Image es obligatorio");
		}

		// si es nulo o esta en blanco
		if (entity.getName() == null || entity.getName().isBlank() == true) {
			throw new Exception("El Nombre es obligatorio");
		}

		// si es nulo o esta en blanco
		if (entity.getPrice() == null || entity.getPrice() < 0) {
			throw new Exception("El Precio es obligatorio y debe ser valido");
		}

		// si es nulo o esta en blanco
		if (entity.getProId() == null || entity.getProId().isBlank() == true) {
			throw new Exception("El proId es obligatorio");
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return productRepository.count();
	}

}
