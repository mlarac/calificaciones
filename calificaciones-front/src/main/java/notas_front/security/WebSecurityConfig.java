package notas_front.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/home").permitAll()
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/").permitAll()
                        .defaultSuccessUrl("/home", true));

        return http.build();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}