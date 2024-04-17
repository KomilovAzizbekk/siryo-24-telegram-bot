package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;

public interface CategoryService {

    ApiResult<Page<CategoryDTO>> getAll(int page, int size, String search);

    ApiResult<CategoryDTO> getById(Long id);

    ApiResult<?> add(CategoryDTO categoryDTO);

    ApiResult<?> edit(CategoryDTO categoryDTO, Long id);

    ApiResult<?> delete(Long id);
}
