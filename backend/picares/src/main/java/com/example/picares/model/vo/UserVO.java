package com.example.picares.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class UserVO {
    List<GetUserVO> records;
    Integer total;
}
