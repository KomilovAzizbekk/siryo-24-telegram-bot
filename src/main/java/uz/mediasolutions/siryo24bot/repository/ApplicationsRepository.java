package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Applications;

public interface ApplicationsRepository extends JpaRepository<Applications, Long> {

    Page<Applications> findAllByUserChatIdIsNotOrderByCreatedAtDesc(String chatId, Pageable pageable);

    Page<Applications> findAllByUserChatIdOrderByCreatedAtDesc(String chatId, Pageable pageable);

}
