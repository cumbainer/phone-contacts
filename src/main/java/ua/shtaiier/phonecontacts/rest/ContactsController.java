package ua.shtaiier.phonecontacts.rest;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.shtaiier.phonecontacts.dto.ContactDto;
import ua.shtaiier.phonecontacts.service.ContactService;
import ua.shtaiier.phonecontacts.service.ExcelReportService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contacts")
public class ContactsController {

    private final ExcelReportService excelReportService;
    private final ContactService contactService;

    @GetMapping("/export")
    public void generateExcelReport(HttpServletResponse response) throws Exception {
        excelReportService.export(response);
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=contacts.xls";
        response.setHeader(headerKey, headerValue);

        response.flushBuffer();
    }

    @PostMapping("/import")
    public List<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        return excelReportService.importData(file);
    }

    @PostMapping("/new")
    public ResponseEntity<ContactDto> addNew(@ModelAttribute @Valid ContactDto contactDto,
                                             @RequestParam(required = false) MultipartFile image) {

        return new ResponseEntity<>(contactService.create(contactDto, image), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ContactDto> update(@PathVariable("id") Integer id,
                                             @ModelAttribute @Valid ContactDto contactDto,
                                             @RequestParam(required = false) MultipartFile image) {
        return ResponseEntity.ok(contactService.update(id, contactDto, image));
    }

}
