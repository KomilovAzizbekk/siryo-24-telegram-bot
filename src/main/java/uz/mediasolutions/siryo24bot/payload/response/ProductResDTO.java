package uz.mediasolutions.siryo24bot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mediasolutions.siryo24bot.payload.AlternativeDTO;
import uz.mediasolutions.siryo24bot.payload.AnalogProductDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResDTO {

    private Long id;

    private String name;

    private String category;

    private Long categoryId;

    private List<AlternativeDTO> alternativeNames;

    private String country;

    private String manufacturer;

    private String imageUrl;

    private String priceUpdatedTime;

    private List<AnalogProductDTO> analogs;
}
