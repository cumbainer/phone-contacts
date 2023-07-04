package ua.shtaiier.phonecontacts.api.impl;

import com.ua.shtaiier.phonecontacts.dto.ContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.shtaiier.phonecontacts.api.ContactApi;
import ua.shtaiier.phonecontacts.domain.Contact;
import ua.shtaiier.phonecontacts.exception.ContactNotFoundException;
import ua.shtaiier.phonecontacts.mapper.ContactMapper;
import ua.shtaiier.phonecontacts.repository.ContactRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ContactService implements ContactApi {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactDto create(ContactDto contactDto) {
        Contact contact = contactMapper.toDomain(contactDto);
        return contactMapper.toDto(contactRepository.save(contact));
    }

    @Override
    public ContactDto update(int id, ContactDto contactDto) {
        Contact contactFromDb = contactMapper.toDomain(getByIdOrThrow(id));

        contactFromDb.setName(contactDto.getName().get());
        contactFromDb.setEmails(contactDto.getEmails());
        contactFromDb.setPhoneNumbers(contactDto.getPhoneNumbers());

        return contactMapper.toDto(contactRepository.save(contactFromDb));
    }

    @Override
    public void delete(int id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDto> getAll() {
        return contactMapper.toDtos(contactRepository.findAll());
    }

    @Override
    public ContactDto get(int id) {
        return getByIdOrThrow(id);
    }

    private ContactDto getByIdOrThrow(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() ->
                new ContactNotFoundException("Contact with id: " + id + " not found"));

        return contactMapper.toDto(contact);
    }
}
