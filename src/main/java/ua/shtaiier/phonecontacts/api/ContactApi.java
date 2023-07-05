package ua.shtaiier.phonecontacts.api;

import org.springframework.data.domain.Page;
import ua.shtaiier.phonecontacts.dto.ContactDto;

public interface ContactApi {

    ContactDto create(ContactDto contactDto);

    ContactDto update(int id, ContactDto contactDto);

    void delete(int id);

    Page<ContactDto> getAll(int page, int size);

    ContactDto get(int id);

}
