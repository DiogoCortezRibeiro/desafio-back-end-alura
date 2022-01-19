package br.orcamento.orcamento.despesas;

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
@RequestMapping("/despesa")
public class DespesaController {
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@PostMapping
	public ResponseEntity<HttpStatus> cadastrarDespesa(@RequestBody Despesa novaDespesa)
	{
		if(novaDespesa.getDescricao() == null || novaDespesa.getData() == null || novaDespesa.getValor() == null)
		{
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
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
	
	@GetMapping
	public List<Despesa> listaDespesas()
	{
		return this.despesaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Despesa listaDespesaPorId(@PathVariable("id") Integer id)
	{
		return this.despesaRepository.findById(id).get();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> atualizaDespesa(@PathVariable("id") Integer id, @RequestBody Despesa despesa)
	{
		if(despesa.getDescricao() == null || despesa.getData() == null || despesa.getValor() == null)
		{
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		this.despesaRepository.deleteById(id);
		this.despesaRepository.save(despesa);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	};
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deletarDespesa(@PathVariable("id") Integer id)
	{
		Despesa despesa = this.despesaRepository.findById(id).get();
		if(despesa != null)
		{
			this.despesaRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}
}
