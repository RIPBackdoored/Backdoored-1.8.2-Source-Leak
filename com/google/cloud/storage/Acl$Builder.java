package com.google.cloud.storage;

public static class Builder
{
    private Entity entity;
    private Role role;
    private String id;
    private String etag;
    
    private Builder(final Entity a1, final Role a2) {
        super();
        this.entity = a1;
        this.role = a2;
    }
    
    private Builder(final Acl a1) {
        super();
        this.entity = Acl.access$100(a1);
        this.role = Acl.access$200(a1);
        this.id = Acl.access$300(a1);
        this.etag = Acl.access$400(a1);
    }
    
    public Builder setEntity(final Entity a1) {
        this.entity = a1;
        return this;
    }
    
    public Builder setRole(final Role a1) {
        this.role = a1;
        return this;
    }
    
    Builder setId(final String a1) {
        this.id = a1;
        return this;
    }
    
    Builder setEtag(final String a1) {
        this.etag = a1;
        return this;
    }
    
    public Acl build() {
        return new Acl(this, null);
    }
    
    static /* synthetic */ Entity access$700(final Builder a1) {
        return a1.entity;
    }
    
    static /* synthetic */ Role access$800(final Builder a1) {
        return a1.role;
    }
    
    static /* synthetic */ String access$900(final Builder a1) {
        return a1.id;
    }
    
    static /* synthetic */ String access$1000(final Builder a1) {
        return a1.etag;
    }
    
    Builder(final Acl a1, final Acl$1 a2) {
        this(a1);
    }
    
    Builder(final Entity a1, final Role a2, final Acl$1 a3) {
        this(a1, a2);
    }
}
