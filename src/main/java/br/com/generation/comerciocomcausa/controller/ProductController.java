package br.com.generation.comerciocomcausa.controller;


import br.com.generation.comerciocomcausa.model.Product;
import br.com.generation.comerciocomcausa.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @PostMapping
    public ResponseEntity<Product> post(@Valid @RequestBody Product product) {
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(product));
    }

    @PutMapping
    public ResponseEntity<Product> put (@Valid @RequestBody Product product) {
        return productRepository.findById(product.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(productRepository.save(product)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

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

