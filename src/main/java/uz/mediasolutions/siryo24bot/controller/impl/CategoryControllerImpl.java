package uz.mediasolutions.siryo24bot.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.abs.CategoryController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;
import uz.mediasolutions.siryo24bot.service.abs.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public ApiResult<Page<CategoryDTO>> getAll(int page, int size, String search) {
        return categoryService.getAll(page, size, search);
    }

    @Override
    public ApiResult<CategoryDTO> getById(Long id) {
        return categoryService.getById(id);
    }

    @Override
    public ApiResult<?> add(CategoryDTO categoryDTO) {
        return categoryService.add(categoryDTO);
    }

    @Override
    public ApiResult<?> edit(CategoryDTO categoryDTO, Long id) {
        return categoryService.edit(categoryDTO, id);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return categoryService.delete(id);
    }
}
