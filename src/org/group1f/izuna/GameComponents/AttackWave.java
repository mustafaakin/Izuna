package org.group1f.izuna.GameComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Mustafa
 */
public class AttackWave {

    private List<Enemy> enemies = new ArrayList<Enemy>();

    /**
     * 
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    /**
     * 
     * @return
     */
    public boolean isFinished() {
        return enemies.isEmpty();
    }

    /**
     * 
     * @param e
     */
    public void removeEnemy(Enemy e) {
        synchronized (enemies) {
            enemies.remove(e);
        }
    }

    /**
     * 
     * @param time
     * @return
     */
    public List<Enemy> startWave(long time) {
        for (Enemy enemy : enemies) {
            enemy.setVisible(true);
        }
        return enemies;
    }

    @Override
    public String toString() {
        return "Enemy Size: " + enemies.size();
    }
}
