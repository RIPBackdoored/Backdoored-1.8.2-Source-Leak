package com.google.api.client.json;

import java.util.*;
import com.google.api.client.util.*;

public static class Builder
{
    final JsonFactory jsonFactory;
    Collection<String> wrapperKeys;
    
    public Builder(final JsonFactory a1) {
        super();
        this.wrapperKeys = (Collection<String>)Sets.newHashSet();
        this.jsonFactory = Preconditions.checkNotNull(a1);
    }
    
    public JsonObjectParser build() {
        return new JsonObjectParser(this);
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final Collection<String> getWrapperKeys() {
        return this.wrapperKeys;
    }
    
    public Builder setWrapperKeys(final Collection<String> a1) {
        this.wrapperKeys = a1;
        return this;
    }
}
