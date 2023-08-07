package com.example.lab8_androinwk.Bai1_Bai2;

public class Todo {
    private String title, id,author;
    private boolean isDone;

    @Override
    public String toString() {
        return "Todo{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", isDone=" + isDone +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
