package org.group1f.izuna;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.group1f.izuna.GameComponents.*;
import org.group1f.izuna.GameComponents.Drawing.Sprite;

/**
 *
 * @author Mustafa
 */
public class GameState {

    private Level currentLevel;
    private long startTime;
    private long currentTime;
    private long lastEnemyDeath;
    private boolean showLevelCleared = false;
    private boolean showWaveCleared = false;
    private int currentLevelNo;
    private SoundEffect backgroundMusic;
    private Player p1;
    private Player p2;
    volatile private ArrayList<Enemy> enemies;
    volatile private ArrayList<Bonus> bonuses;
    volatile private ArrayList<Weapon> userWeapons;
    volatile private ArrayList<Weapon> enemyWeapons;
    private int score;
    volatile private ArrayList<GameObject> explosions;

    /**
     * 
     */
    public GameState() {
        enemies = new ArrayList<Enemy>();
        bonuses = new ArrayList<Bonus>();
        userWeapons = new ArrayList<Weapon>();
        enemyWeapons = new ArrayList<Weapon>();
        score = 0;
        explosions = new ArrayList<GameObject>();
        startTime = System.currentTimeMillis();
    }

    /**
     * 
     * @return
     */
    public boolean isShowLevelCleared() {
        return showLevelCleared;
    }

    /**
     * 
     * @return
     */
    public boolean isShowWaveCleared() {
        return showWaveCleared;
    }

    /**
     * 
     * @return
     */
    public SoundEffect getBackgroundMusic() {
        return backgroundMusic;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Bonus> getBonuses() {
        return bonuses;
    }

    /**
     * 
     * @return
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * 
     * @return
     */
    public int getCurrentLevelNo() {
        return currentLevelNo;
    }

    /**
     * 
     * @return
     */
    public long getCurrentTime() {
        return currentTime;
    }

    /**
     * 
     * @return
     */
    public long getLastEnemyDeath() {
        return lastEnemyDeath;
    }

    /**
     * 
     * @return
     */
    public Player getP1() {
        return p1;
    }

    /**
     * 
     * @return
     */
    public Player getP2() {
        return p2;
    }

    /**
     * 
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * 
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param backgroundMusic
     */
    public void setBackgroundMusic(SoundEffect backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    /**
     * 
     * @param bonuses
     */
    public void setBonuses(ArrayList<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

    /**
     * 
     * @param currentLevel
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * 
     * @param currentLevelNo
     */
    public void setCurrentLevelNo(int currentLevelNo) {
        this.currentLevelNo = currentLevelNo;
    }

    /**
     * 
     * @param currentTime
     */
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * 
     * @param enemies
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * 
     * @param enemyWeapons
     */
    public void setEnemyWeapons(ArrayList<Weapon> enemyWeapons) {
        this.enemyWeapons = enemyWeapons;
    }

    /**
     * 
     * @param explosions
     */
    public void setExplosions(ArrayList<GameObject> explosions) {
        this.explosions = explosions;
    }

    /**
     * 
     * @param lastEnemyDeath
     */
    public void setLastEnemyDeath(long lastEnemyDeath) {
        this.lastEnemyDeath = lastEnemyDeath;
    }

    /**
     * 
     * @param p1
     */
    public void setP1(Player p1) {
        this.p1 = p1;
    }

    /**
     * 
     * @param p2
     */
    public void setP2(Player p2) {
        this.p2 = p2;
    }

    /**
     * 
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 
     * @param startTime
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @param userWeapons
     */
    public void setUserWeapons(ArrayList<Weapon> userWeapons) {
        this.userWeapons = userWeapons;
    }

    /**
     * 
     * @return
     */
    public synchronized ArrayList<Weapon> getEnemyWeapons() {
        return enemyWeapons;
    }

    /**
     * 
     * @param showLevelCleared
     */
    public void setShowLevelCleared(boolean showLevelCleared) {
        this.showLevelCleared = showLevelCleared;
    }

    /**
     * 
     * @param showWaveCleared
     */
    public void setShowWaveCleared(boolean showWaveCleared) {
        this.showWaveCleared = showWaveCleared;
    }

    /**
     *
     * @return
     */
    public synchronized ArrayList<GameObject> getExplosions() {
        return explosions;
    }

    /**
     *
     * @return
     */
    public synchronized ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     *
     * @return
     */
    public synchronized ArrayList<Weapon> getUserWeapons() {
        return userWeapons;
    }

    /**
     * 
     * @param difference
     */
    public void increaseScore(int difference) {
        int difficulty = GameCore.getDifficulty();
        double factor = 1;
        if (difficulty == 1) {
            factor = 1.25;
        } else if (factor == 2) {
            factor = 1.75;
        }
        score += (double) (difference * factor);
    }


}
