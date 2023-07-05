package ua.shtaiier.phonecontacts.validation.existingValidation.phoneNumbers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ua.shtaiier.phonecontacts.validation.existingValidation.InfoValidator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class PhoneNumberValidator implements InfoValidator {

    private static final String API_KEY = "ef2509e2bd186c36b25af4622eabcce3";

    @SneakyThrows
    private PhoneNumberValidationApiResponse invokeApi(String phoneNumber) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(buildUrl(phoneNumber)))
                .build();
        String jsonResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readerFor(PhoneNumberValidationApiResponse.class).readValue(jsonResponse);
    }

    private String buildUrl(String phoneNumber) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://apilayer.net/api/validate")
                .queryParam("access_key", API_KEY)
                .queryParam("number", phoneNumber)
                .queryParam("format", 1);

        return builder.toUriString();
    }

    @Override
    public boolean validate(String data) {
        return invokeApi(data).getStatus();
    }

}
