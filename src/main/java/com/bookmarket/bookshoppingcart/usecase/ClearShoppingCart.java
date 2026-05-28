package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.entity.CartItem;
import com.bookmarket.bookshoppingcart.gateway.http.json.ShoppingCartResponse;
import com.bookmarket.bookshoppingcart.gateway.repository.BookRepository;
import com.bookmarket.bookshoppingcart.gateway.repository.CartItemRepository;
import com.bookmarket.bookshoppingcart.gateway.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClearShoppingCart {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final GetShoppingCartDetails getShoppingCartDetails;

    @Transactional
    public ShoppingCartResponse clear() {

        var optionalShoppingCart = shoppingCartRepository.findAll().stream().findFirst();

        if (optionalShoppingCart.isEmpty()) {
            return getShoppingCartDetails.getCartDetails();
        }

        var shoppingCart = optionalShoppingCart.get();

        var itemsToClear = List.copyOf(shoppingCart.getItems());

        for (CartItem item : itemsToClear) {
            item.getBook().setStock(item.getBook().getStock() + item.getQuantity());
            bookRepository.save(item.getBook());

            shoppingCart.removeItem(item);
            cartItemRepository.delete(item);
        }

        var clearedCart = shoppingCartRepository.save(shoppingCart);

        return clearedCart.tojson();
    }
}