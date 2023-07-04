package ua.shtaiier.phonecontacts.rest;


import com.ua.shtaiier.phonecontacts.dto.ContactDto;
import com.ua.shtaiier.phonecontacts.rest.ContactsManagementApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactsApiDelegate implements ContactsManagementApiDelegate {

    @Override
    public ResponseEntity<Void> addNew(ContactDto contactDto) {
        return ContactsManagementApiDelegate.super.addNew(contactDto);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        return ContactsManagementApiDelegate.super.delete(id);
    }

    @Override
    public ResponseEntity<ContactDto> get(String id) {
        return ContactsManagementApiDelegate.super.get(id);
    }

    @Override
    public ResponseEntity<List<ContactDto>> getAll() {
        return ContactsManagementApiDelegate.super.getAll();
    }

    @Override
    public ResponseEntity<Void> update(Integer id, ContactDto contactDto) {
        return ContactsManagementApiDelegate.super.update(id, contactDto);
    }

}
