package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.FavouriteProducts;

public interface FavouriteProductsRepository extends JpaRepository<FavouriteProducts, Long> {

    FavouriteProducts findByProductIdAndSellerId(Long productId, Long sellerId);

}
