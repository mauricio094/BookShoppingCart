package com.bookmarket.bookshoppingcart.entity;

import com.bookmarket.bookshoppingcart.gateway.http.json.CartItemResponse;
import com.bookmarket.bookshoppingcart.gateway.http.json.ShoppingCartResponse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        this.items.add(item);
        item.setShoppingCart(this);
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setShoppingCart(null);
    }

    public ShoppingCartResponse tojson() {
        var itemResponses = this.items.stream()
                .map(cartItem -> {
                    BigDecimal itemTotalCost = cartItem.getBook().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                    return new CartItemResponse(
                            cartItem.getBook().getId(),
                            cartItem.getBook().getTitle(),
                            cartItem.getBook().getAuthor(),
                            cartItem.getBook().getPrice(),
                            cartItem.getQuantity(),
                            itemTotalCost);

                })
                .collect(Collectors.toList());

        var totalCartCost = itemResponses.stream()
                .map(CartItemResponse::itemTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ShoppingCartResponse(this.id, itemResponses, totalCartCost);
    }
}