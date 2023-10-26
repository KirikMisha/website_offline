package com.example.test32.controllers;

import com.example.test32.services.VacationTemplatesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class VacationTemplatesController {
    private final VacationTemplatesService vacationTemplatesService;

    public VacationTemplatesController(VacationTemplatesService vacationTemplatesService) {
        this.vacationTemplatesService = vacationTemplatesService;
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String surname,
            @RequestParam String profession,
            @RequestParam String departmentNumber) {

        byte[] pdfContent = vacationTemplatesService.generatePdfDocument(firstName, lastName, surname, startDate, endDate, profession, departmentNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "attachment; filename=vacation-template.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

    @GetMapping("/vacation-templates")
    public String showTemplatePage() {
        return "vacation-templates";
    }
}
