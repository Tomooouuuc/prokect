package com.example.picares.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class Test {
    public String test() {
        System.out.println("执行方法");
        return "test";
    }

    public static void main(String[] args) {
        File file = new File("./test/logo.jpg");
        try {
            InputStream input = new FileInputStream(file);

            BufferedImage image = ImageIO.read(input);

            // 输出图像的宽度和高度
            System.out.println("Width: " + image.getWidth());
            System.out.println("Height: " + image.getHeight());
            System.out.println("type:" + image.getPropertyNames());
            int argb = image.getRGB(0, 0);

            int alpha = (argb >> 24) & 0xff;
            System.out.println("Alpha value of the top-left pixel: " + alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // String[] readerFormats = ImageIO.getReaderFormatNames();
        // System.out.println("Supported reader formats:");
        // for (String format : readerFormats) {
        // System.out.println(format);
        // }

        // // 获取支持的写入格式
        // String[] writerFormats = ImageIO.getWriterFormatNames();
        // System.out.println("\nSupported writer formats:");
        // for (String format : writerFormats) {
        // System.out.println(format);
        // }
    }
}
