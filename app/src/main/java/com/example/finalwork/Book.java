package com.example.finalwork;

public class Book {
    private String name;
    private String author;
    private String publishing;
    private String published;
    private Integer douban;
    private Integer doubanScore;
    private String photoUrl;
    private String authorIntro;
    private String description;
    private long ISBN;
    public Book(Long ISBN,String name,String author,String authorIntro,String photoUrl,String publishing ,String published,String description,Integer douban,Integer doubanScore) {
        this.ISBN=ISBN;
        this.name=name;
        this.author=author;
        this.authorIntro=authorIntro;
        this.photoUrl=photoUrl;
        this.publishing=publishing;
        this.published=published;
        this.description=description;
        this.douban=douban;
        this.doubanScore=doubanScore;
    }
    public String getName(){
        return name;
    }

    public long getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getPublished() {
        return published;
    }

    public String getPublishing() {
        return publishing;
    }

    public Integer getDouban() {
        return douban;
    }

    public Integer getDoubanScore() {
        return doubanScore;
    }

    public String getDescription() {
        return description;
    }
}
