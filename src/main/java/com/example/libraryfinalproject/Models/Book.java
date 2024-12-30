package com.example.libraryfinalproject.Models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String type;

    @Column(name = "publish_date")
    private LocalDate publishDate;  // استخدام LocalDate بدلاً من String لتمثيل التاريخ

    private String status;
    private String image;

    // الكونستركتور الكامل
    public Book(String title, String author, String type, LocalDate publishDate, String status, String image) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.publishDate = publishDate;
        this.status = status;
        this.image = image;
    }

    // الكونستركتور الفارغ
    public Book() {}

    // Getters و Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
