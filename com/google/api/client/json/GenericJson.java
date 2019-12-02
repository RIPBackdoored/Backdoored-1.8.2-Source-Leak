package com.google.api.client.json;

import com.google.api.client.util.*;
import java.io.*;

public class GenericJson extends GenericData implements Cloneable
{
    private JsonFactory jsonFactory;
    
    public GenericJson() {
        super();
    }
    
    public final JsonFactory getFactory() {
        return this.jsonFactory;
    }
    
    public final void setFactory(final JsonFactory a1) {
        this.jsonFactory = a1;
    }
    
    @Override
    public String toString() {
        if (this.jsonFactory != null) {
            try {
                return this.jsonFactory.toString(this);
            }
            catch (IOException v1) {
                throw Throwables.propagate(v1);
            }
        }
        return super.toString();
    }
    
    public String toPrettyString() throws IOException {
        if (this.jsonFactory != null) {
            return this.jsonFactory.toPrettyString(this);
        }
        return super.toString();
    }
    
    @Override
    public GenericJson clone() {
        return (GenericJson)super.clone();
    }
    
    @Override
    public GenericJson set(final String a1, final Object a2) {
        return (GenericJson)super.set(a1, a2);
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
