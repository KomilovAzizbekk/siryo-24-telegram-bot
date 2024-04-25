package uz.mediasolutions.siryo24bot.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.siryo24bot.controller.abs.ProductController;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.payload.request.ProductReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.ProductResDTO;
import uz.mediasolutions.siryo24bot.service.abs.ProductService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public ApiResult<Page<ProductResDTO>> getAll(int page, int size, String search) {
        return productService.getAll(page, size, search);
    }

    @Override
    public ApiResult<ProductResDTO> getById(Long id) {
        return productService.getById(id);
    }

    @Override
    public ApiResult<?> add(ProductReqDTO dto) {
        return productService.add(dto);
    }

    @Override
    public ApiResult<?> edit(ProductReqDTO dto, Long id) throws IOException {
        return productService.edit(dto, id);
    }

    @Override
    public ApiResult<?> delete(Long id) throws IOException {
        return productService.delete(id);
    }
}
