# Book Market Shopping Cart API

REST API for managing books and a shopping cart in a book marketplace application.

---

# Features

* Manage book catalogue
* Add and remove books from shopping cart
* Update stock quantities
* Export shopping cart to JSON
* RESTful API design
* Validation and error handling
* H2 database support
* OpenAPI / Swagger documentation ready

---

# Technologies

* Java
* Spring Boot
* Spring Web
* Spring Data JPA / JDBC
* H2 Database
* OpenAPI 3.1
* Maven

---

# Base URL

```http
http://localhost:8080
```

---

# API Endpoints

## Books

### List all books

```http
GET /v1/books
```

### Get book by ID

```http
GET /v1/books/{id}
```

### Create a new book

```http
POST /v1/books
```

Request example:

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "price": 39.99,
  "stock": 10,
  "genre": "Programming"
}
```

### Update book stock

```http
PATCH /v1/books/{id}/stock
```

Request example:

```json
{
  "stock": 20
}
```

### Delete a book

```http
DELETE /v1/books/{id}
```

---

## Shopping Cart

### Get shopping cart details

```http
GET /v1/cart
```

### Add a book to cart

```http
POST /v1/cart/items
```

Request example:

```json
{
  "bookId": 1,
  "quantity": 2
}
```

### Remove a book from cart

```http
DELETE /v1/cart/items/{bookId}?quantity=1
```

If the quantity parameter is omitted or equals `0`, the item is completely removed from the cart.

### Clear shopping cart

```http
DELETE /v1/cart
```

### Export cart to JSON

```http
GET /v1/cart/export
```

---

# Response Examples

## Book Response

```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "price": 39.99,
  "stock": 10,
  "genre": "Programming"
}
```

## Shopping Cart Response

```json
{
  "cartId": 1,
  "items": [
    {
      "bookId": 1,
      "bookTitle": "Clean Code",
      "bookAuthor": "Robert C. Martin",
      "bookPrice": 39.99,
      "quantity": 2,
      "itemTotalCost": 79.98
    }
  ],
  "totalCartCost": 79.98
}
```

## Error Response

```json
{
  "message": "Book not found"
}
```

---

# HTTP Status Codes

| Status Code | Description                         |
| ----------- | ----------------------------------- |
| 200         | Success                             |
| 201         | Resource created                    |
| 204         | Resource deleted successfully       |
| 400         | Invalid request or validation error |
| 404         | Resource not found                  |
| 409         | Conflict                            |
| 500         | Internal server error               |

---

# Validation Rules

## Book Creation

* `title` cannot be empty
* `author` cannot be empty
* `genre` cannot be empty
* `price` must be greater than or equal to `0`
* `stock` must be greater than or equal to `0`

## Add To Cart

* `quantity` must be greater than or equal to `1`
* Book must exist
* Stock must be available

---

# Running the Application

## Run the project

```bash
mvn spring-boot:run
```

---

# H2 Database

H2 console:

```http
http://localhost:8080/h2-console
```

Example configuration:

```properties
JDBC URL: jdbc:h2:mem:bookstoredb
User Name: sa
Password:
```

---

# Swagger / OpenAPI

If Swagger UI is enabled:

```http
http://localhost:8080/swagger-ui.html
```

---

# Project Structure
Clean Architecture

![img.png](img.png)
---
