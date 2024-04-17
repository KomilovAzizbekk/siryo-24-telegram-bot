package uz.mediasolutions.siryo24bot.controller.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.web.abs.WebProductController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;

@RestController
@RequiredArgsConstructor
public class WebProductControllerImpl implements WebProductController {
    @Override
    public ApiResult<Page<ProductWebDTO>> getAll(int page, int size, String name, String category, String country, String manufacturer, String seller) {
        return null;
    }

    @Override
    public ApiResult<ProductWeb2DTO> getById(Long id) {
        return null;
    }

    @Override
    public ApiResult<ProductWebDTO> addToFavorite(Long id) {
        return null;
    }
}
