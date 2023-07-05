package ua.shtaiier.phonecontacts.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.shtaiier.phonecontacts.api.ContactService;
import ua.shtaiier.phonecontacts.dto.ContactDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactsApiDelegate implements ContactsManagementApiDelegate {

    private final ContactService contactService;

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        contactService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ContactDto> get(Integer id) {
        return ResponseEntity.ok(contactService.get(id));
    }

    @Override
    public ResponseEntity<List<ContactDto>> getAll(Integer page, Integer size) {
        return ResponseEntity.ok(contactService.getAll(page, size).getContent());
    }

}
