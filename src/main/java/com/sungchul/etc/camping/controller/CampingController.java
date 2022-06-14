package com.sungchul.etc.camping.controller;


import com.sungchul.etc.camping.service.CampingService;
import com.sungchul.etc.common.ResponseAPI;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "CampingController")
@RequestMapping("/camping")
public class CampingController {


    @Autowired
    CampingService campingService;

    @GetMapping("")
    public ResponseEntity<ResponseAPI> getReservationList(){
        ResponseAPI responseAPI = new ResponseAPI();

        campingService.getReservationList();

        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }

}
