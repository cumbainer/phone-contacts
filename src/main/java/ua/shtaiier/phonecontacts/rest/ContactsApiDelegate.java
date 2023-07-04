package ua.shtaiier.phonecontacts.rest;


import ua.shtaiier.phonecontacts.dto.ContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.shtaiier.phonecontacts.api.ContactApi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactsApiDelegate implements ContactsManagementApiDelegate {

    private final ContactApi contactApi;

    @Override
    public ResponseEntity<ContactDto> addNew(ContactDto contactDto) {
        return ResponseEntity.ok(contactApi.create(contactDto));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        contactApi.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ContactDto> get(Integer id) {
        return ResponseEntity.ok(contactApi.get(id));
    }

    @Override
    public ResponseEntity<List<ContactDto>> getAll() {
        return ResponseEntity.ok(contactApi.getAll());
    }

    @Override
    public ResponseEntity<ContactDto> update(Integer id, ContactDto contactDto) {
        return ResponseEntity.ok(contactApi.update(id, contactDto));
    }

}
