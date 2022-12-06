package com.dealerapp.validation;

import com.dealerapp.validation.exceptions.CarModelAlreadyExistsException;
import com.dealerapp.validation.exceptions.ConfigurationAlreadyExists;
import com.dealerapp.validation.exceptions.EntityNotFoundException;
import com.dealerapp.validation.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ValidationErrorResponse onUserAlreadyExistsException(
            UserAlreadyExistsException e
    ) {
        List<Violation> violations = new ArrayList<>();
        violations.add(new Violation("User Create", e.getMessage()));
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CarModelAlreadyExistsException.class)
    public ValidationErrorResponse onCarModelAlreadyExistsException(
            CarModelAlreadyExistsException e
    ) {
        List<Violation> violations = new ArrayList<>();
        violations.add(new Violation("Car Create", e.getMessage()));
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConfigurationAlreadyExists.class)
    public ValidationErrorResponse onConfigurationAlreadyExistsException(
            ConfigurationAlreadyExists e
    ) {
        List<Violation> violations = new ArrayList<>();
        violations.add(new Violation("Configuration Create", e.getMessage()));
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ValidationErrorResponse onEntityNotFoundException(
            EntityNotFoundException e
    ) {
        List<Violation> violations = new ArrayList<>();
        violations.add(new Violation("Entity Get", e.getMessage()));
        return new ValidationErrorResponse(violations);
    }
}

