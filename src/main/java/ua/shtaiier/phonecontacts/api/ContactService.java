package ua.shtaiier.phonecontacts.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.shtaiier.phonecontacts.domain.Contact;
import ua.shtaiier.phonecontacts.domain.Image;
import ua.shtaiier.phonecontacts.dto.ContactDto;
import ua.shtaiier.phonecontacts.exception.ContactNotFoundException;
import ua.shtaiier.phonecontacts.mapper.ContactMapper;
import ua.shtaiier.phonecontacts.repository.ContactRepository;


@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactDto create(ContactDto contactDto, MultipartFile image) {
        Contact contact = contactMapper.toDomain(contactDto);

        if (StringUtils.isNotEmpty(image.getOriginalFilename())) {
            Image imageEntity = convertImage(image);
            contact.setImage(imageEntity);
        }

        return contactMapper.toDto(contactRepository.save(contact));
    }

    public ContactDto update(int id, ContactDto contactDto, MultipartFile image) {
        Contact contactFromDb = contactMapper.toDomain(getByIdOrThrow(id));

        contactFromDb.setName(contactDto.getName());
        contactFromDb.setEmails(contactDto.getEmails());
        contactFromDb.setPhoneNumbers(contactDto.getPhoneNumbers());
        if (StringUtils.isNotEmpty(image.getOriginalFilename())) {
            contactFromDb.setImage(convertImage(image));
        }

        return contactMapper.toDto(contactRepository.save(contactFromDb));
    }

    public void delete(int id) {
        if(!contactRepository.existsById(id)){
            throw new ContactNotFoundException("Contact with id: " + id + " not found");
        }
        contactRepository.deleteById(id);
    }

    public Page<ContactDto> getAll(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Contact> contacts = contactRepository.findAll(paging);
        return new PageImpl<>(contactMapper.toDtos(contacts.getContent()), paging, contacts.getTotalElements());
    }

    public ContactDto get(int id) {
        return getByIdOrThrow(id);
    }

    private ContactDto getByIdOrThrow(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() ->
                new ContactNotFoundException("Contact with id: " + id + " not found"));

        return contactMapper.toDto(contact);
    }

    @SneakyThrows
    private Image convertImage(MultipartFile userImage) {
        return Image.builder()
                .bytes(userImage.getBytes())
                .contentType(userImage.getContentType())
                .originalFileName(userImage.getOriginalFilename())
                .size(userImage.getSize())
                .build();
    }

}
