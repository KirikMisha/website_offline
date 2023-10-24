package com.example.test32.controllers;

import com.example.test32.models.CalendarDay;
import com.example.test32.models.News;
import com.example.test32.models.Quotes;
import com.example.test32.repository.QuoteRepository;
import com.example.test32.services.AnnouncementService;
import com.example.test32.services.CalendarDayService;
import com.example.test32.models.Announcement;
import com.example.test32.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class MainController {
    private final CalendarDayService calendarDayService;
    private final QuoteRepository quoteRepository;
    private final NewsService newsService;
    private final AnnouncementService announcementService;

    @Autowired
    public MainController(CalendarDayService calendarDayService, QuoteRepository quoteRepository, AnnouncementService announcementService, NewsService newsService) {
        this.calendarDayService = calendarDayService;
        this.quoteRepository = quoteRepository;
        this.newsService = newsService;
        this.announcementService = announcementService;
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
        model.addAttribute("infoNumber", 1);

        List<News> latestNews = newsService.getTwoLatestNews();
        model.addAttribute("allNews", latestNews);

        List<Quotes> quoteList = quoteRepository.findAll();

        List<Announcement> announcements = announcementService.getTwoLatestAnnoucement();
        model.addAttribute("latestAnnouncement", announcements);

        Random random = new Random();
        int randomIndex = random.nextInt(quoteList.size());

        model.addAttribute("quoteList", quoteList);
        model.addAttribute("randomIndex", randomIndex);

        return "home";
    }
}

