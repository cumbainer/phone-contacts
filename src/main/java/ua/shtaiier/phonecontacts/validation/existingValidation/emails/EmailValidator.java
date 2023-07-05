package ua.shtaiier.phonecontacts.validation.existingValidation.emails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ua.shtaiier.phonecontacts.validation.existingValidation.InfoValidator;


@Component
public class EmailValidator implements InfoValidator {

    private static final String API_KEY = "a4f424ba295049aea2de064f2fb6fb3f";

    @SneakyThrows
    private EmailValidationResponse invokeApi(String email) {
        String jsonResponse = Unirest.get(buildUrl(email))
                .asString().getBody();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readerFor(EmailValidationResponse.class).readValue(jsonResponse);
    }

    private String buildUrl(String email) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://emailvalidation.abstractapi.com/v1")
                .queryParam("api_key", API_KEY)
                .queryParam("email", email);

        return builder.toUriString();
    }

    @Override
    public boolean validate(String data) {
        return invokeApi(data).getValidationProps().getValue();
    }
}
