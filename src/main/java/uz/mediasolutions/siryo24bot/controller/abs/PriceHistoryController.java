package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(PriceHistoryController.PRICE_HISTORY)
public interface PriceHistoryController {

    String PRICE_HISTORY = Rest.BASE_PATH + "price-history/";
    String GET_ALL = "get-all";

    @GetMapping(GET_ALL)
    ApiResult<Page<PriceHistoryDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                            @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                            @RequestParam(required = false) String search);

}
