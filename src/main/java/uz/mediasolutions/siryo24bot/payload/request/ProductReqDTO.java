package uz.mediasolutions.siryo24bot.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReqDTO {

    private Long id;

    private Long sellerId;

    private String nameUz;

    private String nameRu;

    private Long subcategoryId;

    private List<String> alternativeNames;

    private String country;

    private String manufacturer;

    private Integer price;

    private String imageUrl;

    private List<Long> analogs;

}
