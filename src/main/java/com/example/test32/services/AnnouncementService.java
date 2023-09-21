package com.example.test32.services;

import com.example.test32.forms.AnnouncementForm;
import com.example.test32.models.Announcement;
import com.example.test32.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public Announcement saveAnnouncementWithHtmlBreaks(Announcement announcement) {
        String textWithHtmlBreaks = announcement.getText().replace("\n", "<br>");
        announcement.setText(textWithHtmlBreaks);
        return announcementRepository.save(announcement);
    }

    public Announcement createAnnouncement(AnnouncementForm announcementForm) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementForm.getTitle());
        announcement.setText(announcementForm.getText());

        // Установка текущей даты как даты создания
        announcement.setCreatedAt(new Date());

        MultipartFile image = announcementForm.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                byte[] imageData = image.getBytes();
                Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(imageData);
                announcement.setImageUrl(imageBlob);
            } catch (IOException | SQLException e) {
                // Обработка ошибки чтения изображения
            }
        }

        return announcementRepository.save(announcement);
    }
}