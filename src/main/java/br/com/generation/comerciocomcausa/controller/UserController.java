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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.generation.comerciocomcausa.model.User;
import br.com.generation.comerciocomcausa.repository.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<List<User>> getAll(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "cpf", required = false) String cpf,
			@RequestParam(name = "cnpj", required = false) String cnpj) {

		List<User> users;

		if (name != null && cpf != null)
			users = userRepository.findAllByNameContainingAndCpfContainingIgnoreCase(name, cpf);
		else if (name != null && cnpj != null)
			users = userRepository.findAllByNameContainingAndCnpjContainingIgnoreCase(name, cnpj);
		else if (name != null)
			users = userRepository.findAllByNameContainingIgnoreCase(name);
		else if (cpf != null)
			users = userRepository.findAllByCpfContainingIgnoreCase(cpf);
		else if (cnpj != null)
			users = userRepository.findAllByCnpjContainingIgnoreCase(cnpj);
		else
			users = userRepository.findAll();

		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {

		return userRepository.findById(id).map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("name/{name}")
	public ResponseEntity<List<User>> getByName(@PathVariable String name) {

		return ResponseEntity.ok(userRepository.findAllByNameContainingIgnoreCase(name));
	}

	@PostMapping
	public ResponseEntity<User> post(@Valid @RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
	}

	@PutMapping
	public ResponseEntity<User> put(@Valid @RequestBody User user) {
		return userRepository.findById(user.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		userRepository.deleteById(id);
	}
}
