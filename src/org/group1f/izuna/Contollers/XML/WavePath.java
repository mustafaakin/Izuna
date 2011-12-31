package org.group1f.izuna.Contollers.XML;

import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "Path")
public class WavePath {

    @Attribute
    private String type;
    @Element(required = false)
    private int startX;
    @Element(required = false)
    private int startY;
    @Element(required = false)
    private int midX;
    @Element(required = false)
    private int midY;
    @Element(required = false)
    private int endX;
    @Element(required = false)
    private int endY;
    @Element
    private int duration;

    public int getDuration() {
        return duration;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getMidX() {
        return midX;
    }

    public int getMidY() {
        return midY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public String getType() {
        return type;
    }
}