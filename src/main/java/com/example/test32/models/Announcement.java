package com.example.test32.models;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "announcements")
@Data
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter the title")
    private String title;

    @NotBlank(message = "Please enter the text")
    private String text;

    @Lob
    @Column(name = "image_url")
    private Blob imageUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}

