package uz.mediasolutions.siryo24bot.service.web.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;

public interface WebProductService {

    ApiResult<Page<ProductWebDTO>> getAll(String userId, int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller, boolean stockMarket);

    ApiResult<ProductWeb2DTO> getById(Long id);

    ApiResult<?> addOrRemoveFavorites(Long id, String userId, boolean add);

    ApiResult<Page<ProductWebDTO>> getFav(String userId, int page, int size);
}
