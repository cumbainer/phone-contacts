package ua.shtaiier.phonecontacts.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "account",orphanRemoval = true)
    private List<Contact> contacts;

}
