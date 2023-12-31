package com.sparta.todolist.exception;

import com.sparta.todolist.CommonResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FieldErrorResponseDto extends CommonResponseDto {
    private List<FieldErrorDto> fieldError;


    public FieldErrorResponseDto(String msg, int statusCode, List<FieldErrorDto> fieldErrorDtoList) {
        super(msg,statusCode);
        this.fieldError=fieldErrorDtoList;
    }
}
