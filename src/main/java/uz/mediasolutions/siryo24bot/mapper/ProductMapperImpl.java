package uz.mediasolutions.siryo24bot.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.siryo24bot.entity.*;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.payload.AlternativeDTO;
import uz.mediasolutions.siryo24bot.payload.AnalogProductDTO;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;
import uz.mediasolutions.siryo24bot.repository.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final SellerRepository sellerRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final AlternativeRepository alternativeRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductResDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResDTO.builder()
                .id(product.getId())
                .seller(product.getSeller().getOrganization())
                .name(product.getName())
                .subcategory(product.getSubcategory().getNameUz() + "/" + product.getSubcategory().getNameRu())
                .country(product.getCountry())
                .manufacturer(product.getManufacturer())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .priceUpdatedTime(product.getPriceUpdatedTime() != null ?
                        product.getPriceUpdatedTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")) : "")
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
        Seller seller = sellerRepository.findById(dto.getSellerId()).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));

        Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId()).orElseThrow(
                () -> RestException.restThrow("Subcategory not found", HttpStatus.BAD_REQUEST));

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
                .seller(seller)
                .name(dto.getName())
                .subcategory(subcategory)
                .alternatives(alternatives)
                .country(dto.getCountry())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .priceUpdatedTime(null)
                .analogs(products)
                .build();

    }

}