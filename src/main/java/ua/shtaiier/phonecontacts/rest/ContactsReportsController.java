package ua.shtaiier.phonecontacts.rest;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.shtaiier.phonecontacts.api.impl.ExcelReportService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ContactsReportsController {

    private final ExcelReportService excelReportService;

    @GetMapping("/export")
    public void generateExcelReport(HttpServletResponse response) throws Exception{
        excelReportService.export(response);
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=contacts.xls";
        response.setHeader(headerKey, headerValue);

        response.flushBuffer();
    }

    @PostMapping("/import")
    public List<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws Exception{
        return excelReportService.importData(file);
    }

}
