package com.example.test32.forms;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class AnnouncementForm {
    @NotNull
    private String title;

    @NotNull
    private String text;

    private MultipartFile image;
}
