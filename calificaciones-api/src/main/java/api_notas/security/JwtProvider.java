package api_notas.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtProvider {

    // accedemos a la variable de entorno SECRET_KEY desde el archivo
    // application.properties
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long expirationToken;

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // identificador de usuario
                .claim("role", role) // añadimos el rol.
                .setIssuedAt(new Date()) // fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + expirationToken)) // tiempo de expiración
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256) // firmamos con la llave
                                                                                              // // secreta
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())) // validamos con la misma clave secreta.
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }

    }

}
