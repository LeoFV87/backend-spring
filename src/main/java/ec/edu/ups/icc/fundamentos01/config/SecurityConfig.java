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

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

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
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
        .csrf(csrf -> csrf.disable()) 
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
       
            //Permisos públicos
            .requestMatchers("/api/auth/**").permitAll() 
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Registro
            .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll() // Ver programadores/perfiles
            .requestMatchers("/api/projects/**").permitAll() // Ver proyectos públicamente
            
           
            .anyRequest().authenticated() 
        )
        .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, userDetailsService), 
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        
          return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permitimos el origen de tu proyecto Angular
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        // Permitimos los métodos comunes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Permitimos los encabezados necesarios (como Authorization para el JWT)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}