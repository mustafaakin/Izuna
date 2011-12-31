package org.group1f.izuna.GameComponents;

import java.util.ArrayList;
import java.util.List;

public class AttackWave {

    private List<Enemy> enemies = new ArrayList<Enemy>();

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public boolean isFinished() {
        return enemies.isEmpty();
    }

    public void removeEnemy(Enemy e) {
        enemies.remove(e);
    }

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
