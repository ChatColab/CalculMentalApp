package com.example.calculmentalapp.db.entities;

public class Score extends BaseEntity{
    String userName;
    Integer score;

    public Score() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
