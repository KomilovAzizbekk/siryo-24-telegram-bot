package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Category;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.CategoryMapper;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;
import uz.mediasolutions.siryo24bot.repository.CategoryRepository;
import uz.mediasolutions.siryo24bot.service.abs.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ApiResult<Page<CategoryDTO>> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.isEmpty()) {
            Page<Category> categories = categoryRepository.findAllByOrderByNumberAsc(pageable);
            Page<CategoryDTO> map = categories.map(categoryMapper::toDTO);
            return ApiResult.success(map);
        } else {
            Page<Category> categories = categoryRepository.findAllByNameUzContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrderByNumberAsc(search, search, pageable);
            Page<CategoryDTO> map = categories.map(categoryMapper::toDTO);
            return ApiResult.success(map);
        }
    }

    @Override
    public ApiResult<CategoryDTO> getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST));
        CategoryDTO dto = categoryMapper.toDTO(category);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(CategoryDTO categoryDTO) {
        Category entity = categoryMapper.toEntity(categoryDTO);
        categoryRepository.save(entity);
        return ApiResult.success("Saved successfully");
    }

    @Override
    public ApiResult<?> edit(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST));
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setNumber(categoryDTO.getNumber());
        categoryRepository.save(category);
        return ApiResult.success("Edited successfully");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            return ApiResult.success("Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Delete failed", HttpStatus.BAD_REQUEST);
        }
    }
}
