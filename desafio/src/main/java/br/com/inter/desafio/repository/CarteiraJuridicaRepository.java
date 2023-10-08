package br.com.inter.desafio.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.inter.desafio.entity.CarteiraJuridica;
import br.com.inter.desafio.entity.PessoaJuridica;

/**
 * Classe responsavel por acessar os dados da pessoa juridica 
 * @author julio.silva
 */
public interface CarteiraJuridicaRepository  extends JpaRepository<CarteiraJuridica, Long> {
	
	@Cacheable("carteiraJuridica")
	@Query("SELECT c FROM CarteiraJuridica c WHERE c.pessoaJuridica = :pj and c.moeda = :moeda")
	public CarteiraJuridica buscarCarteiraPessoaJuridica(PessoaJuridica pj, String moeda);
	

}
