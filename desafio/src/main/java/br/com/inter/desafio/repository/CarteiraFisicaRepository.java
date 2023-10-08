package br.com.inter.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.inter.desafio.entity.CarteiraFisica;
import br.com.inter.desafio.entity.PessoaFisica;

/**
 * Classe responsavel por acessar os dados da pessoa juridica 
 * @author julio.silva
 */
public interface CarteiraFisicaRepository  extends JpaRepository<CarteiraFisica, Long> {
	
	@Query("SELECT c FROM CarteiraFisica c WHERE c.pessoaFisica = :pf and c.moeda = :moeda")
	public CarteiraFisica buscarCarteiraPessoaFisica(PessoaFisica pf, String moeda);
	
}
