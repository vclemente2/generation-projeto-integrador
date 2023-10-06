package br.com.generation.comerciocomcausa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
		public ResponseEntity<List<Category>> getAll(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "description", required = false) String description){
			List<Category> categories;

			if (name != null && description != null)
				categories = categoryRepository.findAllByNameContainingAndDescriptionContainingIgnoreCase(name, description);
			else if(name != null)
				categories = categoryRepository.findAllByNameContainingIgnoreCase(name);
			else if(description != null)
				categories = categoryRepository.findAllByDescriptionContainingIgnoreCase(description);
			else
				categories = categoryRepository.findAll();

		return ResponseEntity.ok(categories);
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

	@PutMapping
    public ResponseEntity<Category> put(@Valid @RequestBody Category category){
        return categoryRepository.findById(category.getId())
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
            .body(categoryRepository.save(category)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        categoryRepository.deleteById(id);
    }

}
