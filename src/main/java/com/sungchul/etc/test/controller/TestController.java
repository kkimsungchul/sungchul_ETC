package com.sungchul.etc.test.controller;


import com.sungchul.etc.common.ResponseAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@Api(tags = "TestController")
@RequestMapping("/test")
public class TestController {


    //http://localhost:8000/test/test/get1/dept/김성철
    @GetMapping("/test/get1/{hihi}/{byebye}")
    public void getTestPathVariable(@PathVariable String hihi , @PathVariable String byebye){
        System.out.println("### hihi : " + hihi);
        System.out.println("### byebye : " + byebye);
    }

    //http://localhost:8000/test/test/get2?hihi=zzz&byebye=ggg
    @GetMapping("/test/get2")
    public void getTestRequestParam(@RequestParam("hihi") String hihi , @RequestParam("byebye") String byebye){
        System.out.println("### hihi : " + hihi);
        System.out.println("### byebye : " + byebye);
    }
    //http://localhost:8000/test/test/get3?hihi=zzz
    @GetMapping("/test/get3")
    public void getTestHttpServletRequest(HttpServletRequest request){
        System.out.println("### hihi : " + request.getParameter("hihi"));
    }


    @GetMapping(path = "/test/get5/**")
    public void getImdsUserListBySearchValueForLoginId(HttpServletRequest request){
        String searchColumn = "dept";
        String searchValue = getRequestWithPath(request, searchColumn);

    }
    private String getRequestWithPath(HttpServletRequest request, String column) {
        System.out.println("### request.getContextPath() : " + request.getContextPath());
        String param = request.getRequestURI().split(request.getContextPath()+"/"+column+"/")[1];
        System.out.println("### param : " + param);
        try {
            param = URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("### param : " + param);
        return param;
    }

    @GetMapping("/test/{num}")
    public ResponseEntity<ResponseAPI> getReservationList(@PathVariable int num){

        ResponseAPI responseAPI = new ResponseAPI();
        HashMap<String,Object> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        try {
            for(int j=1;j<10;j++){
                list.add(num +" * " + j +" = " + (num*j));
            }
        }catch (Exception e){
            map.put("message" , "숫자만 입력해주세요");
        }
        map.put("list",list);

        responseAPI.setData(map);
        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseAPI> postTest(HashMap<String,Object> map){
        ResponseAPI responseAPI = new ResponseAPI();
        System.out.println(map);
        map.put("result" , "내가 데이터를 추가했다");
        map.put("method" , "이건 포스트 방식");

        responseAPI.setData(map);
        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }
    @GetMapping(value={"/get" , "/get/{value}"})
    public ResponseEntity<ResponseAPI> getTest(@PathVariable(required = false) String value){
        ResponseAPI responseAPI = new ResponseAPI();
        HashMap<String,Object> map = new HashMap<>();
        System.out.println(value);
        map.put("result" , "내가 데이터를 조회했다");
        map.put("method" , "이건 겟 방식");
        map.put("input", value);
        responseAPI.setData(map);
        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }


    @GetMapping(value={"/list"})
    public ResponseEntity<ResponseAPI> test(){
        ResponseAPI responseAPI = new ResponseAPI();
        HashMap<String,Object> map = new HashMap<>();
        ArrayList<String> list_1 = new ArrayList<>();
        ArrayList<String> list_2 = new ArrayList<>();
        for(int i=0;i<100;i++){
            list_1.add("김성철"+i);
        }
        for(int i=0;i<100;i++){
            list_2.add(i+"김성철");
        }

        map.put("list_1",list_1);
        map.put("list_2",list_2);

        responseAPI.setData(map);
        return new ResponseEntity(responseAPI , HttpStatus.OK);
    }
}
