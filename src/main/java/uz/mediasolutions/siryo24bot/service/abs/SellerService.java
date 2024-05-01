package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.SellerDTO;

public interface SellerService {

    ApiResult<Page<SellerDTO>> getAll(int page, int size, String search);

    ApiResult<Page<SellerDTO>> getAllActive(int page, int size, String search);

    ApiResult<SellerDTO> getById(Long id);

    ApiResult<?> add(SellerDTO dto);

    ApiResult<?> edit(SellerDTO dto, Long id);
}
