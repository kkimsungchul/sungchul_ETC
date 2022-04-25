package com.sungchul.etc.findtext.controller;

import com.sungchul.etc.common.ResponseAPI;
import com.sungchul.etc.findtext.service.FindTextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.rmi.ServerError;
import java.util.HashMap;


@Slf4j
@RestController
@Api(tags = "FindTextController")
@RequestMapping("/findText")
public class FindTextController {

    @Autowired
    FindTextService findTextService;


    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = ResponseAPI.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    @ApiOperation(value="파일 업로드", notes="사용자의 txt 파일을 업로드 함")
    @PostMapping(value="uploadFile")
    public ResponseEntity<ResponseAPI> uploadFile(MultipartFile file) throws IllegalStateException, IOException {
        ResponseAPI responseAPI = new ResponseAPI();
        String returnMessage;
        if( !file.isEmpty() ) {
            returnMessage = findTextService.fileUpload(file);
            responseAPI.setMessage(returnMessage);
        }else{
            returnMessage="파일이 없습니다.";
            responseAPI.setMessage(returnMessage);
        }

        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = ResponseAPI.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    @ApiOperation(value="파일 목록가져오기", notes="로그인한 사용자의 업로드한 파일 목록을 가져옴")
    @GetMapping(value="files")
    public ResponseEntity<ResponseAPI> getFileList() throws IllegalStateException, IOException {
        ResponseAPI responseAPI = new ResponseAPI();
        responseAPI.setData(findTextService.getFileList());
        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = ResponseAPI.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    @ApiOperation(value="파일 분석", notes="선택한 파일의 대화내용을 분석해서 보여줌")
    @GetMapping(value="files/{saveFileName}")
    public ResponseEntity<ResponseAPI> getFindText(@PathVariable("saveFileName") String saveFileName) throws IllegalStateException, IOException {
        ResponseAPI responseAPI = new ResponseAPI();
        HashMap<String,Object> resultMap =findTextService.getFindText(saveFileName);
        if(resultMap.isEmpty()){
            String returnMessage;
            returnMessage="파일이 없습니다.";
            responseAPI.setMessage(returnMessage);
        }else{
            responseAPI.setData(resultMap);
        }

        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }



}
