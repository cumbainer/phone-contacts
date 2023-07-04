package ua.shtaiier.phonecontacts.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(schema = "contact_management", name = "contacts")
@Data
public class Contact {

    @Id
    private Integer id;


}
