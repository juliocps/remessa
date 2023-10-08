package br.com.inter.desafio.entity;

/**
 * Entidade Pessoa com os campos comum entre pessoa fisica e pessoa juridica. 
 * @author julio.silva
 */
public class Pessoa extends EntityBase {
	
	protected String nome;
	protected String email;
	protected String senha;
		
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Integer getId() {
		return super.getId();
	}
}
