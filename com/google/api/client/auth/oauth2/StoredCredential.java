package com.google.api.client.auth.oauth2;

import java.util.concurrent.locks.*;
import com.google.api.client.util.*;
import java.util.*;
import com.google.api.client.util.store.*;
import java.io.*;

@Beta
public final class StoredCredential implements Serializable
{
    public static final String DEFAULT_DATA_STORE_ID;
    private static final long serialVersionUID = 1L;
    private final Lock lock;
    private String accessToken;
    private Long expirationTimeMilliseconds;
    private String refreshToken;
    
    public StoredCredential() {
        super();
        this.lock = new ReentrantLock();
    }
    
    public StoredCredential(final Credential a1) {
        super();
        this.lock = new ReentrantLock();
        this.setAccessToken(a1.getAccessToken());
        this.setRefreshToken(a1.getRefreshToken());
        this.setExpirationTimeMilliseconds(a1.getExpirationTimeMilliseconds());
    }
    
    public String getAccessToken() {
        this.lock.lock();
        try {
            return this.accessToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredCredential setAccessToken(final String a1) {
        this.lock.lock();
        try {
            this.accessToken = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredCredential setExpirationTimeMilliseconds(final Long a1) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public String getRefreshToken() {
        this.lock.lock();
        try {
            return this.refreshToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredCredential setRefreshToken(final String a1) {
        this.lock.lock();
        try {
            this.refreshToken = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(StoredCredential.class).add("accessToken", this.getAccessToken()).add("refreshToken", this.getRefreshToken()).add("expirationTimeMilliseconds", this.getExpirationTimeMilliseconds()).toString();
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (!(a1 instanceof StoredCredential)) {
            return false;
        }
        final StoredCredential v1 = (StoredCredential)a1;
        return Objects.equal(this.getAccessToken(), v1.getAccessToken()) && Objects.equal(this.getRefreshToken(), v1.getRefreshToken()) && Objects.equal(this.getExpirationTimeMilliseconds(), v1.getExpirationTimeMilliseconds());
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] { this.getAccessToken(), this.getRefreshToken(), this.getExpirationTimeMilliseconds() });
    }
    
    public static DataStore<StoredCredential> getDefaultDataStore(final DataStoreFactory a1) throws IOException {
        return a1.getDataStore(StoredCredential.DEFAULT_DATA_STORE_ID);
    }
    
    static {
        DEFAULT_DATA_STORE_ID = StoredCredential.class.getSimpleName();
    }
}
