package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;

public class HmacKey implements Serializable
{
    private static final long serialVersionUID = -1809610424373783062L;
    private final String secretKey;
    private final HmacKeyMetadata metadata;
    
    private HmacKey(final Builder a1) {
        super();
        this.secretKey = a1.secretKey;
        this.metadata = a1.metadata;
    }
    
    public static Builder newBuilder(final String a1) {
        return new Builder(a1);
    }
    
    public String getSecretKey() {
        return this.secretKey;
    }
    
    public HmacKeyMetadata getMetadata() {
        return this.metadata;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.secretKey, this.metadata);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (a1 == null || this.getClass() != a1.getClass()) {
            return false;
        }
        final HmacKeyMetadata v1 = (HmacKeyMetadata)a1;
        return Objects.equals(this.secretKey, this.secretKey) && Objects.equals(this.metadata, this.metadata);
    }
    
    com.google.api.services.storage.model.HmacKey toPb() {
        final com.google.api.services.storage.model.HmacKey v1 = new com.google.api.services.storage.model.HmacKey();
        v1.setSecret(this.secretKey);
        if (this.metadata != null) {
            v1.setMetadata(this.metadata.toPb());
        }
        return v1;
    }
    
    static HmacKey fromPb(final com.google.api.services.storage.model.HmacKey a1) {
        return newBuilder(a1.getSecret()).setMetadata(HmacKeyMetadata.fromPb(a1.getMetadata())).build();
    }
    
    HmacKey(final Builder a1, final HmacKey$1 a2) {
        this(a1);
    }
    
    public static class Builder
    {
        private String secretKey;
        private HmacKeyMetadata metadata;
        
        private Builder(final String a1) {
            super();
            this.secretKey = a1;
        }
        
        public Builder setSecretKey(final String a1) {
            this.secretKey = a1;
            return this;
        }
        
        public Builder setMetadata(final HmacKeyMetadata a1) {
            this.metadata = a1;
            return this;
        }
        
        public HmacKey build() {
            return new HmacKey(this, null);
        }
        
        static /* synthetic */ String access$000(final Builder a1) {
            return a1.secretKey;
        }
        
        static /* synthetic */ HmacKeyMetadata access$100(final Builder a1) {
            return a1.metadata;
        }
        
        Builder(final String a1, final HmacKey$1 a2) {
            this(a1);
        }
    }
    
    public enum HmacKeyState
    {
        ACTIVE("ACTIVE"), 
        INACTIVE("INACTIVE"), 
        DELETED("DELETED");
        
        private final String state;
        private static final /* synthetic */ HmacKeyState[] $VALUES;
        
        public static HmacKeyState[] values() {
            return HmacKeyState.$VALUES.clone();
        }
        
        public static HmacKeyState valueOf(final String a1) {
            return Enum.valueOf(HmacKeyState.class, a1);
        }
        
        private HmacKeyState(final String a1) {
            this.state = a1;
        }
        
        static {
            $VALUES = new HmacKeyState[] { HmacKeyState.ACTIVE, HmacKeyState.INACTIVE, HmacKeyState.DELETED };
        }
    }
    
    public static class HmacKeyMetadata implements Serializable
    {
        private static final long serialVersionUID = 4571684785352640737L;
        private final String accessId;
        private final String etag;
        private final String id;
        private final String projectId;
        private final ServiceAccount serviceAccount;
        private final HmacKeyState state;
        private final Long createTime;
        private final Long updateTime;
        
        private HmacKeyMetadata(final Builder a1) {
            super();
            this.accessId = a1.accessId;
            this.etag = a1.etag;
            this.id = a1.id;
            this.projectId = a1.projectId;
            this.serviceAccount = a1.serviceAccount;
            this.state = a1.state;
            this.createTime = a1.createTime;
            this.updateTime = a1.updateTime;
        }
        
        public static Builder newBuilder(final ServiceAccount a1) {
            return new Builder(a1);
        }
        
        public Builder toBuilder() {
            return new Builder(this);
        }
        
        public static HmacKeyMetadata of(final ServiceAccount a1, final String a2, final String a3) {
            return newBuilder(a1).setAccessId(a2).setProjectId(a3).build();
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.accessId, this.projectId);
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (this == a1) {
                return true;
            }
            if (a1 == null || this.getClass() != a1.getClass()) {
                return false;
            }
            final HmacKeyMetadata v1 = (HmacKeyMetadata)a1;
            return Objects.equals(this.accessId, v1.accessId) && Objects.equals(this.etag, v1.etag) && Objects.equals(this.id, v1.id) && Objects.equals(this.projectId, v1.projectId) && Objects.equals(this.serviceAccount, v1.serviceAccount) && Objects.equals(this.state, v1.state) && Objects.equals(this.createTime, v1.createTime) && Objects.equals(this.updateTime, v1.updateTime);
        }
        
        public com.google.api.services.storage.model.HmacKeyMetadata toPb() {
            final com.google.api.services.storage.model.HmacKeyMetadata v1 = new com.google.api.services.storage.model.HmacKeyMetadata();
            v1.setAccessId(this.accessId);
            v1.setEtag(this.etag);
            v1.setId(this.id);
            v1.setProjectId(this.projectId);
            v1.setServiceAccountEmail((this.serviceAccount == null) ? null : this.serviceAccount.getEmail());
            v1.setState((this.state == null) ? null : this.state.toString());
            v1.setTimeCreated((this.createTime == null) ? null : new DateTime(this.createTime));
            v1.setUpdated((this.updateTime == null) ? null : new DateTime(this.updateTime));
            return v1;
        }
        
        static HmacKeyMetadata fromPb(final com.google.api.services.storage.model.HmacKeyMetadata a1) {
            return newBuilder(ServiceAccount.of(a1.getServiceAccountEmail())).setAccessId(a1.getAccessId()).setCreateTime(a1.getTimeCreated().getValue()).setEtag(a1.getEtag()).setId(a1.getId()).setProjectId(a1.getProjectId()).setState(HmacKeyState.valueOf(a1.getState())).setUpdateTime(a1.getUpdated().getValue()).build();
        }
        
        public String getAccessId() {
            return this.accessId;
        }
        
        public String getEtag() {
            return this.etag;
        }
        
        public String getId() {
            return this.id;
        }
        
        public String getProjectId() {
            return this.projectId;
        }
        
        public ServiceAccount getServiceAccount() {
            return this.serviceAccount;
        }
        
        public HmacKeyState getState() {
            return this.state;
        }
        
        public Long getCreateTime() {
            return this.createTime;
        }
        
        public Long getUpdateTime() {
            return this.updateTime;
        }
        
        static /* synthetic */ String access$1400(final HmacKeyMetadata a1) {
            return a1.accessId;
        }
        
        static /* synthetic */ String access$1500(final HmacKeyMetadata a1) {
            return a1.etag;
        }
        
        static /* synthetic */ String access$1600(final HmacKeyMetadata a1) {
            return a1.id;
        }
        
        static /* synthetic */ String access$1700(final HmacKeyMetadata a1) {
            return a1.projectId;
        }
        
        static /* synthetic */ ServiceAccount access$1800(final HmacKeyMetadata a1) {
            return a1.serviceAccount;
        }
        
        static /* synthetic */ HmacKeyState access$1900(final HmacKeyMetadata a1) {
            return a1.state;
        }
        
        static /* synthetic */ Long access$2000(final HmacKeyMetadata a1) {
            return a1.createTime;
        }
        
        static /* synthetic */ Long access$2100(final HmacKeyMetadata a1) {
            return a1.updateTime;
        }
        
        HmacKeyMetadata(final Builder a1, final HmacKey$1 a2) {
            this(a1);
        }
        
        public static class Builder
        {
            private String accessId;
            private String etag;
            private String id;
            private String projectId;
            private ServiceAccount serviceAccount;
            private HmacKeyState state;
            private Long createTime;
            private Long updateTime;
            
            private Builder(final ServiceAccount a1) {
                super();
                this.serviceAccount = a1;
            }
            
            private Builder(final HmacKeyMetadata a1) {
                super();
                this.accessId = a1.accessId;
                this.etag = a1.etag;
                this.id = a1.id;
                this.projectId = a1.projectId;
                this.serviceAccount = a1.serviceAccount;
                this.state = a1.state;
                this.createTime = a1.createTime;
                this.updateTime = a1.updateTime;
            }
            
            public Builder setAccessId(final String a1) {
                this.accessId = a1;
                return this;
            }
            
            public Builder setEtag(final String a1) {
                this.etag = a1;
                return this;
            }
            
            public Builder setId(final String a1) {
                this.id = a1;
                return this;
            }
            
            public Builder setServiceAccount(final ServiceAccount a1) {
                this.serviceAccount = a1;
                return this;
            }
            
            public Builder setState(final HmacKeyState a1) {
                this.state = a1;
                return this;
            }
            
            public Builder setCreateTime(final long a1) {
                this.createTime = a1;
                return this;
            }
            
            public Builder setProjectId(final String a1) {
                this.projectId = a1;
                return this;
            }
            
            public HmacKeyMetadata build() {
                return new HmacKeyMetadata(this);
            }
            
            public Builder setUpdateTime(final long a1) {
                this.updateTime = a1;
                return this;
            }
            
            static /* synthetic */ String access$400(final Builder a1) {
                return a1.accessId;
            }
            
            static /* synthetic */ String access$500(final Builder a1) {
                return a1.etag;
            }
            
            static /* synthetic */ String access$600(final Builder a1) {
                return a1.id;
            }
            
            static /* synthetic */ String access$700(final Builder a1) {
                return a1.projectId;
            }
            
            static /* synthetic */ ServiceAccount access$800(final Builder a1) {
                return a1.serviceAccount;
            }
            
            static /* synthetic */ HmacKeyState access$900(final Builder a1) {
                return a1.state;
            }
            
            static /* synthetic */ Long access$1000(final Builder a1) {
                return a1.createTime;
            }
            
            static /* synthetic */ Long access$1100(final Builder a1) {
                return a1.updateTime;
            }
            
            Builder(final ServiceAccount a1, final HmacKey$1 a2) {
                this(a1);
            }
            
            Builder(final HmacKeyMetadata a1, final HmacKey$1 a2) {
                this(a1);
            }
        }
    }
}
