package br.com.generation.comerciocomcausa.repository;

import br.com.generation.comerciocomcausa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetweenAndNameContainingAndCategoryNameContainingAllIgnoreCase(BigDecimal minPrice, BigDecimal maxPrice, @Param("name") String name, @Param("categoryName") String categoryName);
}
