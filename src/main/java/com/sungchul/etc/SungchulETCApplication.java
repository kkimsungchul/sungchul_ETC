package com.sungchul.etc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SungchulETCApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(SungchulETCApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(SungchulETCApplication.class);
    }

}
