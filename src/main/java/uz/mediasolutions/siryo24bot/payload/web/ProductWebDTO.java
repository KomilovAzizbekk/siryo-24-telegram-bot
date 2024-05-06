package uz.mediasolutions.siryo24bot.payload.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mediasolutions.siryo24bot.payload.AnalogProductDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductWebDTO {

    private Long id;

    private String seller;

    private Long sellerId;

    private boolean acceptCash;

    private boolean acceptTransfer;

    private boolean favourite;

    private String name;

    private String country;

    private String manufacturer;

    private Integer price;

    private String status;

    private String priceUpdatedTime;

    private List<AnalogProductWebDTO> analogs;

    private Integer analogsCount;
}
