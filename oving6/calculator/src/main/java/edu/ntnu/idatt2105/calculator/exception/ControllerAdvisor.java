package edu.ntnu.idatt2105.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class) // Fix typo
    public ResponseEntity<Object> invalidExpression(IllegalArgumentException e, WebRequest webRequest) { // Fix typo
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message: ", "Invalid expression");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
}
