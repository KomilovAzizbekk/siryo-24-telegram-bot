package uz.mediasolutions.siryo24bot.mapper;

import uz.mediasolutions.siryo24bot.entity.Alternative;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.payload.AlternativeDTO;
import uz.mediasolutions.siryo24bot.payload.AnalogProductDTO;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;

import java.util.List;

public interface ProductMapper {

    ProductResDTO toDTO(Product product);

    AlternativeDTO toAlternativeDTO(Alternative alternative);

    List<AlternativeDTO> toAlternativeDTOList(List<Alternative> alternatives);

    AnalogProductDTO toAnalogProductDTO(Product product);

    List<AnalogProductDTO> toAnalogProductDTOList(List<Product> products);

    Product toEntity(ProductReqDTO dto);

}
