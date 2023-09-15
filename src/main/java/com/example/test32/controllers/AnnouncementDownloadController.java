package com.example.test32.controllers;

import com.example.test32.models.Announcement;
import com.example.test32.repo.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/download")
public class AnnouncementDownloadController {

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementDownloadController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/announcement/{id}")
    public ResponseEntity<byte[]> downloadAnnouncementImage(@PathVariable Long id) {
        Optional<Announcement> announcementOptional = announcementRepository.findById(id);
        if (announcementOptional.isPresent()) {
            Announcement announcement = announcementOptional.get();
            Blob imageBlob = announcement.getImageUrl();
            if (imageBlob != null) {
                try (InputStream inputStream = imageBlob.getBinaryStream()) {
                    byte[] imageData = inputStream.readAllBytes();
                    HttpHeaders headers = new HttpHeaders();
                    // Установите правильный Content-Type для изображения
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
                } catch (SQLException | IOException e) {
                    e.printStackTrace(); // Обработка ошибок чтения изображения
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

