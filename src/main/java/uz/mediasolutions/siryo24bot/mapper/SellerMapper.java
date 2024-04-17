package uz.mediasolutions.siryo24bot.mapper;

import org.mapstruct.Mapper;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.payload.SellerDTO;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerDTO toDTO(Seller seller);

    Seller toEntity(SellerDTO sellerDTO);

}
