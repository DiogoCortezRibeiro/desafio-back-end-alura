package br.orcamento.orcamento.receitas;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receita")
public class ReceitaController {
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@GetMapping
	public List<Receita> apresentarReceita()
	{
		return this.receitaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Receita apresentaReceitaPorId(@PathVariable("id") Integer id)
	{
		return this.receitaRepository.findById(id).get();
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
	
	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> atualizarReceita(@PathVariable("id") Integer id, @RequestBody Receita receitaAtualizada)
	{
		if(receitaAtualizada.getDescricao() == null || receitaAtualizada.getData() == null || receitaAtualizada.getValor() == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		this.receitaRepository.deleteById(id);
		this.receitaRepository.save(receitaAtualizada);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deletarReceita(@PathVariable("id") Integer id)
	{
		Receita receita = this.receitaRepository.findById(id).get();
		if(receita != null)
		{
			this.receitaRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}

}
