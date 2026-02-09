package ec.edu.ups.icc.fundamentos01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ec.edu.ups.icc.fundamentos01.auth.JwtAuthenticationFilter;
import ec.edu.ups.icc.fundamentos01.auth.JwtUtils;

@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    // Se inyectan las dependencias necesarias para el filtro
    public SecurityConfig(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) 
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll() 
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll() 
            .requestMatchers("/api/projects/**").permitAll() 
            .anyRequest().authenticated() 
        )
        
        .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, userDetailsService), 
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        
    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}