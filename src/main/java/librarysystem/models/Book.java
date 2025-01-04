package librarysystem.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "type")
    private String type;

    @Column(name = "publishDate")
    private int publishDate;

    public enum BookStatus {
        available, reserved
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status;



    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "createdAt", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
        updatedAt = Timestamp.from(Instant.now());
        if (status == null) {
            status = BookStatus.available;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Timestamp.from(Instant.now());
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
