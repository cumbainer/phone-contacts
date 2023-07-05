package ua.shtaiier.phonecontacts.mapper;


import ua.shtaiier.phonecontacts.dto.ContactDto;
import org.mapstruct.Mapper;
import ua.shtaiier.phonecontacts.common.BaseMapper;
import ua.shtaiier.phonecontacts.domain.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper extends BaseMapper<Contact, ContactDto> {

}
