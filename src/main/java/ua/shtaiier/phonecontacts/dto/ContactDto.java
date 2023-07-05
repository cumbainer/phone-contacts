package ua.shtaiier.phonecontacts.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ContactDto {

    private Integer id;
    private String name;
    private List<String> emails;
    private List<String> phoneNumbers;

    public ContactDto(String name, List<String> emails, List<String> phoneNumbers) {
        this.name = name;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
    }
}
