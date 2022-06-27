package com.sungchul.etc.test.controller;


import com.sungchul.etc.common.ResponseAPI;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "TestController")
@RequestMapping("/test")
public class TestController {


    @GetMapping("/exception")
    public ResponseEntity<ResponseAPI> getReservationList(){
        ResponseAPI responseAPI = new ResponseAPI();

        int a=0;

        int b = a/0;


        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }
}
