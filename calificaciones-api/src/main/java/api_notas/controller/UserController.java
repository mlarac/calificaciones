package api_notas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import api_notas.model.User;
import api_notas.security.JwtProvider;
import api_notas.service.UserService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> credenciales, HttpServletResponse response) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        try {
            User user = userService.signin(username, password);
            String accessToken = jwtProvider.generateToken(user.getUsername(), user.getRole().toString());
            return ResponseEntity.ok(Map.of("token", accessToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        } catch (Exception ex) {
            ex.printStackTrace(); // Para depuración
            return ResponseEntity.status(500).body("Error en el servidor: " + ex.getMessage());
        }
    }
   
       

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try {
            User newUser = userService.signup(user);
            return ResponseEntity.status(200).body(newUser);

        } catch (Exception ex) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
