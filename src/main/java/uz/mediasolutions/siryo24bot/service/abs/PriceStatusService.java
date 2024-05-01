package uz.mediasolutions.siryo24bot.service.abs;

import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.PriceStatusDTO;

import java.util.List;

public interface PriceStatusService {
    ApiResult<List<PriceStatusDTO>> getAll();

}
