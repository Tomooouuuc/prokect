package com.example.picares.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.picares.exception.BusinessException;
import com.example.picares.exception.ErrorCode;
import com.example.picares.exception.ThrowUtil;
import com.example.picares.mapper.PictureMapper;
import com.example.picares.model.entity.Picture;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.service.PictureService;
import com.example.picares.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PictureServiceImpl implements PictureService {

    @Resource
    private UserService userService;

    @Resource
    private PictureMapper pictureMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadPicture(MultipartFile multipartFile, Picture picture, HttpServletRequest request) {
        LoginUserVO user = userService.getLoginUser(request);
        ThrowUtil.throwIf(picture.getName() == null, ErrorCode.PARAMS_ERROR, "图片名称为空");
        ThrowUtil.throwIf(picture.getName().length() > 64, ErrorCode.PARAMS_ERROR, "图片名字过长");
        ThrowUtil.throwIf(picture.getIntroduction().length() > 256, ErrorCode.PARAMS_ERROR);
        ThrowUtil.throwIf(picture.getCategory().length() > 128, ErrorCode.PARAMS_ERROR);
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());

            long size = multipartFile.getSize();
            int width = image.getWidth();
            int height = image.getHeight();
            String type = getImageFormat(multipartFile.getInputStream());
            System.out.println("图片类型：" + type);

            String uploadDir = "images" + File.separator + user.getUserAccount();
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            long now = Instant.now().toEpochMilli();
            String url = uploadDir + File.separator + now + "." + type;

            File dest = new File(url);
            System.out.println("图片路径：" + dest);

            picture.setUrl(url);
            picture.setPicSize(size);
            picture.setPicWidth(width);
            picture.setPicHeight(height);
            picture.setPicScale(width * 1.0 / height);
            picture.setPicFormat(type);
            picture.setUserId(user.getId());

            pictureMapper.upload(picture);

            ImageIO.write(image, type, dest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "图片上传失败");
        }
    }

    private String getImageFormat(InputStream inputStream) throws IOException {
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            if (!readers.hasNext()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "无法识别的图片格式");
            }
            ImageReader reader = readers.next();
            return reader.getFormatName().toLowerCase();
        }
    }

    @Override
    public void updatePicture(Picture picture) {
        ThrowUtil.throwIf(picture.getName() == null, ErrorCode.PARAMS_ERROR, "图片名字为空");
        ThrowUtil.throwIf(picture.getName().length() > 64, ErrorCode.PARAMS_ERROR, "图片名字过长");
        ThrowUtil.throwIf(picture.getIntroduction().length() > 256, ErrorCode.PARAMS_ERROR);
        ThrowUtil.throwIf(picture.getCategory().length() > 128, ErrorCode.PARAMS_ERROR);
        try {
            pictureMapper.update(picture);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void deletePicture(Long id) {
        try {
            pictureMapper.delete(id);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

}
