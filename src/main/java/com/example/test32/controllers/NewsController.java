package com.example.test32.controllers;

import com.example.test32.forms.NewsForm;
import com.example.test32.models.News;
import com.example.test32.repo.NewsRepository;
import com.example.test32.services.NewsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final NewsRepository newsRepository;

    // Здесь добавьте свой разрешенный IP-адрес
    private final String allowedIpAddress = "0:0:0:0:0:0:0:1"; // Замените на реальный IP

    public NewsController(NewsRepository newsRepository, NewsService newsService) {
        this.newsRepository = newsRepository;
        this.newsService = newsService;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    @GetMapping("/add")
    public String showAddNewsForm(HttpServletRequest request, Model model) {
        String clientIpAddress = getClientIpAddress(request);
        if (!clientIpAddress.equals(allowedIpAddress)) {
            // Если IP-адрес не соответствует разрешенному, вернуть ошибку или перенаправить
            // на другую страницу с сообщением о запрете доступа
            return "list-main";
        }
        NewsForm newsForm = new NewsForm();
        newsForm.setTitle("Default title");
        newsForm.setText("Default text");
        model.addAttribute("newsForm", newsForm);
        return "add-news";
    }

    @PostMapping("/add")
    public String addNews(@ModelAttribute("newsForm") @Valid NewsForm newsForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-news";
        }

        // Создание новости
        News news = newsService.createNews(newsForm);

        // Дополнительная обработка или редирект на главную страницу
        return "redirect:/";
    }
}

