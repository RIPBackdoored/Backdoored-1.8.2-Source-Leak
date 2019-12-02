package com.google.cloud.storage;

import java.io.*;
import com.google.api.client.util.*;
import java.util.*;

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
