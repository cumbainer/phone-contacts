package ua.shtaiier.phonecontacts.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.shtaiier.phonecontacts.dto.AccountDto;
import ua.shtaiier.phonecontacts.service.AccountService;

@Service
@RequiredArgsConstructor
public class AccountApiDelegate implements AccountManagementApiDelegate {

    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountDto> createAccount(AccountDto accountDto) {
        return new ResponseEntity<>(accountService.create(accountDto), HttpStatus.CREATED);
    }

}
