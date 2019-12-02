package com.google.api.client.util;

public static final class Section
{
    private final String title;
    private final byte[] base64decodedBytes;
    
    Section(final String a1, final byte[] a2) {
        super();
        this.title = Preconditions.checkNotNull(a1);
        this.base64decodedBytes = Preconditions.checkNotNull(a2);
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public byte[] getBase64DecodedBytes() {
        return this.base64decodedBytes;
    }
}
