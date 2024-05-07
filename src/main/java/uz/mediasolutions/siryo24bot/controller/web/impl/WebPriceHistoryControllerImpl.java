package uz.mediasolutions.siryo24bot.controller.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.web.abs.WebPriceHistoryController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.PriceHistoryWebDTO;
import uz.mediasolutions.siryo24bot.service.web.abs.WebPriceHistoryService;

@RestController
@RequiredArgsConstructor
public class WebPriceHistoryControllerImpl implements WebPriceHistoryController {

    private final WebPriceHistoryService webPriceHistoryService;

    @Override
    public ApiResult<Page<PriceHistoryWebDTO>> getAll(int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller, Long from, Long to) {
        return webPriceHistoryService.getAll(page, size, search, category, name, country, manufacturer, seller, from, to);
    }
}
