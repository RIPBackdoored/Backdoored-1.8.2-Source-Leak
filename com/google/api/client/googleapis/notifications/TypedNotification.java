package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.*;

@Beta
public class TypedNotification<T> extends AbstractNotification
{
    private T content;
    
    public TypedNotification(final long a1, final String a2, final String a3, final String a4, final String a5) {
        super(a1, a2, a3, a4, a5);
    }
    
    public TypedNotification(final UnparsedNotification a1) {
        super(a1);
    }
    
    public final T getContent() {
        return this.content;
    }
    
    public TypedNotification<T> setContent(final T a1) {
        this.content = a1;
        return this;
    }
    
    @Override
    public TypedNotification<T> setMessageNumber(final long a1) {
        return (TypedNotification)super.setMessageNumber(a1);
    }
    
    @Override
    public TypedNotification<T> setResourceState(final String a1) {
        return (TypedNotification)super.setResourceState(a1);
    }
    
    @Override
    public TypedNotification<T> setResourceId(final String a1) {
        return (TypedNotification)super.setResourceId(a1);
    }
    
    @Override
    public TypedNotification<T> setResourceUri(final String a1) {
        return (TypedNotification)super.setResourceUri(a1);
    }
    
    @Override
    public TypedNotification<T> setChannelId(final String a1) {
        return (TypedNotification)super.setChannelId(a1);
    }
    
    @Override
    public TypedNotification<T> setChannelExpiration(final String a1) {
        return (TypedNotification)super.setChannelExpiration(a1);
    }
    
    @Override
    public TypedNotification<T> setChannelToken(final String a1) {
        return (TypedNotification)super.setChannelToken(a1);
    }
    
    @Override
    public TypedNotification<T> setChanged(final String a1) {
        return (TypedNotification)super.setChanged(a1);
    }
    
    @Override
    public String toString() {
        return super.toStringHelper().add("content", this.content).toString();
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
