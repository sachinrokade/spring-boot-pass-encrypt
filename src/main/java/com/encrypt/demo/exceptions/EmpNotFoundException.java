package com.encrypt.demo.exceptions;

public class EmpNotFoundException extends RuntimeException{

    public EmpNotFoundException(String empNotFoundException){
        super(empNotFoundException);
    }
}
