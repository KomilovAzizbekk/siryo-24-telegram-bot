package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.siryo24bot.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllBySellerActiveIsTrueOrderByCreatedAtDesc(Pageable pageable);

    Page<Product> findAllBySellerActiveIsTrueOrderByNameAsc(Pageable pageable);

    Page<Product> findAllByOrderByNameAsc(Pageable pageable);

    Page<Product> findAllByNameContainsIgnoreCaseOrSellerOrganizationContainsIgnoreCaseOrderByNameAsc(String name, String org, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM products p\n" +
            "         join public.sellers s on s.id = p.seller_id\n" +
            "WHERE (:category IS NULL OR p.category_id = :category)\n" +
            "  AND (:name IS NULL OR p.name = :name)\n" +
            "  AND (:country IS NULL OR p.country = :country)\n" +
            "  AND (:manufacturer IS NULL OR p.manufacturer = :manufacturer)\n" +
            "  AND (:seller IS NULL OR p.seller_id = :seller)\n" +
            "  AND s.active = true", nativeQuery = true)
    Page<Product> findAllByCategoryAndNameAndCountryAndManufacturerAndSeller(
            @Param("category") Long category,
            @Param("name") String name,
            @Param("country") String country,
            @Param("manufacturer") String manufacturer,
            @Param("seller") Long seller,
            Pageable pageable
    );

    List<Product> findAllByCategoryIdAndSellerActiveIsTrue(Long categoryId);

    Page<Product> findAllByNameContainingIgnoreCaseAndSellerActiveIsTrueOrderByNameAsc(String search, Pageable pageable);

}
