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
       
            // Permisos públicos para documentación y autenticación
            .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .requestMatchers("/api/auth/**").permitAll() 
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Registro
            .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll() // Ver perfiles
            .requestMatchers("/api/projects/**").permitAll() // Ver proyectos
            
            .anyRequest().authenticated() 
        )
        .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, userDetailsService), 
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        
          return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Se habilitan ambos orígenes: Local y Producción (Vercel)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200", 
            "https://icc-ppw-proyecto-portafolio.vercel.app"
        ));
        
        // Configuración de métodos y encabezados para JWT y API REST
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
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