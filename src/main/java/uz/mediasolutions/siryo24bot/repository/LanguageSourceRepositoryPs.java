package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.LanguageSourcePs;

import java.util.List;

public interface LanguageSourceRepositoryPs extends JpaRepository<LanguageSourcePs, Long> {
    List<LanguageSourcePs> findAllByLanguagePs_Id(Long id);

    boolean existsByLanguageAndLanguagePsId(String lang, Long langId);
}
