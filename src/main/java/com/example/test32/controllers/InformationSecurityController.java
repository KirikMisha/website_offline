package com.example.test32.controllers;

import com.example.test32.models.FileEntity;
import com.example.test32.repository.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/downloadSecurityInfo/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        Optional<FileEntity> fileOptional = fileEntityRepository.findById(fileId);

        if (fileOptional.isPresent()) {
            FileEntity file = fileOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getFilename());

            return new ResponseEntity<>(file.getContent(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
