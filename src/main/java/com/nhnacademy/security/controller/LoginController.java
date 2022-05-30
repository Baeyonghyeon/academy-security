package com.nhnacademy.security.controller;

import com.nhnacademy.security.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping("/login")
    public String login(HttpSession session) {

        String id = (String) session.getAttribute("id");
        if (Objects.isNull(id)) {
            return "success";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String id
            , @RequestParam String pwd
            , HttpServletRequest request) {

        if (loginService.isLogin(id, pwd)) {
            HttpSession session = request.getSession();
            session.setAttribute("id", id);
        }

        return "redirect:/";
    }

}
