package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.common.base.*;

public final class BlobId implements Serializable
{
    private static final long serialVersionUID = -6156002883225601925L;
    private final String bucket;
    private final String name;
    private final Long generation;
    
    private BlobId(final String a1, final String a2, final Long a3) {
        super();
        this.bucket = a1;
        this.name = a2;
        this.generation = a3;
    }
    
    public String getBucket() {
        return this.bucket;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Long getGeneration() {
        return this.generation;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("bucket", this.getBucket()).add("name", this.getName()).add("generation", this.getGeneration()).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.bucket, this.name, this.generation);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (a1 == null || !a1.getClass().equals(BlobId.class)) {
            return false;
        }
        final BlobId v1 = (BlobId)a1;
        return Objects.equals(this.bucket, v1.bucket) && Objects.equals(this.name, v1.name) && Objects.equals(this.generation, v1.generation);
    }
    
    StorageObject toPb() {
        final StorageObject v1 = new StorageObject();
        v1.setBucket(this.bucket);
        v1.setName(this.name);
        v1.setGeneration(this.generation);
        return v1;
    }
    
    public static BlobId of(final String a1, final String a2) {
        return new BlobId(Preconditions.checkNotNull(a1), Preconditions.checkNotNull(a2), null);
    }
    
    public static BlobId of(final String a1, final String a2, final Long a3) {
        return new BlobId(Preconditions.checkNotNull(a1), Preconditions.checkNotNull(a2), a3);
    }
    
    static BlobId fromPb(final StorageObject a1) {
        return of(a1.getBucket(), a1.getName(), a1.getGeneration());
    }
}
