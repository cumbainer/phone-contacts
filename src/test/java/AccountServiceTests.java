import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.shtaiier.phonecontacts.domain.Account;
import ua.shtaiier.phonecontacts.dto.AccountDto;
import ua.shtaiier.phonecontacts.mapper.AccountMapper;
import ua.shtaiier.phonecontacts.repository.AccountRepository;
import ua.shtaiier.phonecontacts.service.AccountService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        // Arrange
        AccountDto accountDto = new AccountDto();
        Account account = new Account();

        when(accountMapper.toDomain(accountDto)).thenReturn(account);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        // Act
        AccountDto createdAccountDto = accountService.create(accountDto);

        // Assert
        assertNotNull(createdAccountDto);
        verify(accountMapper, times(1)).toDomain(accountDto);
        verify(passwordEncoder, times(1)).encode(any());
        verify(accountRepository, times(1)).save(account);
        verify(accountMapper, times(1)).toDto(account);
    }
    @Test
    public void testGetByUsername_AccountExists() {
        // Arrange
        String username = "testUser";
        Account account = new Account();
        when(accountRepository.findAccountByUsername(username)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(new AccountDto());

        // Act
        AccountDto accountDto = accountService.getByUsername(username);

        // Assert
        assertNotNull(accountDto);
        verify(accountRepository, times(1)).findAccountByUsername(username);
        verify(accountMapper, times(1)).toDto(account);
    }

}