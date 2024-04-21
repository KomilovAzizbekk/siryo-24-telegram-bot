package uz.mediasolutions.siryo24bot.service.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Category;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.enums.LanguageName;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.CategoryWebDTO;
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

    @Override
    public ApiResult<List<?>> getCategory(String userId) {
        TgUser user = tgUserRepository.findByChatId(userId);
        List<CategoryWebDTO> categoryWebDTOS = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            if (user.getLanguage().getName().equals(LanguageName.UZ)) {
                categoryWebDTOS.add(new CategoryWebDTO(category.getId(), category.getNameUz()));
            } else {
                categoryWebDTOS.add(new CategoryWebDTO(category.getId(), category.getNameRu()));
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
        List<String> productNames = new ArrayList<>();
        for (Product product : products) {
            productNames.add(product.getName());
        }
        Collections.sort(productNames);
        return ApiResult.success(productNames);
    }
}
