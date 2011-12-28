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
@Root(name="Wave")
public class WaveInfo {

    @ElementList
    private List<WaveEnemy> Enemies;

    public List<WaveEnemy> getEnemies() {
        return Enemies;
    }    
}
