package com.bookstore.JPA.MODELs;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tb_book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name= "tb_author_book",
            joinColumns = @JoinColumn(name= "book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
//    Esse novo parâmetro cascade = CascadeType.ALL serve para fazer um
//    efeito em cascata, ou seja, se eu for cadastrar um livro, por exemplo,
//    eu posso fazer, ao mesmo tempo, a review dele também. Já para um DELETE
//    a lógica é a mesma, se eu deletar um livro, obrigatóriamente ele vai
//    deletar a review pertencente aquele livro. Precisa ser tomado um
//    cuidado maior ao usar esse tipo de função, pois em métodos de deleção
//    ou atualização, por exemplo, é melhor ter um controle do que vai sair
//    ou atualizar do que apenas deixar ao sistema.
    private Review review;


    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public Publisher getPublisher() {return publisher;}

    public void setPublisher(Publisher publisher) {this.publisher = publisher;}

    public Set<Author> getAuthors() {return authors;}

    public void setAuthors(Set<Author> authors) {this.authors = authors;}

    public Review getReview() {return review;}

    public void setReview(Review review) {this.review = review;}
}
