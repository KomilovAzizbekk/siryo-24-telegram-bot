package uz.mediasolutions.siryo24bot.service.web.abs;

import uz.mediasolutions.siryo24bot.manual.ApiResult;

import java.util.List;

public interface WebFilterService {

    ApiResult<List<?>> getCategory(String userId);

    ApiResult<List<?>> getCountry();

    ApiResult<List<?>> getManufacturer();

    ApiResult<List<?>> getSeller();

}
