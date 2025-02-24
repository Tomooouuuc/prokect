package com.example.picares.mapper;

import org.apache.ibatis.annotations.Update;

import com.example.picares.model.entity.Picture;

public interface PictureMapper {
    void upload(Picture picture);

    void update(Picture picture);

    @Update("update picture set isDelete=1 where id=#{id}")
    void delete(Long id);
}
