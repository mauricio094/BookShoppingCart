package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.CartItem;
import com.bookmarket.bookshoppingcart.entity.ShoppingCart;
import com.bookmarket.bookshoppingcart.entity.exception.InsufficientStockException;
import com.bookmarket.bookshoppingcart.gateway.http.json.AddToCartRequest;
import com.bookmarket.bookshoppingcart.gateway.http.json.ShoppingCartResponse;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import com.bookmarket.bookshoppingcart.gateway.repository.CartItemRepository;
import com.bookmarket.bookshoppingcart.gateway.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddToCart {

    private final BookRepository bookRepository;
    private final FindBookById findBookById;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public ShoppingCartResponse add(AddToCartRequest request) {

        var book = findBookById.find(request.bookId());

        if (book.getStock() < request.quantity()) {
            throw new InsufficientStockException("Insufficient stock for book: " + book.getTitle());
        }

        var shoppingCart = shoppingCartRepository.findAll().stream()
                .findFirst()
                .orElseGet(ShoppingCart::new);

        var existingCartItem = shoppingCart.getItems().stream()
                .filter(item -> book.getId().equals(item.getBook().getId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            var item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
            cartItemRepository.save(item);
        } else {
            var newCartItem = CartItem.builder()
                    .book(book)
                    .quantity(request.quantity())
                    .shoppingCart(shoppingCart)
                    .build();
            shoppingCart.addItem(newCartItem);
        }

        book.setStock(book.getStock() - request.quantity());
        bookRepository.save(book);

        var savedCart = shoppingCartRepository.save(shoppingCart);
        return savedCart.tojson();
    }
}