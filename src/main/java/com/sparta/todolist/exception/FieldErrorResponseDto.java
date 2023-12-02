package com.sparta.todolist.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class FieldErrorResponseDto {
    private String msg;
    private Integer statusCode;
    private List<FieldErrorDto> fieldError;


    public FieldErrorResponseDto(String msg, int statusCode, List<FieldErrorDto> fieldErrorDtoList) {
        this.msg=msg;
        this.statusCode=statusCode;
        this.fieldError=fieldErrorDtoList;
    }
}
