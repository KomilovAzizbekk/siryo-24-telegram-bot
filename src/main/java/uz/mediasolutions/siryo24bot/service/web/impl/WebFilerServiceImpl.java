package uz.mediasolutions.siryo24bot.service.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Category;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.enums.LanguageName;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.ProductMapper;
import uz.mediasolutions.siryo24bot.payload.web.CategoryWebDTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb3DTO;
import uz.mediasolutions.siryo24bot.payload.web.SellerWebDTO;
import uz.mediasolutions.siryo24bot.repository.CategoryRepository;
import uz.mediasolutions.siryo24bot.repository.ProductRepository;
import uz.mediasolutions.siryo24bot.repository.SellerRepository;
import uz.mediasolutions.siryo24bot.repository.TgUserRepository;
import uz.mediasolutions.siryo24bot.service.web.abs.WebFilterService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebFilerServiceImpl implements WebFilterService {

    private final CategoryRepository categoryRepository;
    private final TgUserRepository tgUserRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductMapper productMapper;

    @Override
    public ApiResult<List<?>> getCategory(String userId) {
        TgUser user = tgUserRepository.findByChatId(userId);
        List<CategoryWebDTO> categoryWebDTOS = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            if (user.getLanguage().getName().equals(LanguageName.UZ)) {
                categoryWebDTOS.add(new CategoryWebDTO(category.getId(), category.getNameUz(), productMapper.toProductWeb3DTOList(category.getProducts())));
            } else {
                categoryWebDTOS.add(new CategoryWebDTO(category.getId(), category.getNameRu(), productMapper.toProductWeb3DTOList(category.getProducts())));
            }
        }
        categoryWebDTOS.sort(Comparator.comparing(CategoryWebDTO::getName));
        return ApiResult.success(categoryWebDTOS);
    }

    @Override
    public ApiResult<List<?>> getCountry() {
        List<Product> products = productRepository.findAll();
        Set<String> countries = new HashSet<>();
        for (Product product : products) {
            countries.add(product.getCountry());
        }
        List<String> countriesNames = new ArrayList<>(countries);
        Collections.sort(countriesNames);
        return ApiResult.success(countriesNames);
    }

    @Override
    public ApiResult<List<?>> getManufacturer() {
        List<Product> products = productRepository.findAll();
        Set<String> manufacturers = new HashSet<>();
        for (Product product : products) {
            manufacturers.add(product.getManufacturer());
        }
        List<String> manufacturersNames = new ArrayList<>(manufacturers);
        Collections.sort(manufacturersNames);
        return ApiResult.success(manufacturersNames);
    }

    @Override
    public ApiResult<List<?>> getSeller() {
        List<Seller> sellers = sellerRepository.findAll();
        List<SellerWebDTO> sellerWebDTOS = new ArrayList<>();
        for (Seller seller : sellers) {
            sellerWebDTOS.add(new SellerWebDTO(seller.getId(), seller.getOrganization()));
        }
        sellerWebDTOS.sort(Comparator.comparing(SellerWebDTO::getName));
        return ApiResult.success(sellerWebDTOS);
    }

    @Override
    public ApiResult<List<?>> getProductName(Long categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        List<ProductWeb3DTO> productWeb3DTOS = new ArrayList<>();
        for (Product product : products) {
            productWeb3DTOS.add(new ProductWeb3DTO(product.getId(), product.getName()));
        }
        productWeb3DTOS.sort(Comparator.comparing(ProductWeb3DTO::getName));
        return ApiResult.success(productWeb3DTOS);
    }
}
