package br.com.generation.comerciocomcausa.controller;

import br.com.generation.comerciocomcausa.model.Product;
import br.com.generation.comerciocomcausa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

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
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return productRepository.findById(id)
                .map((response)->ResponseEntity.ok(response))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

}
