package com.example.picares.model.dto.picture;

import lombok.Data;

@Data
public class PictureUploadDTO {
    private String url;
    private String name;
    private String introduction;
    private String category;
    private String tags;
    private Long picSize;
    private Integer picWidth;
    private Integer picHeight;
    private Double picScale;
    private String picFormat;
    private Long userId;
}