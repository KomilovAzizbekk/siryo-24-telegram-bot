package uz.mediasolutions.siryo24bot.service.web.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.siryo24bot.entity.Applications;
import uz.mediasolutions.siryo24bot.entity.Product;
import uz.mediasolutions.siryo24bot.exceptions.RestException;
import uz.mediasolutions.siryo24bot.manual.ApiResult;
import uz.mediasolutions.siryo24bot.mapper.ApplicationsMapper;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebReqDTO;
import uz.mediasolutions.siryo24bot.payload.web.ApplicationsWebResDTO;
import uz.mediasolutions.siryo24bot.payload.web.ProductWeb3DTO;
import uz.mediasolutions.siryo24bot.repository.ApplicationsRepository;
import uz.mediasolutions.siryo24bot.repository.ProductRepository;
import uz.mediasolutions.siryo24bot.repository.TgUserRepository;
import uz.mediasolutions.siryo24bot.service.web.abs.WebApplicationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebApplicationServiceImpl implements WebApplicationService {

    private final ApplicationsRepository applicationsRepository;
    private final ApplicationsMapper applicationsMapper;
    private final TgUserRepository tgUserRepository;
    private final ProductRepository productRepository;

    @Override
    public ApiResult<Page<ApplicationsWebResDTO>> getAll(int page, int size, String userId, boolean mine) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Applications> all = applicationsRepository.findAllByUserChatIdIsNotOrderByCreatedAtDesc(userId, pageable);
        Page<Applications> myApp = applicationsRepository.findAllByUserChatIdOrderByCreatedAtDesc(userId, pageable);

        Page<ApplicationsWebResDTO> map;
        if (mine) {
            map = myApp.map(applicationsMapper::toDTO);
        } else {
            map = all.map(applicationsMapper::toDTO);
        }
        return ApiResult.success(map);
    }

    @Override
    public ApiResult<?> add(ApplicationsWebReqDTO application, String userId) {

        List<Product> products = new ArrayList<>();
        for (ProductWeb3DTO product : application.getProducts()) {
            Optional<Product> byId = productRepository.findById(product.getId());
            byId.ifPresent(products::add);
        }

        Applications applications = Applications.builder()
                .user(tgUserRepository.findByChatId(userId))
                .comment(application.getComment())
                .products(products)
                .build();
        applicationsRepository.save(applications);
        return ApiResult.success("Saved successfully");
    }

    @Override
    public ApiResult<?> delete(Long id, String userId) {
        Applications app = applicationsRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Application not found", HttpStatus.BAD_REQUEST));
        if (app.getUser().getChatId().equals(userId)) {
            try {
                applicationsRepository.deleteById(id);
                return ApiResult.success("Deleted successfully");
            } catch (Exception e) {
                throw RestException.restThrow("Delete failed", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw RestException.restThrow("You are trying to delete others' app", HttpStatus.BAD_REQUEST);
        }
    }
}
