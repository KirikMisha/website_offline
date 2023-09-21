package com.example.test32.controllers;

import com.example.test32.models.News;
import com.example.test32.repository.NewsRepository;
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
public class DownloadController {

    private final NewsRepository newsRepository;

    public DownloadController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            Blob imageBlob = news.getImageUrl();
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

