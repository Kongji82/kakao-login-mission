package org.example.kakao_login.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class ViewController {

    private static final String REST_API_KEY = "231b50a8a0f1a755fb79ac44a527b81c";
    @RequestMapping("/login")
    public String index(Model model){
        log.info("[test!!]");
        String location = "/login/oauth2/kakao";
        model.addAttribute("location", location);
        return "index.html";
    }

    @GetMapping("/userinfo")
    public String userinf(@SessionAttribute(name = "userId", required = false) Long userId, Model model){
        if (userId == null){
            throw new RuntimeException("Session not found");
        }
        model.addAttribute("userid", userId);
        return "userinfo.html";
    }

}
