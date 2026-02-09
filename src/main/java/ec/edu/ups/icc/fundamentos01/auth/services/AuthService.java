package ec.edu.ups.icc.fundamentos01.auth.services;

import java.util.Map;

public interface AuthService {
    Map<String, String> login(String email, String password);
}