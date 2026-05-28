package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.Book;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateBookStock {

    private final BookRepository bookRepository;
    private final FindBookById findBookById;

    public Book update(final Long id, final Integer stock) {
        var book = findBookById.find(id);
        book.setStock(stock);

        return bookRepository.save(book);
    }
}