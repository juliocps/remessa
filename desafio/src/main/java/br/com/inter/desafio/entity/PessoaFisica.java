package br.com.inter.desafio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="pessoa_fisica")
public class PessoaFisica extends Pessoa {
	
	private String cpf;
	private String sobrenome;

	@Column(length = 11, nullable = false)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	public Integer getId() {
		return id;
	}

	@Override
	@Column(length = 50, nullable = false)
	public String getNome() {
		return nome;
	}
	
	
	@Override
	@Column(length = 300, nullable = false)
	public String getEmail() {
		return email;
	}
	
	@Override
	@Column(length = 8, nullable = false)
	public String getSenha() {
		return senha;
	}

	@Column(length = 250, nullable = false)
	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
}
