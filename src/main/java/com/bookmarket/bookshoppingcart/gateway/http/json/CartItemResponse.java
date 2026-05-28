package com.bookmarket.bookshoppingcart.gateway.http.json;

import java.math.BigDecimal;

public record CartItemResponse(Long bookId, String bookTitle, String bookAuthor, BigDecimal bookPrice, Integer quantity,
                               BigDecimal itemTotalCost) {
}