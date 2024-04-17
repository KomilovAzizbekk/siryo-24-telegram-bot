package uz.mediasolutions.siryo24bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.siryo24bot.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByIdAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(UUID userId);

    Optional<Object> findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(String username);

    boolean existsByUsername(String username);

}
