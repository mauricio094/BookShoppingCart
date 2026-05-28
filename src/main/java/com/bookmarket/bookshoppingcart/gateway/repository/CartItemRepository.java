package com.bookmarket.bookshoppingcart.gateway.repository;

import com.bookmarket.bookshoppingcart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsByBookId(Long bookId);
}