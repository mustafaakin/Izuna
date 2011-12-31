package org.group1f.izuna.GameComponents;

import java.util.ArrayDeque;
import java.util.Queue;

public class Level {

    Queue<AttackWave> waves;
    public AttackWave currentWave;

    public Level() {
        waves = new ArrayDeque<AttackWave>();
    }

    public void addWave(AttackWave wave) {
        waves.add(wave);
    }

    public void startLevel() {
        swapNextWave();
    }

    public boolean swapNextWave() {
        if (currentWave.isFinished()) {
            AttackWave wave = waves.poll();
            if (wave != null) {
                currentWave = wave;
            }
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return waves.isEmpty() && currentWave.isFinished();
    }

    public void killEnemy(Enemy e) {
        currentWave.removeEnemy(e);
    }
}
