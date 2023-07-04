package ua.shtaiier.phonecontacts.domain;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(schema = "contact_management", name = "contacts")
@Data
public class Contact {

    @Id
    private Integer id;
    private String name;
    @ElementCollection
    private List<String> emails;
    @ElementCollection
    private List<String> phoneNumbers;

}
