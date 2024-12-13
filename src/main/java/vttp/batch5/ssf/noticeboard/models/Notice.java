package vttp.batch5.ssf.noticeboard.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 128, message = "Title must be between 3 and 128 characters long.")
    private String title;

    @NotBlank(message = "Poster cannot be blank")
    @Email(message = "Input email is not in email format")
    private String poster;

    @NotNull(message = "Date cannot be blank")
    private LocalDate postDate;

    @NotNull(message = "Categories cannot be blank")
    private List<String> categories;

    @NotBlank(message = "Text cannot be blank")
    private String text;
    
    public Notice() {
    }

    public Notice(String title, String poster, LocalDate postDate, List<String> categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
}
