package uz.mediasolutions.siryo24bot.controller.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.web.abs.WebFilterController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.service.web.abs.WebFilterService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebFilterControllerImpl implements WebFilterController {

    private final WebFilterService webFilterService;

    @Override
    public ApiResult<List<?>> getCategory(String userId) {
        return webFilterService.getCategory(userId);
    }

    @Override
    public ApiResult<List<?>> getCountry() {
        return webFilterService.getCountry();
    }

    @Override
    public ApiResult<List<?>> getManufacturer() {
        return webFilterService.getManufacturer();
    }

    @Override
    public ApiResult<List<?>> getSeller() {
        return webFilterService.getSeller();
    }
}
