package com.google.cloud.storage;

import java.util.*;

public static class Builder extends BucketInfo.Builder
{
    private final Storage storage;
    private final BuilderImpl infoBuilder;
    
    Builder(final Bucket a1) {
        super();
        this.storage = Bucket.access$100(a1);
        this.infoBuilder = new BuilderImpl(a1);
    }
    
    @Override
    public Builder setName(final String a1) {
        this.infoBuilder.setName(a1);
        return this;
    }
    
    @Override
    Builder setGeneratedId(final String a1) {
        this.infoBuilder.setGeneratedId(a1);
        return this;
    }
    
    @Override
    Builder setOwner(final Acl.Entity a1) {
        this.infoBuilder.setOwner(a1);
        return this;
    }
    
    @Override
    Builder setSelfLink(final String a1) {
        this.infoBuilder.setSelfLink(a1);
        return this;
    }
    
    @Override
    public Builder setVersioningEnabled(final Boolean a1) {
        this.infoBuilder.setVersioningEnabled(a1);
        return this;
    }
    
    @Override
    public Builder setRequesterPays(final Boolean a1) {
        this.infoBuilder.setRequesterPays(a1);
        return this;
    }
    
    @Override
    public Builder setIndexPage(final String a1) {
        this.infoBuilder.setIndexPage(a1);
        return this;
    }
    
    @Override
    public Builder setNotFoundPage(final String a1) {
        this.infoBuilder.setNotFoundPage(a1);
        return this;
    }
    
    @Deprecated
    @Override
    public Builder setDeleteRules(final Iterable<? extends DeleteRule> a1) {
        this.infoBuilder.setDeleteRules(a1);
        return this;
    }
    
    @Override
    public Builder setLifecycleRules(final Iterable<? extends LifecycleRule> a1) {
        this.infoBuilder.setLifecycleRules(a1);
        return this;
    }
    
    @Override
    public Builder setStorageClass(final StorageClass a1) {
        this.infoBuilder.setStorageClass(a1);
        return this;
    }
    
    @Override
    public Builder setLocation(final String a1) {
        this.infoBuilder.setLocation(a1);
        return this;
    }
    
    @Override
    Builder setEtag(final String a1) {
        this.infoBuilder.setEtag(a1);
        return this;
    }
    
    @Override
    Builder setCreateTime(final Long a1) {
        this.infoBuilder.setCreateTime(a1);
        return this;
    }
    
    @Override
    Builder setMetageneration(final Long a1) {
        this.infoBuilder.setMetageneration(a1);
        return this;
    }
    
    @Override
    public Builder setCors(final Iterable<Cors> a1) {
        this.infoBuilder.setCors(a1);
        return this;
    }
    
    @Override
    public Builder setAcl(final Iterable<Acl> a1) {
        this.infoBuilder.setAcl(a1);
        return this;
    }
    
    @Override
    public Builder setDefaultAcl(final Iterable<Acl> a1) {
        this.infoBuilder.setDefaultAcl(a1);
        return this;
    }
    
    @Override
    public Builder setLabels(final Map<String, String> a1) {
        this.infoBuilder.setLabels(a1);
        return this;
    }
    
    @Override
    public Builder setDefaultKmsKeyName(final String a1) {
        this.infoBuilder.setDefaultKmsKeyName(a1);
        return this;
    }
    
    @Override
    public Builder setDefaultEventBasedHold(final Boolean a1) {
        this.infoBuilder.setDefaultEventBasedHold(a1);
        return this;
    }
    
    @Override
    Builder setRetentionEffectiveTime(final Long a1) {
        this.infoBuilder.setRetentionEffectiveTime(a1);
        return this;
    }
    
    @Override
    Builder setRetentionPolicyIsLocked(final Boolean a1) {
        this.infoBuilder.setRetentionPolicyIsLocked(a1);
        return this;
    }
    
    @Override
    public Builder setRetentionPeriod(final Long a1) {
        this.infoBuilder.setRetentionPeriod(a1);
        return this;
    }
    
    @Override
    public Builder setIamConfiguration(final IamConfiguration a1) {
        this.infoBuilder.setIamConfiguration(a1);
        return this;
    }
    
    @Override
    Builder setLocationType(final String a1) {
        this.infoBuilder.setLocationType(a1);
        return this;
    }
    
    @Override
    public Bucket build() {
        return new Bucket(this.storage, this.infoBuilder);
    }
    
    @Override
    public /* bridge */ BucketInfo build() {
        return this.build();
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setIamConfiguration(final IamConfiguration iamConfiguration) {
        return this.setIamConfiguration(iamConfiguration);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setRetentionPeriod(final Long retentionPeriod) {
        return this.setRetentionPeriod(retentionPeriod);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setRetentionPolicyIsLocked(final Boolean retentionPolicyIsLocked) {
        return this.setRetentionPolicyIsLocked(retentionPolicyIsLocked);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setRetentionEffectiveTime(final Long retentionEffectiveTime) {
        return this.setRetentionEffectiveTime(retentionEffectiveTime);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setDefaultEventBasedHold(final Boolean defaultEventBasedHold) {
        return this.setDefaultEventBasedHold(defaultEventBasedHold);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setDefaultKmsKeyName(final String defaultKmsKeyName) {
        return this.setDefaultKmsKeyName(defaultKmsKeyName);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setLabels(final Map labels) {
        return this.setLabels(labels);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setDefaultAcl(final Iterable defaultAcl) {
        return this.setDefaultAcl(defaultAcl);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setAcl(final Iterable acl) {
        return this.setAcl(acl);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setCors(final Iterable cors) {
        return this.setCors(cors);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setLocationType(final String locationType) {
        return this.setLocationType(locationType);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setMetageneration(final Long metageneration) {
        return this.setMetageneration(metageneration);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setCreateTime(final Long createTime) {
        return this.setCreateTime(createTime);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setEtag(final String etag) {
        return this.setEtag(etag);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setLocation(final String location) {
        return this.setLocation(location);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setStorageClass(final StorageClass storageClass) {
        return this.setStorageClass(storageClass);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setLifecycleRules(final Iterable lifecycleRules) {
        return this.setLifecycleRules(lifecycleRules);
    }
    
    @Deprecated
    @Override
    public /* bridge */ BucketInfo.Builder setDeleteRules(final Iterable deleteRules) {
        return this.setDeleteRules(deleteRules);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setNotFoundPage(final String notFoundPage) {
        return this.setNotFoundPage(notFoundPage);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setIndexPage(final String indexPage) {
        return this.setIndexPage(indexPage);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setVersioningEnabled(final Boolean versioningEnabled) {
        return this.setVersioningEnabled(versioningEnabled);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setRequesterPays(final Boolean requesterPays) {
        return this.setRequesterPays(requesterPays);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setSelfLink(final String selfLink) {
        return this.setSelfLink(selfLink);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setOwner(final Acl.Entity owner) {
        return this.setOwner(owner);
    }
    
    @Override
    /* bridge */ BucketInfo.Builder setGeneratedId(final String generatedId) {
        return this.setGeneratedId(generatedId);
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder setName(final String name) {
        return this.setName(name);
    }
}
