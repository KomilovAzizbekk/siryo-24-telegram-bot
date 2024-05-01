package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.UpdatesMapper;
import uz.mediasolutions.siryo24bot.payload.PriceHistoryDTO;
import uz.mediasolutions.siryo24bot.repository.ProductRepository;
import uz.mediasolutions.siryo24bot.service.abs.PriceHistoryService;

@Service
@RequiredArgsConstructor
public class PriceHistoryServiceImpl implements PriceHistoryService {

    private final ProductRepository productRepository;
    private final UpdatesMapper updatesMapper;

    @Override
    public ApiResult<Page<PriceHistoryDTO>> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search != null && !search.isEmpty()) {
            Page<Product> products = productRepository.findAllByNameContainsIgnoreCaseOrSellerOrganizationContainsIgnoreCaseAndUpdatesIsNotEmptyOrderByNameAsc(search, search, pageable);
            Page<PriceHistoryDTO> map = products.map(updatesMapper::toDTO);
            return ApiResult.success(map);
        } else {
            Page<Product> products = productRepository.findAllByUpdatesIsNotEmptyOrderByNameAsc(pageable);
            Page<PriceHistoryDTO> map = products.map(updatesMapper::toDTO);
            return ApiResult.success(map);
        }
    }
}
