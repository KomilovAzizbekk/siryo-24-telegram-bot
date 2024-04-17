package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.request.SubcategoryReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SubcategoryResDTO;

public interface SubcategoryService {

    ApiResult<Page<SubcategoryResDTO>> getAll(int page, int size, String search);

    ApiResult<SubcategoryResDTO> getById(Long id);

    ApiResult<?> add(SubcategoryReqDTO dto);

    ApiResult<?> edit(SubcategoryReqDTO dto, Long id);

    ApiResult<?> delete(Long id);
}
