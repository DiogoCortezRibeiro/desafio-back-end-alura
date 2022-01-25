package br.orcamento.orcamento.receitas;

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

@RestController
@RequestMapping("/receita")
public class ReceitaController extends BaseController<Receita, ReceitaRepository> {
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@GetMapping("/buscarPorDescricao/{descricao}")
	public Receita buscarPorDescricao(@PathVariable("descricao") String descricao)
	{
		Receita receita = null;
		receita = receitaRepository.findByDescricao(descricao);
		return receita;
	}
	
	@PostMapping
	public ResponseEntity<HttpStatus> inserirReceita(@RequestBody Receita novaReceita)
	{
		if(novaReceita.getDescricao() == null || novaReceita.getData() == null || novaReceita.getValor() == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(jaExisteDescricao(novaReceita.getDescricao(), novaReceita.getData()))
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		this.receitaRepository.save(novaReceita);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	
	private boolean jaExisteDescricao(String descricao, Date data) {
		Receita receita = this.receitaRepository.buscarPorDescricao(descricao, data);
		return receita == null ? false : true;
	}
	
	@GetMapping("/{ano}/{mes}")
	public List<Receita> getReceitasPorMes(@PathVariable("ano") String ano, @PathVariable("mes") String mes)
	{
		List<Receita> receitas = null;
		receitas = receitaRepository.buscaPorMes(ano, mes);
		return receitas;
	}

}
