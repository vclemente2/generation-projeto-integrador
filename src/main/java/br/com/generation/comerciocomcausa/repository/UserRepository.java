package br.com.generation.comerciocomcausa.repository;

import br.com.generation.comerciocomcausa.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findAllByNameContainingIgnoreCase(@Param("name") String name);
	
	List<User> findAllByCpfContainingIgnoreCase(@Param("cpf") String cpf);
	
	List<User> findAllByCnpjContainingIgnoreCase(@Param("cnpj") String cnpj);
	
	List<User> findAllByNameContainingAndCpfContainingIgnoreCase(@Param("name") String name, @Param("cpf") String cpf);
	
	List<User> findAllByNameContainingAndCnpjContainingIgnoreCase(@Param("name") String name, @Param("cnpj") String cnpj);
}
