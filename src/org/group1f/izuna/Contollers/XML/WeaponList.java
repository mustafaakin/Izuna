package org.group1f.izuna.Contollers.XML;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Mustafa
 */
@Root
public class WeaponList {

    @ElementList
    private List<WeaponInfo> list;
    
    public List getList() {
        return list;
    }

    public void setList(List<WeaponInfo> list) {
        this.list = list;
    }

}