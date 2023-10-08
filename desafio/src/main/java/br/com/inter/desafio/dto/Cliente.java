package br.com.inter.desafio.dto;

import br.com.inter.desafio.entity.CarteiraFisica;
import br.com.inter.desafio.entity.CarteiraJuridica;
import br.com.inter.desafio.entity.PessoaFisica;
import br.com.inter.desafio.entity.PessoaJuridica;

/**
 * DTO responsavel por mapear o cliente para realizar operacoes seja ela PF ou PJ simplificando 
 * @author julio.silva
 */
public class Cliente {
	
	private String tipoPessoa;
	
	PessoaFisica pessoaFisica = new PessoaFisica();
	PessoaJuridica pessoaJuridica = new PessoaJuridica();

	CarteiraFisica carteiraPF = new CarteiraFisica();
	CarteiraJuridica carteiraPJ = new CarteiraJuridica();
	
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}
	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}
	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	public CarteiraFisica getCarteiraPF() {
		return carteiraPF;
	}
	public void setCarteiraPF(CarteiraFisica carteiraPF) {
		this.carteiraPF = carteiraPF;
	}
	public CarteiraJuridica getCarteiraPJ() {
		return carteiraPJ;
	}
	public void setCarteiraPJ(CarteiraJuridica carteiraPJ) {
		this.carteiraPJ = carteiraPJ;
	}
}
