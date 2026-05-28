package com.bookmarket.bookshoppingcart.entity;

import com.bookmarket.bookshoppingcart.gateway.http.json.BookResponse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "books", indexes = {@Index(name = "idx_books_genre", columnList = "genre"),})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 50)
    private String genre;

    public BookResponse toJson() {
        return new BookResponse(this.id, this.title, this.author, this.price, this.stock, this.genre);
    }
}

