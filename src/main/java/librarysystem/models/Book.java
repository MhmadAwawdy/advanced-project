package librarysystem.models;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "type")
    private String type;

    @Column(name = "publishDate")
    private int publishDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status;

    public enum BookStatus
    {
        available, AVAILABLE, UNAVAILABLE, RESERVED, reserved
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author;
    }

    @Lob
    @Column(name = "image")
    private byte[] image;

    @PrePersist
    protected void onCreate()
    {
        if (status == null)
        {
            status = BookStatus.available;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(int publishDate) {
        this.publishDate = publishDate;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public BookStatus getStatus() {
        return status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
