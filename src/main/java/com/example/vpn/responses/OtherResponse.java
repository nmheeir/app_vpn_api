package com.example.vpn.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

//tạo các response không liên quan
public class OtherResponse {

    public static ResponseEntity<Object> errorResponseBuilder(
            HttpStatus httpStatus,
            String message
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("isSuccess", false);
        map.put("httpStatus", httpStatus);
        map.put("message", message);

        return new ResponseEntity<>(map, httpStatus);
    }

    public static ResponseEntity<Object> successResponseBuilder(
            HttpStatus httpStatus,
            String message
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("isSuccess", true);
        map.put("httpStatus", httpStatus);
        map.put("message", message);

        return new ResponseEntity<>(map, httpStatus);
    }
}
