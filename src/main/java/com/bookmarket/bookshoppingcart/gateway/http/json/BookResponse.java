package com.bookmarket.bookshoppingcart.gateway.http.json;

import java.math.BigDecimal;

public record BookResponse(Long id, String title, String author, BigDecimal price, Integer stock, String genre) {
}
