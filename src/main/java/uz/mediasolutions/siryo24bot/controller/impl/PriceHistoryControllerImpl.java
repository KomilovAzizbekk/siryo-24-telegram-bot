package uz.mediasolutions.siryo24bot.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.abs.PriceHistoryController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;
import uz.mediasolutions.siryo24bot.service.abs.PriceHistoryService;

@RestController
@RequiredArgsConstructor
public class PriceHistoryControllerImpl implements PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    @Override
    public ApiResult<Page<PriceHistoryDTO>> getAll(int page, int size, String search) {
        return priceHistoryService.getAll(page,size, search);
    }
}
