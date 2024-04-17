package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;
import uz.mediasolutions.siryo24bot.payload.request.SubcategoryReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SubcategoryResDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(SubcategoryController.SUBCATEGORY)
public interface SubcategoryController {

    String SUBCATEGORY = Rest.BASE_PATH + "subcategory/";
    String GET_ALL = "get-all";
    String GET_BY_ID = "get/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET_ALL)
    ApiResult<Page<SubcategoryResDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                              @RequestParam(required = false) String search);

    @GetMapping(GET_BY_ID)
    ApiResult<SubcategoryResDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    ApiResult<?> add(@RequestBody SubcategoryReqDTO dto);

    @PutMapping(EDIT)
    ApiResult<?> edit(@RequestBody SubcategoryReqDTO dto, @PathVariable Long id);

    @DeleteMapping(DELETE)
    ApiResult<?> delete(@PathVariable Long id);
}
