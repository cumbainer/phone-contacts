package ua.shtaiier.phonecontacts.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
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
import ua.shtaiier.phonecontacts.mapper.AccountMapper;
import ua.shtaiier.phonecontacts.mapper.ContactMapper;
import ua.shtaiier.phonecontacts.repository.ContactRepository;
import ua.shtaiier.phonecontacts.validation.existingValidation.emails.EmailValidator;
import ua.shtaiier.phonecontacts.validation.existingValidation.phoneNumbers.PhoneNumberValidator;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;
    private final AccountService accountService;
    private final ContactMapper contactMapper;
    private final AccountMapper accountMapper;
    private final EmailValidator emailValidator;
    private final PhoneNumberValidator phoneNumberValidator;


    public ContactDto create(ContactDto contactDto, MultipartFile image) {
        Contact contact = contactMapper.toDomain(contactDto);

        if (ObjectUtils.isNotEmpty(image) && StringUtils.isNotEmpty(image.getOriginalFilename())) {
            Image imageEntity = convertImage(image);
            contact.setImage(imageEntity);
        }

        validateData(contact.getPhoneNumbers(), contact.getEmails());

        contact.setAccount(accountMapper.toDomain(accountService.getById(contactDto.getAccountId())));
        return contactMapper.toDto(contactRepository.save(contact));
    }

    public ContactDto update(int id, ContactDto contactDto, MultipartFile image) {
        Contact contactFromDb = contactMapper.toDomain(getByIdOrThrow(id));

        validateData(contactFromDb.getPhoneNumbers(), contactFromDb.getEmails());

        contactFromDb.setName(contactDto.getName());
        contactFromDb.setEmails(contactDto.getEmails());
        contactFromDb.setPhoneNumbers(contactDto.getPhoneNumbers());
        contactFromDb.setAccount(accountMapper.toDomain(accountService.getById(contactDto.getAccountId())));
        if (StringUtils.isNotEmpty(image.getOriginalFilename())) {
            contactFromDb.setImage(convertImage(image));
        }

        return contactMapper.toDto(contactRepository.save(contactFromDb));
    }

    public void delete(int id) {
        if (!contactRepository.existsById(id)) {
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


    //Validates only first email and phone, because there is no solid FREE bulk(2+ more records) validators
    private void validateData(List<String> phoneNumbers, List<String> emails) {

        if (CollectionUtils.isNotEmpty(phoneNumbers)) {
            boolean validPhone = phoneNumberValidator.validate(phoneNumbers.get(0));
            phoneNumbers.removeIf(phoneNumber -> !validPhone);
        }
        if (CollectionUtils.isNotEmpty(emails)) {
            boolean validEmail = emailValidator.validate(emails.get(0));
            emails.removeIf(email -> !validEmail);
        }
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
