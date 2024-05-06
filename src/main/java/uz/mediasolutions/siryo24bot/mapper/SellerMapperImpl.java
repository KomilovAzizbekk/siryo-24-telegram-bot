package uz.mediasolutions.siryo24bot.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.payload.request.SellerReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SellerResDTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb3DTO;
import uz.mediasolutions.siryo24bot.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SellerMapperImpl implements SellerMapper {

    private final ProductRepository productRepository;

    @Override
    public SellerResDTO toDTO(Seller seller) {
        if (seller == null) {
            return null;
        }

        List<ProductWeb3DTO> products = new ArrayList<>();
        seller.getProducts().forEach(product -> products.add(new ProductWeb3DTO(product.getId(), product.getName())));

        return SellerResDTO.builder()
                .id(seller.getId())
                .acceptCash(seller.isAcceptCash())
                .acceptTransfer(seller.isAcceptTransfer())
                .active(seller.isActive())
                .info(seller.getInfo())
                .channel(seller.getChannel())
                .chatId(seller.getChatId())
                .phoneNumber1(seller.getPhoneNumber1())
                .phoneNumber2(seller.getPhoneNumber2())
                .organization(seller.getOrganization())
                .stockMarket(seller.isStockMarket())
                .products(products)
                .build();
    }

    @Override
    public Seller toEntity(SellerReqDTO seller) {
        if (seller == null) {
            return null;
        }

        List<Product> products = productRepository.findAllById(seller.getProducts());

        return Seller.builder()
                .id(seller.getId())
                .acceptCash(seller.isAcceptCash())
                .acceptTransfer(seller.isAcceptTransfer())
                .active(seller.isActive())
                .info(seller.getInfo())
                .channel(seller.getChannel())
                .chatId(seller.getChatId())
                .phoneNumber1(seller.getPhoneNumber1())
                .phoneNumber2(seller.getPhoneNumber2())
                .organization(seller.getOrganization())
                .stockMarket(seller.isStockMarket())
                .products(products)
                .build();
    }
}
