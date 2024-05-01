package uz.mediasolutions.siryo24bot.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.abs.PriceStatusController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.PriceStatusDTO;
import uz.mediasolutions.siryo24bot.service.abs.PriceStatusService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceStatusControllerImpl implements PriceStatusController {

    private final PriceStatusService priceStatusService;

    @Override
    public ApiResult<List<PriceStatusDTO>> getAll() {
        return priceStatusService.getAll();
    }
}
