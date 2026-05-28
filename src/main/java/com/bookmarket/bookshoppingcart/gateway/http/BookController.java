package com.bookmarket.bookshoppingcart.gateway.http;

import com.bookmarket.bookshoppingcart.entity.Book;
import com.bookmarket.bookshoppingcart.gateway.http.json.BookRequest;
import com.bookmarket.bookshoppingcart.gateway.http.json.BookResponse;
import com.bookmarket.bookshoppingcart.gateway.http.json.ErrorResponse;
import com.bookmarket.bookshoppingcart.gateway.http.json.StockUpdateRequest;
import com.bookmarket.bookshoppingcart.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book catalogue operations")
public class BookController {

    private final FindBookById findBookById;
    private final FindAllBooks findAllBooks;
    private final CreateBook createBook;
    private final UpdateBookStock updateBookStock;
    private final DeleteBook deleteBook;

    @GetMapping
    @Operation(summary = "List all available books")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public List<BookResponse> listBooks() {
        return findAllBooks.find().stream().map(Book::toJson).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved book")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public BookResponse getBook(final @PathVariable Long id) {
        return findBookById.find(id).toJson();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "201", description = "Book created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid book data provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Book with same title, author, and genre already exists", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public BookResponse createBook(final @RequestBody BookRequest bookRequest) {
        return createBook.create(bookRequest).toJson();
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update book stock")
    @ApiResponse(responseCode = "200", description = "Book stock updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid stock update data or insufficient stock", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public BookResponse updateBookStock(final @PathVariable Long id, final @RequestBody StockUpdateRequest stockUpdateRequest) {
        return updateBookStock.update(id, stockUpdateRequest.stock()).toJson();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a book by ID")
    @ApiResponse(responseCode = "204", description = "Book deleted successfully")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Book is currently in a shopping cart", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public void deleteBook(final @PathVariable Long id) {
        deleteBook.delete(id);
    }
}