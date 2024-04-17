package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Seller;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.SellerMapper;
import uz.mediasolutions.siryo24bot.payload.SellerDTO;
import uz.mediasolutions.siryo24bot.repository.SellerRepository;
import uz.mediasolutions.siryo24bot.service.abs.SellerService;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Override
    public ApiResult<Page<SellerDTO>> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.isEmpty()) {
            Page<Seller> sellers = sellerRepository.findAllByOrderByCreatedAtDesc(pageable);
            Page<SellerDTO> map = sellers.map(sellerMapper::toDTO);
            return ApiResult.success(map);
        } else {
            Page<Seller> sellers = sellerRepository.findAllByOrganizationContainingIgnoreCaseOrderByCreatedAtDesc(search, pageable);
            Page<SellerDTO> map = sellers.map(sellerMapper::toDTO);
            return ApiResult.success(map);
        }
    }

    @Override
    public ApiResult<SellerDTO> getById(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));
        SellerDTO dto = sellerMapper.toDTO(seller);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(SellerDTO dto) {
        Seller seller = sellerMapper.toEntity(dto);
        sellerRepository.save(seller);
        return ApiResult.success("Saved successfully");
    }

    @Override
    public ApiResult<?> edit(SellerDTO dto, Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Seller not found", HttpStatus.BAD_REQUEST));

        seller.setOrganization(dto.getOrganization());
        seller.setPhoneNumber1(dto.getPhoneNumber1());
        seller.setPhoneNumber2(dto.getPhoneNumber2());
        seller.setInfo(dto.getInfo());
        seller.setActive(dto.isActive());
        seller.setChannel(dto.getChannel());
        seller.setAcceptCash(dto.isAcceptCash());
        seller.setAcceptTransfer(dto.isAcceptTransfer());
        sellerRepository.save(seller);
        return ApiResult.success("Edited successfully");
    }
}
