package br.com.inter.desafio.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.inter.desafio.entity.PessoaFisica;

/**
 * Classe responsavel por acessar os dados da pessoa fisica 
 * @author julio.silva
 */
public interface PessoaFisicaRepository  extends JpaRepository<PessoaFisica, Long> {
	
	@Cacheable("pessoaFisica")
	@Query("SELECT pf FROM PessoaFisica pf WHERE pf.cpf = :cpf")
	public PessoaFisica buscarPessoaFisicaPorCPF(String cpf);
	

}
