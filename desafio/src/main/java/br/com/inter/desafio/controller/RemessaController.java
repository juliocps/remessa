package br.com.inter.desafio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.inter.desafio.dto.Entrada;
import br.com.inter.desafio.dto.Retorno;

@Controller
@RequestMapping("/remessa")
public class RemessaController extends ControllerBase {
	
	@ResponseBody
    @PostMapping(produces = "application/json;charset=UTF-8")
    public Retorno registrarEntradaSadi(@RequestBody Entrada dto) {
         try {
             return null;
         } catch(Exception e) {
             log.error(e.getMessage());
             return null;
         }
    } 

}
