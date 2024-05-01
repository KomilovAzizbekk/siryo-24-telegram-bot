package uz.mediasolutions.siryo24bot.controller.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.mediasolutions.siryo24bot.controller.web.abs.WebApplicationController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebReqDTO;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebResDTO;
import uz.mediasolutions.siryo24bot.service.web.abs.WebApplicationService;

@RestController
@RequiredArgsConstructor
public class WebApplicationControllerImpl implements WebApplicationController {

    private final WebApplicationService webApplicationService;

    @Override
    public ApiResult<Page<ApplicationsWebResDTO>> getAll(int page, int size, String userId, boolean mine) {
        return webApplicationService.getAll(page, size, userId, mine);
    }

    @Override
    public ApiResult<?> add(ApplicationsWebReqDTO application, String userId) throws TelegramApiException {
        return webApplicationService.add(application, userId);
    }

    @Override
    public ApiResult<?> delete(Long id, String userId) {
        return webApplicationService.delete(id, userId);
    }
}
