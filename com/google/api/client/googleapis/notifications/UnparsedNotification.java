package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public class UnparsedNotification extends AbstractNotification
{
    private String contentType;
    private InputStream contentStream;
    
    public UnparsedNotification(final long a1, final String a2, final String a3, final String a4, final String a5) {
        super(a1, a2, a3, a4, a5);
    }
    
    public final String getContentType() {
        return this.contentType;
    }
    
    public UnparsedNotification setContentType(final String a1) {
        this.contentType = a1;
        return this;
    }
    
    public final InputStream getContentStream() {
        return this.contentStream;
    }
    
    public UnparsedNotification setContentStream(final InputStream a1) {
        this.contentStream = a1;
        return this;
    }
    
    @Override
    public UnparsedNotification setMessageNumber(final long a1) {
        return (UnparsedNotification)super.setMessageNumber(a1);
    }
    
    @Override
    public UnparsedNotification setResourceState(final String a1) {
        return (UnparsedNotification)super.setResourceState(a1);
    }
    
    @Override
    public UnparsedNotification setResourceId(final String a1) {
        return (UnparsedNotification)super.setResourceId(a1);
    }
    
    @Override
    public UnparsedNotification setResourceUri(final String a1) {
        return (UnparsedNotification)super.setResourceUri(a1);
    }
    
    @Override
    public UnparsedNotification setChannelId(final String a1) {
        return (UnparsedNotification)super.setChannelId(a1);
    }
    
    @Override
    public UnparsedNotification setChannelExpiration(final String a1) {
        return (UnparsedNotification)super.setChannelExpiration(a1);
    }
    
    @Override
    public UnparsedNotification setChannelToken(final String a1) {
        return (UnparsedNotification)super.setChannelToken(a1);
    }
    
    @Override
    public UnparsedNotification setChanged(final String a1) {
        return (UnparsedNotification)super.setChanged(a1);
    }
    
    @Override
    public String toString() {
        return super.toStringHelper().add("contentType", this.contentType).toString();
    }
    
    @Override
    public /* bridge */ AbstractNotification setChanged(final String changed) {
        return this.setChanged(changed);
    }
    
    @Override
    public /* bridge */ AbstractNotification setChannelToken(final String channelToken) {
        return this.setChannelToken(channelToken);
    }
    
    @Override
    public /* bridge */ AbstractNotification setChannelExpiration(final String channelExpiration) {
        return this.setChannelExpiration(channelExpiration);
    }
    
    @Override
    public /* bridge */ AbstractNotification setChannelId(final String channelId) {
        return this.setChannelId(channelId);
    }
    
    @Override
    public /* bridge */ AbstractNotification setResourceUri(final String resourceUri) {
        return this.setResourceUri(resourceUri);
    }
    
    @Override
    public /* bridge */ AbstractNotification setResourceId(final String resourceId) {
        return this.setResourceId(resourceId);
    }
    
    @Override
    public /* bridge */ AbstractNotification setResourceState(final String resourceState) {
        return this.setResourceState(resourceState);
    }
    
    @Override
    public /* bridge */ AbstractNotification setMessageNumber(final long messageNumber) {
        return this.setMessageNumber(messageNumber);
    }
}
