package ua.shtaiier.phonecontacts.mapper;

import org.mapstruct.Mapper;
import ua.shtaiier.phonecontacts.common.BaseMapper;
import ua.shtaiier.phonecontacts.domain.Account;
import ua.shtaiier.phonecontacts.dto.AccountDto;

@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseMapper<Account, AccountDto> {
}
