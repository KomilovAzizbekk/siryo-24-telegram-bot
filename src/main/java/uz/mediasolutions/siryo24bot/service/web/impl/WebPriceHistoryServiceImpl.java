package uz.mediasolutions.siryo24bot.service.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Updates;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.UpdatesMapper;
import uz.mediasolutions.siryo24bot.payload.web.PriceHistoryWebDTO;
import uz.mediasolutions.siryo24bot.repository.UpdatesRepository;
import uz.mediasolutions.siryo24bot.service.web.abs.WebPriceHistoryService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class WebPriceHistoryServiceImpl implements WebPriceHistoryService {

    private final UpdatesRepository updatesRepository;
    private final UpdatesMapper updatesMapper;

    @Override
    public ApiResult<Page<PriceHistoryWebDTO>> getAll(int page, int size, String search, Long category, String name, String country, String manufacturer, Long seller, Long from, Long to) {
        Pageable pageable = PageRequest.of(page, size);
        if (from == null) {
            LocalDate today = LocalDate.now();
            LocalDateTime firstSecondOfDay = LocalDateTime.of(today, LocalTime.MIN);
            from = firstSecondOfDay.toEpochSecond(ZoneOffset.UTC);
        }

        if (to == null) {
            LocalDate today = LocalDate.now();
            LocalDateTime firstSecondOfDay = LocalDateTime.of(today, LocalTime.MAX);
            to = firstSecondOfDay.toEpochSecond(ZoneOffset.UTC);
        }

        if (from > to) {
            throw new RestException("From cannot be higher than to", HttpStatus.BAD_REQUEST);
        }

        if (category != null || name != null || country != null ||
                manufacturer != null || seller != null) {
            Page<Updates> updates = updatesRepository.findAllByFilter(category, name, country, manufacturer, seller, from, to, pageable);
            Page<PriceHistoryWebDTO> webDTOPage = updatesMapper.toWebDTOPage(updates, pageable);
            return ApiResult.success(webDTOPage);
        } else if (search != null && !search.isEmpty()) {
            Page<Updates> updates = updatesRepository.findBySearch(search, from, to, pageable);
            Page<PriceHistoryWebDTO> webDTOPage = updatesMapper.toWebDTOPage(updates, pageable);
            return ApiResult.success(webDTOPage);
        } else {
            return ApiResult.success(new PageImpl<>(new ArrayList<>(), pageable, 0));
        }
    }
}
