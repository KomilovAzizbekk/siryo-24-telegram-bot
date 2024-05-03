package uz.mediasolutions.siryo24bot.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Updates;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;
import uz.mediasolutions.siryo24bot.payload.UpdatesDTO;
import uz.mediasolutions.siryo24bot.payload.web.PriceHistoryWebDTO;

import java.util.List;

public interface UpdatesMapper {

    PriceHistoryDTO toDTO(Product product);

    UpdatesDTO toUpdatesDTO(Updates updates);

    List<UpdatesDTO> toUpdatesDTOList(List<Updates> updates);

    List<PriceHistoryWebDTO> toWebDTO(Updates updates);

    Page<PriceHistoryWebDTO> toWebDTOPage(Page<Updates> updates, Pageable pageable);

}
