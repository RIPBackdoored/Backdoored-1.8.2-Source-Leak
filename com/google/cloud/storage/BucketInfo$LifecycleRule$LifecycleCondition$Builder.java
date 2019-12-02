package com.google.cloud.storage;

import com.google.api.client.util.*;
import java.util.*;

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
