package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class Padding extends GenericJson
{
    @Key
    private Integer bottom;
    @Key
    private Integer left;
    @Key
    private Integer right;
    @Key
    private Integer top;
    
    public Padding() {
        super();
    }
    
    public Integer getBottom() {
        return this.bottom;
    }
    
    public Padding setBottom(final Integer bottom) {
        this.bottom = bottom;
        return this;
    }
    
    public Integer getLeft() {
        return this.left;
    }
    
    public Padding setLeft(final Integer left) {
        this.left = left;
        return this;
    }
    
    public Integer getRight() {
        return this.right;
    }
    
    public Padding setRight(final Integer right) {
        this.right = right;
        return this;
    }
    
    public Integer getTop() {
        return this.top;
    }
    
    public Padding setTop(final Integer top) {
        this.top = top;
        return this;
    }
    
    @Override
    public Padding set(final String a1, final Object a2) {
        return (Padding)super.set(a1, a2);
    }
    
    @Override
    public Padding clone() {
        return (Padding)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
