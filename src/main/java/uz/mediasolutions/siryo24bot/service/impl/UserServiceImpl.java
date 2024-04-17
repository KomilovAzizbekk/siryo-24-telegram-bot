package uz.mediasolutions.siryo24bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.TgUser;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.TgUserMapper;
import uz.mediasolutions.siryo24bot.payload.TgUserDTO;
import uz.mediasolutions.siryo24bot.repository.TgUserRepository;
import uz.mediasolutions.siryo24bot.service.abs.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TgUserRepository tgUserRepository;
    private final TgUserMapper tgUserMapper;

    @Override
    public ApiResult<Page<TgUserDTO>> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.isEmpty()) {
            Page<TgUser> tgUsers = tgUserRepository.findAllByOrderByCreatedAtDesc(pageable);
            Page<TgUserDTO> map = tgUsers.map(tgUserMapper::toDTO);
            return ApiResult.success(map);
        } else {
            Page<TgUser> tgUsers = tgUserRepository.findAllByNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseOrderByCreatedAtDesc(search, search, pageable);
            Page<TgUserDTO> map = tgUsers.map(tgUserMapper::toDTO);
            return ApiResult.success(map);
        }
    }

    @Override
    public ApiResult<TgUserDTO> getById(Long id) {
        TgUser tgUser = tgUserRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("ID NOT FOUND", HttpStatus.BAD_REQUEST));
        TgUserDTO dto = tgUserMapper.toDTO(tgUser);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> banUser(Long id) {
        TgUser tgUser = tgUserRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("ID NOT FOUND", HttpStatus.BAD_REQUEST));
        tgUser.setBanned(true);
        tgUserRepository.save(tgUser);
        return ApiResult.success("BANNED");
    }

    @Override
    public ApiResult<?> unbanUser(Long id) {
        TgUser tgUser = tgUserRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("ID NOT FOUND", HttpStatus.BAD_REQUEST));
        tgUser.setBanned(false);
        tgUserRepository.save(tgUser);
        return ApiResult.success("UNBANNED");
    }
}
