package uz.mediasolutions.siryo24bot.payload.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryWebDTO {

    private Long id;

    private String name;

}
