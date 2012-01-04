package org.group1f.izuna.Contollers.XML;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Mustafa
 */
@Root(name="Enemy")
public class EnemyInfo {
    @Attribute
    private String key;
    @Element
    private int Health;
    @Element
    private String EnterSound;
    @Element
    private String Weapon;

    public String getEnterSound() {
        return EnterSound;
    }

    public int getHealth() {
        return Health;
    }

    public String getKey() {
        return key;
    }

    public String getWeapon() {
        return Weapon;
    }
}
