package librarysystem.models;

public class Book2 {
    private Long id;
    private String title;
    private String author;
    private String type;
    private String publishDate;
    private String image;


    public Book2(Long id, String title, String author, String type, String publishDate, String image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.type = type;
        this.publishDate = publishDate;
        this.image = image;
    }

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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
