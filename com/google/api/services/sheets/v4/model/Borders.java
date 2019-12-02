package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class Borders extends GenericJson
{
    @Key
    private Border bottom;
    @Key
    private Border left;
    @Key
    private Border right;
    @Key
    private Border top;
    
    public Borders() {
        super();
    }
    
    public Border getBottom() {
        return this.bottom;
    }
    
    public Borders setBottom(final Border bottom) {
        this.bottom = bottom;
        return this;
    }
    
    public Border getLeft() {
        return this.left;
    }
    
    public Borders setLeft(final Border left) {
        this.left = left;
        return this;
    }
    
    public Border getRight() {
        return this.right;
    }
    
    public Borders setRight(final Border right) {
        this.right = right;
        return this;
    }
    
    public Border getTop() {
        return this.top;
    }
    
    public Borders setTop(final Border top) {
        this.top = top;
        return this;
    }
    
    @Override
    public Borders set(final String a1, final Object a2) {
        return (Borders)super.set(a1, a2);
    }
    
    @Override
    public Borders clone() {
        return (Borders)super.clone();
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
