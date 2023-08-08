package com.example.test32.controllers;

import com.example.test32.models.CalendarDay;
import com.example.test32.models.News;
import com.example.test32.repo.NewsRepository;
import com.example.test32.services.CalendarDayService;
import com.example.test32.services.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CalendarController {
    private final CalendarDayService calendarDayService;
    private final NewsRepository newsRepository;

    @Autowired
    public CalendarController(CalendarDayService calendarDayService, NewsRepository newsRepository) {
        this.calendarDayService = calendarDayService;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/")
    public String showCalendar(Model model) {
        LocalDate today = LocalDate.now();
        CalendarDay todayDay = calendarDayService.getCalendarDayByDate(today);
        model.addAttribute("todayDay", todayDay);
        List<CalendarDay> calendarDays = calendarDayService.getAllCalendarDays();
        model.addAttribute("calendarDays", calendarDays);
        model.addAttribute("homePage", true);
        model.addAttribute("title", "Главная страница");
        model.addAttribute("additionalInfoList", todayDay.getAdditionalInfoList());
        model.addAttribute("infoNumber", 1); // Установите нужное значение infoNumber

        // Получение последней новости
        News lastNews = newsRepository.findTopByOrderByCreatedAtDesc();
        byte[] imageData = new byte[0];
        try {
            InputStream inputStream = lastNews.getImageUrl().getBinaryStream();
            imageData = inputStream.readAllBytes();
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // Обработка ошибок чтения изображения
        }
        System.out.println("Image data length: " + imageData.length); // Вывод длины данных изображения
        model.addAttribute("lastNews", lastNews);


        // Получение списка последних новостей
        List<News> newsList = newsRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("newsList", newsList);

        return "home";
    }
}

