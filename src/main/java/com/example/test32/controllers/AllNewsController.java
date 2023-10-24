package com.example.test32.controllers;

import com.example.test32.models.News;
import com.example.test32.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AllNewsController {
    private final NewsRepository newsRepository;

    @Autowired
    public AllNewsController(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    @GetMapping("/news/all")
    public String AllNews(Model model){
        List<News> allNews = newsRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("allNews", allNews);
        model.addAttribute("title", "Все новости");
        return "all-news";
    }
}
