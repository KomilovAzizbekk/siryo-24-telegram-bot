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
public class ApplicationsWebResDTO {

    private Long id;

    private List<ProductWeb3DTO> products;

    private String comment;

    private String createdAt;

    private UserWebDTO user;

}
