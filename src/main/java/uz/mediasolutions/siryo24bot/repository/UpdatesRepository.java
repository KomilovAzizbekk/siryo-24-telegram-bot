package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.Updates;

public interface UpdatesRepository extends JpaRepository<Updates, Long> {

    Page<Updates> findAllByProductNameContainsIgnoreCaseOrSellerOrganizationContainsIgnoreCaseOrderByProductName(String productName, String sellerName, Pageable pageable);

    Page<Updates> findAllByOrderByProductNameAsc(Pageable pageable);

}
