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
public class ApplicationsWebReqDTO {

    private Long id;

    private List<Long> products;

    private String comment;

}
