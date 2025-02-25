package com.example.picares.model.dto.picture;

import lombok.Data;

@Data
public class PictureUploadDTO {
    private String name;
    private String introduction;
    private String category;
    private String tags;
}