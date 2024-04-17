package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Category;
import uz.mediasolutions.siryo24bot.entity.Step;
import uz.mediasolutions.siryo24bot.enums.StepName;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByOrderByNumberAsc(Pageable pageable);

    Page<Category> findAllByNameUzContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrderByNumberAsc(String nameUz, String nameRu, Pageable pageable);

}
