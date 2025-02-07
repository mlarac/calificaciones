package notas_front.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import notas_front.dto.AlumnoDTO;

@Service
public class AlumnoService {
	@Autowired
	private RestTemplate restTemplate;
	private static final String BASE_URL = "http://localhost:3000/api";
	
	 public List<AlumnoDTO> findAll(String authToken) {
	        String url = BASE_URL + "/alumnos";
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer " + authToken);
	        HttpEntity<String> entity = new HttpEntity<>(headers);

	        return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AlumnoDTO>>() {
	        }).getBody();
	    }
	

}
