package org.group1f.izuna.Contollers.XML;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Mustafa
 */
@Root
public class EnemyList {

    @ElementList
    private List<EnemyInfo> list;
    
    /**
     * 
     * @return
     */
    public List<EnemyInfo> getList() {
        return list;
    }

    /**
     * 
     * @param list
     */
    public void setList(List<EnemyInfo> list) {
        this.list = list;
    }
}