package br.com.generation.comerciocomcausa.controller;

import br.com.generation.comerciocomcausa.model.Product;
import br.com.generation.comerciocomcausa.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
