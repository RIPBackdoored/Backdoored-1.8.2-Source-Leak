package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public final class FileContent extends AbstractInputStreamContent
{
    private final File file;
    
    public FileContent(final String a1, final File a2) {
        super(a1);
        this.file = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public long getLength() {
        return this.file.length();
    }
    
    @Override
    public boolean retrySupported() {
        return true;
    }
    
    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(this.file);
    }
    
    public File getFile() {
        return this.file;
    }
    
    @Override
    public FileContent setType(final String a1) {
        return (FileContent)super.setType(a1);
    }
    
    @Override
    public FileContent setCloseInputStream(final boolean a1) {
        return (FileContent)super.setCloseInputStream(a1);
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setCloseInputStream(final boolean closeInputStream) {
        return this.setCloseInputStream(closeInputStream);
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setType(final String type) {
        return this.setType(type);
    }
}
