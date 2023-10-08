package br.com.inter.desafio.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidade que representa a Pessoa Juridica 
 * @author julio.silva
 */
@Entity
@Table(name="pessoa_juridica")
public class PessoaJuridica extends Pessoa{

	private String cnpj;
	
	@OneToMany(mappedBy = "pessoaJuridica")
    private List<CarteiraFisica> listaCarteira;

	@Column(length = 14, nullable = false, unique = true)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	public Integer getId() {
		return id;
	}

	@Override
	@Column(length = 300, nullable = false)
	public String getNome() {
		return nome;
	}
		
	@Override
	@Column(length = 100, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}
	
	@Override
	@Column(length = 8, nullable = false)
	public String getSenha() {
		return senha;
	}
	
}
