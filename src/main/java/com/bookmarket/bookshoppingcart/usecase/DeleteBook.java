package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.exception.BookInCartException;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import com.bookmarket.bookshoppingcart.gateway.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBook {

    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    public void delete(Long id) {
        if (cartItemRepository.existsByBookId(id)) {
            throw new BookInCartException("Book with ID " + id + " cannot be deleted because it is currently in a shopping cart.");
        }
        bookRepository.deleteById(id);
    }
}