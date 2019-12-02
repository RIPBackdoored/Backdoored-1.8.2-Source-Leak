package com.google.cloud.storage;

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
        this.accessId = HmacKeyMetadata.access$1400(a1);
        this.etag = HmacKeyMetadata.access$1500(a1);
        this.id = HmacKeyMetadata.access$1600(a1);
        this.projectId = HmacKeyMetadata.access$1700(a1);
        this.serviceAccount = HmacKeyMetadata.access$1800(a1);
        this.state = HmacKeyMetadata.access$1900(a1);
        this.createTime = HmacKeyMetadata.access$2000(a1);
        this.updateTime = HmacKeyMetadata.access$2100(a1);
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
