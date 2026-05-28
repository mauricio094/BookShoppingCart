package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.Book;
import com.bookmarket.bookshoppingcart.entity.exception.NotFoundException;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBookById {

    private final BookRepository bookRepository;

    public Book find(final Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
    }

}
