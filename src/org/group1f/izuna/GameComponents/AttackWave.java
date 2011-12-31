package org.group1f.izuna.GameComponents;

import java.util.List;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.Contollers.XML.EnemyInfo;

public class AttackWave {

    List<Enemy> enemies;

    public void addEnemy(EnemyInfo info) {
        Enemy e = LoadManager.getEnemy(info.getKey());
        e.setHealth(info.getHealth());
    }
}
