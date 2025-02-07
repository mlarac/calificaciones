package api_notas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_notas.model.User;
import api_notas.repository.UserRepository;
@Service
public class UserService {

	//1. Método signin para autenticar usuario.
	//2. Método signup para registrar usuario.
	//3. Método loadUserByUsername para que se pueda verificar los atributos del
	//usuario.
	
	private UserRepository userRepository;	
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Implementación de métodos
    
    public User loadUserByUsername(String username) {
    	return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		}
    
    public User signin(String username, String password) throws BadCredentialsException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        return user;
    }

    public User signup(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    
	
}
