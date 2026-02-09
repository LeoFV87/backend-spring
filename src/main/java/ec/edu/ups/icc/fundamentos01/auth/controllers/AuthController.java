package ec.edu.ups.icc.fundamentos01.auth.controllers;

import ec.edu.ups.icc.fundamentos01.auth.services.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        return authService.login(
            credentials.get("email"), 
            credentials.get("password")
        );
    }
}