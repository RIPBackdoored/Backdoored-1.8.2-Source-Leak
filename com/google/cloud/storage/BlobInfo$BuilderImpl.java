package com.google.cloud.storage;

import com.google.api.client.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.math.*;
import com.google.common.io.*;
import java.util.*;

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
        this.blobId = BlobInfo.access$000(a1);
        this.generatedId = BlobInfo.access$100(a1);
        this.cacheControl = BlobInfo.access$200(a1);
        this.contentEncoding = BlobInfo.access$300(a1);
        this.contentType = BlobInfo.access$400(a1);
        this.contentDisposition = BlobInfo.access$500(a1);
        this.contentLanguage = BlobInfo.access$600(a1);
        this.componentCount = BlobInfo.access$700(a1);
        this.customerEncryption = BlobInfo.access$800(a1);
        this.acl = (List<Acl>)BlobInfo.access$900(a1);
        this.owner = BlobInfo.access$1000(a1);
        this.size = BlobInfo.access$1100(a1);
        this.etag = BlobInfo.access$1200(a1);
        this.selfLink = BlobInfo.access$1300(a1);
        this.md5 = BlobInfo.access$1400(a1);
        this.crc32c = BlobInfo.access$1500(a1);
        this.mediaLink = BlobInfo.access$1600(a1);
        this.metadata = (Map<String, String>)BlobInfo.access$1700(a1);
        this.metageneration = BlobInfo.access$1800(a1);
        this.deleteTime = BlobInfo.access$1900(a1);
        this.updateTime = BlobInfo.access$2000(a1);
        this.createTime = BlobInfo.access$2100(a1);
        this.isDirectory = BlobInfo.access$2200(a1);
        this.storageClass = BlobInfo.access$2300(a1);
        this.kmsKeyName = BlobInfo.access$2400(a1);
        this.eventBasedHold = BlobInfo.access$2500(a1);
        this.temporaryHold = BlobInfo.access$2600(a1);
        this.retentionExpirationTime = BlobInfo.access$2700(a1);
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
