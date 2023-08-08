package com.example.test32.forms;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class NewsForm {
    @NotNull
    private String title;

    @NotNull
    private String text;

    private MultipartFile image;

}
