package uz.mediasolutions.siryo24bot.service.web.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebReqDTO;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebResDTO;

public interface WebApplicationService {

    ApiResult<Page<ApplicationsWebResDTO>> getAll(int page, int size, String userId, boolean mine);

    ApiResult<?> add(ApplicationsWebReqDTO application, String userId);

    ApiResult<?> delete(Long id, String userId);
}
