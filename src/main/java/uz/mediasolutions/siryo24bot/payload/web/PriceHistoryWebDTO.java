package uz.mediasolutions.siryo24bot.payload.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mediasolutions.siryo24bot.payload.UpdatesDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceHistoryWebDTO {

    private Long id;

    private String product;

    private String seller;

    private String country;

    private String manufacturer;

    private String price;

    private String updatedTime;

}
