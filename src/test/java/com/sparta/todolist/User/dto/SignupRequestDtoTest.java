package com.sparta.todolist.User.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void test_SignupRequestDto_1() {
        // Given
        SignupRequestDto signupRequestDto = new SignupRequestDto("testuser", "testPassword");

        // When-Then
        assertEquals("testuser", signupRequestDto.getUsername());
        assertEquals("testPassword", signupRequestDto.getPassword());
        assertEquals(0, validator.validate(signupRequestDto).size());
    }

    @Test
    void test_SignupRequestDto_2() {
        // Given
        SignupRequestDto signupRequestDto = new SignupRequestDto("invalidUser", "short");

        // When-Then
        assertEquals(2, validator.validate(signupRequestDto).size());
    }
}