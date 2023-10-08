package br.com.inter.desafio.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.inter.desafio.entity.PessoaJuridica;

/**
 * Classe responsavel por acessar os dados da pessoa juridica 
 * @author julio.silva
 */
public interface PessoaJuridicaRepository  extends JpaRepository<PessoaJuridica, Long> {
	
	@Cacheable("pessoaJuridica")
	@Query("SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = :cnpj")
	public PessoaJuridica buscarPessoaJuridicaPorCNPJ(String cnpj);

}
