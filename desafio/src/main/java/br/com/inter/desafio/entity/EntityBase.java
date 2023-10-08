package br.com.inter.desafio.entity;

/**
 * Entidade base para todas as entidades com os atributos comuns 
 * Os mapeamentos pode ser sobreescritos no método get permitindo o reuso das classes
 * @author julio.silva
 */
public class EntityBase {

	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
