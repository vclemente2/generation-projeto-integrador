package br.com.generation.comerciocomcausa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "O nome é um campo obrigatório")
    @Size(min = 2, max = 100, message = "O campo nome deve conter entre 2 e 100 caracteres")
    private String name;

    @Size(max = 1000, message = "O campo descriçao deve conter no máximo 1000 caracteres")
    private String description;

    @NotNull(message = "O preço é um campo obrigatório")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Size(max = 255, message = "O campo imagem deve conter no máximo 255 caracteres")
    private String image_url;

    @ManyToOne
    @JsonIgnoreProperties("products")
    @NotNull(message = "O usuário é um campo obrigatório")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("products")
    @NotNull(message = "A categoria é um campo obrigatório")
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
