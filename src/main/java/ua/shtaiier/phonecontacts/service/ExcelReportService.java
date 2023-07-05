package ua.shtaiier.phonecontacts.service;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.shtaiier.phonecontacts.common.ExcelImportUtil;
import ua.shtaiier.phonecontacts.dto.ContactDto;
import ua.shtaiier.phonecontacts.exception.ContactNotFoundException;
import ua.shtaiier.phonecontacts.mapper.ContactMapper;
import ua.shtaiier.phonecontacts.repository.ContactRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelReportService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final ExcelImportUtil uploadUtil;
    private final ContactService contactService;

    @SneakyThrows
    public void export(HttpServletResponse response) {

        List<ContactDto> contacts = contactMapper.toDtos(contactRepository.findAll());
        if (CollectionUtils.isEmpty(contacts)) {
            throw new ContactNotFoundException("No contacts found to generate");
        }
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Contacts Info");
        HSSFRow row = sheet.createRow(0);

        generateHeader(row);

        int dataRowIndex = 1;
        for (ContactDto contact : contacts) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(contact.getName());
            dataRow.createCell(1).setCellValue(StringUtils.join(contact.getEmails()));
            dataRow.createCell(2).setCellValue(StringUtils.join(contact.getPhoneNumbers()));
            dataRowIndex++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
    }

    public List<Map<String, String>> importData(MultipartFile file, int accountId) throws Exception {

        Path tempDir = Files.createTempDirectory("");
        File tempFile = tempDir.resolve(Objects.requireNonNull(file.getOriginalFilename())).toFile();
        file.transferTo(tempFile);

        Workbook workbook = WorkbookFactory.create(tempFile);
        Sheet sheet = workbook.getSheetAt(0);

        if (sheet.getPhysicalNumberOfRows() == 0 || workbook.getNumberOfSheets() == 0) {
            return Collections.emptyList();
        }

        Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);
        Row headerRow = rowStreamSupplier.get().findFirst().get();
        List<String> headerCells = uploadUtil.getStream(headerRow)
                .map(Cell::getStringCellValue)
                .toList();

        List<Map<String, String>> importedResponse = rowStreamSupplier.get()
                .skip(1)
                .map(row -> {
                    List<String> cellList = uploadUtil.getStream(row)
                            .map(Cell::getStringCellValue)
                            .toList();

                    return uploadUtil.cellIteratorSupplier(headerCells.size())
                            .get()
                            .collect(Collectors.toMap(headerCells::get, cellList::get));
                })
                .toList();
        formatAndCreate(importedResponse, accountId);

        return importedResponse;
    }

    public void formatAndCreate(List<Map<String, String>> importedData, int accountId) {
        for (Map<String, String> contact : importedData) {
            String name = contact.get("Name");
            String emails = contact.get("Emails");
            String phoneContacts = contact.get("PhoneContacts");

            List<String> formattedEmails = formatField(emails);
            List<String> formattedPhoneContacts = formatField(phoneContacts);

            ContactDto contactDto = new ContactDto(name, formattedEmails, formattedPhoneContacts);
            contactDto.setAccountId(accountId);
            contactService.create(contactDto, null, true);
        }
    }

    private List<String> formatField(String field) {
        field = field.replace("[", "").replace("]", "").replace(" ", "");
        String[] fieldArray = field.split(",");

        return Arrays.stream(fieldArray)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private void generateHeader(HSSFRow row) {
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Emails");
        row.createCell(2).setCellValue("PhoneContacts");
    }
}
