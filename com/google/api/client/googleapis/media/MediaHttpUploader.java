package com.google.api.client.googleapis.media;

import java.util.*;
import java.io.*;
import com.google.api.client.googleapis.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public final class MediaHttpUploader
{
    public static final String CONTENT_LENGTH_HEADER = "X-Upload-Content-Length";
    public static final String CONTENT_TYPE_HEADER = "X-Upload-Content-Type";
    private UploadState uploadState;
    static final int MB = 1048576;
    private static final int KB = 1024;
    public static final int MINIMUM_CHUNK_SIZE = 262144;
    public static final int DEFAULT_CHUNK_SIZE = 10485760;
    private final AbstractInputStreamContent mediaContent;
    private final HttpRequestFactory requestFactory;
    private final HttpTransport transport;
    private HttpContent metadata;
    private long mediaContentLength;
    private boolean isMediaContentLengthCalculated;
    private String initiationRequestMethod;
    private HttpHeaders initiationHeaders;
    private HttpRequest currentRequest;
    private InputStream contentInputStream;
    private boolean directUploadEnabled;
    private MediaHttpUploaderProgressListener progressListener;
    String mediaContentLengthStr;
    private long totalBytesServerReceived;
    private int chunkSize;
    private Byte cachedByte;
    private long totalBytesClientSent;
    private int currentChunkLength;
    private byte[] currentRequestContentBuffer;
    private boolean disableGZipContent;
    Sleeper sleeper;
    
    public MediaHttpUploader(final AbstractInputStreamContent a1, final HttpTransport a2, final HttpRequestInitializer a3) {
        super();
        this.uploadState = UploadState.NOT_STARTED;
        this.initiationRequestMethod = "POST";
        this.initiationHeaders = new HttpHeaders();
        this.mediaContentLengthStr = "*";
        this.chunkSize = 10485760;
        this.sleeper = Sleeper.DEFAULT;
        this.mediaContent = Preconditions.checkNotNull(a1);
        this.transport = Preconditions.checkNotNull(a2);
        this.requestFactory = ((a3 == null) ? a2.createRequestFactory() : a2.createRequestFactory(a3));
    }
    
    public HttpResponse upload(final GenericUrl a1) throws IOException {
        Preconditions.checkArgument(this.uploadState == UploadState.NOT_STARTED);
        if (this.directUploadEnabled) {
            return this.directUpload(a1);
        }
        return this.resumableUpload(a1);
    }
    
    private HttpResponse directUpload(final GenericUrl a1) throws IOException {
        this.updateStateAndNotifyListener(UploadState.MEDIA_IN_PROGRESS);
        HttpContent v1 = this.mediaContent;
        if (this.metadata != null) {
            v1 = new MultipartContent().setContentParts(Arrays.asList(this.metadata, this.mediaContent));
            a1.put("uploadType", "multipart");
        }
        else {
            a1.put("uploadType", "media");
        }
        final HttpRequest v2 = this.requestFactory.buildRequest(this.initiationRequestMethod, a1, v1);
        v2.getHeaders().putAll(this.initiationHeaders);
        final HttpResponse v3 = this.executeCurrentRequest(v2);
        boolean v4 = false;
        try {
            if (this.isMediaLengthKnown()) {
                this.totalBytesServerReceived = this.getMediaContentLength();
            }
            this.updateStateAndNotifyListener(UploadState.MEDIA_COMPLETE);
            v4 = true;
        }
        finally {
            if (!v4) {
                v3.disconnect();
            }
        }
        return v3;
    }
    
    private HttpResponse resumableUpload(final GenericUrl v-2) throws IOException {
        final HttpResponse executeUploadInitiation = this.executeUploadInitiation(v-2);
        if (!executeUploadInitiation.isSuccessStatusCode()) {
            return executeUploadInitiation;
        }
        try {
            final GenericUrl a1 = new GenericUrl(executeUploadInitiation.getHeaders().getLocation());
        }
        finally {
            executeUploadInitiation.disconnect();
        }
        this.contentInputStream = this.mediaContent.getInputStream();
        if (!this.contentInputStream.markSupported() && this.isMediaLengthKnown()) {
            this.contentInputStream = new BufferedInputStream(this.contentInputStream);
        }
        while (true) {
            GenericUrl v0 = null;
            this.currentRequest = this.requestFactory.buildPutRequest(v0, null);
            this.setContentAndHeadersOnCurrentRequest();
            new MediaUploadErrorHandler(this, this.currentRequest);
            HttpResponse v2;
            if (this.isMediaLengthKnown()) {
                v2 = this.executeCurrentRequestWithoutGZip(this.currentRequest);
            }
            else {
                v2 = this.executeCurrentRequest(this.currentRequest);
            }
            boolean v3 = false;
            try {
                if (v2.isSuccessStatusCode()) {
                    this.totalBytesServerReceived = this.getMediaContentLength();
                    if (this.mediaContent.getCloseInputStream()) {
                        this.contentInputStream.close();
                    }
                    this.updateStateAndNotifyListener(UploadState.MEDIA_COMPLETE);
                    v3 = true;
                    return v2;
                }
                if (v2.getStatusCode() != 308) {
                    v3 = true;
                    return v2;
                }
                final String v4 = v2.getHeaders().getLocation();
                if (v4 != null) {
                    v0 = new GenericUrl(v4);
                }
                final long v5 = this.getNextByteIndex(v2.getHeaders().getRange());
                final long v6 = v5 - this.totalBytesServerReceived;
                Preconditions.checkState(v6 >= 0L && v6 <= this.currentChunkLength);
                final long v7 = this.currentChunkLength - v6;
                if (this.isMediaLengthKnown()) {
                    if (v7 > 0L) {
                        this.contentInputStream.reset();
                        final long v8 = this.contentInputStream.skip(v6);
                        Preconditions.checkState(v6 == v8);
                    }
                }
                else if (v7 == 0L) {
                    this.currentRequestContentBuffer = null;
                }
                this.totalBytesServerReceived = v5;
                this.updateStateAndNotifyListener(UploadState.MEDIA_IN_PROGRESS);
            }
            finally {
                if (!v3) {
                    v2.disconnect();
                }
            }
        }
    }
    
    private boolean isMediaLengthKnown() throws IOException {
        return this.getMediaContentLength() >= 0L;
    }
    
    private long getMediaContentLength() throws IOException {
        if (!this.isMediaContentLengthCalculated) {
            this.mediaContentLength = this.mediaContent.getLength();
            this.isMediaContentLengthCalculated = true;
        }
        return this.mediaContentLength;
    }
    
    private HttpResponse executeUploadInitiation(final GenericUrl a1) throws IOException {
        this.updateStateAndNotifyListener(UploadState.INITIATION_STARTED);
        a1.put("uploadType", "resumable");
        final HttpContent v1 = (this.metadata == null) ? new EmptyContent() : this.metadata;
        final HttpRequest v2 = this.requestFactory.buildRequest(this.initiationRequestMethod, a1, v1);
        this.initiationHeaders.set("X-Upload-Content-Type", this.mediaContent.getType());
        if (this.isMediaLengthKnown()) {
            this.initiationHeaders.set("X-Upload-Content-Length", this.getMediaContentLength());
        }
        v2.getHeaders().putAll(this.initiationHeaders);
        final HttpResponse v3 = this.executeCurrentRequest(v2);
        boolean v4 = false;
        try {
            this.updateStateAndNotifyListener(UploadState.INITIATION_COMPLETE);
            v4 = true;
        }
        finally {
            if (!v4) {
                v3.disconnect();
            }
        }
        return v3;
    }
    
    private HttpResponse executeCurrentRequestWithoutGZip(final HttpRequest a1) throws IOException {
        new MethodOverride().intercept(a1);
        a1.setThrowExceptionOnExecuteError(false);
        final HttpResponse v1 = a1.execute();
        return v1;
    }
    
    private HttpResponse executeCurrentRequest(final HttpRequest a1) throws IOException {
        if (!this.disableGZipContent && !(a1.getContent() instanceof EmptyContent)) {
            a1.setEncoding(new GZipEncoding());
        }
        final HttpResponse v1 = this.executeCurrentRequestWithoutGZip(a1);
        return v1;
    }
    
    private void setContentAndHeadersOnCurrentRequest() throws IOException {
        int v1;
        if (this.isMediaLengthKnown()) {
            v1 = (int)Math.min(this.chunkSize, this.getMediaContentLength() - this.totalBytesServerReceived);
        }
        else {
            v1 = this.chunkSize;
        }
        int v2 = v1;
        AbstractInputStreamContent v4;
        if (this.isMediaLengthKnown()) {
            this.contentInputStream.mark(v1);
            final InputStream v3 = ByteStreams.limit(this.contentInputStream, v1);
            v4 = new InputStreamContent(this.mediaContent.getType(), v3).setRetrySupported(true).setLength(v1).setCloseInputStream(false);
            this.mediaContentLengthStr = String.valueOf(this.getMediaContentLength());
        }
        else {
            int v5 = 0;
            int v6;
            if (this.currentRequestContentBuffer == null) {
                v6 = ((this.cachedByte == null) ? (v1 + 1) : v1);
                this.currentRequestContentBuffer = new byte[v1 + 1];
                if (this.cachedByte != null) {
                    this.currentRequestContentBuffer[0] = this.cachedByte;
                }
            }
            else {
                v5 = (int)(this.totalBytesClientSent - this.totalBytesServerReceived);
                System.arraycopy(this.currentRequestContentBuffer, this.currentChunkLength - v5, this.currentRequestContentBuffer, 0, v5);
                if (this.cachedByte != null) {
                    this.currentRequestContentBuffer[v5] = this.cachedByte;
                }
                v6 = v1 - v5;
            }
            final int v7 = ByteStreams.read(this.contentInputStream, this.currentRequestContentBuffer, v1 + 1 - v6, v6);
            if (v7 < v6) {
                v2 = v5 + Math.max(0, v7);
                if (this.cachedByte != null) {
                    ++v2;
                    this.cachedByte = null;
                }
                if (this.mediaContentLengthStr.equals("*")) {
                    this.mediaContentLengthStr = String.valueOf(this.totalBytesServerReceived + v2);
                }
            }
            else {
                this.cachedByte = this.currentRequestContentBuffer[v1];
            }
            v4 = new ByteArrayContent(this.mediaContent.getType(), this.currentRequestContentBuffer, 0, v2);
            this.totalBytesClientSent = this.totalBytesServerReceived + v2;
        }
        this.currentChunkLength = v2;
        this.currentRequest.setContent(v4);
        if (v2 == 0) {
            final HttpHeaders headers = this.currentRequest.getHeaders();
            final String s = "bytes */";
            final String value = String.valueOf(this.mediaContentLengthStr);
            headers.setContentRange((value.length() != 0) ? s.concat(value) : new String(s));
        }
        else {
            final HttpHeaders headers2 = this.currentRequest.getHeaders();
            final long totalBytesServerReceived = this.totalBytesServerReceived;
            final long n = this.totalBytesServerReceived + v2 - 1L;
            final String value2 = String.valueOf(String.valueOf(this.mediaContentLengthStr));
            headers2.setContentRange(new StringBuilder(48 + value2.length()).append("bytes ").append(totalBytesServerReceived).append("-").append(n).append("/").append(value2).toString());
        }
    }
    
    @Beta
    void serverErrorCallback() throws IOException {
        Preconditions.checkNotNull(this.currentRequest, (Object)"The current request should not be null");
        this.currentRequest.setContent(new EmptyContent());
        final HttpHeaders headers = this.currentRequest.getHeaders();
        final String s = "bytes */";
        final String value = String.valueOf(this.mediaContentLengthStr);
        headers.setContentRange((value.length() != 0) ? s.concat(value) : new String(s));
    }
    
    private long getNextByteIndex(final String a1) {
        if (a1 == null) {
            return 0L;
        }
        return Long.parseLong(a1.substring(a1.indexOf(45) + 1)) + 1L;
    }
    
    public HttpContent getMetadata() {
        return this.metadata;
    }
    
    public MediaHttpUploader setMetadata(final HttpContent a1) {
        this.metadata = a1;
        return this;
    }
    
    public HttpContent getMediaContent() {
        return this.mediaContent;
    }
    
    public HttpTransport getTransport() {
        return this.transport;
    }
    
    public MediaHttpUploader setDirectUploadEnabled(final boolean a1) {
        this.directUploadEnabled = a1;
        return this;
    }
    
    public boolean isDirectUploadEnabled() {
        return this.directUploadEnabled;
    }
    
    public MediaHttpUploader setProgressListener(final MediaHttpUploaderProgressListener a1) {
        this.progressListener = a1;
        return this;
    }
    
    public MediaHttpUploaderProgressListener getProgressListener() {
        return this.progressListener;
    }
    
    public MediaHttpUploader setChunkSize(final int a1) {
        Preconditions.checkArgument(a1 > 0 && a1 % 262144 == 0, (Object)"chunkSize must be a positive multiple of 262144.");
        this.chunkSize = a1;
        return this;
    }
    
    public int getChunkSize() {
        return this.chunkSize;
    }
    
    public boolean getDisableGZipContent() {
        return this.disableGZipContent;
    }
    
    public MediaHttpUploader setDisableGZipContent(final boolean a1) {
        this.disableGZipContent = a1;
        return this;
    }
    
    public Sleeper getSleeper() {
        return this.sleeper;
    }
    
    public MediaHttpUploader setSleeper(final Sleeper a1) {
        this.sleeper = a1;
        return this;
    }
    
    public String getInitiationRequestMethod() {
        return this.initiationRequestMethod;
    }
    
    public MediaHttpUploader setInitiationRequestMethod(final String a1) {
        Preconditions.checkArgument(a1.equals("POST") || a1.equals("PUT") || a1.equals("PATCH"));
        this.initiationRequestMethod = a1;
        return this;
    }
    
    public MediaHttpUploader setInitiationHeaders(final HttpHeaders a1) {
        this.initiationHeaders = a1;
        return this;
    }
    
    public HttpHeaders getInitiationHeaders() {
        return this.initiationHeaders;
    }
    
    public long getNumBytesUploaded() {
        return this.totalBytesServerReceived;
    }
    
    private void updateStateAndNotifyListener(final UploadState a1) throws IOException {
        this.uploadState = a1;
        if (this.progressListener != null) {
            this.progressListener.progressChanged(this);
        }
    }
    
    public UploadState getUploadState() {
        return this.uploadState;
    }
    
    public double getProgress() throws IOException {
        Preconditions.checkArgument(this.isMediaLengthKnown(), (Object)"Cannot call getProgress() if the specified AbstractInputStreamContent has no content length. Use  getNumBytesUploaded() to denote progress instead.");
        return (this.getMediaContentLength() == 0L) ? 0.0 : (this.totalBytesServerReceived / (double)this.getMediaContentLength());
    }
    
    public enum UploadState
    {
        NOT_STARTED, 
        INITIATION_STARTED, 
        INITIATION_COMPLETE, 
        MEDIA_IN_PROGRESS, 
        MEDIA_COMPLETE;
        
        private static final /* synthetic */ UploadState[] $VALUES;
        
        public static UploadState[] values() {
            return UploadState.$VALUES.clone();
        }
        
        public static UploadState valueOf(final String a1) {
            return Enum.valueOf(UploadState.class, a1);
        }
        
        static {
            $VALUES = new UploadState[] { UploadState.NOT_STARTED, UploadState.INITIATION_STARTED, UploadState.INITIATION_COMPLETE, UploadState.MEDIA_IN_PROGRESS, UploadState.MEDIA_COMPLETE };
        }
    }
}
