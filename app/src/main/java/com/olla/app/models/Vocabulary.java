package com.olla.app.models;

public class Vocabulary {
    private int id;
    private String nativeWord;
    private String targetWord;
    private String languagePair;

    public Vocabulary() {
    }

    public Vocabulary(String nativeWord, String targetWord, String languagePair) {
        this.nativeWord = nativeWord;
        this.targetWord = targetWord;
        this.languagePair = languagePair;
    }

    public Vocabulary(int id, String nativeWord, String targetWord, String languagePair) {
        this.id = id;
        this.nativeWord = nativeWord;
        this.targetWord = targetWord;
        this.languagePair = languagePair;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNativeWord() {
        return nativeWord;
    }

    public void setNativeWord(String nativeWord) {
        this.nativeWord = nativeWord;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public String getLanguagePair() {
        return languagePair;
    }

    public void setLanguagePair(String languagePair) {
        this.languagePair = languagePair;
    }
}

