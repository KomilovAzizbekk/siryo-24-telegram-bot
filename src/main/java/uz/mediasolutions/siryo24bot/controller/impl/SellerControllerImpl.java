package uz.mediasolutions.siryo24bot.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.abs.SellerController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.request.SellerReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SellerResDTO;
import uz.mediasolutions.siryo24bot.service.abs.SellerService;

@RestController
@RequiredArgsConstructor
public class SellerControllerImpl implements SellerController {

    private final SellerService sellerService;

    @Override
    public ApiResult<Page<SellerResDTO>> getAll(int page, int size, String search) {
        return sellerService.getAll(page, size, search);
    }

    @Override
    public ApiResult<Page<SellerResDTO>> getAllActive(int page, int size, String search) {
        return sellerService.getAllActive(page, size, search);
    }

    @Override
    public ApiResult<SellerResDTO> getById(Long id) {
        return sellerService.getById(id);
    }

    @Override
    public ApiResult<?> add(SellerReqDTO dto) {
        return sellerService.add(dto);
    }

    @Override
    public ApiResult<?> edit(SellerReqDTO dto, Long id) {
        return sellerService.edit(dto, id);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return sellerService.delete(id);
    }
}
