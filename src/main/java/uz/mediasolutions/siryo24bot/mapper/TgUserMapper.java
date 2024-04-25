package uz.mediasolutions.siryo24bot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.payload.TgUserDTO;
import uz.mediasolutions.siryo24bot.payload.web.UserWebDTO;

@Mapper(componentModel = "spring")
public interface TgUserMapper {

    @Mapping(target = "role", source = "role.name")
    TgUserDTO toDTO(TgUser user);

    UserWebDTO toWebDTO(TgUser user);

}
