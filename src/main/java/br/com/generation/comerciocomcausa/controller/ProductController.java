package br.com.generation.comerciocomcausa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.generation.comerciocomcausa.model.Product;
import br.com.generation.comerciocomcausa.repository.ProductRepository;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/name/{name}")
	public ResponseEntity<List<Product>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(productRepository.findAllByNameContainingIgnoreCase(name));
	}
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	Optional<Product> product = productRepository.findById(id);
	
	  if (product.isEmpty())
		  throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	  
	  productRepository.deleteById(id); 
	
	  }
}