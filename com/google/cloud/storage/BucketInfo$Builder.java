package com.google.cloud.storage;

import java.util.*;
import com.google.api.core.*;

public abstract static class Builder
{
    Builder() {
        super();
    }
    
    public abstract Builder setName(final String p0);
    
    abstract Builder setGeneratedId(final String p0);
    
    abstract Builder setOwner(final Acl.Entity p0);
    
    abstract Builder setSelfLink(final String p0);
    
    public abstract Builder setRequesterPays(final Boolean p0);
    
    public abstract Builder setVersioningEnabled(final Boolean p0);
    
    public abstract Builder setIndexPage(final String p0);
    
    public abstract Builder setNotFoundPage(final String p0);
    
    @Deprecated
    public abstract Builder setDeleteRules(final Iterable<? extends DeleteRule> p0);
    
    public abstract Builder setLifecycleRules(final Iterable<? extends LifecycleRule> p0);
    
    public abstract Builder setStorageClass(final StorageClass p0);
    
    public abstract Builder setLocation(final String p0);
    
    abstract Builder setEtag(final String p0);
    
    abstract Builder setCreateTime(final Long p0);
    
    abstract Builder setMetageneration(final Long p0);
    
    abstract Builder setLocationType(final String p0);
    
    public abstract Builder setCors(final Iterable<Cors> p0);
    
    public abstract Builder setAcl(final Iterable<Acl> p0);
    
    public abstract Builder setDefaultAcl(final Iterable<Acl> p0);
    
    public abstract Builder setLabels(final Map<String, String> p0);
    
    public abstract Builder setDefaultKmsKeyName(final String p0);
    
    @BetaApi
    public abstract Builder setDefaultEventBasedHold(final Boolean p0);
    
    @BetaApi
    abstract Builder setRetentionEffectiveTime(final Long p0);
    
    @BetaApi
    abstract Builder setRetentionPolicyIsLocked(final Boolean p0);
    
    @BetaApi
    public abstract Builder setRetentionPeriod(final Long p0);
    
    @BetaApi
    public abstract Builder setIamConfiguration(final IamConfiguration p0);
    
    public abstract BucketInfo build();
}
