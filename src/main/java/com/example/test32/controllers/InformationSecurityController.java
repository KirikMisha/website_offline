package com.example.test32.controllers;

import com.example.test32.models.FileEntity;
import com.example.test32.repository.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Controller
public class InformationSecurityController {
    private final FileEntityRepository fileEntityRepository;
    @Autowired
    public InformationSecurityController(FileEntityRepository fileEntityRepository){
        this.fileEntityRepository = fileEntityRepository;
    }
    @GetMapping("/InformationSecurity")
    public String showInformation (Model model){
        List<FileEntity> files = fileEntityRepository.findAll();
        model.addAttribute("files", files);
        return "information-security";
    }

    @GetMapping("/viewPdf/{fileId}")
    public void viewPdf(@PathVariable Long fileId, HttpServletResponse response) {
        Optional<FileEntity> fileOptional = fileEntityRepository.findById(fileId);

        if (fileOptional.isPresent()) {
            FileEntity file = fileOptional.get();

            response.setContentType("application/pdf");

            try {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(file.getContent());
                outputStream.close();
            } catch (IOException e) {
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
