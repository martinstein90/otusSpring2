package com.martin.domain;

public class Genre implements Storable {
    private  int id;
    private final String title;

    public Genre(String title) {
        this.title = title;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
