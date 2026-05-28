package com.bookmarket.bookshoppingcart.gateway.http.json;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCartResponse(Long cartId, List<CartItemResponse> items, BigDecimal totalCartCost) {
}