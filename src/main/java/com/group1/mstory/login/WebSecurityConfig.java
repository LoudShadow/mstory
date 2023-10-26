package com.group1.mstory.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

        @Bean
        public UserDetailsService userDetailsService() {
                return new UserDetailsServiceImpl();
        }

        @Bean
        public PasswordEncoder encoder() {
                // return new PasswordEnconderTest();
                return new BCryptPasswordEncoder(10);
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(encoder());
                provider.setUserDetailsService(userDetailsService());
                return provider;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests((requests) -> requests
                                                .requestMatchers("/resources/**").permitAll()
                                                .requestMatchers("/user/signup").permitAll()
                                                .requestMatchers("/login").permitAll()
                                                .requestMatchers("/signup").permitAll()
                                                .requestMatchers("userInteractions/login.html**").permitAll()
                                                .requestMatchers("userInteractions/signup.html**").permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .formLogin((form) -> form
                                                .permitAll()
                                                .loginPage("/login"))
                                .logout((logout) -> logout.permitAll());

                return http.build();
        }
}

