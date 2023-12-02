package com.sparta.todolist.exception;

import com.sparta.todolist.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CommonResponseDto> IllegelArgumentExceptionHandler(IllegalArgumentException ex) {
        CommonResponseDto commonResponseDto = new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(commonResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<CommonResponseDto> NoSuchElementExceptionHandler(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(ex.getMessage(),HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<CommonResponseDto> UnAuthorizedException(UnAuthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResponseDto(ex.getMessage(),HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler({FieldErrorException.class})
    public ResponseEntity<FieldErrorResponseDto> FieldErrorException(FieldErrorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new FieldErrorResponseDto(
                        ex.getMessage(),
                        ex.getFieldErrorResponseDto().getStatusCode(),
                        ex.getFieldErrorResponseDto().getFieldError()
                ));
    }
}
