package com.google.cloud.storage;

import java.util.*;
import com.google.api.client.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

static final class BuilderImpl extends Builder
{
    private String generatedId;
    private String name;
    private Acl.Entity owner;
    private String selfLink;
    private Boolean requesterPays;
    private Boolean versioningEnabled;
    private String indexPage;
    private String notFoundPage;
    private List<DeleteRule> deleteRules;
    private List<LifecycleRule> lifecycleRules;
    private StorageClass storageClass;
    private String location;
    private String etag;
    private Long createTime;
    private Long metageneration;
    private List<Cors> cors;
    private List<Acl> acl;
    private List<Acl> defaultAcl;
    private Map<String, String> labels;
    private String defaultKmsKeyName;
    private Boolean defaultEventBasedHold;
    private Long retentionEffectiveTime;
    private Boolean retentionPolicyIsLocked;
    private Long retentionPeriod;
    private IamConfiguration iamConfiguration;
    private String locationType;
    
    BuilderImpl(final String a1) {
        super();
        this.name = a1;
    }
    
    BuilderImpl(final BucketInfo a1) {
        super();
        this.generatedId = BucketInfo.access$1200(a1);
        this.name = BucketInfo.access$1300(a1);
        this.etag = BucketInfo.access$1400(a1);
        this.createTime = BucketInfo.access$1500(a1);
        this.metageneration = BucketInfo.access$1600(a1);
        this.location = BucketInfo.access$1700(a1);
        this.storageClass = BucketInfo.access$1800(a1);
        this.cors = (List<Cors>)BucketInfo.access$1900(a1);
        this.acl = (List<Acl>)BucketInfo.access$2000(a1);
        this.defaultAcl = (List<Acl>)BucketInfo.access$2100(a1);
        this.owner = BucketInfo.access$2200(a1);
        this.selfLink = BucketInfo.access$2300(a1);
        this.versioningEnabled = BucketInfo.access$2400(a1);
        this.indexPage = BucketInfo.access$2500(a1);
        this.notFoundPage = BucketInfo.access$2600(a1);
        this.deleteRules = (List<DeleteRule>)BucketInfo.access$2700(a1);
        this.lifecycleRules = (List<LifecycleRule>)BucketInfo.access$2800(a1);
        this.labels = (Map<String, String>)BucketInfo.access$2900(a1);
        this.requesterPays = BucketInfo.access$3000(a1);
        this.defaultKmsKeyName = BucketInfo.access$3100(a1);
        this.defaultEventBasedHold = BucketInfo.access$3200(a1);
        this.retentionEffectiveTime = BucketInfo.access$3300(a1);
        this.retentionPolicyIsLocked = BucketInfo.access$3400(a1);
        this.retentionPeriod = BucketInfo.access$3500(a1);
        this.iamConfiguration = BucketInfo.access$3600(a1);
        this.locationType = BucketInfo.access$3700(a1);
    }
    
