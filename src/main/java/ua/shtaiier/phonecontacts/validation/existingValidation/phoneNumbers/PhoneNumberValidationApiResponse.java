package ua.shtaiier.phonecontacts.validation.existingValidation.phoneNumbers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneNumberValidationApiResponse {

    @JsonProperty("valid")
    private Boolean status;
    private String number;

}
