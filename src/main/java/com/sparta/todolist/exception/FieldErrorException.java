package com.sparta.todolist.exception;

import lombok.Getter;

@Getter
public class FieldErrorException extends RuntimeException{
    private final FieldErrorResponseDto fieldErrorResponseDto;
    public FieldErrorException(FieldErrorResponseDto fieldErrorResponseDto) {
        super(fieldErrorResponseDto.getMsg());
        this.fieldErrorResponseDto=fieldErrorResponseDto;
    }
}
