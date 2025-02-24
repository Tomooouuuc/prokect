package com.example.picares.model.dto.picture;

import java.util.List;

import lombok.Data;

@Data
public class PictureUpdateDTO {
    private Long id;
    private String name;
    private String introduction;
    private String category;
    private List<String> tags;
}
