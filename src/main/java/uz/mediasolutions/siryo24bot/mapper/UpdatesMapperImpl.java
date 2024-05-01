package uz.mediasolutions.siryo24bot.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Updates;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;
import uz.mediasolutions.siryo24bot.payload.UpdatesDTO;

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
                .status(product.getStatus() != null ? product.getStatus().getName().name() : null)
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
                .updatedTime(updates.getUpdatedTime())
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
}
