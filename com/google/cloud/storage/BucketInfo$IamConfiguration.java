package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;

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
