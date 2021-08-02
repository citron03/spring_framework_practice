package com.example.club.config;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // PasswordEncoder는 인터페이스, BCryptPasswordEncoder는 스프링 시큐리티에서 제공하는
    // PasswordEncoder 클레스이다. 특정한 문자열이 암호화된 결과인지만을 확인할 수 있어 원본 내용을 볼 수 없어 선호되는 클래스이다.
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 스프링 시큐리티를 이용해 특정한 리소스에 접근 제한을 한다.
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
        // 로그인 없이 접근 가능
                .antMatchers("/sample/member").hasRole("USER");
        // 로그인 해야 접근 가능

        http.formLogin(); // 인가나 인증에 문제가 생겼을 시 로그인 화면으로 간다.
        http.csrf().disable(); // 외부에서 REST 방식으로 이용할 수 있는 보안 설정을 다루기 위해 csrf토큰을 발행하지 않는다.
        http.logout(); // csrf 토큰을 사용하지 않아서 form 태그 방식이 아니라 GET방식으로 로그아웃이 처리된다.

        // 별도의 로그인 페이지 디자인을 사용하기 위해서는 loginPage()를 사용한다.
    }

    /*

    // Bean에 등록된 UserDetailService를 자동으로 스프링 시큐리티가 인식하기 때문에 이 코드는 사용하지 않는다.

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        // 사용자 계정은 user1
        auth.inMemoryAuthentication().withUser("user1")
        // 1111의 인코딩 결과
                .password("")
                .roles("USER");

    }

     */

}
