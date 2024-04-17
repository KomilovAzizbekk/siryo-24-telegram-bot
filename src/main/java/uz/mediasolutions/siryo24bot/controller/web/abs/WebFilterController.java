package uz.mediasolutions.siryo24bot.controller.web.abs;

import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

import java.util.List;
import java.util.Set;

@RequestMapping(WebFilterController.FILTER_WEB)
public interface WebFilterController {

    String FILTER_WEB = Rest.BASE_PATH + "filter-web/";
    String GET_CATEGORY = "get-category";
    String GET_COUNTRY = "get-country";
    String GET_MANUFACTURER = "get-manufacturer";
    String GET_SELLER = "get-seller";

    @GetMapping(GET_CATEGORY)
    ApiResult<List<?>> getCategory();

    @GetMapping(GET_COUNTRY)
    ApiResult<Set<?>> getCountry();

    @GetMapping(GET_MANUFACTURER)
    ApiResult<Set<?>> getManufacturer();

    @GetMapping(GET_SELLER)
    ApiResult<List<?>> getSeller();
}
