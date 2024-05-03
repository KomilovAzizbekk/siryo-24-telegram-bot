package uz.mediasolutions.siryo24bot.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Updates;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;
import uz.mediasolutions.siryo24bot.payload.UpdatesDTO;
import uz.mediasolutions.siryo24bot.payload.web.PriceHistoryWebDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdatesMapperImpl implements UpdatesMapper {

    @Override
    public PriceHistoryDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        return PriceHistoryDTO.builder()
                .id(product.getId())
                .seller(product.getSeller().getOrganization())
                .product(product.getName())
                .updates(toUpdatesDTOList(product.getUpdates()))
                .build();
    }

    @Override
    public UpdatesDTO toUpdatesDTO(Updates updates) {
        if (updates == null) {
            return null;
        }
        return UpdatesDTO.builder()
                .id(updates.getId())
                .status(updates.getStatus().getName().name())
                .updatedTime(updates.getUpdatedTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")))
                .price(updates.getPrice())
                .build();
    }

    @Override
    public List<UpdatesDTO> toUpdatesDTOList(List<Updates> updates) {
        if (updates == null) {
            return null;
        }
        List<UpdatesDTO> updatesDTOList = new ArrayList<>();
        for (Updates update : updates) {
            updatesDTOList.add(toUpdatesDTO(update));
        }
        return updatesDTOList;
    }

    @Override
    public List<PriceHistoryWebDTO> toWebDTO(Updates updates) {
        if (updates == null) {
            return null;
        }

        List<PriceHistoryWebDTO> priceHistoryWebDTOList = new ArrayList<>();
        PriceHistoryWebDTO priceHistoryWebDTO = PriceHistoryWebDTO.builder()
                .id(updates.getId())
                .country(updates.getProduct().getCountry())
                .price(updates.getPrice())
                .manufacturer(updates.getProduct().getManufacturer())
                .seller(updates.getSeller().getOrganization())
                .updatedTime(updates.getUpdatedTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")))
                .status(updates.getStatus().getName().name())
                .product(updates.getProduct().getName())
                .build();
        priceHistoryWebDTOList.add(priceHistoryWebDTO);
        return priceHistoryWebDTOList;
    }

    @Override
    public Page<PriceHistoryWebDTO> toWebDTOPage(Page<Updates> updates, Pageable pageable) {
        if (updates == null) {
            return null;
        }
        List<PriceHistoryWebDTO> priceHistoryWebDTOList = new ArrayList<>();
        for (Updates update : updates) {
            priceHistoryWebDTOList.addAll(toWebDTO(update));
        }
        return new PageImpl<>(priceHistoryWebDTOList, pageable, updates.getTotalPages());
    }
}
