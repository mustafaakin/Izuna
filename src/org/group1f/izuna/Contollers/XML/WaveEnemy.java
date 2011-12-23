package org.group1f.izuna.Contollers.XML;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="Enemy")
public class WaveEnemy {
    @Attribute
    private String key;
    @ElementList
    private List<WavePath> Paths;
}