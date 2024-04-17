package uz.mediasolutions.siryo24bot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.payload.TgUserDTO;

@Mapper(componentModel = "spring")
public interface TgUserMapper {

    TgUserDTO toDTO(TgUser user);

}
