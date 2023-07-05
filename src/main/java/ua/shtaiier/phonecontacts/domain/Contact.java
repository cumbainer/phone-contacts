package ua.shtaiier.phonecontacts.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    private String name;
    @ElementCollection
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private List<String> emails;
    @ElementCollection
//    @Pattern(regexp = "^\\+?\\d+$")
    private List<String> phoneNumbers;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

}
