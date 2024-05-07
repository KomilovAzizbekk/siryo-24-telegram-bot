package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb3DTO;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ApiResult<Page<ProductResDTO>> getAll(int page, int size, String search);

    ApiResult<ProductResDTO> getById(Long id);

    ApiResult<?> add(ProductReqDTO dto);

    ApiResult<?> edit(ProductReqDTO dto, Long id) throws IOException;

    ApiResult<?> delete(Long id) throws IOException;

    ApiResult<List<ProductWeb3DTO>> getBySeller(Long sellerId);
}
