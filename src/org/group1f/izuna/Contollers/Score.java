package org.group1f.izuna.Contollers;

/**
 *
 * @author Mustafa
 */
public class Score {

    private int highScore;
    private String time;
    private String name;

    public Score(int highScore, String time, String name) {
        this.highScore = highScore;
        this.time = time;
        this.name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
