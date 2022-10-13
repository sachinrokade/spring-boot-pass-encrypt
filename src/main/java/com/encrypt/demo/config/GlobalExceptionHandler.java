package com.encrypt.demo.config;

import com.encrypt.demo.exceptions.CustomsException;
import com.encrypt.demo.exceptions.EmpNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmpNotFoundException.class)
    public ResponseEntity empNotFound(WebRequest web,Exception exp){

        CustomsException customsException=new CustomsException();
        customsException.setMsg(exp.getMessage());
        customsException.setTimestamp(new Date().toString());
        customsException.setStatusCode(HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity(customsException, HttpStatus.NOT_FOUND);
    }
}
