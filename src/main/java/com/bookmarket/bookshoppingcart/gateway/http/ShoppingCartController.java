package com.bookmarket.bookshoppingcart.gateway.http;

import com.bookmarket.bookshoppingcart.gateway.http.json.AddToCartRequest;
import com.bookmarket.bookshoppingcart.gateway.http.json.ErrorResponse;
import com.bookmarket.bookshoppingcart.gateway.http.json.ShoppingCartResponse;
import com.bookmarket.bookshoppingcart.usecase.AddToCart;
import com.bookmarket.bookshoppingcart.usecase.ClearShoppingCart;
import com.bookmarket.bookshoppingcart.usecase.ExportShoppingCartToJson;
import com.bookmarket.bookshoppingcart.usecase.GetShoppingCartDetails;
import com.bookmarket.bookshoppingcart.usecase.RemoveBookFromCart;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Operations related to the shopping cart")
public class ShoppingCartController {

    private final AddToCart addToCart;
    private final GetShoppingCartDetails getShoppingCartDetails;
    private final ExportShoppingCartToJson exportShoppingCartToJson;
    private final RemoveBookFromCart removeBookFromCart;
    private final ClearShoppingCart clearShoppingCart; // Inject new use case

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a book to the shopping cart")
    @ApiResponse(responseCode = "201", description = "Book added to cart successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data or insufficient stock", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ShoppingCartResponse addBookToCart(final @RequestBody AddToCartRequest request) {
        return addToCart.add(request);
    }

    @GetMapping
    @Operation(summary = "Get shopping cart details")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved shopping cart details")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ShoppingCartResponse getCart() {
        return getShoppingCartDetails.getCartDetails();
    }

    @DeleteMapping("/items/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remove a book from the shopping cart")
    @ApiResponse(responseCode = "200", description = "Book removed from cart successfully")
    @ApiResponse(responseCode = "400", description = "Invalid quantity to remove", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Book or shopping cart not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ShoppingCartResponse removeBookFromCart(final @PathVariable Long bookId, final @RequestParam(required = false, defaultValue = "0") Integer quantity) {
        if (quantity <= 0) {
            return removeBookFromCart.remove(bookId, Integer.MAX_VALUE);
        }
        return removeBookFromCart.remove(bookId, quantity);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Clear the entire shopping cart")
    @ApiResponse(responseCode = "200", description = "Shopping cart cleared successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ShoppingCartResponse clearCart() {
        return clearShoppingCart.clear();
    }

    @GetMapping("/export")
    @Operation(summary = "Export shopping cart to JSON file")
    @ApiResponse(responseCode = "200", description = "Shopping cart exported to JSON file successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error during export", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public String exportCartToJson() throws IOException {
        return exportShoppingCartToJson.export();
    }
}