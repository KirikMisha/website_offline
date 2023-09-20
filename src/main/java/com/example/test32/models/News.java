package com.example.test32.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "news")
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter the title")
    private String title;

    @Lob
    @Column(name = "text", columnDefinition = "CLOB")
    private String text;

    @Lob
    @Column(name = "image_url")
    private Blob imageUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
