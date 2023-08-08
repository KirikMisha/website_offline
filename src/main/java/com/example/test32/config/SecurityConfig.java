//package com.example.test32.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@Order(1)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .antMatcher("/news/add")
//                .authorizeRequests()
//                .anyRequest().hasIpAddress("ВАШ_IP_АДРЕС")
//                .and()
//                .formLogin() // Если вы хотите использовать форму входа
//                .loginPage("/login") // Укажите путь к странице входа
//                .permitAll()
//                .and()
//                .csrf().disable(); // Отключение CSRF (если необходимо)
//    }
//}
//
