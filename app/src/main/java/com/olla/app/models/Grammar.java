package com.olla.app.models;

public class Grammar {
    private int id;
    private String topic;
    private String content;
    private String language;

    public Grammar() {
    }

    public Grammar(String topic, String content, String language) {
        this.topic = topic;
        this.content = content;
        this.language = language;
    }

    public Grammar(int id, String topic, String content, String language) {
        this.id = id;
        this.topic = topic;
        this.content = content;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

