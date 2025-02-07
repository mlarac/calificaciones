package notas_front.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:3000/auth";

    public String signin(String username, String password) {
        String url = BASE_URL + "/signin";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(credentials, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // Devuelve el token de acceso
            } else {
                throw new RuntimeException("Error al iniciar sesión: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            // Manejar el error de forma adecuada
            throw new RuntimeException("Error al iniciar sesión: " + e.getMessage(), e);
        }
    }
}