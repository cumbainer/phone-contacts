package ua.shtaiier.phonecontacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.shtaiier.phonecontacts.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
