package com.olla.app.models;

public class Profile {
    private int id;
    private String username;
    private String password;
    private String nativeLanguage;
    private String targetLanguage;

    public Profile() {
    }

    public Profile(String username, String password, String nativeLanguage, String targetLanguage) {
        this.username = username;
        this.password = password;
        this.nativeLanguage = nativeLanguage;
        this.targetLanguage = targetLanguage;
    }

    public Profile(int id, String username, String password, String nativeLanguage, String targetLanguage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nativeLanguage = nativeLanguage;
        this.targetLanguage = targetLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}

