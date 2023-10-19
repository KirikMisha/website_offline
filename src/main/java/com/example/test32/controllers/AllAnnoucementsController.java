package com.example.test32.controllers;

import com.example.test32.models.Announcement;
import com.example.test32.repository.AnnouncementRepository;
import com.example.test32.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AllAnnoucementsController {
    final private AnnouncementService announcementService;
    final private AnnouncementRepository announcementRepository;
    @Autowired
    public AllAnnoucementsController(AnnouncementService announcementService, AnnouncementRepository announcementRepository){
        this.announcementRepository = announcementRepository;
        this.announcementService = announcementService;
    }
    @GetMapping("/announcement/all")
        public String AllAnnoucements(Model model){
        List<Announcement> latestAnnouncements = announcementService.getLatestAnnoucements();
        model.addAttribute("allAnnouncements", latestAnnouncements);
        model.addAttribute("title", "Все объявление");
        return "all-announcement";
        }
}
