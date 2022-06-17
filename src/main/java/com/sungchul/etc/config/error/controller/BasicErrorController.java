package com.sungchul.etc.config.error.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("${server.error.path:${error.path:/error}}") // server.error.path를 불러오고, error.path를 불러오고 없을 경우 '/error'로 설정한다.
public class BasicErrorController extends AbstractErrorController {

    @Value("${server.error.path:${error.path:/error}}")
    private String ERROR_PATH;

    /**
     * @param errorAttributes
     */
    public BasicErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    // 페이지 요청
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView pageError(HttpServletRequest request, HttpServletResponse response) {
        // request에서 status를 가져옴
        HttpStatus status = getStatus(request);
        //ErrorAttributeOptions 객체 선언
        ErrorAttributeOptions options = ErrorAttributeOptions
                                        .defaults()
                                        .including(ErrorAttributeOptions.Include.MESSAGE);
        // 발생 시간, status code, message를 가져옴
        Map<String, Object> model = getErrorAttributes(request,options);

        // error code 설정
        response.setStatus(status.value());

        return new ModelAndView(ERROR_PATH, model);
    }

    // RestAPI요청
    @RequestMapping
    public ResponseEntity<Map<String, Object>> apiError(HttpServletRequest request) {
        // request에서 status를 가져옴
        HttpStatus status = getStatus(request);
        //ErrorAttributeOptions 객체 선언
        ErrorAttributeOptions options = ErrorAttributeOptions
                .defaults()
                .including(ErrorAttributeOptions.Include.MESSAGE);
        // 발생 시간, status code, message를 가져옴
        Map<String, Object> model = getErrorAttributes(request, options);

        return new ResponseEntity<>(model, status);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> mediaTypeNotAcceptable(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return ResponseEntity.status(status).build();
    }


}