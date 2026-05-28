package com.bookmarket.bookshoppingcart.gateway.http.json;

import com.bookmarket.bookshoppingcart.entity.Book;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookRequest(@NotBlank String title, @NotBlank String author,
                          @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
                          @NotNull @Min(0) Integer stock, @NotBlank String genre) {

    public Book toBook() {
        return Book.builder()
                .title(title)
                .author(author)
                .price(price)
                .stock(stock)
                .genre(genre)
                .build();
    }
}