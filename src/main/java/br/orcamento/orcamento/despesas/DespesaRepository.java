package br.orcamento.orcamento.despesas;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

	@Query(value = "SELECT * FROM despesa WHERE (UPPER(descricao) = UPPER(:descricao) AND (month(data) = month(:data)))", nativeQuery = true)
	Despesa buscarPorDescricao(@Param("descricao") String descricao, @Param("data") Date data);

}
