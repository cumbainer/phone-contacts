package ua.shtaiier.phonecontacts.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.shtaiier.phonecontacts.validation.formatValidation.ValidateData;
import ua.shtaiier.phonecontacts.validation.formatValidation.ValidationType;

import java.util.List;

@Data
@NoArgsConstructor
public class ContactDto {

    private Integer id;
    @NotEmpty(message = "Name is required")
    private String name;
    @ValidateData(type = ValidationType.EMAIL, message = "One or more emails with invalid formats was found")
    private List<String> emails;
    @ValidateData(type = ValidationType.PHONE_NUMBERS,
            message = "Invalid phone format. Correct format is: +[COUNTRY_CODE][YOUR_NUMBER] ")
    private List<String> phoneNumbers;
    private ImageDto imageDto;
    private Integer accountId;

    public ContactDto(String name, List<String> emails, List<String> phoneNumbers) {
        this.name = name;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
    }

}
