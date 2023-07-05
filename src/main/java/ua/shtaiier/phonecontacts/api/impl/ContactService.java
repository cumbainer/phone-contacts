package ua.shtaiier.phonecontacts.api.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.shtaiier.phonecontacts.api.ContactApi;
import ua.shtaiier.phonecontacts.domain.Contact;
import ua.shtaiier.phonecontacts.dto.ContactDto;
import ua.shtaiier.phonecontacts.exception.ContactNotFoundException;
import ua.shtaiier.phonecontacts.mapper.ContactMapper;
import ua.shtaiier.phonecontacts.repository.ContactRepository;


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

        contactFromDb.setName(contactDto.getName());
        contactFromDb.setEmails(contactDto.getEmails());
        contactFromDb.setPhoneNumbers(contactDto.getPhoneNumbers());

        return contactMapper.toDto(contactRepository.save(contactFromDb));
    }

    @Override
    public void delete(int id) {
        contactRepository.deleteById(id);
    }

    @Override
    public Page<ContactDto> getAll(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Contact> contacts = contactRepository.findAll(paging);
        return new PageImpl<>(contactMapper.toDtos(contacts.getContent()), paging, contacts.getTotalElements());
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

//    @EventListener(ApplicationReadyEvent.class)
//    public void createData(){
//        ContactDto contactDto = new ContactDto();
//        contactDto.setName("testName");
//        contactDto.setEmails(List.of("a@gmail.com", "b@gmail.com"));
//        contactDto.setPhoneNumbers(List.of("+3434243255", "+3803455423"));
//        create(contactDto);
//        ContactDto seocond = new ContactDto();
//        seocond.setName("testName2");
//        seocond.setEmails(List.of("a2@gmail.com", "2b@gmail.com"));
//        seocond.setPhoneNumbers(List.of("+34342432543215", "+38431203455423"));
//        create(seocond);
//    }
}
