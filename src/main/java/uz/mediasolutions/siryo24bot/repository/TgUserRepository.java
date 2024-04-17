package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.TgUser;

import java.util.Optional;

public interface TgUserRepository extends JpaRepository<TgUser, Long> {

    TgUser findByChatId(String chatId);

    boolean existsByChatId(String chatId);

    Page<TgUser> findAllByNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseOrderByCreatedAtDesc(String name, String phoneNumber, Pageable pageable);

    Page<TgUser> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
