package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.request.SellerReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SellerResDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(SellerController.SELLER)
public interface SellerController {

    String SELLER = Rest.BASE_PATH + "seller/";
    String GET_ALL = "get-all";
    String GET_ALL_ACTIVE = "get-all-active";
    String GET_BY_ID = "get/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET_ALL)
    ApiResult<Page<SellerResDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                         @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                         @RequestParam(required = false) String search);

    @GetMapping(GET_ALL_ACTIVE)
    ApiResult<Page<SellerResDTO>> getAllActive(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                               @RequestParam(required = false) String search);

    @GetMapping(GET_BY_ID)
    ApiResult<SellerResDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    ApiResult<?> add(@RequestBody SellerReqDTO dto);

    @PutMapping(EDIT)
    ApiResult<?> edit(@RequestBody SellerReqDTO dto, @PathVariable Long id);

    @DeleteMapping(DELETE)
    ApiResult<?> delete(@PathVariable Long id);
}
