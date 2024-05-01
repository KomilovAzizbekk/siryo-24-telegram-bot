package uz.mediasolutions.siryo24bot.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.abs.SellerController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.SellerDTO;
import uz.mediasolutions.siryo24bot.service.abs.SellerService;

@RestController
@RequiredArgsConstructor
public class SellerControllerImpl implements SellerController {

    private final SellerService sellerService;

    @Override
    public ApiResult<Page<SellerDTO>> getAll(int page, int size, String search) {
        return sellerService.getAll(page, size, search);
    }

    @Override
    public ApiResult<Page<SellerDTO>> getAllActive(int page, int size, String search) {
        return sellerService.getAllActive(page, size, search);
    }

    @Override
    public ApiResult<SellerDTO> getById(Long id) {
        return sellerService.getById(id);
    }

    @Override
    public ApiResult<?> add(SellerDTO dto) {
        return sellerService.add(dto);
    }

    @Override
    public ApiResult<?> edit(SellerDTO dto, Long id) {
        return sellerService.edit(dto, id);
    }
}
