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
    @ElementList()
    private List<WaveInfo> Waves;
}
