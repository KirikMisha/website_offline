package com.example.test32.services;

import com.example.test32.forms.AnnouncementForm;
import com.example.test32.models.Announcement;
import com.example.test32.repository.AnnouncementRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;


    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public Announcement createAnnouncement(AnnouncementForm announcementForm) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementForm.getTitle());
        announcement.setText(announcementForm.getText());

        announcement.setCreatedAt(new Date());

        MultipartFile image = announcementForm.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                byte[] imageData = image.getBytes();
                Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(imageData);
                announcement.setImageUrl(imageBlob);
            } catch (IOException | SQLException e) {
            }
        }

        return announcementRepository.save(announcement);
    }
    public List<Announcement> getTwoLatestAnnoucement() {
        Sort sortByCreatedAtDesc = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Announcement> latestAnnouncement = announcementRepository.findAll(sortByCreatedAtDesc);

        if (latestAnnouncement.size() >= 2) {
            return latestAnnouncement.subList(0, 2);
        } else {
            return latestAnnouncement;
        }
    }
    public List<Announcement> getLatestAnnoucements() {
        Sort sortByCreatedAtDesc = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Announcement> allAnnouncements = announcementRepository.findAll(sortByCreatedAtDesc);
        return allAnnouncements;
    }

}