package uz.mediasolutions.siryo24bot.mapper;

import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.payload.request.SellerReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SellerResDTO;

public interface SellerMapper {

    SellerResDTO toDTO(Seller seller);

    Seller toEntity(SellerReqDTO sellerReqDTO);

}
