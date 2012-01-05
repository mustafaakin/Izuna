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
    @Element
    private int Speed;
    
    
    @Override
    public String toString() {
        return key + "-" + DefaultAmount + "-" + CausedDamage;
    }

    /**
     * 
     * @return
     */
    public int getSpeed() {
        return Speed;
    }
    
    /**
     * 
     * @return
     */
    public int getCausedDamage() {
        return CausedDamage;
    }

    /**
     * 
     * @return
     */
    public int getDefaultAmount() {
        return DefaultAmount;
    }

    /**
     * 
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * 
     * @return
     */
    public int getRateOfFire() {
        return RateOfFire;
    }

    /**
     * 
     * @return
     */
    public String getExplodeSound() {
        return ExplodeSound;
    }

    /**
     * 
     * @return
     */
    public String getFireSound() {
        return FireSound;
    }

    /**
     * 
     * @param CausedDamage
     */
    public void setCausedDamage(int CausedDamage) {
        this.CausedDamage = CausedDamage;
    }

    /**
     * 
     * @param DefaultAmount
     */
    public void setDefaultAmount(int DefaultAmount) {
        this.DefaultAmount = DefaultAmount;
    }

    /**
     * 
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 
     * @param RateOfFire
     */
    public void setRateOfFire(int RateOfFire) {
        this.RateOfFire = RateOfFire;
    }
 
    /**
     * 
     * @param ExplodeSound
     */
    public void setExplodeSound(String ExplodeSound) {
        this.ExplodeSound = ExplodeSound;
    }

    /**
     * 
     * @param FireSound
     */
    public void setFireSound(String FireSound) {
        this.FireSound = FireSound;
    }
}
