package br.com.inter.desafio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

/**
 * Classe responsavel por implementar as regras que podem ser utilizadas por mais de um service
 * @author julio.silva
 */
public class ServiceBase {

	public final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public Gson gson;
}
