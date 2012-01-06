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

    public GameState() {
        enemies = new ArrayList<Enemy>();
        bonuses = new ArrayList<Bonus>();
        userWeapons = new ArrayList<Weapon>();
        enemyWeapons = new ArrayList<Weapon>();
        score = 0;
        explosions = new ArrayList<GameObject>();
        startTime = System.currentTimeMillis();
    }

    public boolean isShowLevelCleared() {
        return showLevelCleared;
    }

    public boolean isShowWaveCleared() {
        return showWaveCleared;
    }
        
    public SoundEffect getBackgroundMusic() {
        return backgroundMusic;
    }

    public ArrayList<Bonus> getBonuses() {
        return bonuses;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentLevelNo() {
        return currentLevelNo;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public long getLastEnemyDeath() {
        return lastEnemyDeath;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public int getScore() {
        return score;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setBackgroundMusic(SoundEffect backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public void setBonuses(ArrayList<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setCurrentLevelNo(int currentLevelNo) {
        this.currentLevelNo = currentLevelNo;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setEnemyWeapons(ArrayList<Weapon> enemyWeapons) {
        this.enemyWeapons = enemyWeapons;
    }

    public void setExplosions(ArrayList<GameObject> explosions) {
        this.explosions = explosions;
    }

    public void setLastEnemyDeath(long lastEnemyDeath) {
        this.lastEnemyDeath = lastEnemyDeath;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setUserWeapons(ArrayList<Weapon> userWeapons) {
        this.userWeapons = userWeapons;
    }

    public synchronized ArrayList<Weapon> getEnemyWeapons() {
        return enemyWeapons;
    }

    public void setShowLevelCleared(boolean showLevelCleared) {
        this.showLevelCleared = showLevelCleared;
    }

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
}
