package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Language;
import uz.mediasolutions.siryo24bot.enums.LanguageName;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByName(LanguageName languageName);
}
