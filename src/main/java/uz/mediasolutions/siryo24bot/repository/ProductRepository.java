package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Product> findAllByNameUzContainingIgnoreCaseOrNameRuContainingIgnoreCaseOrderByCreatedAtDesc(String nameUz, String nameRu, Pageable pageable);

}
