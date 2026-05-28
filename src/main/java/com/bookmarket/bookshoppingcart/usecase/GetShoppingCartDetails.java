package com.bookmarket.bookshoppingcart.usecase;

import com.bookmarket.bookshoppingcart.gateway.http.json.ShoppingCartResponse;
import com.bookmarket.bookshoppingcart.gateway.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GetShoppingCartDetails {

    private final ShoppingCartRepository shoppingCartRepository;

    @Transactional(readOnly = true)
    public ShoppingCartResponse getCartDetails() {

        var optionalShoppingCart = shoppingCartRepository.findAll().stream().findFirst();

        if (optionalShoppingCart.isEmpty()) {
            return new ShoppingCartResponse(null, Collections.emptyList(), BigDecimal.ZERO);
        }

        var shoppingCart = optionalShoppingCart.get();

        return shoppingCart.tojson();
    }
}