package com.google.api.client.googleapis.media;

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
