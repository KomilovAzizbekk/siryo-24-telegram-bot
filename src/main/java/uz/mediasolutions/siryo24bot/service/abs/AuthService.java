package uz.mediasolutions.siryo24bot.service.abs;

import uz.mediasolutions.siryo24bot.entity.User;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.SignInDTO;
import uz.mediasolutions.siryo24bot.payload.TokenDTO;

public interface AuthService {

    ApiResult<TokenDTO> signIn(SignInDTO signInDTO);

    TokenDTO generateToken(User user);

    User checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(String username, String password);


}
