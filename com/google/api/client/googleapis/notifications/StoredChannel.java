package com.google.api.client.googleapis.notifications;

import java.util.concurrent.locks.*;
import java.io.*;
import com.google.api.client.util.store.*;
import com.google.api.client.util.*;

@Beta
public final class StoredChannel implements Serializable
{
    public static final String DEFAULT_DATA_STORE_ID;
    private static final long serialVersionUID = 1L;
    private final Lock lock;
    private final UnparsedNotificationCallback notificationCallback;
    private String clientToken;
    private Long expiration;
    private final String id;
    private String topicId;
    
    public StoredChannel(final UnparsedNotificationCallback a1) {
        this(a1, NotificationUtils.randomUuidString());
    }
    
    public StoredChannel(final UnparsedNotificationCallback a1, final String a2) {
        super();
        this.lock = new ReentrantLock();
        this.notificationCallback = Preconditions.checkNotNull(a1);
        this.id = Preconditions.checkNotNull(a2);
    }
    
    public StoredChannel store(final DataStoreFactory a1) throws IOException {
        return this.store(getDefaultDataStore(a1));
    }
    
    public StoredChannel store(final DataStore<StoredChannel> a1) throws IOException {
        this.lock.lock();
        try {
            a1.set(this.getId(), (Serializable)this);
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public UnparsedNotificationCallback getNotificationCallback() {
        this.lock.lock();
        try {
            return this.notificationCallback;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public String getClientToken() {
        this.lock.lock();
        try {
            return this.clientToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredChannel setClientToken(final String a1) {
        this.lock.lock();
        try {
            this.clientToken = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public Long getExpiration() {
        this.lock.lock();
        try {
            return this.expiration;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredChannel setExpiration(final Long a1) {
        this.lock.lock();
        try {
            this.expiration = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public String getId() {
        this.lock.lock();
        try {
            return this.id;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public String getTopicId() {
        this.lock.lock();
        try {
            return this.topicId;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredChannel setTopicId(final String a1) {
        this.lock.lock();
        try {
            this.topicId = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(StoredChannel.class).add("notificationCallback", this.getNotificationCallback()).add("clientToken", this.getClientToken()).add("expiration", this.getExpiration()).add("id", this.getId()).add("topicId", this.getTopicId()).toString();
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (!(a1 instanceof StoredChannel)) {
            return false;
        }
        final StoredChannel v1 = (StoredChannel)a1;
        return this.getId().equals(v1.getId());
    }
    
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
    
    public static DataStore<StoredChannel> getDefaultDataStore(final DataStoreFactory a1) throws IOException {
        return a1.getDataStore(StoredChannel.DEFAULT_DATA_STORE_ID);
    }
    
    static {
        DEFAULT_DATA_STORE_ID = StoredChannel.class.getSimpleName();
    }
}
