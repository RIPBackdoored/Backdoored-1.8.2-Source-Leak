package com.google.api.client.http;

import java.util.logging.*;
import java.io.*;
import java.util.*;
import com.google.api.client.util.*;

public class MultipartContent extends AbstractHttpContent
{
    static final String NEWLINE = "\r\n";
    private static final String TWO_DASHES = "--";
    private ArrayList<Part> parts;
    
    public MultipartContent() {
        super(new HttpMediaType("multipart/related").setParameter("boundary", "__END_OF_PART__"));
        this.parts = new ArrayList<Part>();
    }
    
    @Override
    public void writeTo(final OutputStream v-7) throws IOException {
        final Writer a2 = new OutputStreamWriter(v-7, this.getCharset());
        final String boundary = this.getBoundary();
        for (final Part part : this.parts) {
            final HttpHeaders setAcceptEncoding = new HttpHeaders().setAcceptEncoding(null);
            if (part.headers != null) {
                setAcceptEncoding.fromHttpHeaders(part.headers);
            }
            setAcceptEncoding.setContentEncoding(null).setUserAgent(null).setContentType(null).setContentLength(null).set("Content-Transfer-Encoding", null);
            final HttpContent content = part.content;
            StreamingContent v0 = null;
            if (content != null) {
                setAcceptEncoding.set("Content-Transfer-Encoding", Arrays.asList("binary"));
                setAcceptEncoding.setContentType(content.getType());
                final HttpEncoding v2 = part.encoding;
                long v3 = 0L;
                if (v2 == null) {
                    final long a1 = content.getLength();
                    v0 = content;
                }
                else {
                    setAcceptEncoding.setContentEncoding(v2.getName());
                    v0 = new HttpEncodingStreamingContent(content, v2);
                    v3 = AbstractHttpContent.computeLength(content);
                }
                if (v3 != -1L) {
                    setAcceptEncoding.setContentLength(v3);
                }
            }
            a2.write("--");
            a2.write(boundary);
            a2.write("\r\n");
            HttpHeaders.serializeHeadersForMultipartRequests(setAcceptEncoding, null, null, a2);
            if (v0 != null) {
                a2.write("\r\n");
                a2.flush();
                v0.writeTo(v-7);
            }
            a2.write("\r\n");
        }
        a2.write("--");
        a2.write(boundary);
        a2.write("--");
        a2.write("\r\n");
        a2.flush();
    }
    
    @Override
    public boolean retrySupported() {
        for (final Part v1 : this.parts) {
            if (!v1.content.retrySupported()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public MultipartContent setMediaType(final HttpMediaType a1) {
        super.setMediaType(a1);
        return this;
    }
    
    public final Collection<Part> getParts() {
        return Collections.unmodifiableCollection((Collection<? extends Part>)this.parts);
    }
    
    public MultipartContent addPart(final Part a1) {
        this.parts.add(Preconditions.checkNotNull(a1));
        return this;
    }
    
    public MultipartContent setParts(final Collection<Part> a1) {
        this.parts = new ArrayList<Part>(a1);
        return this;
    }
    
    public MultipartContent setContentParts(final Collection<? extends HttpContent> v2) {
        this.parts = new ArrayList<Part>(v2.size());
        for (final HttpContent a1 : v2) {
            this.addPart(new Part(a1));
        }
        return this;
    }
    
    public final String getBoundary() {
        return this.getMediaType().getParameter("boundary");
    }
    
    public MultipartContent setBoundary(final String a1) {
        this.getMediaType().setParameter("boundary", Preconditions.checkNotNull(a1));
        return this;
    }
    
    @Override
    public /* bridge */ AbstractHttpContent setMediaType(final HttpMediaType mediaType) {
        return this.setMediaType(mediaType);
    }
    
    public static final class Part
    {
        HttpContent content;
        HttpHeaders headers;
        HttpEncoding encoding;
        
        public Part() {
            this(null);
        }
        
        public Part(final HttpContent a1) {
            this(null, a1);
        }
        
        public Part(final HttpHeaders a1, final HttpContent a2) {
            super();
            this.setHeaders(a1);
            this.setContent(a2);
        }
        
        public Part setContent(final HttpContent a1) {
            this.content = a1;
            return this;
        }
        
        public HttpContent getContent() {
            return this.content;
        }
        
        public Part setHeaders(final HttpHeaders a1) {
            this.headers = a1;
            return this;
        }
        
        public HttpHeaders getHeaders() {
            return this.headers;
        }
        
        public Part setEncoding(final HttpEncoding a1) {
            this.encoding = a1;
            return this;
        }
        
        public HttpEncoding getEncoding() {
            return this.encoding;
        }
    }
}
