package br.com.generation.comerciocomcausa.repository;

import br.com.generation.comerciocomcausa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
