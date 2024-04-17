package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Category;
import uz.mediasolutions.siryo24bot.entity.Subcategory;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.CategoryMapper;
import uz.mediasolutions.siryo24bot.mapper.SubcategoryMapper;
import uz.mediasolutions.siryo24bot.payload.request.SubcategoryReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SubcategoryResDTO;
import uz.mediasolutions.siryo24bot.repository.CategoryRepository;
import uz.mediasolutions.siryo24bot.repository.SubcategoryRepository;
import uz.mediasolutions.siryo24bot.service.abs.SubcategoryService;

import static org.json.XMLTokener.entity;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryMapper subcategoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResult<Page<SubcategoryResDTO>> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.isEmpty()) {
            Page<Subcategory> subcategories = subcategoryRepository.findAllByOrderByNumberAsc(pageable);
            Page<SubcategoryResDTO> map = subcategories.map(subcategoryMapper::toDto);
            return ApiResult.success(map);
        } else {
            Page<Subcategory> subcategories = subcategoryRepository.findAllByNameUzContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrderByNumberAsc(search, search, pageable);
            Page<SubcategoryResDTO> map = subcategories.map(subcategoryMapper::toDto);
            return ApiResult.success(map);
        }
    }

    @Override
    public ApiResult<SubcategoryResDTO> getById(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Subcategory not found", HttpStatus.BAD_REQUEST));
        SubcategoryResDTO dto = subcategoryMapper.toDto(subcategory);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(SubcategoryReqDTO dto) {
        Subcategory entity = subcategoryMapper.toEntity(dto);
        subcategoryRepository.save(entity);
        return ApiResult.success("Saved successfully");
    }

    @Override
    public ApiResult<?> edit(SubcategoryReqDTO dto, Long id) {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST));

        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Subcategory not found", HttpStatus.BAD_REQUEST));

        subcategory.setCategory(category);
        subcategory.setNumber(dto.getNumber());
        subcategory.setNameUz(dto.getNameUz());
        subcategory.setNameRu(dto.getNameRu());
        subcategoryRepository.save(subcategory);
        return ApiResult.success("Edited successfully");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        try {
            subcategoryRepository.deleteById(id);
            return ApiResult.success("Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Delete failed", HttpStatus.BAD_REQUEST);
        }
    }
}
