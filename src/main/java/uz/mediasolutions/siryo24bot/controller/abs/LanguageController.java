package uz.mediasolutions.siryo24bot.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.siryo24bot.entity.LanguagePs;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.TranslateDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(LanguageController.LANGUAGE)
public interface LanguageController {

    String LANGUAGE = Rest.BASE_PATH + "lang/";
    String ALL = "all";
    String ALL_BY_LANG = "by-lang";
    String CREATE_EDIT = "create-edit";
    String CREATE_MAIN_KEY = "create-with-key";
    String CREATE_KEY = "create-key";

    @GetMapping(ALL)
    ApiResult<Page<LanguagePs>> getAllPageable(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                               @RequestParam(required = false) String key);

    @GetMapping(ALL_BY_LANG)
    ResponseEntity<Map<String, String>> getAllByLang(@RequestParam(defaultValue = "UZ") String language);

    @PostMapping(CREATE_EDIT)
    ApiResult<?> createTranslation(@RequestBody TranslateDTO dto);

    @PostMapping(CREATE_MAIN_KEY)
    ApiResult<?> createMainKey(@RequestBody List<TranslateDTO> dtos);

    @PostMapping(CREATE_KEY)
    ApiResult<?> createKey(@RequestBody HashMap<String, String> dto);

}
