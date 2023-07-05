package ua.shtaiier.phonecontacts.validation.existingValidation.emails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidEmail {

    private Boolean value;

}
