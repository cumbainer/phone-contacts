package ua.shtaiier.phonecontacts.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.shtaiier.phonecontacts.common.BaseMapper;
import ua.shtaiier.phonecontacts.domain.Contact;
import ua.shtaiier.phonecontacts.dto.ContactDto;

@Mapper(componentModel = "spring")
public interface ContactMapper extends BaseMapper<Contact, ContactDto> {

    @Mapping(target = "image", source = "imageDto")
    Contact toDomain(ContactDto dto);

    @Mapping(target = "imageDto", source = "image")
    @Mapping(target = "accountId", source = "account.id")
    ContactDto toDto(Contact entity);

}
