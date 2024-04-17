package uz.mediasolutions.siryo24bot.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlternativeDTO {

    private Long id;

    private String name;

}
