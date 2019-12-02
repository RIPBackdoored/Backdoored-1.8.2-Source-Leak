package com.google.cloud.storage;

import java.io.*;
import com.google.common.io.*;
import com.google.api.core.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;
import java.math.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class BlobInfo implements Serializable
{
    static final Function<BlobInfo, StorageObject> INFO_TO_PB_FUNCTION;
    private static final long serialVersionUID = -5625857076205028976L;
    private final BlobId blobId;
    private final String generatedId;
    private final String selfLink;
    private final String cacheControl;
    private final List<Acl> acl;
    private final Acl.Entity owner;
    private final Long size;
    private final String etag;
    private final String md5;
    private final String crc32c;
    private final String mediaLink;
    private final Map<String, String> metadata;
    private final Long metageneration;
    private final Long deleteTime;
    private final Long updateTime;
    private final Long createTime;
    private final String contentType;
    private final String contentEncoding;
    private final String contentDisposition;
    private final String contentLanguage;
    private final StorageClass storageClass;
    private final Integer componentCount;
    private final boolean isDirectory;
    private final CustomerEncryption customerEncryption;
    private final String kmsKeyName;
    private final Boolean eventBasedHold;
    private final Boolean temporaryHold;
    private final Long retentionExpirationTime;
    
    BlobInfo(final BuilderImpl a1) {
        super();
        this.blobId = a1.blobId;
        this.generatedId = a1.generatedId;
        this.cacheControl = a1.cacheControl;
        this.contentEncoding = a1.contentEncoding;
        this.contentType = a1.contentType;
        this.contentDisposition = a1.contentDisposition;
        this.contentLanguage = a1.contentLanguage;
        this.componentCount = a1.componentCount;
        this.customerEncryption = a1.customerEncryption;
        this.acl = a1.acl;
        this.owner = a1.owner;
        this.size = a1.size;
        this.etag = a1.etag;
        this.selfLink = a1.selfLink;
        this.md5 = a1.md5;
        this.crc32c = a1.crc32c;
        this.mediaLink = a1.mediaLink;
        this.metadata = a1.metadata;
        this.metageneration = a1.metageneration;
        this.deleteTime = a1.deleteTime;
        this.updateTime = a1.updateTime;
        this.createTime = a1.createTime;
        this.isDirectory = MoreObjects.firstNonNull(a1.isDirectory, Boolean.FALSE);
        this.storageClass = a1.storageClass;
        this.kmsKeyName = a1.kmsKeyName;
        this.eventBasedHold = a1.eventBasedHold;
        this.temporaryHold = a1.temporaryHold;
        this.retentionExpirationTime = a1.retentionExpirationTime;
    }
    
    public BlobId getBlobId() {
        return this.blobId;
    }
    
    public String getBucket() {
        return this.getBlobId().getBucket();
    }
    
    public String getGeneratedId() {
        return this.generatedId;
    }
    
    public String getName() {
        return this.getBlobId().getName();
    }
    
    public String getCacheControl() {
        return Data.isNull(this.cacheControl) ? null : this.cacheControl;
    }
    
    public List<Acl> getAcl() {
        return this.acl;
    }
    
    public Acl.Entity getOwner() {
        return this.owner;
    }
    
    public Long getSize() {
        return this.size;
    }
    
    public String getContentType() {
        return Data.isNull(this.contentType) ? null : this.contentType;
    }
    
    public String getContentEncoding() {
        return Data.isNull(this.contentEncoding) ? null : this.contentEncoding;
    }
    
    public String getContentDisposition() {
        return Data.isNull(this.contentDisposition) ? null : this.contentDisposition;
    }
    
    public String getContentLanguage() {
        return Data.isNull(this.contentLanguage) ? null : this.contentLanguage;
    }
    
    public Integer getComponentCount() {
        return this.componentCount;
    }
    
    public String getEtag() {
        return this.etag;
    }
    
    public String getSelfLink() {
        return this.selfLink;
    }
    
    public String getMd5() {
        return Data.isNull(this.md5) ? null : this.md5;
    }
    
    public String getMd5ToHexString() {
        if (this.md5 == null) {
            return null;
        }
        final byte[] decode = BaseEncoding.base64().decode((CharSequence)this.md5);
        final StringBuilder sb = new StringBuilder();
        for (final byte v1 : decode) {
            sb.append(String.format("%02x", v1 & 0xFF));
        }
        return sb.toString();
    }
    
    public String getCrc32c() {
        return Data.isNull(this.crc32c) ? null : this.crc32c;
    }
    
    public String getCrc32cToHexString() {
        if (this.crc32c == null) {
            return null;
        }
        final byte[] decode = BaseEncoding.base64().decode((CharSequence)this.crc32c);
        final StringBuilder sb = new StringBuilder();
        for (final byte v1 : decode) {
            sb.append(String.format("%02x", v1 & 0xFF));
        }
        return sb.toString();
    }
    
    public String getMediaLink() {
        return this.mediaLink;
    }
    
    public Map<String, String> getMetadata() {
        return (this.metadata == null || Data.isNull(this.metadata)) ? null : Collections.unmodifiableMap((Map<? extends String, ? extends String>)this.metadata);
    }
    
    public Long getGeneration() {
        return this.getBlobId().getGeneration();
    }
    
    public Long getMetageneration() {
        return this.metageneration;
    }
    
    public Long getDeleteTime() {
        return this.deleteTime;
    }
    
    public Long getUpdateTime() {
        return this.updateTime;
    }
    
    public Long getCreateTime() {
        return this.createTime;
    }
    
    public boolean isDirectory() {
        return this.isDirectory;
    }
    
    public CustomerEncryption getCustomerEncryption() {
        return this.customerEncryption;
    }
    
    public StorageClass getStorageClass() {
        return this.storageClass;
    }
    
    public String getKmsKeyName() {
        return this.kmsKeyName;
    }
    
    @BetaApi
    public Boolean getEventBasedHold() {
        return Data.isNull(this.eventBasedHold) ? null : this.eventBasedHold;
    }
    
    @BetaApi
    public Boolean getTemporaryHold() {
        return Data.isNull(this.temporaryHold) ? null : this.temporaryHold;
    }
    
    @BetaApi
    public Long getRetentionExpirationTime() {
        return Data.isNull(this.retentionExpirationTime) ? null : this.retentionExpirationTime;
    }
    
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("bucket", this.getBucket()).add("name", this.getName()).add("generation", this.getGeneration()).add("size", this.getSize()).add("content-type", this.getContentType()).add("metadata", this.getMetadata()).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.blobId);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == this || (a1 != null && a1.getClass().equals(BlobInfo.class) && Objects.equals(this.toPb(), ((BlobInfo)a1).toPb()));
    }
    
    StorageObject toPb() {
        final StorageObject pb = this.blobId.toPb();
        if (this.acl != null) {
            pb.setAcl((List)Lists.transform(this.acl, (Function<? super Acl, ?>)new Function<Acl, ObjectAccessControl>() {
                final /* synthetic */ BlobInfo this$0;
                
                BlobInfo$2() {
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
        if (this.deleteTime != null) {
            pb.setTimeDeleted(new DateTime(this.deleteTime));
        }
        if (this.updateTime != null) {
            pb.setUpdated(new DateTime(this.updateTime));
        }
        if (this.createTime != null) {
            pb.setTimeCreated(new DateTime(this.createTime));
        }
        if (this.size != null) {
            pb.setSize(BigInteger.valueOf(this.size));
        }
        if (this.owner != null) {
            pb.setOwner(new StorageObject.Owner().setEntity(this.owner.toPb()));
        }
        if (this.storageClass != null) {
            pb.setStorageClass(this.storageClass.toString());
        }
        Map<String, String> metadata = this.metadata;
        if (this.metadata != null && !Data.isNull(this.metadata)) {
            metadata = (Map<String, String>)Maps.newHashMapWithExpectedSize(this.metadata.size());
            for (final Map.Entry<String, String> v1 : this.metadata.entrySet()) {
                metadata.put(v1.getKey(), MoreObjects.firstNonNull(v1.getValue(), Data.nullOf(String.class)));
            }
        }
        if (this.customerEncryption != null) {
            pb.setCustomerEncryption(this.customerEncryption.toPb());
        }
        if (this.retentionExpirationTime != null) {
            pb.setRetentionExpirationTime(new DateTime(this.retentionExpirationTime));
        }
        pb.setKmsKeyName(this.kmsKeyName);
        pb.setEventBasedHold(this.eventBasedHold);
        pb.setTemporaryHold(this.temporaryHold);
        pb.setMetadata((Map)metadata);
        pb.setCacheControl(this.cacheControl);
        pb.setContentEncoding(this.contentEncoding);
        pb.setCrc32c(this.crc32c);
        pb.setContentType(this.contentType);
        pb.setMd5Hash(this.md5);
        pb.setMediaLink(this.mediaLink);
        pb.setMetageneration(this.metageneration);
        pb.setContentDisposition(this.contentDisposition);
        pb.setComponentCount(this.componentCount);
        pb.setContentLanguage(this.contentLanguage);
        pb.setEtag(this.etag);
        pb.setId(this.generatedId);
        pb.setSelfLink(this.selfLink);
        return pb;
    }
    
    public static Builder newBuilder(final BucketInfo a1, final String a2) {
        return newBuilder(a1.getName(), a2);
    }
    
    public static Builder newBuilder(final String a1, final String a2) {
        return newBuilder(BlobId.of(a1, a2));
    }
    
    public static Builder newBuilder(final BucketInfo a1, final String a2, final Long a3) {
        return newBuilder(a1.getName(), a2, a3);
    }
    
    public static Builder newBuilder(final String a1, final String a2, final Long a3) {
        return newBuilder(BlobId.of(a1, a2, a3));
    }
    
    public static Builder newBuilder(final BlobId a1) {
        return new BuilderImpl(a1);
    }
    
    static BlobInfo fromPb(final StorageObject a1) {
        final Builder v1 = newBuilder(BlobId.fromPb(a1));
        if (a1.getCacheControl() != null) {
            v1.setCacheControl(a1.getCacheControl());
        }
        if (a1.getContentEncoding() != null) {
            v1.setContentEncoding(a1.getContentEncoding());
        }
        if (a1.getCrc32c() != null) {
            v1.setCrc32c(a1.getCrc32c());
        }
        if (a1.getContentType() != null) {
            v1.setContentType(a1.getContentType());
        }
        if (a1.getMd5Hash() != null) {
            v1.setMd5(a1.getMd5Hash());
        }
        if (a1.getMediaLink() != null) {
            v1.setMediaLink(a1.getMediaLink());
        }
        if (a1.getMetageneration() != null) {
            v1.setMetageneration(a1.getMetageneration());
        }
        if (a1.getContentDisposition() != null) {
            v1.setContentDisposition(a1.getContentDisposition());
        }
        if (a1.getComponentCount() != null) {
            v1.setComponentCount(a1.getComponentCount());
        }
        if (a1.getContentLanguage() != null) {
            v1.setContentLanguage(a1.getContentLanguage());
        }
        if (a1.getEtag() != null) {
            v1.setEtag(a1.getEtag());
        }
        if (a1.getId() != null) {
            v1.setGeneratedId(a1.getId());
        }
        if (a1.getSelfLink() != null) {
            v1.setSelfLink(a1.getSelfLink());
        }
        if (a1.getMetadata() != null) {
            v1.setMetadata(a1.getMetadata());
        }
        if (a1.getTimeDeleted() != null) {
            v1.setDeleteTime(a1.getTimeDeleted().getValue());
        }
        if (a1.getUpdated() != null) {
            v1.setUpdateTime(a1.getUpdated().getValue());
        }
        if (a1.getTimeCreated() != null) {
            v1.setCreateTime(a1.getTimeCreated().getValue());
        }
        if (a1.getSize() != null) {
            v1.setSize(a1.getSize().longValue());
        }
        if (a1.getOwner() != null) {
            v1.setOwner(Acl.Entity.fromPb(a1.getOwner().getEntity()));
        }
        if (a1.getAcl() != null) {
            v1.setAcl(Lists.transform((List<Object>)a1.getAcl(), (Function<? super Object, ? extends Acl>)new Function<ObjectAccessControl, Acl>() {
                BlobInfo$3() {
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
        if (a1.containsKey((Object)"isDirectory")) {
            v1.setIsDirectory(Boolean.TRUE);
        }
        if (a1.getCustomerEncryption() != null) {
            v1.setCustomerEncryption(CustomerEncryption.fromPb(a1.getCustomerEncryption()));
        }
        if (a1.getStorageClass() != null) {
            v1.setStorageClass(StorageClass.valueOf(a1.getStorageClass()));
        }
        if (a1.getKmsKeyName() != null) {
            v1.setKmsKeyName(a1.getKmsKeyName());
        }
        if (a1.getEventBasedHold() != null) {
            v1.setEventBasedHold(a1.getEventBasedHold());
        }
        if (a1.getTemporaryHold() != null) {
            v1.setTemporaryHold(a1.getTemporaryHold());
        }
        if (a1.getRetentionExpirationTime() != null) {
            v1.setRetentionExpirationTime(a1.getRetentionExpirationTime().getValue());
        }
        return v1.build();
    }
    
    static /* synthetic */ BlobId access$000(final BlobInfo a1) {
        return a1.blobId;
    }
    
    static /* synthetic */ String access$100(final BlobInfo a1) {
        return a1.generatedId;
    }
    
    static /* synthetic */ String access$200(final BlobInfo a1) {
        return a1.cacheControl;
    }
    
    static /* synthetic */ String access$300(final BlobInfo a1) {
        return a1.contentEncoding;
    }
    
    static /* synthetic */ String access$400(final BlobInfo a1) {
        return a1.contentType;
    }
    
    static /* synthetic */ String access$500(final BlobInfo a1) {
        return a1.contentDisposition;
    }
    
    static /* synthetic */ String access$600(final BlobInfo a1) {
        return a1.contentLanguage;
    }
    
    static /* synthetic */ Integer access$700(final BlobInfo a1) {
        return a1.componentCount;
    }
    
    static /* synthetic */ CustomerEncryption access$800(final BlobInfo a1) {
        return a1.customerEncryption;
    }
    
    static /* synthetic */ List access$900(final BlobInfo a1) {
        return a1.acl;
    }
    
    static /* synthetic */ Acl.Entity access$1000(final BlobInfo a1) {
        return a1.owner;
    }
    
    static /* synthetic */ Long access$1100(final BlobInfo a1) {
        return a1.size;
    }
    
    static /* synthetic */ String access$1200(final BlobInfo a1) {
        return a1.etag;
    }
    
    static /* synthetic */ String access$1300(final BlobInfo a1) {
        return a1.selfLink;
    }
    
    static /* synthetic */ String access$1400(final BlobInfo a1) {
        return a1.md5;
    }
    
    static /* synthetic */ String access$1500(final BlobInfo a1) {
        return a1.crc32c;
    }
    
    static /* synthetic */ String access$1600(final BlobInfo a1) {
        return a1.mediaLink;
    }
    
    static /* synthetic */ Map access$1700(final BlobInfo a1) {
        return a1.metadata;
    }
    
    static /* synthetic */ Long access$1800(final BlobInfo a1) {
        return a1.metageneration;
    }
    
    static /* synthetic */ Long access$1900(final BlobInfo a1) {
        return a1.deleteTime;
    }
    
    static /* synthetic */ Long access$2000(final BlobInfo a1) {
        return a1.updateTime;
    }
    
    static /* synthetic */ Long access$2100(final BlobInfo a1) {
        return a1.createTime;
    }
    
    static /* synthetic */ boolean access$2200(final BlobInfo a1) {
        return a1.isDirectory;
    }
    
    static /* synthetic */ StorageClass access$2300(final BlobInfo a1) {
        return a1.storageClass;
    }
    
    static /* synthetic */ String access$2400(final BlobInfo a1) {
        return a1.kmsKeyName;
    }
    
    static /* synthetic */ Boolean access$2500(final BlobInfo a1) {
        return a1.eventBasedHold;
    }
    
    static /* synthetic */ Boolean access$2600(final BlobInfo a1) {
        return a1.temporaryHold;
    }
    
    static /* synthetic */ Long access$2700(final BlobInfo a1) {
        return a1.retentionExpirationTime;
    }
    
    static {
        INFO_TO_PB_FUNCTION = new Function<BlobInfo, StorageObject>() {
            BlobInfo$1() {
                super();
            }
            
            @Override
            public StorageObject apply(final BlobInfo a1) {
                return a1.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BlobInfo)o);
            }
        };
    }
    
    public static final class ImmutableEmptyMap<K, V> extends AbstractMap<K, V>
    {
        public ImmutableEmptyMap() {
            super();
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return (Set<Map.Entry<K, V>>)ImmutableSet.of();
        }
    }
    
    public static class CustomerEncryption implements Serializable
    {
        private static final long serialVersionUID = -2133042982786959351L;
        private final String encryptionAlgorithm;
        private final String keySha256;
        
        CustomerEncryption(final String a1, final String a2) {
            super();
            this.encryptionAlgorithm = a1;
            this.keySha256 = a2;
        }
        
        public String getEncryptionAlgorithm() {
            return this.encryptionAlgorithm;
        }
        
        public String getKeySha256() {
            return this.keySha256;
        }
        
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("encryptionAlgorithm", this.getEncryptionAlgorithm()).add("keySha256", this.getKeySha256()).toString();
        }
        
        @Override
        public final int hashCode() {
            return Objects.hash(this.encryptionAlgorithm, this.keySha256);
        }
        
        @Override
        public final boolean equals(final Object a1) {
            return a1 == this || (a1 != null && a1.getClass().equals(CustomerEncryption.class) && Objects.equals(this.toPb(), ((CustomerEncryption)a1).toPb()));
        }
        
        StorageObject.CustomerEncryption toPb() {
            return new StorageObject.CustomerEncryption().setEncryptionAlgorithm(this.encryptionAlgorithm).setKeySha256(this.keySha256);
        }
        
        static CustomerEncryption fromPb(final StorageObject.CustomerEncryption a1) {
            return new CustomerEncryption(a1.getEncryptionAlgorithm(), a1.getKeySha256());
        }
    }
    
    public abstract static class Builder
    {
        public Builder() {
            super();
        }
        
        public abstract Builder setBlobId(final BlobId p0);
        
        abstract Builder setGeneratedId(final String p0);
        
        public abstract Builder setContentType(final String p0);
        
        public abstract Builder setContentDisposition(final String p0);
        
        public abstract Builder setContentLanguage(final String p0);
        
        public abstract Builder setContentEncoding(final String p0);
        
        abstract Builder setComponentCount(final Integer p0);
        
        public abstract Builder setCacheControl(final String p0);
        
        public abstract Builder setAcl(final List<Acl> p0);
        
        abstract Builder setOwner(final Acl.Entity p0);
        
        abstract Builder setSize(final Long p0);
        
        abstract Builder setEtag(final String p0);
        
        abstract Builder setSelfLink(final String p0);
        
        public abstract Builder setMd5(final String p0);
        
        public abstract Builder setMd5FromHexString(final String p0);
        
        public abstract Builder setCrc32c(final String p0);
        
        public abstract Builder setCrc32cFromHexString(final String p0);
        
        abstract Builder setMediaLink(final String p0);
        
        public abstract Builder setStorageClass(final StorageClass p0);
        
        public abstract Builder setMetadata(final Map<String, String> p0);
        
        abstract Builder setMetageneration(final Long p0);
        
        abstract Builder setDeleteTime(final Long p0);
        
        abstract Builder setUpdateTime(final Long p0);
        
        abstract Builder setCreateTime(final Long p0);
        
        abstract Builder setIsDirectory(final boolean p0);
        
        abstract Builder setCustomerEncryption(final CustomerEncryption p0);
        
        abstract Builder setKmsKeyName(final String p0);
        
        @BetaApi
        public abstract Builder setEventBasedHold(final Boolean p0);
        
        @BetaApi
        public abstract Builder setTemporaryHold(final Boolean p0);
        
        @BetaApi
        abstract Builder setRetentionExpirationTime(final Long p0);
        
        public abstract BlobInfo build();
    }
    
    static final class BuilderImpl extends Builder
    {
        private BlobId blobId;
        private String generatedId;
        private String contentType;
        private String contentEncoding;
        private String contentDisposition;
        private String contentLanguage;
        private Integer componentCount;
        private String cacheControl;
        private List<Acl> acl;
        private Acl.Entity owner;
        private Long size;
        private String etag;
        private String selfLink;
        private String md5;
        private String crc32c;
        private String mediaLink;
        private Map<String, String> metadata;
        private Long metageneration;
        private Long deleteTime;
        private Long updateTime;
        private Long createTime;
        private Boolean isDirectory;
        private CustomerEncryption customerEncryption;
        private StorageClass storageClass;
        private String kmsKeyName;
        private Boolean eventBasedHold;
        private Boolean temporaryHold;
        private Long retentionExpirationTime;
        
        BuilderImpl(final BlobId a1) {
            super();
            this.blobId = a1;
        }
        
        BuilderImpl(final BlobInfo a1) {
            super();
            this.blobId = a1.blobId;
            this.generatedId = a1.generatedId;
            this.cacheControl = a1.cacheControl;
            this.contentEncoding = a1.contentEncoding;
            this.contentType = a1.contentType;
            this.contentDisposition = a1.contentDisposition;
            this.contentLanguage = a1.contentLanguage;
            this.componentCount = a1.componentCount;
            this.customerEncryption = a1.customerEncryption;
            this.acl = a1.acl;
            this.owner = a1.owner;
            this.size = a1.size;
            this.etag = a1.etag;
            this.selfLink = a1.selfLink;
            this.md5 = a1.md5;
            this.crc32c = a1.crc32c;
            this.mediaLink = a1.mediaLink;
            this.metadata = a1.metadata;
            this.metageneration = a1.metageneration;
            this.deleteTime = a1.deleteTime;
            this.updateTime = a1.updateTime;
            this.createTime = a1.createTime;
            this.isDirectory = a1.isDirectory;
            this.storageClass = a1.storageClass;
            this.kmsKeyName = a1.kmsKeyName;
            this.eventBasedHold = a1.eventBasedHold;
            this.temporaryHold = a1.temporaryHold;
            this.retentionExpirationTime = a1.retentionExpirationTime;
        }
        
        @Override
        public Builder setBlobId(final BlobId a1) {
            this.blobId = Preconditions.checkNotNull(a1);
            return this;
        }
        
        @Override
        Builder setGeneratedId(final String a1) {
            this.generatedId = a1;
            return this;
        }
        
        @Override
        public Builder setContentType(final String a1) {
            this.contentType = MoreObjects.firstNonNull(a1, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setContentDisposition(final String a1) {
            this.contentDisposition = MoreObjects.firstNonNull(a1, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setContentLanguage(final String a1) {
            this.contentLanguage = MoreObjects.firstNonNull(a1, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setContentEncoding(final String a1) {
            this.contentEncoding = MoreObjects.firstNonNull(a1, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        Builder setComponentCount(final Integer a1) {
            this.componentCount = a1;
            return this;
        }
        
        @Override
        public Builder setCacheControl(final String a1) {
            this.cacheControl = MoreObjects.firstNonNull(a1, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setAcl(final List<Acl> a1) {
            this.acl = (List<Acl>)((a1 != null) ? ImmutableList.copyOf((Collection<?>)a1) : null);
            return this;
        }
        
        @Override
        Builder setOwner(final Acl.Entity a1) {
            this.owner = a1;
            return this;
        }
        
        @Override
        Builder setSize(final Long a1) {
            this.size = a1;
            return this;
        }
        
        @Override
        Builder setEtag(final String a1) {
            this.etag = a1;
            return this;
        }
        
        @Override
        Builder setSelfLink(final String a1) {
            this.selfLink = a1;
            return this;
        }
        
        @Override
        public Builder setMd5(final String a1) {
            this.md5 = MoreObjects.firstNonNull(a1, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setMd5FromHexString(final String a1) {
            if (a1 == null) {
                return this;
            }
            byte[] v1 = new BigInteger(a1, 16).toByteArray();
            final int v2 = v1.length - a1.length() / 2;
            if (v2 > 0) {
                v1 = Arrays.copyOfRange(v1, v2, v1.length);
            }
            this.md5 = BaseEncoding.base64().encode(v1);
            return this;
        }
        
        @Override
        public Builder setCrc32c(final String a1) {
            this.crc32c = MoreObjects.firstNonNull(a1, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setCrc32cFromHexString(final String a1) {
            if (a1 == null) {
                return this;
            }
            byte[] v1 = new BigInteger(a1, 16).toByteArray();
            final int v2 = v1.length - a1.length() / 2;
            if (v2 > 0) {
                v1 = Arrays.copyOfRange(v1, v2, v1.length);
            }
            this.crc32c = BaseEncoding.base64().encode(v1);
            return this;
        }
        
        @Override
        Builder setMediaLink(final String a1) {
            this.mediaLink = a1;
            return this;
        }
        
        @Override
        public Builder setMetadata(final Map<String, String> a1) {
            if (a1 != null) {
                this.metadata = new HashMap<String, String>(a1);
            }
            else {
                this.metadata = Data.nullOf(ImmutableEmptyMap.class);
            }
            return this;
        }
        
        @Override
        public Builder setStorageClass(final StorageClass a1) {
            this.storageClass = a1;
            return this;
        }
        
        @Override
        Builder setMetageneration(final Long a1) {
            this.metageneration = a1;
            return this;
        }
        
        @Override
        Builder setDeleteTime(final Long a1) {
            this.deleteTime = a1;
            return this;
        }
        
        @Override
        Builder setUpdateTime(final Long a1) {
            this.updateTime = a1;
            return this;
        }
        
        @Override
        Builder setCreateTime(final Long a1) {
            this.createTime = a1;
            return this;
        }
        
        @Override
        Builder setIsDirectory(final boolean a1) {
            this.isDirectory = a1;
            return this;
        }
        
        @Override
        Builder setCustomerEncryption(final CustomerEncryption a1) {
            this.customerEncryption = a1;
            return this;
        }
        
        @Override
        Builder setKmsKeyName(final String a1) {
            this.kmsKeyName = a1;
            return this;
        }
        
        @Override
        public Builder setEventBasedHold(final Boolean a1) {
            this.eventBasedHold = a1;
            return this;
        }
        
        @Override
        public Builder setTemporaryHold(final Boolean a1) {
            this.temporaryHold = a1;
            return this;
        }
        
        @Override
        Builder setRetentionExpirationTime(final Long a1) {
            this.retentionExpirationTime = a1;
            return this;
        }
        
        @Override
        public BlobInfo build() {
            Preconditions.checkNotNull(this.blobId);
            return new BlobInfo(this);
        }
        
        static /* synthetic */ BlobId access$2800(final BuilderImpl a1) {
            return a1.blobId;
        }
        
        static /* synthetic */ String access$2900(final BuilderImpl a1) {
            return a1.generatedId;
        }
        
        static /* synthetic */ String access$3000(final BuilderImpl a1) {
            return a1.cacheControl;
        }
        
        static /* synthetic */ String access$3100(final BuilderImpl a1) {
            return a1.contentEncoding;
        }
        
        static /* synthetic */ String access$3200(final BuilderImpl a1) {
            return a1.contentType;
        }
        
        static /* synthetic */ String access$3300(final BuilderImpl a1) {
            return a1.contentDisposition;
        }
        
        static /* synthetic */ String access$3400(final BuilderImpl a1) {
            return a1.contentLanguage;
        }
        
        static /* synthetic */ Integer access$3500(final BuilderImpl a1) {
            return a1.componentCount;
        }
        
        static /* synthetic */ CustomerEncryption access$3600(final BuilderImpl a1) {
            return a1.customerEncryption;
        }
        
        static /* synthetic */ List access$3700(final BuilderImpl a1) {
            return a1.acl;
        }
        
        static /* synthetic */ Acl.Entity access$3800(final BuilderImpl a1) {
            return a1.owner;
        }
        
        static /* synthetic */ Long access$3900(final BuilderImpl a1) {
            return a1.size;
        }
        
        static /* synthetic */ String access$4000(final BuilderImpl a1) {
            return a1.etag;
        }
        
        static /* synthetic */ String access$4100(final BuilderImpl a1) {
            return a1.selfLink;
        }
        
        static /* synthetic */ String access$4200(final BuilderImpl a1) {
            return a1.md5;
        }
        
        static /* synthetic */ String access$4300(final BuilderImpl a1) {
            return a1.crc32c;
        }
        
        static /* synthetic */ String access$4400(final BuilderImpl a1) {
            return a1.mediaLink;
        }
        
        static /* synthetic */ Map access$4500(final BuilderImpl a1) {
            return a1.metadata;
        }
        
        static /* synthetic */ Long access$4600(final BuilderImpl a1) {
            return a1.metageneration;
        }
        
        static /* synthetic */ Long access$4700(final BuilderImpl a1) {
            return a1.deleteTime;
        }
        
        static /* synthetic */ Long access$4800(final BuilderImpl a1) {
            return a1.updateTime;
        }
        
        static /* synthetic */ Long access$4900(final BuilderImpl a1) {
            return a1.createTime;
        }
        
        static /* synthetic */ Boolean access$5000(final BuilderImpl a1) {
            return a1.isDirectory;
        }
        
        static /* synthetic */ StorageClass access$5100(final BuilderImpl a1) {
            return a1.storageClass;
        }
        
        static /* synthetic */ String access$5200(final BuilderImpl a1) {
            return a1.kmsKeyName;
        }
        
        static /* synthetic */ Boolean access$5300(final BuilderImpl a1) {
            return a1.eventBasedHold;
        }
        
        static /* synthetic */ Boolean access$5400(final BuilderImpl a1) {
            return a1.temporaryHold;
        }
        
        static /* synthetic */ Long access$5500(final BuilderImpl a1) {
            return a1.retentionExpirationTime;
        }
    }
}
