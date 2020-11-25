package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.SopingProductDTO;

@Mapper
public interface ShopingProductMapper {
	
	public SopingProductDTO toShopingProductDTO(ShoppingProduct product);

	public ShoppingProduct toShopingProduct(SopingProductDTO productDTO);
	
	public List<SopingProductDTO> toShopingProductsDto(List<ShoppingProduct> products);

	public List<ShoppingProduct> toShopingProducts(List<SopingProductDTO> productsDTO);

}
