package notas_front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import notas_front.dto.AlumnoDTO;
import notas_front.service.AlumnoService;
import notas_front.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/")
    public String loginForm() {
        return "login";
    }


    
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            HttpServletResponse response,
            Model model) {
        try {
            logger.info("Iniciando proceso de login para el usuario: {}", username);
            
            String accessToken = userService.signin(username, password);
            if (accessToken == null || accessToken.isEmpty()) {
                throw new IllegalStateException("El token de acceso es nulo o vacío");
            }
            
            logger.info("Token de acceso obtenido correctamente");
         // Codificar el token para asegurar que no contenga caracteres inválidos
            String encodedToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8.toString());
            session.setAttribute("accessToken", encodedToken);	
            logger.info("Token de acceso almacenado en la sesión");
    
            try {
                
                
                Cookie cookie = new Cookie("accessToken", encodedToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60);
    
                response.addCookie(cookie);
                logger.info("Cookie creada y añadida a la respuesta");
            } catch (Exception e) {
                logger.error("Error al crear la cookie: {}", e.getMessage());
                throw new RuntimeException("Error al crear la cookie", e);
            }
    
            return "redirect:/home";
        } catch (Exception e) {
            logger.error("Error durante el inicio de sesión", e);
            String errorMessage = "Error de inicio de sesión: " + e.getMessage();
            model.addAttribute("error", errorMessage);
            return "login";
        }
    }
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        String encodedToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    encodedToken = cookie.getValue();
                    break;
                }
            }
        }

        if (encodedToken != null) {
            try {
                // Decodificar el token
                String decodedToken = URLDecoder.decode(encodedToken, StandardCharsets.UTF_8.toString());
                
                // Usar el token decodificado para obtener la lista de alumnos
                List<AlumnoDTO> listaAlumnos = alumnoService.findAll(decodedToken);
                model.addAttribute("alumnos", listaAlumnos);
                return "home"; // Return the view name for the home page
            } catch (Exception e) {
                logger.error("Error al decodificar el token o obtener la lista de alumnos", e);
                return "redirect:/";
            }
        } else {
            logger.warn("No se encontró el token en las cookies");
            return "redirect:/";
        }
    }
   }

