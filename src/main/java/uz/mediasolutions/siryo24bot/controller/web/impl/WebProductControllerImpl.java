package uz.mediasolutions.siryo24bot.controller.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.web.abs.WebProductController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;
import uz.mediasolutions.siryo24bot.service.web.abs.WebProductService;

@RestController
@RequiredArgsConstructor
public class WebProductControllerImpl implements WebProductController {

    private final WebProductService webProductService;

    @Override
    public ApiResult<Page<ProductWebDTO>> getAll(String userId, int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller) {
        return webProductService.getAll(userId, page, size, search, category, name, country, manufacturer, seller);
    }

    @Override
    public ApiResult<Page<ProductWebDTO>> getFavourites(String userId, int page, int size) {
        return webProductService.getFav(userId, page, size);
    }

    @Override
    public ApiResult<ProductWeb2DTO> getById(Long id) {
        return webProductService.getById(id);
    }

    @Override
    public ApiResult<?> addOrRemoveFavorites(Long id, String userId, boolean add) {
        return webProductService.addOrRemoveFavorites(id, userId, add);
    }


}
