package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Page<Seller> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Seller> findAllByOrganizationContainingIgnoreCaseOrderByCreatedAtDesc(String org, Pageable pageable);

}
