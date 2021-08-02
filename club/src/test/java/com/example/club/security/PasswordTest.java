package com.example.club.security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncoder(){

        String password = "1111";

        String enPw = passwordEncoder.encode(password);
        // 같은 비밀번호라도 매번 실행시 매번 다른 인코딩된 값을 가진다.

        System.out.println("enPw : "+enPw);

        boolean matchResult = passwordEncoder.matches(password, enPw);

        System.out.println("matchResult: "+matchResult);

    }

}
