package com.google.api.client.googleapis.media;

import com.google.api.client.util.*;
import java.util.logging.*;
import java.io.*;
import com.google.api.client.http.*;

@Beta
class MediaUploadErrorHandler implements HttpUnsuccessfulResponseHandler, HttpIOExceptionHandler
{
    static final Logger LOGGER;
    private final MediaHttpUploader uploader;
    private final HttpIOExceptionHandler originalIOExceptionHandler;
    private final HttpUnsuccessfulResponseHandler originalUnsuccessfulHandler;
    
    public MediaUploadErrorHandler(final MediaHttpUploader a1, final HttpRequest a2) {
        super();
        this.uploader = Preconditions.checkNotNull(a1);
        this.originalIOExceptionHandler = a2.getIOExceptionHandler();
        this.originalUnsuccessfulHandler = a2.getUnsuccessfulResponseHandler();
        a2.setIOExceptionHandler(this);
        a2.setUnsuccessfulResponseHandler(this);
    }
    
    public boolean handleIOException(final HttpRequest v1, final boolean v2) throws IOException {
        final boolean v3 = this.originalIOExceptionHandler != null && this.originalIOExceptionHandler.handleIOException(v1, v2);
        if (v3) {
            try {
                this.uploader.serverErrorCallback();
            }
            catch (IOException a1) {
                MediaUploadErrorHandler.LOGGER.log(Level.WARNING, "exception thrown while calling server callback", a1);
            }
        }
        return v3;
    }
    
    public boolean handleResponse(final HttpRequest a3, final HttpResponse v1, final boolean v2) throws IOException {
        final boolean v3 = this.originalUnsuccessfulHandler != null && this.originalUnsuccessfulHandler.handleResponse(a3, v1, v2);
        if (v3 && v2 && v1.getStatusCode() / 100 == 5) {
            try {
                this.uploader.serverErrorCallback();
            }
            catch (IOException a4) {
                MediaUploadErrorHandler.LOGGER.log(Level.WARNING, "exception thrown while calling server callback", a4);
            }
        }
        return v3;
    }
    
    static {
        LOGGER = Logger.getLogger(MediaUploadErrorHandler.class.getName());
    }
}
