package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Alternative;

public interface AlternativeRepository extends JpaRepository<Alternative, Long> {

    boolean existsByName(String name);

    Alternative findByName(String name);

}
