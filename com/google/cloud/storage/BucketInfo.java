package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.api.client.util.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import java.io.*;
import com.google.api.client.json.jackson2.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class BucketInfo implements Serializable
{
    static final Function<Bucket, BucketInfo> FROM_PB_FUNCTION;
    static final Function<BucketInfo, Bucket> TO_PB_FUNCTION;
    private static final long serialVersionUID = -4712013629621638459L;
    private final String generatedId;
    private final String name;
    private final Acl.Entity owner;
    private final String selfLink;
    private final Boolean requesterPays;
    private final Boolean versioningEnabled;
    private final String indexPage;
    private final String notFoundPage;
    private final List<DeleteRule> deleteRules;
    private final List<LifecycleRule> lifecycleRules;
    private final String etag;
    private final Long createTime;
    private final Long metageneration;
    private final List<Cors> cors;
    private final List<Acl> acl;
    private final List<Acl> defaultAcl;
    private final String location;
    private final StorageClass storageClass;
    private final Map<String, String> labels;
    private final String defaultKmsKeyName;
    private final Boolean defaultEventBasedHold;
    private final Long retentionEffectiveTime;
    private final Boolean retentionPolicyIsLocked;
    private final Long retentionPeriod;
    private final IamConfiguration iamConfiguration;
    private final String locationType;
    
    BucketInfo(final BuilderImpl a1) {
        super();
        this.generatedId = a1.generatedId;
        this.name = a1.name;
        this.etag = a1.etag;
        this.createTime = a1.createTime;
        this.metageneration = a1.metageneration;
        this.location = a1.location;
        this.storageClass = a1.storageClass;
        this.cors = a1.cors;
        this.acl = a1.acl;
        this.defaultAcl = a1.defaultAcl;
        this.owner = a1.owner;
        this.selfLink = a1.selfLink;
        this.versioningEnabled = a1.versioningEnabled;
        this.indexPage = a1.indexPage;
        this.notFoundPage = a1.notFoundPage;
        this.deleteRules = a1.deleteRules;
        this.lifecycleRules = a1.lifecycleRules;
        this.labels = a1.labels;
        this.requesterPays = a1.requesterPays;
        this.defaultKmsKeyName = a1.defaultKmsKeyName;
        this.defaultEventBasedHold = a1.defaultEventBasedHold;
        this.retentionEffectiveTime = a1.retentionEffectiveTime;
        this.retentionPolicyIsLocked = a1.retentionPolicyIsLocked;
        this.retentionPeriod = a1.retentionPeriod;
        this.iamConfiguration = a1.iamConfiguration;
        this.locationType = a1.locationType;
    }
    
    public String getGeneratedId() {
        return this.generatedId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Acl.Entity getOwner() {
        return this.owner;
    }
    
    public String getSelfLink() {
        return this.selfLink;
    }
    
    public Boolean versioningEnabled() {
        return Data.isNull(this.versioningEnabled) ? null : this.versioningEnabled;
    }
    
    public Boolean requesterPays() {
        return Data.isNull(this.requesterPays) ? null : this.requesterPays;
    }
    
    public String getIndexPage() {
        return this.indexPage;
    }
    
    public String getNotFoundPage() {
        return this.notFoundPage;
    }
    
    @Deprecated
    public List<? extends DeleteRule> getDeleteRules() {
        return this.deleteRules;
    }
    
    public List<? extends LifecycleRule> getLifecycleRules() {
        return this.lifecycleRules;
    }
    
    public String getEtag() {
        return this.etag;
    }
    
    public Long getCreateTime() {
        return this.createTime;
    }
    
    public Long getMetageneration() {
        return this.metageneration;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public String getLocationType() {
        return this.locationType;
    }
    
    public StorageClass getStorageClass() {
        return this.storageClass;
    }
    
    public List<Cors> getCors() {
        return this.cors;
    }
    
    public List<Acl> getAcl() {
        return this.acl;
    }
    
    public List<Acl> getDefaultAcl() {
        return this.defaultAcl;
    }
    
    public Map<String, String> getLabels() {
        return this.labels;
    }
    
    public String getDefaultKmsKeyName() {
        return this.defaultKmsKeyName;
    }
    
    @BetaApi
    public Boolean getDefaultEventBasedHold() {
        return Data.isNull(this.defaultEventBasedHold) ? null : this.defaultEventBasedHold;
    }
    
    @BetaApi
    public Long getRetentionEffectiveTime() {
        return this.retentionEffectiveTime;
    }
    
    @BetaApi
    public Boolean retentionPolicyIsLocked() {
        return Data.isNull(this.retentionPolicyIsLocked) ? null : this.retentionPolicyIsLocked;
    }
    
    @BetaApi
    public Long getRetentionPeriod() {
        return this.retentionPeriod;
    }
    
    @BetaApi
    public IamConfiguration getIamConfiguration() {
        return this.iamConfiguration;
    }
    
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == this || (a1 != null && a1.getClass().equals(BucketInfo.class) && Objects.equals(this.toPb(), ((BucketInfo)a1).toPb()));
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.name).toString();
    }
    
    Bucket toPb() {
        final Bucket v0 = new Bucket();
        v0.setId(this.generatedId);
        v0.setName(this.name);
        v0.setEtag(this.etag);
        if (this.createTime != null) {
            v0.setTimeCreated(new DateTime(this.createTime));
        }
        if (this.metageneration != null) {
            v0.setMetageneration(this.metageneration);
        }
        if (this.location != null) {
            v0.setLocation(this.location);
        }
        if (this.locationType != null) {
            v0.setLocationType(this.locationType);
        }
        if (this.storageClass != null) {
            v0.setStorageClass(this.storageClass.toString());
        }
        if (this.cors != null) {
            v0.setCors((List)Lists.transform(this.cors, (Function<? super Cors, ?>)Cors.TO_PB_FUNCTION));
        }
        if (this.acl != null) {
            v0.setAcl((List)Lists.transform(this.acl, (Function<? super Acl, ?>)new Function<Acl, BucketAccessControl>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$3() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public BucketAccessControl apply(final Acl a1) {
                    return a1.toBucketPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Acl)o);
                }
            }));
        }
        if (this.defaultAcl != null) {
            v0.setDefaultObjectAcl((List)Lists.transform(this.defaultAcl, (Function<? super Acl, ?>)new Function<Acl, ObjectAccessControl>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$4() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public ObjectAccessControl apply(final Acl a1) {
                    return a1.toObjectPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Acl)o);
                }
            }));
        }
        if (this.owner != null) {
            v0.setOwner(new Bucket.Owner().setEntity(this.owner.toPb()));
        }
        v0.setSelfLink(this.selfLink);
        if (this.versioningEnabled != null) {
            v0.setVersioning(new Bucket.Versioning().setEnabled(this.versioningEnabled));
        }
        if (this.requesterPays != null) {
            final Bucket.Billing v2 = new Bucket.Billing();
            v2.setRequesterPays(this.requesterPays);
            v0.setBilling(v2);
        }
        if (this.indexPage != null || this.notFoundPage != null) {
            final Bucket.Website v3 = new Bucket.Website();
            v3.setMainPageSuffix(this.indexPage);
            v3.setNotFoundPage(this.notFoundPage);
            v0.setWebsite(v3);
        }
        final Set<Bucket.Lifecycle.Rule> v4 = new HashSet<Bucket.Lifecycle.Rule>();
        if (this.deleteRules != null) {
            v4.addAll((Collection<? extends Bucket.Lifecycle.Rule>)Lists.transform(this.deleteRules, (Function<? super DeleteRule, ?>)new Function<DeleteRule, Bucket.Lifecycle.Rule>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$5() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Bucket.Lifecycle.Rule apply(final DeleteRule a1) {
                    return a1.toPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((DeleteRule)o);
                }
            }));
        }
        if (this.lifecycleRules != null) {
            v4.addAll((Collection<? extends Bucket.Lifecycle.Rule>)Lists.transform(this.lifecycleRules, (Function<? super LifecycleRule, ?>)new Function<LifecycleRule, Bucket.Lifecycle.Rule>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$6() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Bucket.Lifecycle.Rule apply(final LifecycleRule a1) {
                    return a1.toPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((LifecycleRule)o);
                }
            }));
        }
        if (!v4.isEmpty()) {
            final Bucket.Lifecycle v5 = new Bucket.Lifecycle();
            v5.setRule((List)ImmutableList.copyOf((Collection<?>)v4));
            v0.setLifecycle(v5);
        }
        if (this.labels != null) {
            v0.setLabels((Map)this.labels);
        }
        if (this.defaultKmsKeyName != null) {
            v0.setEncryption(new Bucket.Encryption().setDefaultKmsKeyName(this.defaultKmsKeyName));
        }
        if (this.defaultEventBasedHold != null) {
            v0.setDefaultEventBasedHold(this.defaultEventBasedHold);
        }
        if (this.retentionPeriod != null) {
            if (Data.isNull(this.retentionPeriod)) {
                v0.setRetentionPolicy((Bucket.RetentionPolicy)Data.nullOf(Bucket.RetentionPolicy.class));
            }
            else {
                final Bucket.RetentionPolicy v6 = new Bucket.RetentionPolicy();
                v6.setRetentionPeriod(this.retentionPeriod);
                if (this.retentionEffectiveTime != null) {
                    v6.setEffectiveTime(new DateTime(this.retentionEffectiveTime));
                }
                if (this.retentionPolicyIsLocked != null) {
                    v6.setIsLocked(this.retentionPolicyIsLocked);
                }
                v0.setRetentionPolicy(v6);
            }
        }
        if (this.iamConfiguration != null) {
            v0.setIamConfiguration(this.iamConfiguration.toPb());
        }
        return v0;
    }
    
    public static BucketInfo of(final String a1) {
        return newBuilder(a1).build();
    }
    
    public static Builder newBuilder(final String a1) {
        return new BuilderImpl(a1);
    }
    
    static BucketInfo fromPb(final Bucket a1) {
        final Builder v1 = new BuilderImpl(a1.getName());
        if (a1.getId() != null) {
            v1.setGeneratedId(a1.getId());
        }
        if (a1.getEtag() != null) {
            v1.setEtag(a1.getEtag());
        }
        if (a1.getMetageneration() != null) {
            v1.setMetageneration(a1.getMetageneration());
        }
        if (a1.getSelfLink() != null) {
            v1.setSelfLink(a1.getSelfLink());
        }
        if (a1.getTimeCreated() != null) {
            v1.setCreateTime(a1.getTimeCreated().getValue());
        }
        if (a1.getLocation() != null) {
            v1.setLocation(a1.getLocation());
        }
        if (a1.getStorageClass() != null) {
            v1.setStorageClass(StorageClass.valueOf(a1.getStorageClass()));
        }
        if (a1.getCors() != null) {
            v1.setCors((Iterable<Cors>)Lists.transform((List<Object>)a1.getCors(), (Function<? super Object, ?>)Cors.FROM_PB_FUNCTION));
        }
        if (a1.getAcl() != null) {
            v1.setAcl((Iterable<Acl>)Lists.transform((List<Object>)a1.getAcl(), (Function<? super Object, ?>)new Function<BucketAccessControl, Acl>() {
                BucketInfo$7() {
                    super();
                }
                
                @Override
                public Acl apply(final BucketAccessControl a1) {
                    return Acl.fromPb(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((BucketAccessControl)o);
                }
            }));
        }
        if (a1.getDefaultObjectAcl() != null) {
            v1.setDefaultAcl((Iterable<Acl>)Lists.transform((List<Object>)a1.getDefaultObjectAcl(), (Function<? super Object, ?>)new Function<ObjectAccessControl, Acl>() {
                BucketInfo$8() {
                    super();
                }
                
                @Override
                public Acl apply(final ObjectAccessControl a1) {
                    return Acl.fromPb(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((ObjectAccessControl)o);
                }
            }));
        }
        if (a1.getOwner() != null) {
            v1.setOwner(Acl.Entity.fromPb(a1.getOwner().getEntity()));
        }
        if (a1.getVersioning() != null) {
            v1.setVersioningEnabled(a1.getVersioning().getEnabled());
        }
        final Bucket.Website v2 = a1.getWebsite();
        if (v2 != null) {
            v1.setIndexPage(v2.getMainPageSuffix());
            v1.setNotFoundPage(v2.getNotFoundPage());
        }
        if (a1.getLifecycle() != null && a1.getLifecycle().getRule() != null) {
            v1.setLifecycleRules((Iterable<? extends LifecycleRule>)Lists.transform((List<Object>)a1.getLifecycle().getRule(), (Function<? super Object, ?>)new Function<Bucket.Lifecycle.Rule, LifecycleRule>() {
                BucketInfo$9() {
                    super();
                }
                
                @Override
                public LifecycleRule apply(final Bucket.Lifecycle.Rule a1) {
                    return LifecycleRule.fromPb(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Bucket.Lifecycle.Rule)o);
                }
            }));
            v1.setDeleteRules((Iterable<? extends DeleteRule>)Lists.transform((List<Object>)a1.getLifecycle().getRule(), (Function<? super Object, ?>)new Function<Bucket.Lifecycle.Rule, DeleteRule>() {
                BucketInfo$10() {
                    super();
                }
                
                @Override
                public DeleteRule apply(final Bucket.Lifecycle.Rule a1) {
                    return DeleteRule.fromPb(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Bucket.Lifecycle.Rule)o);
                }
            }));
        }
        if (a1.getLabels() != null) {
            v1.setLabels(a1.getLabels());
        }
        final Bucket.Billing v3 = a1.getBilling();
        if (v3 != null) {
            v1.setRequesterPays(v3.getRequesterPays());
        }
        final Bucket.Encryption v4 = a1.getEncryption();
        if (v4 != null && v4.getDefaultKmsKeyName() != null && !v4.getDefaultKmsKeyName().isEmpty()) {
            v1.setDefaultKmsKeyName(v4.getDefaultKmsKeyName());
        }
        if (a1.getDefaultEventBasedHold() != null) {
            v1.setDefaultEventBasedHold(a1.getDefaultEventBasedHold());
        }
        final Bucket.RetentionPolicy v5 = a1.getRetentionPolicy();
        if (v5 != null) {
            if (v5.getEffectiveTime() != null) {
                v1.setRetentionEffectiveTime(v5.getEffectiveTime().getValue());
            }
            if (v5.getIsLocked() != null) {
                v1.setRetentionPolicyIsLocked(v5.getIsLocked());
            }
            if (v5.getRetentionPeriod() != null) {
                v1.setRetentionPeriod(v5.getRetentionPeriod());
            }
        }
        final Bucket.IamConfiguration v6 = a1.getIamConfiguration();
        if (a1.getLocationType() != null) {
            v1.setLocationType(a1.getLocationType());
        }
        if (v6 != null) {
            v1.setIamConfiguration(IamConfiguration.fromPb(v6));
        }
        return v1.build();
    }
    
    static /* synthetic */ String access$1200(final BucketInfo a1) {
        return a1.generatedId;
    }
    
    static /* synthetic */ String access$1300(final BucketInfo a1) {
        return a1.name;
    }
    
    static /* synthetic */ String access$1400(final BucketInfo a1) {
        return a1.etag;
    }
    
    static /* synthetic */ Long access$1500(final BucketInfo a1) {
        return a1.createTime;
    }
    
    static /* synthetic */ Long access$1600(final BucketInfo a1) {
        return a1.metageneration;
    }
    
    static /* synthetic */ String access$1700(final BucketInfo a1) {
        return a1.location;
    }
    
    static /* synthetic */ StorageClass access$1800(final BucketInfo a1) {
        return a1.storageClass;
    }
    
    static /* synthetic */ List access$1900(final BucketInfo a1) {
        return a1.cors;
    }
    
    static /* synthetic */ List access$2000(final BucketInfo a1) {
        return a1.acl;
    }
    
    static /* synthetic */ List access$2100(final BucketInfo a1) {
        return a1.defaultAcl;
    }
    
    static /* synthetic */ Acl.Entity access$2200(final BucketInfo a1) {
        return a1.owner;
    }
    
    static /* synthetic */ String access$2300(final BucketInfo a1) {
        return a1.selfLink;
    }
    
    static /* synthetic */ Boolean access$2400(final BucketInfo a1) {
        return a1.versioningEnabled;
    }
    
    static /* synthetic */ String access$2500(final BucketInfo a1) {
        return a1.indexPage;
    }
    
    static /* synthetic */ String access$2600(final BucketInfo a1) {
        return a1.notFoundPage;
    }
    
    static /* synthetic */ List access$2700(final BucketInfo a1) {
        return a1.deleteRules;
    }
    
    static /* synthetic */ List access$2800(final BucketInfo a1) {
        return a1.lifecycleRules;
    }
    
    static /* synthetic */ Map access$2900(final BucketInfo a1) {
        return a1.labels;
    }
    
    static /* synthetic */ Boolean access$3000(final BucketInfo a1) {
        return a1.requesterPays;
    }
    
    static /* synthetic */ String access$3100(final BucketInfo a1) {
        return a1.defaultKmsKeyName;
    }
    
    static /* synthetic */ Boolean access$3200(final BucketInfo a1) {
        return a1.defaultEventBasedHold;
    }
    
    static /* synthetic */ Long access$3300(final BucketInfo a1) {
        return a1.retentionEffectiveTime;
    }
    
    static /* synthetic */ Boolean access$3400(final BucketInfo a1) {
        return a1.retentionPolicyIsLocked;
    }
    
    static /* synthetic */ Long access$3500(final BucketInfo a1) {
        return a1.retentionPeriod;
    }
    
    static /* synthetic */ IamConfiguration access$3600(final BucketInfo a1) {
        return a1.iamConfiguration;
    }
    
    static /* synthetic */ String access$3700(final BucketInfo a1) {
        return a1.locationType;
    }
    
    static {
        FROM_PB_FUNCTION = new Function<Bucket, BucketInfo>() {
            BucketInfo$1() {
                super();
            }
            
            @Override
            public BucketInfo apply(final Bucket a1) {
                return BucketInfo.fromPb(a1);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Bucket)o);
            }
        };
        TO_PB_FUNCTION = new Function<BucketInfo, Bucket>() {
            BucketInfo$2() {
                super();
            }
            
            @Override
            public Bucket apply(final BucketInfo a1) {
                return a1.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BucketInfo)o);
            }
        };
    }
    
    public static class IamConfiguration implements Serializable
    {
        private static final long serialVersionUID = -8671736104909424616L;
        private Boolean isBucketPolicyOnlyEnabled;
        private Long bucketPolicyOnlyLockedTime;
        
        @Override
        public boolean equals(final Object a1) {
            if (this == a1) {
                return true;
            }
            if (a1 == null || this.getClass() != a1.getClass()) {
                return false;
            }
            final IamConfiguration v1 = (IamConfiguration)a1;
            return Objects.equals(this.toPb(), v1.toPb());
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.isBucketPolicyOnlyEnabled, this.bucketPolicyOnlyLockedTime);
        }
        
        private IamConfiguration(final Builder a1) {
            super();
            this.isBucketPolicyOnlyEnabled = a1.isBucketPolicyOnlyEnabled;
            this.bucketPolicyOnlyLockedTime = a1.bucketPolicyOnlyLockedTime;
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        public Builder toBuilder() {
            final Builder v1 = new Builder();
            v1.isBucketPolicyOnlyEnabled = this.isBucketPolicyOnlyEnabled;
            v1.bucketPolicyOnlyLockedTime = this.bucketPolicyOnlyLockedTime;
            return v1;
        }
        
        public Boolean isBucketPolicyOnlyEnabled() {
            return this.isBucketPolicyOnlyEnabled;
        }
        
        public Long getBucketPolicyOnlyLockedTime() {
            return this.bucketPolicyOnlyLockedTime;
        }
        
        Bucket.IamConfiguration toPb() {
            final Bucket.IamConfiguration v1 = new Bucket.IamConfiguration();
            final Bucket.IamConfiguration.BucketPolicyOnly v2 = new Bucket.IamConfiguration.BucketPolicyOnly();
            v2.setEnabled(this.isBucketPolicyOnlyEnabled);
            v2.setLockedTime((this.bucketPolicyOnlyLockedTime == null) ? null : new DateTime(this.bucketPolicyOnlyLockedTime));
            v1.setBucketPolicyOnly(v2);
            return v1;
        }
        
        static IamConfiguration fromPb(final Bucket.IamConfiguration a1) {
            final Bucket.IamConfiguration.BucketPolicyOnly v1 = a1.getBucketPolicyOnly();
            final DateTime v2 = v1.getLockedTime();
            return newBuilder().setIsBucketPolicyOnlyEnabled(v1.getEnabled()).setBucketPolicyOnlyLockedTime((v2 == null) ? null : Long.valueOf(v2.getValue())).build();
        }
        
        IamConfiguration(final Builder a1, final BucketInfo$1 a2) {
            this(a1);
        }
        
        public static class Builder
        {
            private Boolean isBucketPolicyOnlyEnabled;
            private Long bucketPolicyOnlyLockedTime;
            
            public Builder() {
                super();
            }
            
            public Builder setIsBucketPolicyOnlyEnabled(final Boolean a1) {
                this.isBucketPolicyOnlyEnabled = a1;
                return this;
            }
            
            Builder setBucketPolicyOnlyLockedTime(final Long a1) {
                this.bucketPolicyOnlyLockedTime = a1;
                return this;
            }
            
            public IamConfiguration build() {
                return new IamConfiguration(this);
            }
            
            static /* synthetic */ Boolean access$000(final Builder a1) {
                return a1.isBucketPolicyOnlyEnabled;
            }
            
            static /* synthetic */ Long access$100(final Builder a1) {
                return a1.bucketPolicyOnlyLockedTime;
            }
            
            static /* synthetic */ Boolean access$002(final Builder a1, final Boolean a2) {
                return a1.isBucketPolicyOnlyEnabled = a2;
            }
            
            static /* synthetic */ Long access$102(final Builder a1, final Long a2) {
                return a1.bucketPolicyOnlyLockedTime = a2;
            }
        }
    }
    
    public static class LifecycleRule implements Serializable
    {
        private static final long serialVersionUID = -5739807320148748613L;
        private final LifecycleAction lifecycleAction;
        private final LifecycleCondition lifecycleCondition;
        
        public LifecycleRule(final LifecycleAction a1, final LifecycleCondition a2) {
            super();
            if (a2.getIsLive() == null && a2.getAge() == null && a2.getCreatedBefore() == null && a2.getMatchesStorageClass() == null && a2.getNumberOfNewerVersions() == null) {
                throw new IllegalArgumentException("You must specify at least one condition to use object lifecycle management. Please see https://cloud.google.com/storage/docs/lifecycle for details.");
            }
            this.lifecycleAction = a1;
            this.lifecycleCondition = a2;
        }
        
        public LifecycleAction getAction() {
            return this.lifecycleAction;
        }
        
        public LifecycleCondition getCondition() {
            return this.lifecycleCondition;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.lifecycleAction, this.lifecycleCondition);
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (this == a1) {
                return true;
            }
            if (a1 == null || this.getClass() != a1.getClass()) {
                return false;
            }
            final LifecycleRule v1 = (LifecycleRule)a1;
            return Objects.equals(this.toPb(), v1.toPb());
        }
        
        Bucket.Lifecycle.Rule toPb() {
            final Bucket.Lifecycle.Rule v1 = new Bucket.Lifecycle.Rule();
            final Bucket.Lifecycle.Rule.Action v2 = new Bucket.Lifecycle.Rule.Action().setType(this.lifecycleAction.getActionType());
            if (this.lifecycleAction.getActionType().equals("SetStorageClass")) {
                v2.setStorageClass(((SetStorageClassLifecycleAction)this.lifecycleAction).getStorageClass().toString());
            }
            v1.setAction(v2);
            final Bucket.Lifecycle.Rule.Condition v3 = new Bucket.Lifecycle.Rule.Condition().setAge(this.lifecycleCondition.getAge()).setCreatedBefore((this.lifecycleCondition.getCreatedBefore() == null) ? null : new DateTime(true, this.lifecycleCondition.getCreatedBefore().getValue(), 0)).setIsLive(this.lifecycleCondition.getIsLive()).setNumNewerVersions(this.lifecycleCondition.getNumberOfNewerVersions()).setMatchesStorageClass((List)((this.lifecycleCondition.getMatchesStorageClass() == null) ? null : Lists.transform(this.lifecycleCondition.getMatchesStorageClass(), (Function<? super StorageClass, ?>)Functions.toStringFunction())));
            v1.setCondition(v3);
            return v1;
        }
        
        static LifecycleRule fromPb(final Bucket.Lifecycle.Rule v0) {
            final Bucket.Lifecycle.Rule.Action v = v0.getAction();
            final String type = v.getType();
            final LifecycleAction v2;
            switch (type) {
                case "Delete": {
                    final LifecycleAction a1 = LifecycleAction.newDeleteAction();
                    break;
                }
                case "SetStorageClass": {
                    v2 = LifecycleAction.newSetStorageClassAction(StorageClass.valueOf(v.getStorageClass()));
                    break;
                }
                default: {
                    throw new UnsupportedOperationException("The specified lifecycle action " + v.getType() + " is not currently supported");
                }
            }
            final Bucket.Lifecycle.Rule.Condition v3 = v0.getCondition();
            final LifecycleCondition.Builder v4 = LifecycleCondition.newBuilder().setAge(v3.getAge()).setCreatedBefore(v3.getCreatedBefore()).setIsLive(v3.getIsLive()).setNumberOfNewerVersions(v3.getNumNewerVersions()).setMatchesStorageClass((v3.getMatchesStorageClass() == null) ? null : Lists.transform((List<Object>)v3.getMatchesStorageClass(), (Function<? super Object, ? extends StorageClass>)new Function<String, StorageClass>() {
                BucketInfo$LifecycleRule$1() {
                    super();
                }
                
                @Override
                public StorageClass apply(final String a1) {
                    return StorageClass.valueOf(a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }));
            return new LifecycleRule(v2, v4.build());
        }
        
        public static class LifecycleCondition implements Serializable
        {
            private static final long serialVersionUID = -6482314338394768785L;
            private final Integer age;
            private final DateTime createdBefore;
            private final Integer numberOfNewerVersions;
            private final Boolean isLive;
            private final List<StorageClass> matchesStorageClass;
            
            private LifecycleCondition(final Builder a1) {
                super();
                this.age = a1.age;
                this.createdBefore = a1.createdBefore;
                this.numberOfNewerVersions = a1.numberOfNewerVersions;
                this.isLive = a1.isLive;
                this.matchesStorageClass = a1.matchesStorageClass;
            }
            
            public Builder toBuilder() {
                return newBuilder().setAge(this.age).setCreatedBefore(this.createdBefore).setNumberOfNewerVersions(this.numberOfNewerVersions).setIsLive(this.isLive).setMatchesStorageClass(this.matchesStorageClass);
            }
            
            public static Builder newBuilder() {
                return new Builder();
            }
            
            public Integer getAge() {
                return this.age;
            }
            
            public DateTime getCreatedBefore() {
                return this.createdBefore;
            }
            
            public Integer getNumberOfNewerVersions() {
                return this.numberOfNewerVersions;
            }
            
            public Boolean getIsLive() {
                return this.isLive;
            }
            
            public List<StorageClass> getMatchesStorageClass() {
                return this.matchesStorageClass;
            }
            
            LifecycleCondition(final Builder a1, final BucketInfo$1 a2) {
                this(a1);
            }
            
            public static class Builder
            {
                private Integer age;
                private DateTime createdBefore;
                private Integer numberOfNewerVersions;
                private Boolean isLive;
                private List<StorageClass> matchesStorageClass;
                
                private Builder() {
                    super();
                }
                
                public Builder setAge(final Integer a1) {
                    this.age = a1;
                    return this;
                }
                
                public Builder setCreatedBefore(final DateTime a1) {
                    this.createdBefore = a1;
                    return this;
                }
                
                public Builder setNumberOfNewerVersions(final Integer a1) {
                    this.numberOfNewerVersions = a1;
                    return this;
                }
                
                public Builder setIsLive(final Boolean a1) {
                    this.isLive = a1;
                    return this;
                }
                
                public Builder setMatchesStorageClass(final List<StorageClass> a1) {
                    this.matchesStorageClass = a1;
                    return this;
                }
                
                public LifecycleCondition build() {
                    return new LifecycleCondition(this);
                }
                
                static /* synthetic */ Integer access$300(final Builder a1) {
                    return a1.age;
                }
                
                static /* synthetic */ DateTime access$400(final Builder a1) {
                    return a1.createdBefore;
                }
                
                static /* synthetic */ Integer access$500(final Builder a1) {
                    return a1.numberOfNewerVersions;
                }
                
                static /* synthetic */ Boolean access$600(final Builder a1) {
                    return a1.isLive;
                }
                
                static /* synthetic */ List access$700(final Builder a1) {
                    return a1.matchesStorageClass;
                }
                
                Builder(final BucketInfo$1 a1) {
                    this();
                }
            }
        }
        
        public abstract static class LifecycleAction implements Serializable
        {
            private static final long serialVersionUID = 5801228724709173284L;
            
            public LifecycleAction() {
                super();
            }
            
            public abstract String getActionType();
            
            public static DeleteLifecycleAction newDeleteAction() {
                return new DeleteLifecycleAction();
            }
            
            public static SetStorageClassLifecycleAction newSetStorageClassAction(final StorageClass a1) {
                return new SetStorageClassLifecycleAction(a1);
            }
        }
        
        public static class DeleteLifecycleAction extends LifecycleAction
        {
            public static final String TYPE = "Delete";
            private static final long serialVersionUID = -2050986302222644873L;
            
            private DeleteLifecycleAction() {
                super();
            }
            
            @Override
            public String getActionType() {
                return "Delete";
            }
            
            DeleteLifecycleAction(final BucketInfo$1 a1) {
                this();
            }
        }
        
        public static class SetStorageClassLifecycleAction extends LifecycleAction
        {
            public static final String TYPE = "SetStorageClass";
            private static final long serialVersionUID = -62615467186000899L;
            private final StorageClass storageClass;
            
            private SetStorageClassLifecycleAction(final StorageClass a1) {
                super();
                this.storageClass = a1;
            }
            
            @Override
            public String getActionType() {
                return "SetStorageClass";
            }
            
            StorageClass getStorageClass() {
                return this.storageClass;
            }
            
            SetStorageClassLifecycleAction(final StorageClass a1, final BucketInfo$1 a2) {
                this(a1);
            }
        }
    }
    
    @Deprecated
    public abstract static class DeleteRule implements Serializable
    {
        private static final long serialVersionUID = 3137971668395933033L;
        private static final String SUPPORTED_ACTION = "Delete";
        private final Type type;
        
        DeleteRule(final Type a1) {
            super();
            this.type = a1;
        }
        
        public Type getType() {
            return this.type;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.type);
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (this == a1) {
                return true;
            }
            if (a1 == null || this.getClass() != a1.getClass()) {
                return false;
            }
            final DeleteRule v1 = (DeleteRule)a1;
            return Objects.equals(this.toPb(), v1.toPb());
        }
        
        Bucket.Lifecycle.Rule toPb() {
            final Bucket.Lifecycle.Rule v1 = new Bucket.Lifecycle.Rule();
            v1.setAction(new Bucket.Lifecycle.Rule.Action().setType("Delete"));
            final Bucket.Lifecycle.Rule.Condition v2 = new Bucket.Lifecycle.Rule.Condition();
            this.populateCondition(v2);
            v1.setCondition(v2);
            return v1;
        }
        
        abstract void populateCondition(final Bucket.Lifecycle.Rule.Condition p0);
        
        static DeleteRule fromPb(final Bucket.Lifecycle.Rule v-1) {
            if (v-1.getAction() != null && "Delete".endsWith(v-1.getAction().getType())) {
                final Bucket.Lifecycle.Rule.Condition a1 = v-1.getCondition();
                final Integer v1 = a1.getAge();
                if (v1 != null) {
                    return new AgeDeleteRule(v1);
                }
                final DateTime v2 = a1.getCreatedBefore();
                if (v2 != null) {
                    return new CreatedBeforeDeleteRule(v2.getValue());
                }
                final Integer v3 = a1.getNumNewerVersions();
                if (v3 != null) {
                    return new NumNewerVersionsDeleteRule(v3);
                }
                final Boolean v4 = a1.getIsLive();
                if (v4 != null) {
                    return new IsLiveDeleteRule(v4);
                }
            }
            return new RawDeleteRule(v-1);
        }
        
        public enum Type
        {
            AGE, 
            CREATE_BEFORE, 
            NUM_NEWER_VERSIONS, 
            IS_LIVE, 
            UNKNOWN;
            
            private static final /* synthetic */ Type[] $VALUES;
            
            public static Type[] values() {
                return Type.$VALUES.clone();
            }
            
            public static Type valueOf(final String a1) {
                return Enum.valueOf(Type.class, a1);
            }
            
            static {
                $VALUES = new Type[] { Type.AGE, Type.CREATE_BEFORE, Type.NUM_NEWER_VERSIONS, Type.IS_LIVE, Type.UNKNOWN };
            }
        }
    }
    
    @Deprecated
    public static class AgeDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = 5697166940712116380L;
        private final int daysToLive;
        
        public AgeDeleteRule(final int a1) {
            super(Type.AGE);
            this.daysToLive = a1;
        }
        
        public int getDaysToLive() {
            return this.daysToLive;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
            a1.setAge(Integer.valueOf(this.daysToLive));
        }
    }
    
    static class RawDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = -7166938278642301933L;
        private transient Bucket.Lifecycle.Rule rule;
        
        RawDeleteRule(final Bucket.Lifecycle.Rule a1) {
            super(Type.UNKNOWN);
            this.rule = a1;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
            throw new UnsupportedOperationException();
        }
        
        private void writeObject(final ObjectOutputStream a1) throws IOException {
            a1.defaultWriteObject();
            a1.writeUTF(this.rule.toString());
        }
        
        private void readObject(final ObjectInputStream a1) throws IOException, ClassNotFoundException {
            a1.defaultReadObject();
            this.rule = new JacksonFactory().fromString(a1.readUTF(), Bucket.Lifecycle.Rule.class);
        }
        
        @Override
        Bucket.Lifecycle.Rule toPb() {
            return this.rule;
        }
    }
    
    @Deprecated
    public static class CreatedBeforeDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = 881692650279195867L;
        private final long timeMillis;
        
        public CreatedBeforeDeleteRule(final long a1) {
            super(Type.CREATE_BEFORE);
            this.timeMillis = a1;
        }
        
        public long getTimeMillis() {
            return this.timeMillis;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
            a1.setCreatedBefore(new DateTime(true, this.timeMillis, 0));
        }
    }
    
    @Deprecated
    public static class NumNewerVersionsDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = -1955554976528303894L;
        private final int numNewerVersions;
        
        public NumNewerVersionsDeleteRule(final int a1) {
            super(Type.NUM_NEWER_VERSIONS);
            this.numNewerVersions = a1;
        }
        
        public int getNumNewerVersions() {
            return this.numNewerVersions;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
            a1.setNumNewerVersions(Integer.valueOf(this.numNewerVersions));
        }
    }
    
    @Deprecated
    public static class IsLiveDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = -3502994563121313364L;
        private final boolean isLive;
        
        public IsLiveDeleteRule(final boolean a1) {
            super(Type.IS_LIVE);
            this.isLive = a1;
        }
        
        public boolean isLive() {
            return this.isLive;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition a1) {
            a1.setIsLive(Boolean.valueOf(this.isLive));
        }
    }
    
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
            this.generatedId = a1.generatedId;
            this.name = a1.name;
            this.etag = a1.etag;
            this.createTime = a1.createTime;
            this.metageneration = a1.metageneration;
            this.location = a1.location;
            this.storageClass = a1.storageClass;
            this.cors = a1.cors;
            this.acl = a1.acl;
            this.defaultAcl = a1.defaultAcl;
            this.owner = a1.owner;
            this.selfLink = a1.selfLink;
            this.versioningEnabled = a1.versioningEnabled;
            this.indexPage = a1.indexPage;
            this.notFoundPage = a1.notFoundPage;
            this.deleteRules = a1.deleteRules;
            this.lifecycleRules = a1.lifecycleRules;
            this.labels = a1.labels;
            this.requesterPays = a1.requesterPays;
            this.defaultKmsKeyName = a1.defaultKmsKeyName;
            this.defaultEventBasedHold = a1.defaultEventBasedHold;
            this.retentionEffectiveTime = a1.retentionEffectiveTime;
            this.retentionPolicyIsLocked = a1.retentionPolicyIsLocked;
            this.retentionPeriod = a1.retentionPeriod;
            this.iamConfiguration = a1.iamConfiguration;
            this.locationType = a1.locationType;
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
}
