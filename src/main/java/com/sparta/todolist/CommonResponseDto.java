package com.sparta.todolist;

import lombok.Getter;

@Getter
public class CommonResponseDto {

    private String msg;
    private Integer statusCode;

    public CommonResponseDto(String msg, int code) {
        this.msg=msg;
        this.statusCode=code;
    }
}
