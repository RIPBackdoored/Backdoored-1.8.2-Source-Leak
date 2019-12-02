package com.google.cloud.storage;

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
