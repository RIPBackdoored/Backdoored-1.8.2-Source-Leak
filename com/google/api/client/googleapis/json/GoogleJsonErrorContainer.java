package com.google.api.client.googleapis.json;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class GoogleJsonErrorContainer extends GenericJson
{
    @Key
    private GoogleJsonError error;
    
    public GoogleJsonErrorContainer() {
        super();
    }
    
    public final GoogleJsonError getError() {
        return this.error;
    }
    
    public final void setError(final GoogleJsonError a1) {
        this.error = a1;
    }
    
    @Override
    public GoogleJsonErrorContainer set(final String a1, final Object a2) {
        return (GoogleJsonErrorContainer)super.set(a1, a2);
    }
    
    @Override
    public GoogleJsonErrorContainer clone() {
        return (GoogleJsonErrorContainer)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String a1, final Object a2) {
        return this.set(a1, a2);
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
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
