package com.example.picares.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.picares.annotation.AuthCheck;
import com.example.picares.common.BaseResponse;
import com.example.picares.common.DeleteRequest;
import com.example.picares.common.ResultUtil;
import com.example.picares.constant.UserConstant;
import com.example.picares.exception.ErrorCode;
import com.example.picares.exception.ThrowUtil;
import com.example.picares.model.dto.picture.PictureUpdateDTO;
import com.example.picares.model.dto.picture.PictureUploadDTO;
import com.example.picares.model.entity.Picture;
import com.example.picares.service.PictureService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private PictureService pictureService;

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deletePicture(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtil.throwIf(deleteRequest == null || deleteRequest.getId() == null, ErrorCode.PARAMS_ERROR);

        pictureService.deletePicture(deleteRequest.getId());
        return ResultUtil.success(true);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updatePicture(@RequestBody PictureUpdateDTO pictureUpdateDTO) {
        ThrowUtil.throwIf(pictureUpdateDTO == null, ErrorCode.PARAMS_ERROR);
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureUpdateDTO, picture);
        pictureService.updatePicture(picture);
        return ResultUtil.success(true);
    }

    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<Boolean> uploadUser(@RequestParam("image") MultipartFile multipartFile,
            @ModelAttribute PictureUploadDTO pictureUploadDTO,
            HttpServletRequest request) {
        ThrowUtil.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR);
        ThrowUtil.throwIf(pictureUploadDTO == null, ErrorCode.PARAMS_ERROR);

        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureUploadDTO, picture);

        pictureService.uploadPicture(multipartFile, picture, request);

        return ResultUtil.success(true);
    }

    @GetMapping("/avatar/{path}")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public void getAvatar(@PathVariable String path, HttpServletResponse response) {
        getTypePictrue(path, "./avatar/", response);
    }

    @GetMapping("/image/{path}")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public void getImage(@PathVariable String path, HttpServletResponse response)
            throws IOException {
        getTypePictrue(path, "./images/", response);
    }

    private void getTypePictrue(String path, String type, HttpServletResponse response) {
        try {
            String imagePath = type + path;
            File imageFile = new File(imagePath);
            Files.copy(imageFile.toPath(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}