package com.encrypt.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomsException {

    String msg;
    String timestamp;
    String statusCode;
}
