package com.github.gymodo.news;

import org.simpleframework.xml.Element;

public class News {

    @Element(name = "title", required = false)
    private String title;
    @Element(name = "description", required = false)
    private String description;

    public News() {
    }

    public News(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public News setDescription(String description) {
        this.description = description;
        return this;
    }
}
