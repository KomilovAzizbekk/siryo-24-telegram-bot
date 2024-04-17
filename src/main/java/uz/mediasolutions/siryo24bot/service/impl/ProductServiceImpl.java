package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.*;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.ProductMapperImpl;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;
import uz.mediasolutions.siryo24bot.repository.*;
import uz.mediasolutions.siryo24bot.service.abs.ProductService;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;
    private final SellerRepository sellerRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final AlternativeRepository alternativeRepository;
    private final PriceRepository priceRepository;

    @Override
    public ApiResult<Page<ProductResDTO>> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.isEmpty()) {
            Page<Product> products = productRepository.findAllByOrderByCreatedAtDesc(pageable);
            Page<ProductResDTO> map = products.map(productMapper::toDTO);
            return ApiResult.success(map);
        } else {
            Page<Product> products = productRepository.findAllByNameUzContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrderByCreatedAtDesc(search, search, pageable);
            Page<ProductResDTO> map = products.map(productMapper::toDTO);
            return ApiResult.success(map);
        }
    }

    @Override
    public ApiResult<ProductResDTO> getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));
        ProductResDTO dto = productMapper.toDTO(product);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(ProductReqDTO dto) {
        Product entity = productMapper.toEntity(dto);
        productRepository.save(entity);
        return ApiResult.success("Saved successfully");
    }

    @Override
    public ApiResult<?> edit(ProductReqDTO dto, Long id) {
        //Getting product
        Product product = productRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));

        //Getting new seller of product
        Seller seller = sellerRepository.findById(dto.getSellerId()).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));

        //Getting new subcategory of product
        Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId()).orElseThrow(
                () -> RestException.restThrow("Subcategory not found", HttpStatus.BAD_REQUEST));

        //Editing alternatives
        List<Alternative> alternatives = product.getAlternatives();
        alternatives.clear();
        for (String alternativeName : dto.getAlternativeNames()) {
            if (!alternativeRepository.existsByName(alternativeName)) {
                Alternative alternative = alternativeRepository.save(new Alternative(alternativeName));
                alternatives.add(alternative);
            } else {
                Alternative alternative = alternativeRepository.findByName(alternativeName);
                alternatives.add(alternative);
            }
        }

        //Editing analogs
        List<Product> analogs = product.getAnalogs();
        analogs.clear();
        for (Long analog : dto.getAnalogs()) {
            if (productRepository.existsById(analog)) {
                Product analogProduct = productRepository.findById(analog).orElseThrow(
                        () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));
                analogs.add(analogProduct);
            }
        }

        //Editing price
        Price price = priceRepository.findById(product.getPrice().getId()).orElseThrow(
                () -> RestException.restThrow("Price not found", HttpStatus.BAD_REQUEST));
        price.setPrice(dto.getPrice());
        price.setProduct(product);
        Price savedPrice = priceRepository.save(price);

        //Setting all fields
        product.setSeller(seller);
        product.setSubcategory(subcategory);
        product.setNameUz(dto.getNameUz());
        product.setNameRu(dto.getNameRu());
        product.setAlternatives(alternatives);
        product.setCountry(dto.getCountry());
        product.setManufacturer(dto.getManufacturer());
        product.setPrice(savedPrice);
        product.setImageUrl(dto.getImageUrl());
        product.setPriceUpdatedTime(new Timestamp(System.currentTimeMillis()));
        product.setAnalogs(analogs);
        productRepository.save(product);
        return ApiResult.success("Edited successfully");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        try {
            productRepository.deleteById(id);
            return ApiResult.success("Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Delete failed", HttpStatus.BAD_REQUEST);
        }
    }
}
