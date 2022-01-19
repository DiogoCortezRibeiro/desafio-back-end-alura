package br.orcamento.orcamento.receitas;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceitaRepository extends JpaRepository<Receita, Integer>{

	@Query(value = "SELECT * FROM receita WHERE (UPPER(descricao) = UPPER(:descricao) AND (month(data) = month(:data)))", nativeQuery = true)
	Receita buscarPorDescricao(@Param("descricao") String descricao, @Param("data") Date data);
}
