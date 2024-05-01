package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;

public interface PriceHistoryService {

    ApiResult<Page<PriceHistoryDTO>> getAll(int page, int size, String search);

}
