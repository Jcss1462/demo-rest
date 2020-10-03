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

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.repository.PaymentMethodRepository;

@Service
@Scope("singleton")
public class PaymentMethodServiceImpl implements PaymentMethodService {

	// inyecto el repositorio
	@Autowired
	PaymentMethodRepository paymentMethodRepository;

	// inyecto el validador
	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<PaymentMethod> findAll() {
		return paymentMethodRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PaymentMethod save(PaymentMethod entity) throws Exception {
		// valido
		validate(entity);

		// NO VERIFICO si existe EL payId PORQUE ES AUTOINCREMENT

		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PaymentMethod update(PaymentMethod entity) throws Exception {

		// si es nulo o esta en blanco
		if (entity.getPayId() == null) {
			throw new Exception("El payId es obligatorio");
		}

		// valido
		validate(entity);

		// si no existe lanza el error
		if (paymentMethodRepository.existsById(entity.getPayId()) == false) {
			throw new Exception("El paymentMethod con id:" + entity.getPayId() + " no existe");
		}

		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(PaymentMethod entity) throws Exception {
		// valido que se mandaron datos
		if (entity == null) {
			throw new Exception("El paymentMethod es nulo");
		}

		// si es nulo o esta en blanco
		if (entity.getPayId() == null) {
			throw new Exception("El payId es obligatorio");
		}

		// si no existe lanza el error
		if (paymentMethodRepository.existsById(entity.getPayId()) == false) {
			throw new Exception("El paymentMethood con payId:" + entity.getPayId() + " no existe\nNo se puede borrar");
		}

		// valido que no tenga registro shopingCart
		paymentMethodRepository.findById(entity.getPayId()).ifPresent(paymentMethod -> {
			if (paymentMethod.getShoppingCarts() != null && paymentMethod.getShoppingCarts().isEmpty() == false) {
				throw new RuntimeException("El paymentMethod con payId:" + entity.getPayId()
						+ " tiene shoping shopingCart\nNo se puede borrar");
			}
		});

		paymentMethodRepository.deleteById(entity.getPayId());

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		// valido que vengan los datos
		if (id == null) {
			throw new Exception("El payId es obligatorio");
		}

		// si el id existe
		if (paymentMethodRepository.existsById(id)) {
			// manda el objeto
			delete(paymentMethodRepository.findById(id).get());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PaymentMethod> findById(Integer id) throws Exception {
		return paymentMethodRepository.findById(id);
	}

	@Override
	public void validate(PaymentMethod entity) throws Exception {

		if (entity == null) {
			throw new Exception("El paymentMethod es nulo");
		}

		// validator
		// retorna una lista de los constraint violados
		Set<ConstraintViolation<PaymentMethod>> constrintViolation = validator.validate(entity);
		// si no esta vacia lanza el error
		if (constrintViolation.isEmpty() == false) {

			throw new ConstraintViolationException(constrintViolation);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return paymentMethodRepository.count();
	}

}
