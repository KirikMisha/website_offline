package com.example.test32.models;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private byte[] content;
}
