package com.google.cloud.storage;

import java.io.*;

public static class SourceBlob implements Serializable
{
    private static final long serialVersionUID = 4094962795951990439L;
    final String name;
    final Long generation;
    
    SourceBlob(final String a1) {
        this(a1, null);
    }
    
    SourceBlob(final String a1, final Long a2) {
        super();
        this.name = a1;
        this.generation = a2;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Long getGeneration() {
        return this.generation;
    }
}
