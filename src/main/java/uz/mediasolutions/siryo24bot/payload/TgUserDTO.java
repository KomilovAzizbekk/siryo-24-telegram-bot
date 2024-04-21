package uz.mediasolutions.siryo24bot.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TgUserDTO {

    private Long id;

    private String chatId;

    private String name;

    private String username;

    private String phoneNumber;

    private String role;

    private boolean banned;

}
