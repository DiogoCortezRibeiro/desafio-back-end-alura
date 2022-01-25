package br.orcamento.orcamento.despesas;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import br.orcamento.orcamento.base.BaseEntity;
import br.orcamento.orcamento.categoria.Categoria;

@Entity
@Table(name = "despesa")
public class Despesa extends BaseEntity{
	
	@OneToOne
	private Categoria categoria;
	
	public Despesa() {}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
