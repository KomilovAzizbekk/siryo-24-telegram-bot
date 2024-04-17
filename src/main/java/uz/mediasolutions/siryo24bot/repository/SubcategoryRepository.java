package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Category;
import uz.mediasolutions.siryo24bot.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Page<Subcategory> findAllByOrderByNumberAsc(Pageable pageable);

    Page<Subcategory> findAllByNameUzContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrderByNumberAsc(String nameUz, String nameRu, Pageable pageable);

}
