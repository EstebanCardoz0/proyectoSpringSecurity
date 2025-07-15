package com.example.springsecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults()) //se usa solo en caso de loguear con user y pass
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(http -> {
//                    //endpoints publicos
//                    http.requestMatchers(HttpMethod.GET, "/holaNoSeg").permitAll();
//                    http.requestMatchers(HttpMethod.GET, "/holaSeg").hasAnyAuthority("READ");
//                    http.anyRequest().denyAll();
//    }).
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List userDetailsList = new ArrayList<>();

        userDetailsList.add(User.withUsername("esteban").password("123")// esto si no está codificado, sino tendrá que seguir el algoritmo de codificación
                .roles("ADMIN").authorities("CREATE", "READ", "UPDATE", "DELETE").build());

        userDetailsList.add(User.withUsername("seguidor").password("123").roles("USER").authorities("READ").build());

        userDetailsList.add(User.withUsername("actualizador").password("123").roles("USER").authorities("UPDATE").build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }


//    @Bean
//    public SecurityFilterChain apiNotas(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.authorizeHttpRequests().anyRequest().authenticated().and().formLogin().permitAll().and().build();
//    }

}
