package uz.mediasolutions.siryo24bot.mapper;

import uz.mediasolutions.siryo24bot.entity.Applications;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebResDTO;

public interface ApplicationsMapper {

    ApplicationsWebResDTO toDTO(Applications applications);

}
