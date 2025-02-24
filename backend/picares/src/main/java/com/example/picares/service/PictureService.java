package com.example.picares.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.picares.model.entity.Picture;

import jakarta.servlet.http.HttpServletRequest;

public interface PictureService {
    void uploadPicture(MultipartFile multipartFile, Picture picture, HttpServletRequest request);

    void updatePicture(Picture picture);

    void deletePicture(Long id);
}
