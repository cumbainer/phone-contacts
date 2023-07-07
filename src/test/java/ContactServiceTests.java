import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.shtaiier.phonecontacts.domain.Contact;
import ua.shtaiier.phonecontacts.dto.ContactDto;
import ua.shtaiier.phonecontacts.exception.ContactNotFoundException;
import ua.shtaiier.phonecontacts.mapper.ContactMapper;
import ua.shtaiier.phonecontacts.repository.ContactRepository;
import ua.shtaiier.phonecontacts.service.ContactService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContactServiceTests {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDelete_ContactExists() {
        // Arrange
        int id = 1;
        when(contactRepository.existsById(id)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> contactService.delete(id));

        // Assert
        verify(contactRepository, times(1)).existsById(id);
        verify(contactRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDelete_ContactNotFound() {
        // Arrange
        int id = 1;
        when(contactRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ContactNotFoundException.class, () -> contactService.delete(id));

        // Assert
        verify(contactRepository, times(1)).existsById(id);
        verify(contactRepository, never()).deleteById(id);
    }

    @Test
    public void testGetAll() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<Contact> contacts = new ArrayList<>();
        when(contactRepository.findAll(pageable)).thenReturn(new PageImpl<>(contacts));
        when(contactMapper.toDtos(contacts)).thenReturn(new ArrayList<>());

        // Act
        Page<ContactDto> resultPage = contactService.getAll(page, size);

        // Assert
        assertNotNull(resultPage);
        assertEquals(0, resultPage.getContent().size());
        verify(contactRepository, times(1)).findAll(pageable);
        verify(contactMapper, times(1)).toDtos(contacts);
    }

    @Test
    public void testGet_ContactExists() {
        // Arrange
        int id = 1;
        Contact contact = new Contact();
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        when(contactMapper.toDto(contact)).thenReturn(new ContactDto());

        // Act
        ContactDto resultDto = contactService.get(id);

        // Assert
        assertNotNull(resultDto);
        verify(contactRepository, times(1)).findById(id);
        verify(contactMapper, times(1)).toDto(contact);
    }

    @Test
    public void testGet_ContactNotFound() {
        // Arrange
        int id = 1;
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ContactNotFoundException.class, () -> contactService.get(id));

        // Assert
        verify(contactRepository, times(1)).findById(id);
        verify(contactMapper, never()).toDto(any());
    }

}
