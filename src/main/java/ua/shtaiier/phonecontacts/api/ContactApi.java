package ua.shtaiier.phonecontacts.api;

import com.ua.shtaiier.phonecontacts.dto.ContactDto;

import java.util.List;

public interface ContactApi {

    ContactDto create(ContactDto contactDto);

    ContactDto update(int id, ContactDto contactDto);

    void delete(int id);

    List<ContactDto> getAll();

    ContactDto get(int id);
}