    @Override
    public Builder setName(final String a1) {
        this.name = Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    Builder setGeneratedId(final String a1) {
        this.generatedId = a1;
        return this;
    }
    
    @Override
    Builder setOwner(final Acl.Entity a1) {
        this.owner = a1;
        return this;
    }
    
    @Override
    Builder setSelfLink(final String a1) {
        this.selfLink = a1;
        return this;
    }
    
    @Override
    public Builder setVersioningEnabled(final Boolean a1) {
        this.versioningEnabled = MoreObjects.firstNonNull(a1, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    public Builder setRequesterPays(final Boolean a1) {
        this.requesterPays = MoreObjects.firstNonNull(a1, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    public Builder setIndexPage(final String a1) {
        this.indexPage = a1;
        return this;
    }
    
    @Override
    public Builder setNotFoundPage(final String a1) {
        this.notFoundPage = a1;
        return this;
    }
    
    @Deprecated
    @Override
    public Builder setDeleteRules(final Iterable<? extends DeleteRule> a1) {
        this.deleteRules = (List<DeleteRule>)((a1 != null) ? ImmutableList.copyOf((Iterable<?>)a1) : null);
        return this;
    }
    
    @Override
    public Builder setLifecycleRules(final Iterable<? extends LifecycleRule> a1) {
        this.lifecycleRules = (List<LifecycleRule>)((a1 != null) ? ImmutableList.copyOf((Iterable<?>)a1) : null);
        return this;
    }
    
    @Override
    public Builder setStorageClass(final StorageClass a1) {
        this.storageClass = a1;
        return this;
    }
    
    @Override
    public Builder setLocation(final String a1) {
        this.location = a1;
        return this;
    }
    
    @Override
    Builder setEtag(final String a1) {
        this.etag = a1;
        return this;
    }
    
    @Override
    Builder setCreateTime(final Long a1) {
        this.createTime = a1;
        return this;
    }
    
    @Override
    Builder setMetageneration(final Long a1) {
        this.metageneration = a1;
        return this;
    }
    
    @Override
    public Builder setCors(final Iterable<Cors> a1) {
        this.cors = (List<Cors>)((a1 != null) ? ImmutableList.copyOf((Iterable<?>)a1) : null);
        return this;
    }
    
    @Override
    public Builder setAcl(final Iterable<Acl> a1) {
        this.acl = (List<Acl>)((a1 != null) ? ImmutableList.copyOf((Iterable<?>)a1) : null);
        return this;
    }
    
    @Override
    public Builder setDefaultAcl(final Iterable<Acl> a1) {
        this.defaultAcl = (List<Acl>)((a1 != null) ? ImmutableList.copyOf((Iterable<?>)a1) : null);
        return this;
    }
    
    @Override
    public Builder setLabels(final Map<String, String> a1) {
        this.labels = (Map<String, String>)((a1 != null) ? ImmutableMap.copyOf((Map)a1) : null);
        return this;
    }
    
    @Override
    public Builder setDefaultKmsKeyName(final String a1) {
        this.defaultKmsKeyName = ((a1 != null) ? a1 : Data.nullOf(String.class));
        return this;
    }
    
    @Override
    public Builder setDefaultEventBasedHold(final Boolean a1) {
        this.defaultEventBasedHold = MoreObjects.firstNonNull(a1, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    Builder setRetentionEffectiveTime(final Long a1) {
        this.retentionEffectiveTime = MoreObjects.firstNonNull(a1, Data.nullOf(Long.class));
        return this;
    }
    
    @Override
    Builder setRetentionPolicyIsLocked(final Boolean a1) {
        this.retentionPolicyIsLocked = MoreObjects.firstNonNull(a1, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    public Builder setRetentionPeriod(final Long a1) {
        this.retentionPeriod = MoreObjects.firstNonNull(a1, Data.nullOf(Long.class));
        return this;
    }
    
    @Override
    public Builder setIamConfiguration(final IamConfiguration a1) {
        this.iamConfiguration = a1;
        return this;
    }
    
    @Override
    Builder setLocationType(final String a1) {
        this.locationType = a1;
        return this;
    }
    
    @Override
    public BucketInfo build() {
        Preconditions.checkNotNull(this.name);
        return new BucketInfo(this);
    }
    
    static /* synthetic */ String access$3800(final BuilderImpl a1) {
        return a1.generatedId;
    }
    
    static /* synthetic */ String access$3900(final BuilderImpl a1) {
        return a1.name;
    }
    
    static /* synthetic */ String access$4000(final BuilderImpl a1) {
        return a1.etag;
    }
    
    static /* synthetic */ Long access$4100(final BuilderImpl a1) {
        return a1.createTime;
    }
    
    static /* synthetic */ Long access$4200(final BuilderImpl a1) {
        return a1.metageneration;
    }
    
    static /* synthetic */ String access$4300(final BuilderImpl a1) {
        return a1.location;
    }
    
    static /* synthetic */ StorageClass access$4400(final BuilderImpl a1) {
        return a1.storageClass;
    }
    
    static /* synthetic */ List access$4500(final BuilderImpl a1) {
        return a1.cors;
    }
    
    static /* synthetic */ List access$4600(final BuilderImpl a1) {
        return a1.acl;
    }
    
    static /* synthetic */ List access$4700(final BuilderImpl a1) {
        return a1.defaultAcl;
    }
    
    static /* synthetic */ Acl.Entity access$4800(final BuilderImpl a1) {
        return a1.owner;
    }
    
    static /* synthetic */ String access$4900(final BuilderImpl a1) {
        return a1.selfLink;
    }
    
    static /* synthetic */ Boolean access$5000(final BuilderImpl a1) {
        return a1.versioningEnabled;
    }
    
    static /* synthetic */ String access$5100(final BuilderImpl a1) {
        return a1.indexPage;
    }
    
    static /* synthetic */ String access$5200(final BuilderImpl a1) {
        return a1.notFoundPage;
    }
    
    static /* synthetic */ List access$5300(final BuilderImpl a1) {
        return a1.deleteRules;
    }
    
    static /* synthetic */ List access$5400(final BuilderImpl a1) {
        return a1.lifecycleRules;
    }
    
    static /* synthetic */ Map access$5500(final BuilderImpl a1) {
        return a1.labels;
    }
    
    static /* synthetic */ Boolean access$5600(final BuilderImpl a1) {
        return a1.requesterPays;
    }
    
    static /* synthetic */ String access$5700(final BuilderImpl a1) {
        return a1.defaultKmsKeyName;
    }
    
    static /* synthetic */ Boolean access$5800(final BuilderImpl a1) {
        return a1.defaultEventBasedHold;
    }
    
    static /* synthetic */ Long access$5900(final BuilderImpl a1) {
        return a1.retentionEffectiveTime;
    }
    
    static /* synthetic */ Boolean access$6000(final BuilderImpl a1) {
        return a1.retentionPolicyIsLocked;
    }
    
    static /* synthetic */ Long access$6100(final BuilderImpl a1) {
        return a1.retentionPeriod;
    }
    
    static /* synthetic */ IamConfiguration access$6200(final BuilderImpl a1) {
        return a1.iamConfiguration;
    }
    
    static /* synthetic */ String access$6300(final BuilderImpl a1) {
        return a1.locationType;
    }
}
