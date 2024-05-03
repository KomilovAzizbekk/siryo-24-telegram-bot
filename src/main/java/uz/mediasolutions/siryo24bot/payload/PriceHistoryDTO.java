package uz.mediasolutions.siryo24bot.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceHistoryDTO {

    private Long id;

    private String product;

    private String seller;

    private List<UpdatesDTO> updates;

}
