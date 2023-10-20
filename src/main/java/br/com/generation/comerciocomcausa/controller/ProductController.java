package br.com.generation.comerciocomcausa.controller;

import br.com.generation.comerciocomcausa.model.Product;
import br.com.generation.comerciocomcausa.repository.CategoryRepository;
import br.com.generation.comerciocomcausa.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name="min", required = false) BigDecimal minPrice,
            @RequestParam(name="max", required = false) BigDecimal maxPrice
    ){
        List<Product> products;

        minPrice = minPrice == null ? BigDecimal.valueOf(0) : minPrice;
        maxPrice = maxPrice == null ? BigDecimal.valueOf(Double.MAX_VALUE) : maxPrice;
        name = name == null ? "" : name;
        category = category == null ? "" : category;

        products = productRepository.findByPriceBetweenAndNameContainingAndCategoryNameContainingAllIgnoreCase(minPrice, maxPrice, name, category);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map((response) -> ResponseEntity.ok(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PostMapping
    public ResponseEntity<Product> post(@Valid @RequestBody Product product) {
        if(categoryRepository.existsById(product.getCategory().getId()))
        	return  ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(product));
        
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
    }

    @PutMapping
    public ResponseEntity<Product> put (@Valid @RequestBody Product product) {
        if(productRepository.existsById(product.getId())) {
        	
        	if(categoryRepository.existsById(product.getCategory().getId()))
        		return ResponseEntity.status(HttpStatus.OK)
        				.body(productRepository.save(product));
        	
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
