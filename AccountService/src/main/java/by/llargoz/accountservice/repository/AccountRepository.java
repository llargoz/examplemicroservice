package by.llargoz.accountservice.repository;

import by.llargoz.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByOrderByCreatedAtDesc();
    List<Account> findAllByCreatedAtGreaterThanEqualOrderByCreatedAt(LocalDateTime createdAt);
}
