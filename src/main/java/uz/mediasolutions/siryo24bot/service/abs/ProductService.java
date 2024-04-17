package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;

public interface ProductService {

    ApiResult<Page<ProductResDTO>> getAll(int page, int size, String search);

    ApiResult<ProductResDTO> getById(Long id);

    ApiResult<?> add(ProductReqDTO dto);

    ApiResult<?> edit(ProductReqDTO dto, Long id);

    ApiResult<?> delete(Long id);

}
