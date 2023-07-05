package ua.shtaiier.phonecontacts.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "contacts")
@Data
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    @ElementCollection
    private List<String> emails;
    @ElementCollection
    private List<String> phoneNumbers;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account_id")
    private Account account;

}
