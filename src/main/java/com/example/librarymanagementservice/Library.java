package com.example.librarymanagementservice;

import com.example.librarymanagementservice.exceptions.BookAlreadyExistsException;
import com.example.librarymanagementservice.exceptions.BookNotFoundException;

import java.util.List;

public class Library {

    private static final List<Book> libraryBooks;
    private static final LibraryDao libraryDao = new LibraryDao();

    static {
        libraryBooks = libraryDao.readAllBooks();
    }

    public void addBook(Book book) {
        if (libraryBooks.contains(book)) {
            throw new BookAlreadyExistsException("Book with such ISBN already in library storage: %s".formatted(book.getIsbn()));
        }

        libraryBooks.add(book);
        libraryDao.addBook(book);
    }

    public void deleteBook(String isbn) {
        Book bookToBeDeleted = libraryBooks.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findAny()
                .orElseThrow(() ->
                        new BookNotFoundException("Book with such isbn is not found: %s".formatted(isbn)));

        libraryBooks.remove(bookToBeDeleted);
        libraryDao.removeBook(bookToBeDeleted.getIsbn());
    }

    public void updateBook(String isbn, Book book) {
        libraryBooks.stream().filter(b -> b.getIsbn().equals(isbn))
                .findAny()
                .ifPresentOrElse(b -> {
                    if (b.getTitle() != null || !b.getTitle().isEmpty()) {
                        b.setTitle(book.getTitle());
                    } if (b.getAuthor() != null || !b.getAuthor().isEmpty()) {
                        b.setAuthor(book.getAuthor());
                    } if (b.getPublicationDate() != null || !b.getPublicationDate().isEmpty()) {
                        b.setPublicationDate(book.getPublicationDate());
                    }
                }, () -> {
                    throw new BookNotFoundException("Book with such ISBN is not found: %s".formatted("isbn"));
                }
        );
        Book updatedBook = libraryBooks.stream()
                        .filter(sBook -> sBook.getIsbn().equals(isbn)).findAny().get();
        libraryDao.updateBook(isbn, updatedBook);
    }

    public List<Book> findByAuthorName(String authorName) {
        return libraryBooks.stream()
                .filter(book -> book.getAuthor().equals(authorName))
                .toList();
    }

    public Book findByTitle(String title) {
        return libraryBooks.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() ->
                        new BookNotFoundException("Book with such ISBN is not found: %s".formatted("isbn")));
    }

    public List<Book> findByPublicationDate(String publicationDate) {
        return libraryBooks.stream()
                .filter(book -> book.getPublicationDate().equals(publicationDate))
                .toList();
    }

    public List<Book> findAll() {
        return libraryBooks;
    }
}
