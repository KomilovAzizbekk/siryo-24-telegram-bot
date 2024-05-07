package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.SellerMapper;
import uz.mediasolutions.siryo24bot.payload.request.SellerReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SellerResDTO;
import uz.mediasolutions.siryo24bot.repository.ProductRepository;
import uz.mediasolutions.siryo24bot.repository.SellerRepository;
import uz.mediasolutions.siryo24bot.service.abs.SellerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Override
    public ApiResult<Page<SellerResDTO>> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.isEmpty()) {
            Page<Seller> sellers = sellerRepository.findAllByOrderByCreatedAtDesc(pageable);
            Page<SellerResDTO> map = sellers.map(sellerMapper::toDTO);
            return ApiResult.success(map);
        } else {
            Page<Seller> sellers = sellerRepository.findAllByOrganizationContainingIgnoreCaseOrderByCreatedAtDesc(search, pageable);
            Page<SellerResDTO> map = sellers.map(sellerMapper::toDTO);
            return ApiResult.success(map);
        }
    }

    @Override
    public ApiResult<Page<SellerResDTO>> getAllActive(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.isEmpty()) {
            Page<Seller> sellers = sellerRepository.findAllByActiveIsTrueOrderByCreatedAtDesc(pageable);
            Page<SellerResDTO> map = sellers.map(sellerMapper::toDTO);
            return ApiResult.success(map);
        } else {
            Page<Seller> sellers = sellerRepository.findAllByOrganizationContainingIgnoreCaseAndActiveIsTrueOrderByCreatedAtDesc(search, pageable);
            Page<SellerResDTO> map = sellers.map(sellerMapper::toDTO);
            return ApiResult.success(map);
        }
    }

    @Override
    public ApiResult<SellerResDTO> getById(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));
        SellerResDTO dto = sellerMapper.toDTO(seller);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(SellerReqDTO dto) {
        Seller seller = sellerMapper.toEntity(dto);
        sellerRepository.save(seller);
        return ApiResult.success("Saved successfully");
    }

    @Override
    public ApiResult<?> edit(SellerReqDTO dto, Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));

        List<Product> products = productRepository.findAllById(dto.getProducts());

        seller.setProducts(dto.getProducts() != null ? products : null);
        seller.setOrganization(dto.getOrganization());
        seller.setPhoneNumber1(dto.getPhoneNumber1());
        seller.setPhoneNumber2(dto.getPhoneNumber2());
        seller.setInfo(dto.getInfo());
        seller.setChatId(dto.getChatId());
        seller.setActive(dto.isActive());
        seller.setChannel(dto.getChannel());
        seller.setStockMarket(dto.isStockMarket());
        seller.setAcceptCash(dto.isAcceptCash());
        seller.setAcceptTransfer(dto.isAcceptTransfer());
        sellerRepository.save(seller);
        return ApiResult.success("Edited successfully");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));
        List<Product> products = seller.getProducts();
        products.clear();
        try {
            sellerRepository.deleteById(id);
            return ApiResult.success("Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Delete failed", HttpStatus.BAD_REQUEST);
        }
    }
}
