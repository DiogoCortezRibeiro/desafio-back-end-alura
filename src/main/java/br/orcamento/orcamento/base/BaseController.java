package br.orcamento.orcamento.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class BaseController<ENTITY extends BaseEntity, REPO extends JpaRepository<ENTITY, Long>> {
	
	@Autowired
	private REPO repo;
	
	@GetMapping
	public List<ENTITY> apresentar()
	{
		return this.repo.findAll();
	}
	
	@GetMapping("/{id}")
	public ENTITY apresentaPorId(@PathVariable("id") Long id)
	{
		return this.repo.findById(id).get();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> atualizar(@PathVariable("id") Long id, @RequestBody ENTITY dados)
	{
		if(dados.getDescricao() == null || dados.getData() == null || dados.getValor() == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		this.repo.deleteById(id);
		this.repo.save(dados);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deletar(@PathVariable("id") Long id)
	{
		ENTITY delete = this.repo.findById(id).get();
		if(delete != null)
		{
			this.repo.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}
}
