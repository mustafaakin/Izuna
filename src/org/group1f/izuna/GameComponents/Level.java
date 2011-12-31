package org.group1f.izuna.GameComponents;

import java.util.ArrayDeque;
import java.util.Queue;

public class Level {

    private Queue<AttackWave> waves;
    private AttackWave currentWave;

    public AttackWave getCurrentWave() {
        return currentWave;
    }

    public Level() {
        waves = new ArrayDeque<AttackWave>();
    }

    public void addWave(AttackWave wave) {
        waves.add(wave);
    }

    public AttackWave startLevel() {
        currentWave = waves.poll();
        return currentWave;
    }

    public AttackWave swapNextWave() {
        if (currentWave.isFinished()) {
            AttackWave wave = waves.poll();
            if (wave != null) {
                currentWave = wave;
            }
            return currentWave;
        }
        currentWave = null;
        return currentWave;
    }

    public boolean isFinished() {
        return waves.isEmpty() && currentWave.isFinished();
    }

    public void killEnemy(Enemy e) {
        currentWave.removeEnemy(e);
    }    
}
