package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.siryo24bot.entity.Seller;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Page<Seller> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Seller> findAllByOrganizationContainingIgnoreCaseOrderByCreatedAtDesc(String org, Pageable pageable);

    Page<Seller> findAllByActiveIsTrueOrderByCreatedAtDesc(Pageable pageable);

    Page<Seller> findAllByOrganizationContainingIgnoreCaseAndActiveIsTrueOrderByCreatedAtDesc(String org, Pageable pageable);

    List<Seller> findAllByActiveIsTrue();

    @Query(value = "SELECT s.*\n" +
            "FROM sellers s\n" +
            "         join public.sellers_products sp on s.id = sp.seller_id\n" +
            "         join public.products p on p.id = sp.products_id\n" +
            "WHERE p.id = :id\n" +
            "  AND s.active = true", nativeQuery = true)
    Seller findAllByProductId(@Param("id") Long productId);

}
