package com.google.cloud.storage;

import java.util.*;
import com.google.api.core.*;

public abstract static class Builder
{
    public Builder() {
        super();
    }
    
    public abstract Builder setBlobId(final BlobId p0);
    
    abstract Builder setGeneratedId(final String p0);
    
    public abstract Builder setContentType(final String p0);
    
    public abstract Builder setContentDisposition(final String p0);
    
    public abstract Builder setContentLanguage(final String p0);
    
    public abstract Builder setContentEncoding(final String p0);
    
    abstract Builder setComponentCount(final Integer p0);
    
    public abstract Builder setCacheControl(final String p0);
    
    public abstract Builder setAcl(final List<Acl> p0);
    
    abstract Builder setOwner(final Acl.Entity p0);
    
    abstract Builder setSize(final Long p0);
    
    abstract Builder setEtag(final String p0);
    
    abstract Builder setSelfLink(final String p0);
    
    public abstract Builder setMd5(final String p0);
    
    public abstract Builder setMd5FromHexString(final String p0);
    
    public abstract Builder setCrc32c(final String p0);
    
    public abstract Builder setCrc32cFromHexString(final String p0);
    
    abstract Builder setMediaLink(final String p0);
    
    public abstract Builder setStorageClass(final StorageClass p0);
    
    public abstract Builder setMetadata(final Map<String, String> p0);
    
    abstract Builder setMetageneration(final Long p0);
    
    abstract Builder setDeleteTime(final Long p0);
    
    abstract Builder setUpdateTime(final Long p0);
    
    abstract Builder setCreateTime(final Long p0);
    
    abstract Builder setIsDirectory(final boolean p0);
    
    abstract Builder setCustomerEncryption(final CustomerEncryption p0);
    
    abstract Builder setKmsKeyName(final String p0);
    
    @BetaApi
    public abstract Builder setEventBasedHold(final Boolean p0);
    
    @BetaApi
    public abstract Builder setTemporaryHold(final Boolean p0);
    
    @BetaApi
    abstract Builder setRetentionExpirationTime(final Long p0);
    
    public abstract BlobInfo build();
}
