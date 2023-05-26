package com.example.calculmentalapp;

public class ScoreManager {

    private static int score = 0;

    public static void setScore(int score) {
        ScoreManager.score = score;
    }

    public static int getScore() {
        return score;
    }
}
