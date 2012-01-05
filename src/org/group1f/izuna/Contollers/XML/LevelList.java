package org.group1f.izuna.Contollers.XML;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Mustafa
 */
@Root()
public class LevelList {

    @ElementList
    private List<LevelInfo> List;
    
    /**
     * 
     * @return
     */
    public List<LevelInfo> getList() {
        return List;
    }

    /**
     * 
     * @param list
     */
    public void setList(List<LevelInfo> list) {
        this.List = list;
    }
}