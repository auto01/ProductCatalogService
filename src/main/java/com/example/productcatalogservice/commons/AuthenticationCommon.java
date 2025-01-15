package com.example.productcatalogservice.commons;

import com.example.productcatalogservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationCommon {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public UserDto validateToken(String token) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        String authUrl = "http://localhost:8080/user/validate/" + token;
        System.out.println("authUrl:"+authUrl);
        try {
            UserDto userDto = restTemplate.getForObject(authUrl, UserDto.class);
            return userDto;

        }catch (Exception e){
            return null;
        }
    }
}
