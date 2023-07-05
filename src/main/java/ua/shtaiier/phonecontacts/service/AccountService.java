package ua.shtaiier.phonecontacts.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.shtaiier.phonecontacts.domain.Account;
import ua.shtaiier.phonecontacts.dto.AccountDto;
import ua.shtaiier.phonecontacts.mapper.AccountMapper;
import ua.shtaiier.phonecontacts.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;

    public AccountDto create(AccountDto accountDto) {
        Account account = accountMapper.toDomain(accountDto);
        account.setPassword(encoder.encode(account.getPassword()));
        return accountMapper.toDto(accountRepository.save(account));
    }

    public AccountDto getByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Account with username: " + username + " not found"));
        return accountMapper.toDto(account);
    }

    public AccountDto getById(int id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Account with id: " + id + " not found"));
        return accountMapper.toDto(account);
    }
}
