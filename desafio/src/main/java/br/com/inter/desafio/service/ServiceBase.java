package br.com.inter.desafio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

/**
 * Classe responsavel por implementar as regras que podem ser utilizadas por mais de um service
 * @author julio.silva
 */
public class ServiceBase {

	public final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public Gson gson;
	
	protected ResponseEntity<String> executarRequisicaoGet(String url, HttpHeaders header) {
		RestTemplate restTemplate= new RestTemplate();		
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).build();		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, String.class);
		return response;
	}
}
