package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

import java.io.IOException;

@RequestMapping(ProductController.PRODUCT)
public interface ProductController {

    String PRODUCT = Rest.BASE_PATH + "product/";
    String GET_ALL = "get-all";
    String GET_BY_ID = "get/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET_ALL)
    ApiResult<Page<ProductResDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                          @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                          @RequestParam(required = false) String search);

    @GetMapping(GET_BY_ID)
    ApiResult<ProductResDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    ApiResult<?> add(@RequestBody ProductReqDTO dto);

    @PutMapping(EDIT)
    ApiResult<?> edit(@RequestBody ProductReqDTO dto, @PathVariable Long id) throws IOException;

    @DeleteMapping(DELETE)
    ApiResult<?> delete(@PathVariable Long id) throws IOException;
}
