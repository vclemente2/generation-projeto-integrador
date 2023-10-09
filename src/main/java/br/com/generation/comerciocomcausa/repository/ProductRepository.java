package br.com.generation.comerciocomcausa.repository;

import br.com.generation.comerciocomcausa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
