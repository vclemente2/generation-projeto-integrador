package br.com.generation.comerciocomcausa.repository;

import br.com.generation.comerciocomcausa.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findAllByNameContainingIgnoreCase(@Param("name") String name);
	  
}
