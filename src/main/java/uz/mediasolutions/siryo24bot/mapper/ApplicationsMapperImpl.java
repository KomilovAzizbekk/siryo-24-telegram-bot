package uz.mediasolutions.siryo24bot.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.mediasolutions.siryo24bot.entity.Applications;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebResDTO;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ApplicationsMapperImpl implements ApplicationsMapper {

    private final TgUserMapper tgUserMapper;
    private final ProductMapper productMapper;

    @Override
    public ApplicationsWebResDTO toDTO(Applications applications) {
        if (applications == null) {
            return null;
        }

        return ApplicationsWebResDTO.builder()
                .id(applications.getId())
                .user(tgUserMapper.toWebDTO(applications.getUser()))
                .comment(applications.getComment())
                .products(productMapper.toProductWeb3DTOList(applications.getProducts()))
                .createdAt(applications.getCreatedAt().toLocalDateTime().format(
                        DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")))
                .build();
    }
}
