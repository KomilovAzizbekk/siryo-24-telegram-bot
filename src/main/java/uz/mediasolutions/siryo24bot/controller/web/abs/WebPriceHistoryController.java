package uz.mediasolutions.siryo24bot.controller.web.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.PriceHistoryWebDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(WebPriceHistoryController.WEB_PRICE_HISTORY)
public interface WebPriceHistoryController {

    String WEB_PRICE_HISTORY = Rest.BASE_PATH + "web-price-history/";
    String GET = "get";

    @GetMapping(GET)
    ApiResult<Page<PriceHistoryWebDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                               @RequestParam(required = false) String search,
                                               @RequestParam(required = false) Long category,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String country,
                                               @RequestParam(required = false) String manufacturer,
                                               @RequestParam(required = false) Long seller,
                                               @RequestParam Long from,
                                               @RequestParam Long to);

}
