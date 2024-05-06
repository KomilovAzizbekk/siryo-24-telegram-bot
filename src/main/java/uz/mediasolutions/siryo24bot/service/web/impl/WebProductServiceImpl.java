package uz.mediasolutions.siryo24bot.service.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.FavouriteProducts;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.ProductMapper;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;
import uz.mediasolutions.siryo24bot.repository.FavouriteProductsRepository;
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
    private final FavouriteProductsRepository favouriteProductsRepository;

    @Override
    public ApiResult<Page<ProductWebDTO>> getAll(String userId, int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller, boolean stockMarket) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductWebDTO> dtos = new ArrayList<>();
        Page<ProductWebDTO> convert;

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
            for (Product product : products) {
                Seller seller1 = sellerRepository.findAllByProductId(product.getId());
                dtos.addAll(productMapper.toProductWebDTOList(products, userId, seller1));
            }
            convert = pageableConverter.convert(dtos, pageable);
        }

        else {
            List<Product> products = productRepository.findAllByNameContainingIgnoreCaseAndSellerActiveIsTrueOrderByNameAsc(search);
            for (Product product : products) {
                Seller seller1 = sellerRepository.findAllByProductId(product.getId());
                dtos.addAll(productMapper.toProductWebDTOList(products, userId, seller1));
            }
            convert = pageableConverter.convert(dtos, pageable);
        }
        return ApiResult.success(convert);
    }

    @Override
    public ApiResult<ProductWeb2DTO> getById(Long productId, Long sellerId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));

        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));
        ProductWeb2DTO productWeb2DTO = productMapper.toProductWeb2DTO(product, seller);
        return ApiResult.success(productWeb2DTO);
    }

    @Override
    public ApiResult<?> addOrRemoveFavorites(Long productId, Long sellerId, String userId, boolean add) {
        TgUser user = tgUserRepository.findByChatId(userId);
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));
        Product product = productRepository.findById(productId).orElseThrow(
                () -> RestException.restThrow("Product not found", HttpStatus.BAD_REQUEST));
        List<FavouriteProducts> products = user.getFavouriteProducts();
        for (Product p : seller.getProducts()) {
            if (p.getId().equals(productId)) {
                if (add) {
                    FavouriteProducts saved = favouriteProductsRepository.save(new FavouriteProducts(product, seller));
                    products.add(saved);
                } else {
                    FavouriteProducts favouriteProducts = favouriteProductsRepository.findByProductIdAndSellerId(productId, sellerId);
                    products.remove(favouriteProducts);
                    favouriteProductsRepository.delete(favouriteProducts);
                    favouriteProductsRepository.save(favouriteProducts);
                }
            }
        }

        user.setFavouriteProducts(products);
        tgUserRepository.save(user);
        return ApiResult.success("Success");
    }

    @Override
    public ApiResult<Page<ProductWebDTO>> getFav(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        TgUser user = tgUserRepository.findByChatId(userId);
        List<FavouriteProducts> products = user.getFavouriteProducts();
        List<FavouriteProducts> actives = new ArrayList<>();
        List<Long> favouriteProductIds = new ArrayList<>();
        for (FavouriteProducts product : products) {
            if (product.getSeller().isActive()) {
                favouriteProductIds.add(product.getId());
                actives.add(product);
            }
        }

        List<ProductWebDTO> dtos = new ArrayList<>();
        List<Product> productList = productRepository.findAllById(favouriteProductIds);
        for (FavouriteProducts active : actives) {
             dtos.addAll(productMapper.toProductWebDTOList(productList, userId, active.getSeller()));
        }
        Page<ProductWebDTO> convert = pageableConverter.convert(dtos, pageable);
        return ApiResult.success(convert);
    }


}
