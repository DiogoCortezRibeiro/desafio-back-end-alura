package br.orcamento.orcamento.despesas;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.orcamento.orcamento.base.BaseController;
import br.orcamento.orcamento.categoria.CategoriaRepository;

@RestController
@RequestMapping("/despesa")
public class DespesaController extends BaseController<Despesa, DespesaRepository> {
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@PostMapping
	public ResponseEntity<HttpStatus> cadastrarDespesa(@RequestBody Despesa novaDespesa)
	{
		if(novaDespesa.getDescricao() == null || novaDespesa.getData() == null || novaDespesa.getValor() == null)
		{
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		if(novaDespesa.getCategoria() ==  null) {
			novaDespesa.setCategoria(categoriaRepository.findByDescricao("Outras"));
		}
		
		if(jaExisteDescricao(novaDespesa.getDescricao(), novaDespesa.getData()))
		{
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		this.despesaRepository.save(novaDespesa);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	private boolean jaExisteDescricao(String descricao, Date data) {
		Despesa despesa = this.despesaRepository.buscarPorDescricao(descricao, data);
		return despesa == null ? false : true;
	}
	
	@GetMapping("/buscarPorDescricao/{descricao}")
	public Despesa buscarPorDescricao(@PathVariable("descricao") String descricao)
	{
		Despesa despesa = null;
		despesa = despesaRepository.findByDescricao(descricao);
		return despesa;
	}
	
	@GetMapping("/{ano}/{mes}")
	public List<Despesa> getReceitasPorMes(@PathVariable("ano") String ano, @PathVariable("mes") String mes)
	{
		List<Despesa> despesas = null;
		despesas = despesaRepository.buscaPorMes(ano, mes);
		return despesas;
	}
}
