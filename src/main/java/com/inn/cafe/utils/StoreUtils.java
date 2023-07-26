package com.inn.cafe.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StoreUtils {

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus status) {
        return new ResponseEntity<>("{\"message\":\"" + responseMessage + "\"}", status);
    }
}
