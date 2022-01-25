package br.orcamento.orcamento.despesas;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	@Query(value = "SELECT * FROM despesa WHERE (UPPER(descricao) = UPPER(:descricao) AND (month(data) = month(:data)))", nativeQuery = true)
	Despesa buscarPorDescricao(@Param("descricao") String descricao, @Param("data") Date data);

	Despesa findByDescricao(String descricao);
	
	@Query(value = "SELECT * FROM despesa WHERE (EXTRACT(MONTH FROM DATA)) = :mes AND (EXTRACT(YEAR FROM DATA)) = :ano", nativeQuery = true)
	List<Despesa> buscaPorMes(String ano, String mes);

}
