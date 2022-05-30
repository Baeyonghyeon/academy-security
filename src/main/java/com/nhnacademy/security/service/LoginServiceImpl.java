package com.nhnacademy.security.service;

import com.nhnacademy.security.entitiy.Member;
import com.nhnacademy.security.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public boolean isLogin(String id, String pwd) {
        Optional<Member> member = loginRepository.findById(id);

        return member.get().getPwd().equals(pwd);
    }
}
