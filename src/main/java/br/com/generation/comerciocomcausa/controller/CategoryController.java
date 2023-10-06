package br.com.generation.comerciocomcausa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.generation.comerciocomcausa.model.Category;
import br.com.generation.comerciocomcausa.repository.CategoryRepository;

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
	
	@GetMapping
		public ResponseEntity<Category> getById(@PathVariable Long id){
			return categoryRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}
