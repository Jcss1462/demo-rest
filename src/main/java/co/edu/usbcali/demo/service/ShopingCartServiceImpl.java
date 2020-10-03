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

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.repository.CustomerRepository;
import co.edu.usbcali.demo.repository.PaymentMethodRepository;
import co.edu.usbcali.demo.repository.ShoppingCartRepository;

@Service
@Scope("singleton")
public class ShopingCartServiceImpl implements ShopingCartService {

	// inyecto el repositorio
	@Autowired
	ShoppingCartRepository shopingCartRepository;

	// traigo los repositorios de cuatomer y payment method para manipular los
	// objetos de la bd
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PaymentMethodRepository paymentMthodRepository;

	// inyecto el validador
	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<ShoppingCart> findAll() {
		return shopingCartRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingCart save(ShoppingCart entity) throws Exception {
		// valido
		validate(entity);

		// NO VERIFICO si existe EL cartId PORQUE ES AUTOINCREMENT

		return shopingCartRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingCart update(ShoppingCart entity) throws Exception {
		// si es nulo o esta en blanco
		if (entity.getCarId() == null) {
			throw new Exception("El carId es obligatorio");
		}

		// valido
		validate(entity);

		// si no existe lanza el error
		if (shopingCartRepository.existsById(entity.getCarId()) == false) {
			throw new Exception("El shopingCart con id:" + entity.getCarId() + " no existe");
		}

		return shopingCartRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(ShoppingCart entity) throws Exception {

		if (entity == null) {
			throw new Exception("El shopingCart es nulo");
		}

		// si es nulo o esta en blanco
		if (entity.getCarId() == null) {
			throw new Exception("El cartId es obligatorio");
		}

		// si no existe lanza el error
		if (shopingCartRepository.existsById(entity.getCarId()) == false) {
			throw new Exception("El shopingCart con carId:" + entity.getCarId() + " no existe\nNo se puede borrar");
		}

		// valido que no tenga registro shopingProduct
		shopingCartRepository.findById(entity.getCarId()).ifPresent(shopingCart -> {
			if (shopingCart.getShoppingProducts() != null && shopingCart.getShoppingProducts().isEmpty() == false) {
				throw new RuntimeException("El shopingCart con carId:" + entity.getCarId()
						+ " tiene shoping products\nNo se puede borrar");
			}
		});

		shopingCartRepository.deleteById(entity.getCarId());

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {

		// valido que vengan los datos
		if (id == null) {
			throw new Exception("El carId es obligatorio");
		}

		// si el id existe
		if (shopingCartRepository.existsById(id)) {
			// manda el objeto
			delete(shopingCartRepository.findById(id).get());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ShoppingCart> findById(Integer id) throws Exception {
		return shopingCartRepository.findById(id);
	}

	@Override
	public void validate(ShoppingCart entity) throws Exception {

		if (entity == null) {
			throw new Exception("El ShoppingCart es nulo");
		}

		// verifico que el customer exista
		if (customerRepository.findById(entity.getCustomer().getEmail()).isPresent() == false) {
			throw new Exception("El customer no existe");
		}

		// verifico que el paymentMethod exista
		if (paymentMthodRepository.findById(entity.getPaymentMethod().getPayId()).isPresent() == false) {
			throw new Exception("El paymentMethod no existe");
		}

		// validator
		// retorna una lista de los constraint violados
		Set<ConstraintViolation<ShoppingCart>> constrintViolation = validator.validate(entity);
		// si no esta vacia lanza el error
		if (constrintViolation.isEmpty() == false) {

			throw new ConstraintViolationException(constrintViolation);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return shopingCartRepository.count();
	}

}
