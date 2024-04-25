package uz.mediasolutions.siryo24bot.controller.abs;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.SignInDTO;
import uz.mediasolutions.siryo24bot.payload.TokenDTO;
import uz.mediasolutions.siryo24bot.utills.constants.Rest;

import javax.validation.Valid;

@Api(value = "Siryo-24-telegram-bot")
@RequestMapping(AuthController.AUTH)
public interface AuthController {

    String AUTH = Rest.BASE_PATH + "auth/";

    String SIGN_IN = "sign-in";

    @PostMapping(SIGN_IN)
    ApiResult<TokenDTO> signIn(@RequestBody @Valid SignInDTO dto);


}
