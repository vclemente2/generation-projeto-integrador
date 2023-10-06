package br.com.generation.comerciocomcausa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.generation.comerciocomcausa.model.Category;
import br.com.generation.comerciocomcausa.repository.CategoryRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
		public ResponseEntity<List<Category>> getAll(){
			return ResponseEntity.ok(categoryRepository.findAll());
	}
	
	@GetMapping("/{id}")
		public ResponseEntity<Category> getById(@PathVariable Long id){
			return categoryRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Category>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(categoryRepository.findAllByNameContainingIgnoreCase(name));
	}
	
	@PostMapping
	public ResponseEntity<Category> post(@Valid @RequestBody Category category) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
	}

}
