package uz.mediasolutions.siryo24bot.service.web.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.PriceHistoryWebDTO;

public interface WebPriceHistoryService {

    ApiResult<Page<PriceHistoryWebDTO>> getAll(int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller, Long from, Long to);

}
