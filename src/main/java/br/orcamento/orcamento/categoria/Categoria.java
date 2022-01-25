package br.orcamento.orcamento.categoria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	
	public Categoria() {}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
}
