package org.json;

import java.util.*;

public static class Builder
{
    private final List<String> refTokens;
    
    public Builder() {
        super();
        this.refTokens = new ArrayList<String>();
    }
    
    public JSONPointer build() {
        return new JSONPointer(this.refTokens);
    }
    
    public Builder append(final String a1) {
        if (a1 == null) {
            throw new NullPointerException("token cannot be null");
        }
        this.refTokens.add(a1);
        return this;
    }
    
    public Builder append(final int a1) {
        this.refTokens.add(String.valueOf(a1));
        return this;
    }
}
