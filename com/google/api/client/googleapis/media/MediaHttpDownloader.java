package com.google.api.client.googleapis.media;

import java.io.*;
import java.util.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;

public final class MediaHttpDownloader
{
    public static final int MAXIMUM_CHUNK_SIZE = 33554432;
    private final HttpRequestFactory requestFactory;
    private final HttpTransport transport;
    private boolean directDownloadEnabled;
    private MediaHttpDownloaderProgressListener progressListener;
    private int chunkSize;
    private long mediaContentLength;
    private DownloadState downloadState;
    private long bytesDownloaded;
    private long lastBytePos;
    
    public MediaHttpDownloader(final HttpTransport a1, final HttpRequestInitializer a2) {
        super();
        this.directDownloadEnabled = false;
        this.chunkSize = 33554432;
        this.downloadState = DownloadState.NOT_STARTED;
        this.lastBytePos = -1L;
        this.transport = Preconditions.checkNotNull(a1);
        this.requestFactory = ((a2 == null) ? a1.createRequestFactory() : a1.createRequestFactory(a2));
    }
    
    public void download(final GenericUrl a1, final OutputStream a2) throws IOException {
        this.download(a1, null, a2);
    }
    
    public void download(final GenericUrl v-5, final HttpHeaders v-4, final OutputStream v-3) throws IOException {
        Preconditions.checkArgument(this.downloadState == DownloadState.NOT_STARTED);
        v-5.put("alt", "media");
        if (this.directDownloadEnabled) {
            this.updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
            final HttpResponse a1 = this.executeCurrentRequest(this.lastBytePos, v-5, v-4, v-3);
            this.mediaContentLength = a1.getHeaders().getContentLength();
            this.bytesDownloaded = this.mediaContentLength;
            this.updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
            return;
        }
        while (true) {
            long a2 = this.bytesDownloaded + this.chunkSize - 1L;
            if (this.lastBytePos != -1L) {
                a2 = Math.min(this.lastBytePos, a2);
            }
            final HttpResponse a3 = this.executeCurrentRequest(a2, v-5, v-4, v-3);
            final String v1 = a3.getHeaders().getContentRange();
            final long v2 = this.getNextByteIndex(v1);
            this.setMediaContentLength(v1);
            if (this.mediaContentLength <= v2) {
                break;
            }
            this.bytesDownloaded = v2;
            this.updateStateAndNotifyListener(DownloadState.MEDIA_IN_PROGRESS);
        }
        this.bytesDownloaded = this.mediaContentLength;
        this.updateStateAndNotifyListener(DownloadState.MEDIA_COMPLETE);
    }
    
    private HttpResponse executeCurrentRequest(final long a3, final GenericUrl a4, final HttpHeaders v1, final OutputStream v2) throws IOException {
        final HttpRequest v3 = this.requestFactory.buildGetRequest(a4);
        if (v1 != null) {
            v3.getHeaders().putAll(v1);
        }
        if (this.bytesDownloaded != 0L || a3 != -1L) {
            final StringBuilder a5 = new StringBuilder();
            a5.append("bytes=").append(this.bytesDownloaded).append("-");
            if (a3 != -1L) {
                a5.append(a3);
            }
            v3.getHeaders().setRange(a5.toString());
        }
        final HttpResponse v4 = v3.execute();
        try {
            IOUtils.copy(v4.getContent(), v2);
        }
        finally {
            v4.disconnect();
        }
        return v4;
    }
    
    private long getNextByteIndex(final String a1) {
        if (a1 == null) {
            return 0L;
        }
        return Long.parseLong(a1.substring(a1.indexOf(45) + 1, a1.indexOf(47))) + 1L;
    }
    
    public MediaHttpDownloader setBytesDownloaded(final long a1) {
        Preconditions.checkArgument(a1 >= 0L);
        this.bytesDownloaded = a1;
        return this;
    }
    
    public MediaHttpDownloader setContentRange(final long a1, final int a2) {
        Preconditions.checkArgument(a2 >= a1);
        this.setBytesDownloaded(a1);
        this.lastBytePos = a2;
        return this;
    }
    
    private void setMediaContentLength(final String a1) {
        if (a1 == null) {
            return;
        }
        if (this.mediaContentLength == 0L) {
            this.mediaContentLength = Long.parseLong(a1.substring(a1.indexOf(47) + 1));
        }
    }
    
    public boolean isDirectDownloadEnabled() {
        return this.directDownloadEnabled;
    }
    
    public MediaHttpDownloader setDirectDownloadEnabled(final boolean a1) {
        this.directDownloadEnabled = a1;
        return this;
    }
    
    public MediaHttpDownloader setProgressListener(final MediaHttpDownloaderProgressListener a1) {
        this.progressListener = a1;
        return this;
    }
    
    public MediaHttpDownloaderProgressListener getProgressListener() {
        return this.progressListener;
    }
    
    public HttpTransport getTransport() {
        return this.transport;
    }
    
    public MediaHttpDownloader setChunkSize(final int a1) {
        Preconditions.checkArgument(a1 > 0 && a1 <= 33554432);
        this.chunkSize = a1;
        return this;
    }
    
    public int getChunkSize() {
        return this.chunkSize;
    }
    
    public long getNumBytesDownloaded() {
        return this.bytesDownloaded;
    }
    
    public long getLastBytePosition() {
        return this.lastBytePos;
    }
    
    private void updateStateAndNotifyListener(final DownloadState a1) throws IOException {
        this.downloadState = a1;
        if (this.progressListener != null) {
            this.progressListener.progressChanged(this);
        }
    }
    
    public DownloadState getDownloadState() {
        return this.downloadState;
    }
    
    public double getProgress() {
        return (this.mediaContentLength == 0L) ? 0.0 : (this.bytesDownloaded / (double)this.mediaContentLength);
    }
    
    public enum DownloadState
    {
        NOT_STARTED, 
        MEDIA_IN_PROGRESS, 
        MEDIA_COMPLETE;
        
        private static final /* synthetic */ DownloadState[] $VALUES;
        
        public static DownloadState[] values() {
            return DownloadState.$VALUES.clone();
        }
        
        public static DownloadState valueOf(final String a1) {
            return Enum.valueOf(DownloadState.class, a1);
        }
        
        static {
            $VALUES = new DownloadState[] { DownloadState.NOT_STARTED, DownloadState.MEDIA_IN_PROGRESS, DownloadState.MEDIA_COMPLETE };
        }
    }
}
