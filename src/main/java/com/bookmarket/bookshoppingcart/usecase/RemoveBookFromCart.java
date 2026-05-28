package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.exception.NotFoundException;
import com.bookmarket.bookshoppingcart.gateway.http.json.ShoppingCartResponse;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import com.bookmarket.bookshoppingcart.gateway.repository.CartItemRepository;
import com.bookmarket.bookshoppingcart.gateway.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveBookFromCart {

    private final FindBookById findBookById;
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public ShoppingCartResponse remove(final Long bookId, final Integer quantityToRemove) {

        var book = findBookById.find(bookId);

        var shoppingCart = shoppingCartRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Shopping cart not found."));

        var optionalCartItem = shoppingCart.getItems().stream()
                .filter(item -> bookId.equals(item.getBook().getId()))
                .findFirst();

        if (optionalCartItem.isEmpty()) {
            throw new NotFoundException("Book with ID " + bookId + " not found in shopping cart.");
        }

        var cartItem = optionalCartItem.get();

        if (quantityToRemove <= 0) {
            throw new IllegalArgumentException("Quantity to remove must be greater than zero.");
        }

        if (cartItem.getQuantity() <= quantityToRemove) {
            shoppingCart.removeItem(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() - quantityToRemove);
            cartItemRepository.save(cartItem);
        }

        book.setStock(book.getStock() + quantityToRemove);
        bookRepository.save(book);

        var updatedCart = shoppingCartRepository.save(shoppingCart);

        return updatedCart.tojson();
    }
}