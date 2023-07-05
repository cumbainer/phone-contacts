package ua.shtaiier.phonecontacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.shtaiier.phonecontacts.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountByUsername(String username);

}
