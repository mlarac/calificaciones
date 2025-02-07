package api_notas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Excluimos rutas publicas (no necesitan de token)
        String path = request.getRequestURI();

        if (path.equals("/auth/signin") ||
                path.equals("/auth/signup")) {
            filterChain.doFilter(request, response); // dejamos continuar la petición
            return;
        }

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer", "").trim();

            if (jwtProvider.validateToken(token)) {
                // aqui podemos validar datos del token, extrayendola para establecer un
                // contexto de autenticacion (para que spring security se entere.)
                String username = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                String role = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .get("role", String.class);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singletonList(new SimpleGrantedAuthority(role)));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("====> El token no es valido");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("====> Falta el header Authorization");
            return;
        }

        filterChain.doFilter(request, response); // dejamos continuar la petición

    }

}
