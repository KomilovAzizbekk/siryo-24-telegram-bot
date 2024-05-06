package uz.mediasolutions.siryo24bot.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceHistoryDTO {

    private Long id;

    private String product;

    private String seller;

    private UpdatesDTO updates;

}
