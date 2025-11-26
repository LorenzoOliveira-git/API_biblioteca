package com.bookstore.JPA.MODELs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tb_publisher")
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
//    Esse mappedBy serve para mapear qual atributo do lado many vai ser a
//    chave estrangeira, nesse caso, o atributo publisher da classe Book
//    Já o fetch = FetchType.LAZY define a constancia com que a JPA vai pegar
//    os dados do relacionamento, ou seja, se for LAZY (preguiçoso) quer
//    dizer que só vamos fazer a atualização desses livros quando necessário,
//    já o EAGER (ansioso) faz a atualização toda vez que rodar o código na JPA.
    private Set<Book> books = new HashSet<>();

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Set<Book> getBooks() {return books;}

    public void setBooks(Set<Book> books) {this.books = books;}
}
