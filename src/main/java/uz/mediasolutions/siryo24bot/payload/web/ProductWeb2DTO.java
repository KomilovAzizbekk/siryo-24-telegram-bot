package uz.mediasolutions.siryo24bot.payload.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductWeb2DTO {

    private Long id;

    private String name;

    private String sellerPhone1;

    private String sellerPhone2;

    private String imageUrl;

    private String sellerInfo;
}
