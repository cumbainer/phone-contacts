package ua.shtaiier.phonecontacts.dto;


import lombok.Data;

import java.util.List;

@Data
public class ContactDto {

    private Integer id;
    private String name;
    private List<String> emails;
    private List<String> phoneNumbers;

}
