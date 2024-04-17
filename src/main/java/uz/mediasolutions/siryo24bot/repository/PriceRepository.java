package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {

}
