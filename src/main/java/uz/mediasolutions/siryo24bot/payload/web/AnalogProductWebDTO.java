package uz.mediasolutions.siryo24bot.payload.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalogProductWebDTO {

    private Long id;

    private String name;

    private String country;

    private String category;

}
