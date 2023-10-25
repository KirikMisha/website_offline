package com.example.test32.controllers;

import com.example.test32.models.News;
import com.example.test32.repository.NewsRepository;
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
            return "redirect:/";
        }

        model.addAttribute("news", news);
        return "news-detail";
    }
}

