package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.dto.PaymentMethodDTO;

//le digo a mapstruct que hare un mapper
@Mapper
public interface PaymentMethodMapper {
	
	public PaymentMethodDTO toPaymentMethodDTO(PaymentMethod paymentMethod);

	public PaymentMethod toPaymentMethod(PaymentMethodDTO paymentMethodDTO);
	
	public List<PaymentMethodDTO> toPaymentMethodsDTO(List<PaymentMethod> paymentMethods);

	public List<PaymentMethod> toPaymentMethods(List<PaymentMethodDTO> paymentMethodsDTO);

}
