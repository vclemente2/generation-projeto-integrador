package br.com.generation.comerciocomcausa.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(unique = true)
	@Size(min = 11, max = 11, message = "O CPF deve conter 11 caracteres")
	private String cpf;
	
	@Column(unique = true)
	@Size(min = 14, max = 14, message = "O CNPJ deve conter 14 caracteres")
	private String cnpj;
	
	@NotBlank(message = "O CEP é um campo obrigatório")
	@Size(min = 8, max = 8, message = "O CEP deve conter 8 caracteres")
	private String cep;
	
	@NotBlank(message = "O nome é um campo obrigatório")
	@Size(min = 2, max = 100, message = "O campo nome deve conter entre 2 e 100 caracteres")
	private String name;
	
	@Size(max = 1000, message = "O campo sobre deve conter no máximo 1000 caracteres")
	private String about;
	
	@Size(max = 100, message = "O campo gênero deve conter no máximo 100 caracteres")
	private String gender;
	
	@Size(max = 100, message = "O campo etnia deve conter no máximo 100 caracteres")
	private String ethnicity;
	
	private Date born;
	
	@Size(max = 255, message = "O campo url deve conter no máximo 255 caracteres")
	private String url;
	
	@NotBlank(message = "O tipo é um campo obrigatório")
	@Size(max = 50, message = "O campo tipo deve conter no máximo 50 caracteres")
	private String type;
	
	@Column(unique = true)
	@NotBlank(message = "O e-mail é um campo obrigatório")
	@Size(max = 100, message = "O campo e-mail deve conter no máximo 100 caracteres")
	@Email
	private String email;
	
	@NotBlank(message = "A senha é um campo obrigatório")
	private String password;

	@UpdateTimestamp
	private LocalDateTime created_at;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("user")
	private List<Product> products;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
