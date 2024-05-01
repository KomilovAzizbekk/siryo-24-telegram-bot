package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.PriceStatus;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.PriceStatusDTO;
import uz.mediasolutions.siryo24bot.repository.PriceStatusRepository;
import uz.mediasolutions.siryo24bot.service.abs.PriceStatusService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceStatusServiceImpl implements PriceStatusService {

    private final PriceStatusRepository priceStatusRepository;

    @Override
    public ApiResult<List<PriceStatusDTO>> getAll() {
        List<PriceStatus> statusList = priceStatusRepository.findAll();
        List<PriceStatusDTO> dtoList = new ArrayList<>();
        for (PriceStatus status : statusList) {
            PriceStatusDTO statusDTO = PriceStatusDTO.builder()
                    .id(status.getId())
                    .name(status.getName().name())
                    .build();
            dtoList.add(statusDTO);
        }
        return ApiResult.success(dtoList);
    }
}
