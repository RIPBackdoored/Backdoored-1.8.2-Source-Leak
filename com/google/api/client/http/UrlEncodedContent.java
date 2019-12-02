package com.google.api.client.http;

import com.google.api.client.util.escape.*;
import java.io.*;
import java.util.*;
import com.google.api.client.util.*;

public class UrlEncodedContent extends AbstractHttpContent
{
    private Object data;
    
    public UrlEncodedContent(final Object a1) {
        super(UrlEncodedParser.MEDIA_TYPE);
        this.setData(a1);
    }
    
    @Override
    public void writeTo(final OutputStream v-5) throws IOException {
        final Writer writer = new BufferedWriter(new OutputStreamWriter(v-5, this.getCharset()));
        boolean b = true;
        for (final Map.Entry<String, Object> entry : Data.mapOf(this.data).entrySet()) {
            final Object v0 = entry.getValue();
            if (v0 != null) {
                final String v2 = CharEscapers.escapeUri(entry.getKey());
                final Class<?> v3 = v0.getClass();
                if (v0 instanceof Iterable || v3.isArray()) {
                    for (final Object a1 : Types.iterableOf(v0)) {
                        b = appendParam(b, writer, v2, a1);
                    }
                }
                else {
                    b = appendParam(b, writer, v2, v0);
                }
            }
        }
        writer.flush();
    }
    
    @Override
    public UrlEncodedContent setMediaType(final HttpMediaType a1) {
        super.setMediaType(a1);
        return this;
    }
    
    public final Object getData() {
        return this.data;
    }
    
    public UrlEncodedContent setData(final Object a1) {
        this.data = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public static UrlEncodedContent getContent(final HttpRequest a1) {
        final HttpContent v1 = a1.getContent();
        if (v1 != null) {
            return (UrlEncodedContent)v1;
        }
        final UrlEncodedContent v2 = new UrlEncodedContent(new HashMap());
        a1.setContent(v2);
        return v2;
    }
    
    private static boolean appendParam(boolean a1, final Writer a2, final String a3, final Object a4) throws IOException {
        if (a4 == null || Data.isNull(a4)) {
            return a1;
        }
        if (a1) {
            a1 = false;
        }
        else {
            a2.write("&");
        }
        a2.write(a3);
        final String v1 = CharEscapers.escapeUri((a4 instanceof Enum) ? FieldInfo.of((Enum<?>)a4).getName() : a4.toString());
        if (v1.length() != 0) {
            a2.write("=");
            a2.write(v1);
        }
        return a1;
    }
    
    @Override
    public /* bridge */ AbstractHttpContent setMediaType(final HttpMediaType mediaType) {
        return this.setMediaType(mediaType);
    }
}
