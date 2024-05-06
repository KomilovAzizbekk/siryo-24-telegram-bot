package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.request.SellerReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SellerResDTO;

public interface SellerService {

    ApiResult<Page<SellerResDTO>> getAll(int page, int size, String search);

    ApiResult<Page<SellerResDTO>> getAllActive(int page, int size, String search);

    ApiResult<SellerResDTO> getById(Long id);

    ApiResult<?> add(SellerReqDTO dto);

    ApiResult<?> edit(SellerReqDTO dto, Long id);

    ApiResult<?> delete(Long id);
}
