package com.example.test32.controllers;

import com.example.test32.forms.AnnouncementForm;
import com.example.test32.models.Announcement;
import com.example.test32.repository.AnnouncementRepository;
import com.example.test32.services.AnnouncementService;
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

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementRepository announcementRepository;

    //Разрешенный IP-адрес
    private final String allowedIpAddress = "0:0:0:0:0:0:0:1"; // Замените на реальный IP



    public AnnouncementController(AnnouncementService announcementService, AnnouncementRepository announcementRepository) {
        this.announcementService = announcementService;
        this.announcementRepository = announcementRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Каждый день в полночь
    public void deleteOldAnnouncements() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -14); // Определяем дату, которая была 14 дней назад

        Date twoWeeksAgo = calendar.getTime();

        // Получаем список старых объявлений
        List<Announcement> oldAnnouncements = announcementRepository.findAllByCreatedAtBefore(twoWeeksAgo);

        // Удаляем старые объявления
        announcementRepository.deleteAll(oldAnnouncements);
    }


    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    @GetMapping("/add")
    public String showAddAnnouncementForm(HttpServletRequest request, Model model) {
        String clientIpAddress = getClientIpAddress(request);
        if (!clientIpAddress.equals(allowedIpAddress)) {
            // Если IP-адрес не соответствует разрешенному, вернуть ошибку или перенаправить
            // на другую страницу с сообщением о запрете доступа
            return "list-main";
        }
        AnnouncementForm announcementForm = new AnnouncementForm();
        model.addAttribute("announcementForm", announcementForm);
        return "add-announcement";
    }

    @PostMapping("/add")
    public String addAnnouncement(@ModelAttribute("announcementForm") @Valid AnnouncementForm announcementForm, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "add-announcement";
        }

        // Создание объявления
        Announcement announcement = announcementService.createAnnouncement(announcementForm);

        // Сохранение объявления в базе данных
        announcementRepository.save(announcement);

        // Удаление старых объявлений
        deleteOldAnnouncements();

        // Дополнительная обработка или редирект на главную страницу
        return "redirect:/";
    }


    @GetMapping("/all")
    public String showAllAnnouncements(Model model) {
        List<Announcement> allAnnouncements = announcementRepository.findAll();
        model.addAttribute("allAnnouncements", allAnnouncements);
        return "all-announcements";
    }
}
