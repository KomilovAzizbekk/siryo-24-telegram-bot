package uz.mediasolutions.siryo24bot.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDTO {

    private Long id;

    private String organization;

    private String phoneNumber1;

    private String phoneNumber2;

    private String chatId;

    private String info;

    private boolean active;

    private String channel;

    private boolean acceptCash;

    private boolean acceptTransfer;

}
