package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(CategoryController.CATEGORY)
public interface CategoryController {

    String CATEGORY = Rest.BASE_PATH + "category/";
    String GET_ALL = "get-all";
    String GET_BY_ID = "get/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET_ALL)
    ApiResult<Page<CategoryDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                        @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                        @RequestParam(required = false) String search);

    @GetMapping(GET_BY_ID)
    ApiResult<CategoryDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    ApiResult<?> add(@RequestBody CategoryDTO categoryDTO);

    @PutMapping(EDIT)
    ApiResult<?> edit(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id);

    @DeleteMapping(DELETE)
    ApiResult<?> delete(@PathVariable Long id);
}
