package com.example.picares.common;

import com.example.picares.constant.CommonConstant;
import com.example.picares.exception.BusinessException;
import com.example.picares.exception.ErrorCode;

import lombok.Data;

@Data
public class PageRequest {
    private int current = 1;
    private int pageSize = 10;
    private String sortField;
    private String sortOrder = "descend";

    public void setSortOrder(String sortOrder) {
        System.out.println("调用了setter");
        if (CommonConstant.SORT_ORDER_ASC.equals(sortOrder)) {
            this.sortOrder = "ASC";
        } else if (CommonConstant.SORT_ORDER_DESC.equals(sortOrder)) {
            this.sortOrder = "DESC";
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}
