package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.Book;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllBooks {

    private final BookRepository bookRepository;

    public List<Book> find() {
        return bookRepository.findAll();
    }
}
