package uz.mediasolutions.siryo24bot.controller.web.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb2DTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWebDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(WebProductController.PRODUCT_WEB)
public interface WebProductController {

    String PRODUCT_WEB = Rest.BASE_PATH + "product-web/";
    String GET_ALL = "get-all";
    String GET_BY_ID = "get-by-id/{id}";
    String ADD_TO_FAVORITE = "add-to-favorite/{id}";

    @GetMapping(GET_ALL)
    ApiResult<Page<ProductWebDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                          @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String category,
                                          @RequestParam(required = false) String country,
                                          @RequestParam(required = false) String manufacturer,
                                          @RequestParam(required = false) String seller);

    @GetMapping(GET_BY_ID)
    ApiResult<ProductWeb2DTO> getById(@PathVariable Long id);

    @PostMapping(ADD_TO_FAVORITE)
    ApiResult<ProductWebDTO> addToFavorite(@PathVariable Long id);
}
