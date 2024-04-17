package uz.mediasolutions.siryo24bot.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mediasolutions.siryo24bot.payload.CategoryDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubcategoryReqDTO {

    private Long id;

    private String nameUz;

    private String nameRu;

    private Integer number;

    private Long categoryId;

}
