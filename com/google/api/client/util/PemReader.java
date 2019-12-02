package com.google.api.client.util;

import java.io.*;
import java.util.regex.*;

@Beta
public final class PemReader
{
    private static final Pattern BEGIN_PATTERN;
    private static final Pattern END_PATTERN;
    private BufferedReader reader;
    
    public PemReader(final Reader a1) {
        super();
        this.reader = new BufferedReader(a1);
    }
    
    public Section readNextSection() throws IOException {
        return this.readNextSection(null);
    }
    
    public Section readNextSection(final String v-3) throws IOException {
        String a2 = null;
        StringBuilder sb = null;
        while (true) {
            final String v0 = this.reader.readLine();
            if (v0 == null) {
                Preconditions.checkArgument(a2 == null, "missing end tag (%s)", a2);
                return null;
            }
            if (sb == null) {
                final Matcher v2 = PemReader.BEGIN_PATTERN.matcher(v0);
                if (!v2.matches()) {
                    continue;
                }
                final String a1 = v2.group(1);
                if (v-3 != null && !a1.equals(v-3)) {
                    continue;
                }
                sb = new StringBuilder();
                a2 = a1;
            }
            else {
                final Matcher v2 = PemReader.END_PATTERN.matcher(v0);
                if (v2.matches()) {
                    final String v3 = v2.group(1);
                    Preconditions.checkArgument(v3.equals(a2), "end tag (%s) doesn't match begin tag (%s)", v3, a2);
                    return new Section(a2, Base64.decodeBase64(sb.toString()));
                }
                sb.append(v0);
            }
        }
    }
    
    public static Section readFirstSectionAndClose(final Reader a1) throws IOException {
        return readFirstSectionAndClose(a1, null);
    }
    
    public static Section readFirstSectionAndClose(final Reader a1, final String a2) throws IOException {
        final PemReader v1 = new PemReader(a1);
        try {
            return v1.readNextSection(a2);
        }
        finally {
            v1.close();
        }
    }
    
    public void close() throws IOException {
        this.reader.close();
    }
    
    static {
        BEGIN_PATTERN = Pattern.compile("-----BEGIN ([A-Z ]+)-----");
        END_PATTERN = Pattern.compile("-----END ([A-Z ]+)-----");
    }
    
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
}
