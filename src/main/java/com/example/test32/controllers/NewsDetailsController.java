package com.example.test32.controllers;

import com.example.test32.models.News;
import com.example.test32.repo.NewsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NewsDetailsController {

    private final NewsRepository newsRepository;

    public NewsDetailsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/news/{id}")
    public String showNewsDetail(@PathVariable Long id, Model model) {
        News news = newsRepository.findById(id).orElse(null);
        if (news == null) {
            // Обработка случая, если новость не найдена
            return "redirect:/"; // Можно перенаправить на главную страницу
        }

        model.addAttribute("news", news);
        return "news-detail";
    }
}

