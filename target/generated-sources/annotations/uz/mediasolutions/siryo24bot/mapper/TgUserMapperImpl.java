package uz.mediasolutions.siryo24bot.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.payload.TgUserDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-17T11:25:14+0500",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class TgUserMapperImpl implements TgUserMapper {

    @Override
    public TgUserDTO toDTO(TgUser user) {
        if ( user == null ) {
            return null;
        }

        TgUserDTO.TgUserDTOBuilder tgUserDTO = TgUserDTO.builder();

        tgUserDTO.id( user.getId() );
        tgUserDTO.chatId( user.getChatId() );
        tgUserDTO.name( user.getName() );
        tgUserDTO.phoneNumber( user.getPhoneNumber() );
        tgUserDTO.banned( user.isBanned() );

        return tgUserDTO.build();
    }
}
