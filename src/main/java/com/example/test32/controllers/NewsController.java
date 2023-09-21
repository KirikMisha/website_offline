package com.example.test32.controllers;

import com.example.test32.forms.NewsForm;
import com.example.test32.models.News;
import com.example.test32.repository.NewsRepository;
import com.example.test32.services.NewsService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Scheduled(cron = "0 0 0 * * ?") // Каждый день в полночь
    public void deleteOldNews() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -14); // Определяем дату, которая была 14 дней назад

        Date twoWeeksAgo = calendar.getTime();

        // Получаем список старых новостей
        List<News> oldNews = newsRepository.findAllByCreatedAtBefore(twoWeeksAgo);

        // Удаляем старые новости
        newsRepository.deleteAll(oldNews);
    }

    @GetMapping("/details/{id}")
    public String showNewsDetails(@PathVariable Long id, Model model) {
        // Найдите новость по ее идентификатору
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            model.addAttribute("news", news);
            return "news-details"; // Имя представления для полного текста новости
        } else {
            // Обработайте случай, если новость с указанным идентификатором не найдена
            return "redirect:/"; // Редирект на главную страницу или другую страницу по вашему выбору
        }
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

        // Установка значения createdAt
        news.setCreatedAt(new Date()); // Установите актуальную дату

        // Сохранение новости в базу данных
        newsRepository.save(news);

        // Дополнительная обработка или редирект на главную страницу
        return "redirect:/";
    }
}

