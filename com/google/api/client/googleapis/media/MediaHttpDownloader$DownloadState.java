package com.google.api.client.googleapis.media;

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
