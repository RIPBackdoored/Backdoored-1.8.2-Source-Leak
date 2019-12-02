package com.google.cloud.storage;

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
