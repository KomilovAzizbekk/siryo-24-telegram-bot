package uz.mediasolutions.siryo24bot.mapper;

import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Updates;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;
import uz.mediasolutions.siryo24bot.payload.UpdatesDTO;

import java.util.List;

public interface UpdatesMapper {

    PriceHistoryDTO toDTO(Product product);

    UpdatesDTO toUpdatesDTO(Updates updates);

    List<UpdatesDTO> toUpdatesDTOList(List<Updates> updates);

}
