package uz.mediasolutions.siryo24bot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb3DTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerResDTO {

    private Long id;

    private String organization;

    private String phoneNumber1;

    private String phoneNumber2;

    private String chatId;

    private String info;

    private boolean active;

    private List<ProductWeb3DTO> products;

    private String channel;

    private boolean acceptCash;

    private boolean acceptTransfer;

    private boolean stockMarket;

}
