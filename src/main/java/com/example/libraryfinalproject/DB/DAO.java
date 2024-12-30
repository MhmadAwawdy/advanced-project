package com.example.libraryfinalproject.DB;

import com.example.libraryfinalproject.Models.Book;

import java.util.List;

public interface DAO {
    List<Book> getAllBooks();
    List<Book> getBooksBySearch(String searchText);
    List<Book> getBooksByFilter(String filter, String filterValue);
    Book getBookById(int id);
    List<String> getAuthors();
    List<String> getTitles();
    List<String> getTypes();
    List<Book> getBooksByYear(String year);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByTitle(String title);
    List<Book> getBooksByType(String type);
}
