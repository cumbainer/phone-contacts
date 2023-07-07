import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.shtaiier.phonecontacts.domain.Account;
import ua.shtaiier.phonecontacts.repository.AccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AccountRepositoryTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountRepositoryTest accountRepositoryTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAccountByUsername() {
        // Arrange
        String username = "testUser";
        Account account = new Account();
        account.setUsername(username);
        Optional<Account> expectedAccount = Optional.of(account);

        when(accountRepository.findAccountByUsername(username)).thenReturn(expectedAccount);

        // Act
        Optional<Account> actualAccount = accountRepositoryTest.findAccountByUsername(username);

        // Assert
        assertEquals(expectedAccount, actualAccount);
    }

    private static class AccountRepositoryTest {
        private AccountRepository accountRepository;

        public AccountRepositoryTest(AccountRepository accountRepository) {
            this.accountRepository = accountRepository;
        }

        public Optional<Account> findAccountByUsername(String username) {
            return accountRepository.findAccountByUsername(username);
        }
    }
}
