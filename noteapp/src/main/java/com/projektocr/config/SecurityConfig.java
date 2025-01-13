//package com.projektocr.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
////
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Wyłączenie CSRF (jeśli potrzebne, np. do API)
//                .formLogin(form -> form
//                        .loginPage("/login-page") // Ustawienie niestandardowej strony logowania
//                        .defaultSuccessUrl("/index", true) // Przekierowanie po poprawnym logowaniu
//                        .permitAll()
//                )
//                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/**").permitAll()
////                        .requestMatchers("/**", "/index", "/ocr").permitAll()
////                        .requestMatchers("/register", "/css/**", "/js/**").permitAll() // Dostęp dla rejestracji i zasobów statycznych
//                        .anyRequest().permitAll() // Wszystkie inne wymagają zalogowania
//                )
//
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // URL do wylogowania
//                        .logoutSuccessUrl("/login") // Przekierowanie po wylogowaniu
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
