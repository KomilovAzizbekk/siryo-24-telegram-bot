package uz.mediasolutions.siryo24bot.controller.web.abs;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebReqDTO;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebResDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

@RequestMapping(WebApplicationController.APPLICATION_WEB)
public interface WebApplicationController {

    String APPLICATION_WEB = Rest.BASE_PATH + "application-web/";
    String GET_ALL = "get-all/{userId}";
    String ADD = "add/{userId}";
    String DELETE = "delete/{id}/{userId}";

    @GetMapping(GET_ALL)
    ApiResult<Page<ApplicationsWebResDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                                  @PathVariable String userId,
                                                  @RequestParam(defaultValue = "false") boolean mine);

    @PostMapping(ADD)
    ApiResult<?> add(@RequestBody ApplicationsWebReqDTO application,
                     @PathVariable String userId) throws TelegramApiException;

    @DeleteMapping(DELETE)
    ApiResult<?> delete(@PathVariable Long id,
                        @PathVariable String userId);
}
