package com.example.test32.services;

import com.example.test32.forms.NewsForm;
import com.example.test32.models.News;
import org.springframework.stereotype.Service;
import com.example.test32.repo.NewsRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News createNews(NewsForm newsForm) {
        News news = new News();
        news.setTitle(newsForm.getTitle());
        news.setText(newsForm.getText());

        MultipartFile image = newsForm.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                byte[] imageData = image.getBytes();
                Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(imageData); // Создаем Blob с помощью javax.sql.rowset.serial.SerialBlob
                news.setImageUrl(imageBlob);
            } catch (IOException | SQLException e) {
                // Обработка ошибки чтения изображения
            }
        }

        // Другая обработка, сохранение в базу данных и т.д.
        return newsRepository.save(news);
    }
}
