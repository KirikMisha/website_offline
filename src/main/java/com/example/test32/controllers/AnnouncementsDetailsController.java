package com.example.test32.controllers;

import com.example.test32.models.Announcement;
import com.example.test32.repo.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/announcements/details")
public class AnnouncementsDetailsController {

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementsDetailsController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/{id}")
    public String showAnnouncementDetails(@PathVariable Long id, Model model) {
        Optional<Announcement> announcementOptional = announcementRepository.findById(id);

        if (announcementOptional.isPresent()) {
            Announcement announcement = announcementOptional.get();
            model.addAttribute("announcement", announcement);
            return "all-announcements"; // Имя вашего HTML-шаблона для деталей объявления
        } else {
            return "redirect:/"; // Редирект на главную страницу или обработка ошибки, если объявление не найдено
        }
    }
}
