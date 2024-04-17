package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;
import uz.mediasolutions.siryo24bot.payload.SellerDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(SellerController.SELLER)
public interface SellerController {

    String SELLER = Rest.BASE_PATH + "seller/";
    String GET_ALL = "get-all";
    String GET_BY_ID = "get/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";

    @GetMapping(GET_ALL)
    ApiResult<Page<SellerDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                      @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                      @RequestParam(required = false) String search);

    @GetMapping(GET_BY_ID)
    ApiResult<SellerDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    ApiResult<?> add(@RequestBody SellerDTO dto);

    @PutMapping(EDIT)
    ApiResult<?> edit(@RequestBody SellerDTO dto, @PathVariable Long id);
}
