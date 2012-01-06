package org.group1f.izuna.Contollers;

/**
 *
 * @author Mustafa
 */
public class Score {

    private int highScore;
    private String time;
    private String name;

    /**
     * 
     * @param highScore
     * @param time
     * @param name
     */
    public Score(int highScore, String time, String name) {
        this.highScore = highScore;
        this.time = time;
        this.name = name;
    }

    /**
     * 
     * @return
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     */
    public String getTime() {
        return time;
    }
}
