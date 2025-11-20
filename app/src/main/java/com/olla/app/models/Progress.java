package com.olla.app.models;

public class Progress {
    private int id;
    private int quizId;
    private int score;
    private String date;
    private String language;
    private int profileId;
    private String quizName;
    private int totalQuestions;

    public Progress() {
    }

    public Progress(int quizId, int score, String date, String language, 
                   int profileId, String quizName, int totalQuestions) {
        this.quizId = quizId;
        this.score = score;
        this.date = date;
        this.language = language;
        this.profileId = profileId;
        this.quizName = quizName;
        this.totalQuestions = totalQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}

