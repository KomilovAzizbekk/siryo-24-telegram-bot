package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.siryo24bot.entity.Updates;

public interface UpdatesRepository extends JpaRepository<Updates, Long> {

    @Query(value = "SELECT u.*\n" +
            "            FROM updates u\n" +
            "                     join public.products p on p.id = u.product_id\n" +
            "                     join public.sellers s on s.id = u.seller_id\n" +
            "            WHERE (:category IS NULL OR p.category_id = :category)\n" +
            "              AND (:name IS NULL OR p.name = :name)\n" +
            "              AND (:country IS NULL OR p.country = :country)\n" +
            "              AND (:manufacturer IS NULL OR p.manufacturer = :manufacturer)\n" +
            "              AND (:seller IS NULL OR u.seller_id = :seller)\n" +
            "              AND u.updated_time > TO_TIMESTAMP(:from)\n" +
            "              AND u.updated_time < TO_TIMESTAMP(:to)\n" +
            "              AND s.active = true\n" +
            "            ORDER BY u.updated_time DESC", nativeQuery = true)
    Page<Updates> findAllByFilter(
            @Param("category") Long category,
            @Param("name") String name,
            @Param("country") String country,
            @Param("manufacturer") String manufacturer,
            @Param("seller") Long seller,
            @Param("from") Long from,
            @Param("to") Long to,
            Pageable pageable
    );

    @Query(value = "SELECT u.*\n" +
            "FROM updates u\n" +
            "         join public.products p on p.id = u.product_id\n" +
            "         join public.sellers s on s.id = u.seller_id\n" +
            "WHERE p.name ILIKE '%' || :search || '%'\n" +
            "  AND s.organization ILIKE '%' || :search || '%'\n" +
            "  AND s.active = true\n" +
            "ORDER BY u.updated_time DESC", nativeQuery = true)
    Page<Updates> findAllForHistory(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT u.*\n" +
            "FROM updates u\n" +
            "         join public.products p on p.id = u.product_id\n" +
            "         join public.sellers s on s.id = u.seller_id\n" +
            "WHERE p.name ILIKE '%' || :search || '%'\n" +
            "  AND u.updated_time > TO_TIMESTAMP(:from)\n" +
            "  AND u.updated_time < TO_TIMESTAMP(:to)\n" +
            "  AND s.active = true\n" +
            "ORDER BY u.updated_time DESC", nativeQuery = true)
    Page<Updates> findBySearch(
            @Param("search") String search,
            @Param("from") Long from,
            @Param("to") Long to,
            Pageable pageable);

    Updates findTopBySellerIdAndProductIdOrderByUpdatedTimeDesc(Long sellerId, Long productId);

}
