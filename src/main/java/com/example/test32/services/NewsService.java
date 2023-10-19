package com.example.test32.services;

import com.example.test32.forms.NewsForm;
import com.example.test32.models.News;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.test32.repository.NewsRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

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
            }
        }
        return newsRepository.save(news);
    }
    public List<News> getTwoLatestNews() {
        Sort sortByCreatedAtDesc = Sort.by(Sort.Direction.DESC, "createdAt");
        List<News> latestNews = newsRepository.findAll(sortByCreatedAtDesc);

        if (latestNews.size() >= 2) {
            return latestNews.subList(0, 2);
        } else {
            return latestNews;
        }
    }
}
