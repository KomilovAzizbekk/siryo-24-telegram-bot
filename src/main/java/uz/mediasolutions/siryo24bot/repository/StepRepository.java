package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Step;
import uz.mediasolutions.siryo24bot.enums.StepName;

public interface StepRepository extends JpaRepository<Step, Long> {

    Step findByName(StepName stepName);

}
