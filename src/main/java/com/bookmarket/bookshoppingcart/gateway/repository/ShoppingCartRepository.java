package com.bookmarket.bookshoppingcart.gateway.repository;

import com.bookmarket.bookshoppingcart.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}