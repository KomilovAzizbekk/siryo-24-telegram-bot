package uz.mediasolutions.siryo24bot.service.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.ProductMapper;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;
import uz.mediasolutions.siryo24bot.repository.ProductRepository;
import uz.mediasolutions.siryo24bot.repository.SellerRepository;
import uz.mediasolutions.siryo24bot.repository.TgUserRepository;
import uz.mediasolutions.siryo24bot.service.web.PageableConverter;
import uz.mediasolutions.siryo24bot.service.web.abs.WebProductService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebProductServiceImpl implements WebProductService {

    private final ProductRepository productRepository;
    private final TgUserRepository tgUserRepository;
    private final ProductMapper productMapper;
    private final PageableConverter pageableConverter;
    private final SellerRepository sellerRepository;

    @Override
    public ApiResult<Page<ProductWebDTO>> getAll(String userId, int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller, boolean stockMarket) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductWebDTO> dtos = new ArrayList<>();
        Page<ProductWebDTO> convert = null;

        if (search == null && category == null && country == null && manufacturer == null &&
                seller == null && name == null) {
            List<Seller> sellers = sellerRepository.findAllByActiveIsTrue();
            for (Seller s : sellers) {
                List<Product> products = s.getProducts();
                dtos.addAll(productMapper.toProductWebDTOList(products, userId, s));
            }
            convert = pageableConverter.convert(dtos, pageable);
        }

        else if (category != null || name != null || country != null ||
                manufacturer != null || seller != null) {
            List<Product> products = productRepository.findAllByCategoryAndNameAndCountryAndManufacturerAndSeller(
                    category, name, country, manufacturer, seller, stockMarket);
            List<Seller> sellerList = sellerRepository.findAllByProducts(products);
            for (Seller s : sellerList) {
                dtos.addAll(productMapper.toProductWebDTOList(products, userId, s));
            }
            convert = pageableConverter.convert(dtos, pageable);
        }

        else {
            List<Product> products = productRepository.findAllByNameContainingIgnoreCaseAndSellerActiveIsTrueOrderByNameAsc(search);
            List<Seller> sellerList = sellerRepository.findAllByProducts(products);
            for (Seller s : sellerList) {
                dtos.addAll(productMapper.toProductWebDTOList(products, userId, s));
            }
            convert = pageableConverter.convert(dtos, pageable);
        }
        return ApiResult.success(convert);
    }

    @Override
    public ApiResult<ProductWeb2DTO> getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));
        ProductWeb2DTO productWeb2DTO = productMapper.toProductWeb2DTO(product);
        return ApiResult.success(productWeb2DTO);
    }

    @Override
    public ApiResult<?> addOrRemoveFavorites(Long productId, Long sellerId, String userId, boolean add) {
        TgUser user = tgUserRepository.findByChatId(userId);
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));
        List<Product> products = user.getProducts();
        for (Product product : seller.getProducts()) {
            if (product.getId().equals(productId)) {
                if (add) {
                    products.add(product);
                } else {
                    products.remove(product);
                }
            }
        }

        user.setProducts(products);
        tgUserRepository.save(user);
        return ApiResult.success("Success");
    }

    @Override
    public ApiResult<Page<ProductWebDTO>> getFav(String userId, int page, int size) {
        TgUser user = tgUserRepository.findByChatId(userId);
        List<Product> products = user.getProducts();
        List<Seller> sellerList = sellerRepository.findAllByProducts(products);
        List<Product> actives = new ArrayList<>();
        for (Product product : products) {
            if (product.getSeller().isActive()) {
                actives.add(product);
            }
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductWebDTO> dtos = productMapper.toProductWebDTOList(actives, userId, );
        return ApiResult.success(dtos);
    }


}
