package com.example.picares.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.picares.common.BaseResponse;
import com.example.picares.common.ResultUtil;
import com.example.picares.exception.BusinessException;
import com.example.picares.exception.ErrorCode;
import com.example.picares.mapper.UserMapper;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test")
public class MyController {
    @Resource
    private UserMapper userMapper;

    @PostMapping("/upload/image")
    public BaseResponse<Boolean> uploadImage(@RequestParam("avatar") MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        try {
            BufferedImage image = ImageIO.read(inputStream);

            int width = image.getWidth();
            System.out.println("width:" + width);

            int height = image.getHeight();
            System.out.println("height:" + height);

            long size = multipartFile.getSize();
            System.out.println("图片的大小：" + size);
            // 我想在这个获得图片的大小
            long size1 = getSize(image);
            System.out.println("根据像素获取的大小：" + size1);

            String type = getImageFormat(multipartFile.getInputStream());
            System.out.println("图片类型：" + type);
            long size2 = getSize1(image, type);
            System.out.println("转换为inputstream获取的图片大小：" + size2);

            long size3 = getSize1(image, "jpg");
            System.out.println("jpg格式的大小是：" + size3);

            String uploadDir = "images";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File dest = new File(dir.getAbsolutePath() + File.separator + "apple" + "." + type);
            System.out.println("图片路径：" + dest);
            BufferedImage image2 = ImageIO.read(inputStream);
            if (image2 == null) {
                System.out.println("image2创建失败");
            } else {
                System.out.println("image2创建成功" + image2);
            }
            ImageIO.write(image, type, dest);
            BufferedImage image3 = ImageIO.read(inputStream);
            if (image2 == null) {
                System.out.println("image3创建失败");
            } else {
                System.out.println("image3创建成功" + image3);
            }
            return ResultUtil.success(true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "图片上传失败");
        }
    }

    private long getSize1(BufferedImage image, String formatName) throws IOException {
        if (image == null) {
            return 0;
        }
        // 创建一个 ByteArrayOutputStream 用于存储图片数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 将 BufferedImage 写入 ByteArrayOutputStream
        ImageIO.write(image, formatName, outputStream);
        // 获取 ByteArrayOutputStream 中的字节数组
        byte[] imageBytes = outputStream.toByteArray();
        // 创建一个 ByteArrayInputStream 用于后续可能的读取操作
        // InputStream inputStream = new ByteArrayInputStream(imageBytes);
        // 图片大小即为字节数组的长度
        return imageBytes.length;
    }

    private long getSize(BufferedImage image) {
        // 获取图片image的大小
        if (image == null) {
            return 0;
        }
        // 获取图片的宽度
        int width = image.getWidth();
        // 获取图片的高度
        int height = image.getHeight();
        // 获取图片每像素所占用的位数
        int bitsPerPixel = image.getColorModel().getPixelSize();
        // 计算图片像素数据所需的总位数
        long totalBits = (long) width * height * bitsPerPixel;
        // 将总位数转换为字节数
        return (totalBits + 7) / 8;
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

    @PostMapping("/upload")
    public BaseResponse<Boolean> upload(@RequestParam("avatar") MultipartFile multipartFile) {
        System.out.println("接收数据");
        System.out.println(multipartFile == null);
        System.out.println(multipartFile);
        System.out.println("接收数据完成");
        try {
            String uploadDir = "images";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = multipartFile.getOriginalFilename();
            File dest = new File(dir.getAbsolutePath() + File.separator + fileName);
            System.out.println("图片路径：" + dest);
            multipartFile.transferTo(dest);
            return ResultUtil.success(true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "图片上传失败");
        }
    }

    @GetMapping("/image/{path}")
    public void getImage(@PathVariable String path, HttpServletResponse response) throws IOException {
        System.out.println("path的值：" + path);
        System.out.println("开始加载图片" + path);
        try {
            // 图片文件的路径，这里假设图片存放在项目根目录下的 images 文件夹中
            String imagePath = "./images/" + path;
            System.out.println(imagePath);
            File imageFile = new File(imagePath);

            // 检查文件是否存在

            // 读取图片文件内容
            InputStream inputStream = new FileInputStream(imageFile);
            byte[] imageBytes = new byte[(int) imageFile.length()];
            inputStream.read(imageBytes);
            inputStream.close();

            // 设置响应头
            response.setContentType("abcdefg"); // 根据实际图片类型设置
            response.setContentLength(imageBytes.length);
            response.setHeader("Connection", "tcccct");
            Files.copy(imageFile.toPath(), response.getOutputStream());

            // 返回响应实体
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
