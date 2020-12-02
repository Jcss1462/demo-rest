package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.dto.ShopingCartDTO;

@Mapper
public interface ShopingCartMapper {
	
	public ShopingCartDTO toShopingCartDTO(ShoppingCart shopingCart);

	public ShoppingCart toShopingCart(ShopingCartDTO shopingCartDTO);
	
	public List<ShopingCartDTO> toShopingCartsDTO(List<ShoppingCart> shopingCarts);

	public List<ShoppingCart> toShopingCarts(List<ShopingCartDTO> shopingCartsDTO);

}
