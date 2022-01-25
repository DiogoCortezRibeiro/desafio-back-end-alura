package br.orcamento.orcamento.receitas;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{

	@Query(value = "SELECT * FROM receita WHERE (UPPER(descricao) = UPPER(:descricao) AND (month(data) = month(:data)))", nativeQuery = true)
	Receita buscarPorDescricao(@Param("descricao") String descricao, @Param("data") Date data);

	Receita findByDescricao(String descricao);

	@Query(value = "SELECT * FROM receita WHERE (EXTRACT(MONTH FROM DATA)) = :mes AND (EXTRACT(YEAR FROM DATA)) = :ano", nativeQuery = true)
	List<Receita> buscaPorMes(String ano, String mes);
}
