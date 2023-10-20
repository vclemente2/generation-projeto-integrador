package br.com.generation.comerciocomcausa.service;

import java.util.Optional;

import br.com.generation.comerciocomcausa.model.User;
import br.com.generation.comerciocomcausa.model.UserLogin;
import br.com.generation.comerciocomcausa.repository.UserRepository;
import br.com.generation.comerciocomcausa.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<User> registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return Optional.empty();

        user.setPassword(criptografarSenha(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }

    public Optional<User> updateUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> searchUser = userRepository.findByEmail(user.getEmail());

            if ( (searchUser.isPresent()) && ( searchUser.get().getId() != user.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            user.setPassword(criptografarSenha(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

    }

    public Optional<UserLogin> autenticarUsuario(Optional<UserLogin> userLogin) {

        // Gera o Objeto de autenticação
        var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getEmail(), userLogin.get().getPassword());

        // Autentica o Usuario
        Authentication authentication = authenticationManager.authenticate(credentials);

        // Se a autenticação foi efetuada com sucesso
        if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
            Optional<User> user = userRepository.findByEmail(userLogin.get().getEmail());

            // Se o usuário foi encontrado
            if (user.isPresent()) {

                // Preenche o Objeto usuarioLogin com os dados encontrados
                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setUrl(user.get().getUrl());
                userLogin.get().setToken(gerarToken(userLogin.get().getEmail()));
                userLogin.get().setPassword("");

                // Retorna o Objeto preenchido
                return userLogin;

            }

        }

        return Optional.empty();

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private String gerarToken(String usuario) {
        return "Bearer " + jwtService.generateToken(usuario);
    }

}