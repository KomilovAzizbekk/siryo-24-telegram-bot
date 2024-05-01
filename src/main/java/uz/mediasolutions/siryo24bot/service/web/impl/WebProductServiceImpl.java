package uz.mediasolutions.siryo24bot.service.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.ProductMapper;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;
import uz.mediasolutions.siryo24bot.repository.ProductRepository;
import uz.mediasolutions.siryo24bot.repository.TgUserRepository;
import uz.mediasolutions.siryo24bot.service.web.PageableConverter;
import uz.mediasolutions.siryo24bot.service.web.abs.WebProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebProductServiceImpl implements WebProductService {

    private final ProductRepository productRepository;
    private final TgUserRepository tgUserRepository;
    private final ProductMapper productMapper;
    private final PageableConverter pageableConverter;

    @Override
    public ApiResult<Page<ProductWebDTO>> getAll(String userId, int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductWebDTO> dtos = null;

        if (search == null && category == null && country == null && manufacturer == null &&
                seller == null && name == null) {
            Page<Product> products = productRepository.findAllBySellerActiveIsTrueOrderByNameAsc(pageable);
            dtos = productMapper.toProductWebDTOPage(products, userId);
        }

        else if (category != null || name != null || country != null ||
                manufacturer != null || seller != null) {
            Page<Product> products = productRepository.findAllByCategoryAndNameAndCountryAndManufacturerAndSeller(
                    category, name, country, manufacturer, seller, pageable);
            dtos = productMapper.toProductWebDTOPage(products, userId);

        }

        else {
            Page<Product> all = productRepository.findAllByNameContainingIgnoreCaseAndSellerActiveIsTrueOrderByNameAsc(search, pageable);
            dtos = productMapper.toProductWebDTOPage(all, userId);
        }
        return ApiResult.success(dtos);
    }

    @Override
    public ApiResult<ProductWeb2DTO> getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));
        ProductWeb2DTO productWeb2DTO = productMapper.toProductWeb2DTO(product);
        return ApiResult.success(productWeb2DTO);
    }

    @Override
    public ApiResult<?> addOrRemoveFavorites(Long id, String userId, boolean add) {
        TgUser user = tgUserRepository.findByChatId(userId);
        Product product = productRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));

        List<Product> products = user.getProducts();
        if (add) {
            products.add(product);
        } else {
            products.remove(product);
        }
        user.setProducts(products);
        tgUserRepository.save(user);
        return ApiResult.success("Success");
    }

    @Override
    public ApiResult<Page<ProductWebDTO>> getFav(String userId, int page, int size) {
        TgUser user = tgUserRepository.findByChatId(userId);
        List<Product> products = user.getProducts();
        List<Product> actives = new ArrayList<>();
        for (Product product : products) {
            if (product.getSeller().isActive()) {
                actives.add(product);
            }
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> convert = pageableConverter.convert(actives, pageable);
        Page<ProductWebDTO> dtos = productMapper.toProductWebDTOPage(convert, userId);
        return ApiResult.success(dtos);
    }


}
