package com.google.cloud.storage;

public enum PredefinedAcl
{
    AUTHENTICATED_READ("authenticatedRead"), 
    ALL_AUTHENTICATED_USERS("allAuthenticatedUsers"), 
    PRIVATE("private"), 
    PROJECT_PRIVATE("projectPrivate"), 
    PUBLIC_READ("publicRead"), 
    PUBLIC_READ_WRITE("publicReadWrite"), 
    BUCKET_OWNER_READ("bucketOwnerRead"), 
    BUCKET_OWNER_FULL_CONTROL("bucketOwnerFullControl");
    
    private final String entry;
    private static final /* synthetic */ PredefinedAcl[] $VALUES;
    
    public static PredefinedAcl[] values() {
        return PredefinedAcl.$VALUES.clone();
    }
    
    public static PredefinedAcl valueOf(final String a1) {
        return Enum.valueOf(PredefinedAcl.class, a1);
    }
    
    private PredefinedAcl(final String a1) {
        this.entry = a1;
    }
    
    String getEntry() {
        return this.entry;
    }
    
    static {
        $VALUES = new PredefinedAcl[] { PredefinedAcl.AUTHENTICATED_READ, PredefinedAcl.ALL_AUTHENTICATED_USERS, PredefinedAcl.PRIVATE, PredefinedAcl.PROJECT_PRIVATE, PredefinedAcl.PUBLIC_READ, PredefinedAcl.PUBLIC_READ_WRITE, PredefinedAcl.BUCKET_OWNER_READ, PredefinedAcl.BUCKET_OWNER_FULL_CONTROL };
    }
}
