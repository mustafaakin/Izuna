package org.group1f.izuna.Contollers.XML;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Mustafa
 */
@Root(name="Weapon")
public class WeaponInfo {

    @Attribute
    private String key;
    @Element
    private int DefaultAmount;
    @Element
    private int CausedDamage;
    @Element
    private String FireSound;
    @Element
    private String ExplodeSound;
    @Element
    private int RateOfFire;

    @Override
    public String toString() {
        return key + "-" + DefaultAmount + "-" + CausedDamage;
    }

    public int getCausedDamage() {
        return CausedDamage;
    }

    public int getDefaultAmount() {
        return DefaultAmount;
    }

    public String getKey() {
        return key;
    }

    public int getRateOfFire() {
        return RateOfFire;
    }

    public String getExplodeSound() {
        return ExplodeSound;
    }

    public String getFireSound() {
        return FireSound;
    }

    public void setCausedDamage(int CausedDamage) {
        this.CausedDamage = CausedDamage;
    }

    public void setDefaultAmount(int DefaultAmount) {
        this.DefaultAmount = DefaultAmount;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRateOfFire(int RateOfFire) {
        this.RateOfFire = RateOfFire;
    }
 
    public void setExplodeSound(String ExplodeSound) {
        this.ExplodeSound = ExplodeSound;
    }

    public void setFireSound(String FireSound) {
        this.FireSound = FireSound;
    }
}
