package org.group1f.izuna.Contollers.XML;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Mustafa
 */
@Root(name="Level")
public class LevelInfo {
    @Attribute
    private int no;
    @Element
    private String Name;
    @Element
    private String Password;
    @ElementList()
    private List<WaveInfo> Waves;

    public String getPassword() {
        return Password;
    }
    
    /**
     * 
     * @return
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @return
     */
    public int getNo() {
        return no;
    }

    /**
     * 
     * @return
     */
    public List<WaveInfo> getWaves() {
        return Waves;
    }
    
    
}
