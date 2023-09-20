package com.example.test32.controllers;

import com.example.test32.models.CalendarDay;
import com.example.test32.models.News;
import com.example.test32.models.Quotes;
import com.example.test32.repo.NewsRepository;
import com.example.test32.repo.QuoteRepository;
import com.example.test32.services.CalendarDayService;
import com.example.test32.models.Announcement;
import com.example.test32.repo.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class CalendarController {
    private final CalendarDayService calendarDayService;
    private final NewsRepository newsRepository;
    private final QuoteRepository quoteRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public CalendarController(CalendarDayService calendarDayService, NewsRepository newsRepository, QuoteRepository quoteRepository,  AnnouncementRepository announcementRepository) {
        this.calendarDayService = calendarDayService;
        this.newsRepository = newsRepository;
        this.quoteRepository = quoteRepository;
        this.announcementRepository = announcementRepository;
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

        // Получение списка всех новостей
        List<News> allNews = newsRepository.findAllByOrderByCreatedAtDesc();

        // Установка списка новостей в модель
        model.addAttribute("allNews", allNews);

        // Получение списка новостей
        List<News> newsList = newsRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("newsList", newsList);

        // Получение списка цитат
        List<Quotes> quoteList = quoteRepository.findAll();

        // Получение списка объявлений
        List<Announcement> announcements = announcementRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        // Генерация случайного индекса
        Random random = new Random();
        int randomIndex = random.nextInt(quoteList.size());

        model.addAttribute("quoteList", quoteList);
        model.addAttribute("randomIndex", randomIndex);
        model.addAttribute("allAnnouncements", announcements);

        return "home";
    }
}

