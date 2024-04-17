package uz.mediasolutions.siryo24bot.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.abs.CategoryController;
import uz.mediasolutions.siryo24bot.controller.abs.SubcategoryController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;
import uz.mediasolutions.siryo24bot.payload.request.SubcategoryReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SubcategoryResDTO;
import uz.mediasolutions.siryo24bot.service.abs.CategoryService;
import uz.mediasolutions.siryo24bot.service.abs.SubcategoryService;

@RestController
@RequiredArgsConstructor
public class SubcategoryControllerImpl implements SubcategoryController {

    private final SubcategoryService subcategoryService;

    @Override
    public ApiResult<Page<SubcategoryResDTO>> getAll(int page, int size, String search) {
        return subcategoryService.getAll(page, size, search);
    }

    @Override
    public ApiResult<SubcategoryResDTO> getById(Long id) {
        return subcategoryService.getById(id);
    }

    @Override
    public ApiResult<?> add(SubcategoryReqDTO dto) {
        return subcategoryService.add(dto);
    }

    @Override
    public ApiResult<?> edit(SubcategoryReqDTO dto, Long id) {
        return subcategoryService.edit(dto, id);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return subcategoryService.delete(id);
    }
}
