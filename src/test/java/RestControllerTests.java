import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import ua.shtaiier.phonecontacts.dto.AccountDto;
import ua.shtaiier.phonecontacts.dto.ContactDto;
import ua.shtaiier.phonecontacts.rest.ContactsController;
import ua.shtaiier.phonecontacts.service.AccountService;
import ua.shtaiier.phonecontacts.service.ContactService;
import ua.shtaiier.phonecontacts.service.ExcelReportService;

import java.io.IOException;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestControllerTests {

    @Mock
    private ExcelReportService excelReportService;

    @Mock
    private ContactService contactService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private ContactsController contactsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateExcelReport() throws IOException {
        // Arrange
        HttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(excelReportService).export(response);

        // Act
        contactsController.generateExcelReport(response);

        // Assert
        assertEquals("application/octet-stream", response.getContentType());
        assertEquals("attachment;filename=contacts.xls", response.getHeader(HttpHeaders.CONTENT_DISPOSITION));
        verify(excelReportService, times(1)).export(response);
    }

    @Test
    public void testAddNew() {
        // Arrange
        ContactDto contactDto = new ContactDto();
        MultipartFile image = mock(MultipartFile.class);
        Principal principal = mock(Principal.class);
        AccountDto accountDto = new AccountDto();
        ResponseEntity<ContactDto> expectedResponse = new ResponseEntity<>(HttpStatus.CREATED);

        when(principal.getName()).thenReturn("testUser");
        when(accountService.getByUsername("testUser")).thenReturn(accountDto);
        when(contactService.create(contactDto, image, false)).thenReturn(contactDto);

        // Act
        ResponseEntity<ContactDto> response = contactsController.addNew(contactDto, image, principal);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(contactDto, response.getBody());
        verify(accountService, times(1)).getByUsername("testUser");
        verify(contactService, times(1)).create(contactDto, image, false);
    }

    @Test
    public void testUpdate() {
        // Arrange
        int id = 1;
        ContactDto contactDto = new ContactDto();
        MultipartFile image = mock(MultipartFile.class);
        Principal principal = mock(Principal.class);
        AccountDto accountDto = new AccountDto();
        ResponseEntity<ContactDto> expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        when(principal.getName()).thenReturn("testUser");
        when(accountService.getByUsername("testUser")).thenReturn(accountDto);
        when(contactService.update(id, contactDto, image)).thenReturn(contactDto);

        // Act
        ResponseEntity<ContactDto> response = contactsController.update(id, contactDto, image, principal);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(contactDto, response.getBody());
        verify(accountService, times(1)).getByUsername("testUser");
        verify(contactService, times(1)).update(id, contactDto, image);
    }

}
