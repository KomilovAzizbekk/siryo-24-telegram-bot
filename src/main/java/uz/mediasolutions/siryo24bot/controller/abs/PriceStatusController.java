package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.PriceStatusDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

import java.util.List;

@RequestMapping(PriceStatusController.PRICE_STATUS)
public interface PriceStatusController {

    String PRICE_STATUS = Rest.BASE_PATH + "priceStatus/";
    String GET_ALL = "get-all";

    @GetMapping(GET_ALL)
    ApiResult<List<PriceStatusDTO>> getAll();


}
