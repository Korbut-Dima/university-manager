package com.example.university_manager.entity;

public enum Rank {

    ASSISTANT("Assistant"),
    ASSOCIATE("Associate Professor"),
    PROFESSOR("Professor");

    private final String title;

    Rank(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
