package com.example.picares.test;

import lombok.Data;

@Data
public class TestUser {
    private String sortField;
    private String sortOrder = "descend";

    public static void main(String[] args) {
        int i = 5;
        int j = 2;
        System.out.println(i * 1.0 / j);
    }
}
