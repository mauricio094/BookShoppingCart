package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.Book;
import com.bookmarket.bookshoppingcart.entity.exception.DuplicateBookException;
import com.bookmarket.bookshoppingcart.gateway.http.json.BookRequest;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBook {

    private final BookRepository bookRepository;

    public Book create(BookRequest bookRequest) {

        bookRepository.findByTitleAndAuthorAndGenre(bookRequest.title(), bookRequest.author(), bookRequest.genre())
                .ifPresent(book -> {
                    throw new DuplicateBookException("Book with title '" + bookRequest.title() +
                            "', author '" + bookRequest.author() +
                            "' and genre '" + bookRequest.genre() + "' already exists.");
                });

        return bookRepository.save(bookRequest.toBook());
    }
}