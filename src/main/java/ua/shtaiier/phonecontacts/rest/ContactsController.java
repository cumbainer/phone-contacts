package ua.shtaiier.phonecontacts.rest;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.shtaiier.phonecontacts.dto.AccountDto;
import ua.shtaiier.phonecontacts.dto.ContactDto;
import ua.shtaiier.phonecontacts.dto.ImageDto;
import ua.shtaiier.phonecontacts.service.AccountService;
import ua.shtaiier.phonecontacts.service.ContactService;
import ua.shtaiier.phonecontacts.service.ExcelReportService;
import ua.shtaiier.phonecontacts.service.ImageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contacts")
public class ContactsController {

    private final ExcelReportService excelReportService;
    private final ContactService contactService;
    private final AccountService accountService;
    private final ImageService imageService;


    @GetMapping("/export")
    public void generateExcelReport(HttpServletResponse response) throws IOException {
        excelReportService.export(response);
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=contacts.xls";
        response.setHeader(headerKey, headerValue);

        response.flushBuffer();
    }

    @PostMapping("/import")
    public List<Map<String, String>> upload(@RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        AccountDto account = accountService.getByUsername(principal.getName());
        return excelReportService.importData(file, account.getId());
    }

    @PostMapping("/new")
    public ResponseEntity<ContactDto> addNew(@ModelAttribute @Valid ContactDto contactDto,
                                             @RequestParam(required = false) MultipartFile image,
                                             Principal principal) {

        contactDto.setAccountId(accountService.getByUsername(principal.getName()).getId());
        return new ResponseEntity<>(contactService.create(contactDto, image, false), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ContactDto> update(@PathVariable("id") Integer id,
                                             @ModelAttribute @Valid ContactDto contactDto,
                                             @RequestParam(required = false) MultipartFile image, Principal principal) {

        AccountDto account = accountService.getByUsername(principal.getName());
        contactDto.setAccountId(account.getId());
        return ResponseEntity.ok(contactService.update(id, contactDto, image));
    }

    @GetMapping("/{id}/image")
    private ResponseEntity<?> getImage(@PathVariable Integer id) {
        ImageDto image = imageService.getById(id);

        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getContentType())).contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

}
