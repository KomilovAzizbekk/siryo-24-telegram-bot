package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.PriceStatus;
import uz.mediasolutions.siryo24bot.enums.PriceStatusName;

public interface PriceStatusRepository extends JpaRepository<PriceStatus, Long> {

    PriceStatus findByName(PriceStatusName name);

}
