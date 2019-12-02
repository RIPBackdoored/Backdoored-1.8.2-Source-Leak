package com.google.cloud.storage;

import java.io.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

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
