package ua.shtaiier.phonecontacts.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(String message) {
        super(message);
    }
}
