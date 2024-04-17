package uz.mediasolutions.siryo24bot.service.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.mediasolutions.siryo24bot.entity.LanguagePs;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.TranslateDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LanguageServicePs {

    ApiResult<Page<LanguagePs>> getAllPaginated(int page, int size, String key);

    ResponseEntity<Map<String, String>> getAllByLanguage(String language);

    ApiResult<?> createTranslation(TranslateDTO dto);

    ApiResult<?> createMainText(List<TranslateDTO> dtos);

    ApiResult<?> createKey(HashMap<String, String> dto);
}
