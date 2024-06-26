package uz.mediasolutions.siryo24bot.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.siryo24bot.entity.*;
import uz.mediasolutions.siryo24bot.enums.LanguageName;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.payload.AlternativeDTO;
import uz.mediasolutions.siryo24bot.payload.AnalogProductDTO;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;
import uz.mediasolutions.siryo24bot.payload.web.AnalogProductWebDTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb3DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;
import uz.mediasolutions.siryo24bot.repository.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final UpdatesRepository updatesRepository;
    private final CategoryRepository categoryRepository;
    private final AlternativeRepository alternativeRepository;
    private final ProductRepository productRepository;
    private final TgUserRepository tgUserRepository;

    @Override
    public ProductResDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategory().getId())
                .category(product.getCategory().getNameUz() + "/" + product.getCategory().getNameRu())
                .country(product.getCountry())
                .manufacturer(product.getManufacturer())
                .imageUrl(product.getImageUrl())
                .analogs(toAnalogProductDTOList(product.getAnalogs()))
                .alternativeNames(toAlternativeDTOList(product.getAlternatives()))
                .build();
    }

    @Override
    public AlternativeDTO toAlternativeDTO(Alternative alternative) {
        if (alternative == null) {
            return null;
        }
        return AlternativeDTO.builder()
                .id(alternative.getId())
                .name(alternative.getName())
                .build();
    }

    @Override
    public List<AlternativeDTO> toAlternativeDTOList(List<Alternative> alternatives) {
        if (alternatives == null) {
            return null;
        }
        List<AlternativeDTO> alternativeDTOList = new ArrayList<>();
        for (Alternative alternative : alternatives) {
            alternativeDTOList.add(toAlternativeDTO(alternative));
        }
        return alternativeDTOList;
    }

    @Override
    public AnalogProductDTO toAnalogProductDTO(Product product) {
        if (product == null) {
            return null;
        }
        return AnalogProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }

    public List<AnalogProductDTO> toAnalogProductDTOList(List<Product> products) {
        if (products == null) {
            return null;
        }
        List<AnalogProductDTO> analogProductDTOList = new ArrayList<>();
        for (Product product : products) {
            analogProductDTOList.add(toAnalogProductDTO(product));
        }
        return analogProductDTOList;
    }

    @Override
    public Product toEntity(ProductReqDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST));

        List<Product> products = new ArrayList<>();
        if (dto.getAnalogs() != null) {
            products = productRepository.findAllById(dto.getAnalogs());
        }

        List<Alternative> alternatives = new ArrayList<>();
        if (dto.getAlternativeNames() != null) {
            for (String alternativeName : dto.getAlternativeNames()) {
                if (alternativeRepository.existsByName(alternativeName)) {
                    alternatives.add(alternativeRepository.findByName(alternativeName));
                } else {
                    Alternative alternative = alternativeRepository.save(new Alternative(alternativeName));
                    alternatives.add(alternative);
                }
            }
        }

        return Product.builder()
                .name(dto.getName())
                .category(category)
                .alternatives(alternatives)
                .country(dto.getCountry())
                .manufacturer(dto.getManufacturer())
                .imageUrl(dto.getImageUrl())
                .analogs(products)
                .build();

    }

    @Override
    public ProductWeb2DTO toProductWeb2DTO(Product product, Seller seller) {
        if (product == null) {
            return null;
        }
        return ProductWeb2DTO.builder()
                .id(product.getId())
                .sellerInfo(seller.getInfo())
                .name(product.getName())
                .sellerPhone1(seller.getPhoneNumber1())
                .sellerPhone2(seller.getPhoneNumber2())
                .imageUrl(product.getImageUrl())
                .build();
    }

    @Override
    public ProductWebDTO toProductWebDTO(Product product, String userId, Seller seller) {
        if (product == null) {
            return null;
        }

        boolean favourite = false;
        TgUser user = tgUserRepository.findByChatId(userId);
        if (user.getFavouriteProducts() != null) {
            for (FavouriteProducts favouriteProducts : user.getFavouriteProducts()) {
                if (favouriteProducts.getProduct().getId().equals(product.getId()) &&
                        favouriteProducts.getSeller().getId().equals(seller.getId())) {
                    favourite = true;
                    break;
                }
            }
        }

        Updates updates = updatesRepository.findTopBySellerIdAndProductIdOrderByUpdatedTimeDesc(seller.getId(), product.getId());

        return ProductWebDTO.builder()
                .id(product.getId())
                .seller(seller.getOrganization())
                .sellerId(seller.getId())
                .acceptCash(seller.isAcceptCash())
                .acceptTransfer(seller.isAcceptTransfer())
                .favourite(favourite)
                .name(product.getName())
                .country(product.getCountry())
                .manufacturer(product.getManufacturer())
                .priceUpdatedTime(updates != null ? updates.getUpdatedTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")) :
                        seller.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")))
                .analogs(toAnalogProductWebDTOList(product.getAnalogs(), userId))
                .analogsCount(toAnalogProductWebDTOList(product.getAnalogs(), userId).size())
                .build();
    }

    @Override
    public AnalogProductWebDTO toAnalogProductWebDTO(Product product, String userId) {
        if (product == null) {
            return null;
        }

        TgUser user = tgUserRepository.findByChatId(userId);

        return AnalogProductWebDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .country(product.getCountry())
                .category(user.getLanguage().getName().equals(LanguageName.UZ) ?
                        product.getCategory().getNameUz() : product.getCategory().getNameRu())
                .build();
    }

    @Override
    public List<AnalogProductWebDTO> toAnalogProductWebDTOList(List<Product> products, String userId) {
        if (products == null) {
            return null;
        }
        List<AnalogProductWebDTO> analogProductWebDTOs = new ArrayList<>();
        for (Product product : products) {
            analogProductWebDTOs.add(toAnalogProductWebDTO(product, userId));
        }
        return analogProductWebDTOs;
    }

    @Override
    public List<ProductWebDTO> toProductWebDTOList(List<Product> products, String userId, Seller seller) {
        if (products == null) {
            return null;
        }

        List<ProductWebDTO> productWebDTOs = new ArrayList<>();
        for (Product product : products) {
            productWebDTOs.add(toProductWebDTO(product, userId, seller));
        }

        return productWebDTOs;
    }

    @Override
    public ProductWeb3DTO toProductWeb3DTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductWeb3DTO.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }

    @Override
    public List<ProductWeb3DTO> toProductWeb3DTOList(List<Product> products) {
        if (products == null) {
            return null;
        }

        List<ProductWeb3DTO> productWeb3DTOs = new ArrayList<>();
        for (Product product : products) {
            productWeb3DTOs.add(toProductWeb3DTO(product));
        }
        return productWeb3DTOs;
    }

}
