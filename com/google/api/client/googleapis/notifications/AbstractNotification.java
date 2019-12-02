package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.*;

@Beta
public abstract class AbstractNotification
{
    private long messageNumber;
    private String resourceState;
    private String resourceId;
    private String resourceUri;
    private String channelId;
    private String channelExpiration;
    private String channelToken;
    private String changed;
    
    protected AbstractNotification(final long a1, final String a2, final String a3, final String a4, final String a5) {
        super();
        this.setMessageNumber(a1);
        this.setResourceState(a2);
        this.setResourceId(a3);
        this.setResourceUri(a4);
        this.setChannelId(a5);
    }
    
    protected AbstractNotification(final AbstractNotification a1) {
        this(a1.getMessageNumber(), a1.getResourceState(), a1.getResourceId(), a1.getResourceUri(), a1.getChannelId());
        this.setChannelExpiration(a1.getChannelExpiration());
        this.setChannelToken(a1.getChannelToken());
        this.setChanged(a1.getChanged());
    }
    
    @Override
    public String toString() {
        return this.toStringHelper().toString();
    }
    
    protected Objects.ToStringHelper toStringHelper() {
        return Objects.toStringHelper(this).add("messageNumber", this.messageNumber).add("resourceState", this.resourceState).add("resourceId", this.resourceId).add("resourceUri", this.resourceUri).add("channelId", this.channelId).add("channelExpiration", this.channelExpiration).add("channelToken", this.channelToken).add("changed", this.changed);
    }
    
    public final long getMessageNumber() {
        return this.messageNumber;
    }
    
    public AbstractNotification setMessageNumber(final long a1) {
        Preconditions.checkArgument(a1 >= 1L);
        this.messageNumber = a1;
        return this;
    }
    
    public final String getResourceState() {
        return this.resourceState;
    }
    
    public AbstractNotification setResourceState(final String a1) {
        this.resourceState = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getResourceId() {
        return this.resourceId;
    }
    
    public AbstractNotification setResourceId(final String a1) {
        this.resourceId = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getResourceUri() {
        return this.resourceUri;
    }
    
    public AbstractNotification setResourceUri(final String a1) {
        this.resourceUri = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getChannelId() {
        return this.channelId;
    }
    
    public AbstractNotification setChannelId(final String a1) {
        this.channelId = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getChannelExpiration() {
        return this.channelExpiration;
    }
    
    public AbstractNotification setChannelExpiration(final String a1) {
        this.channelExpiration = a1;
        return this;
    }
    
    public final String getChannelToken() {
        return this.channelToken;
    }
    
    public AbstractNotification setChannelToken(final String a1) {
        this.channelToken = a1;
        return this;
    }
    
    public final String getChanged() {
        return this.changed;
    }
    
    public AbstractNotification setChanged(final String a1) {
        this.changed = a1;
        return this;
    }
}
