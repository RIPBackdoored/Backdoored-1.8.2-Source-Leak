package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;

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
