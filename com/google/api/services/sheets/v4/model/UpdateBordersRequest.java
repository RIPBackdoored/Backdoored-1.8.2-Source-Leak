package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateBordersRequest extends GenericJson
{
    @Key
    private Border bottom;
    @Key
    private Border innerHorizontal;
    @Key
    private Border innerVertical;
    @Key
    private Border left;
    @Key
    private GridRange range;
    @Key
    private Border right;
    @Key
    private Border top;
    
    public UpdateBordersRequest() {
        super();
    }
    
    public Border getBottom() {
        return this.bottom;
    }
    
    public UpdateBordersRequest setBottom(final Border bottom) {
        this.bottom = bottom;
        return this;
    }
    
    public Border getInnerHorizontal() {
        return this.innerHorizontal;
    }
    
    public UpdateBordersRequest setInnerHorizontal(final Border innerHorizontal) {
        this.innerHorizontal = innerHorizontal;
        return this;
    }
    
    public Border getInnerVertical() {
        return this.innerVertical;
    }
    
    public UpdateBordersRequest setInnerVertical(final Border innerVertical) {
        this.innerVertical = innerVertical;
        return this;
    }
    
    public Border getLeft() {
        return this.left;
    }
    
    public UpdateBordersRequest setLeft(final Border left) {
        this.left = left;
        return this;
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public UpdateBordersRequest setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public Border getRight() {
        return this.right;
    }
    
    public UpdateBordersRequest setRight(final Border right) {
        this.right = right;
        return this;
    }
    
    public Border getTop() {
        return this.top;
    }
    
    public UpdateBordersRequest setTop(final Border top) {
        this.top = top;
        return this;
    }
    
    @Override
    public UpdateBordersRequest set(final String a1, final Object a2) {
        return (UpdateBordersRequest)super.set(a1, a2);
    }
    
    @Override
    public UpdateBordersRequest clone() {
        return (UpdateBordersRequest)super.clone();
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
