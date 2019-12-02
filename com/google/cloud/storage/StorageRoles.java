package com.google.cloud.storage;

import com.google.cloud.*;

public class StorageRoles
{
    public static Role admin() {
        return Role.of("roles/storage.admin");
    }
    
    public static Role objectViewer() {
        return Role.of("roles/storage.objectViewer");
    }
    
    public static Role objectCreator() {
        return Role.of("roles/storage.objectCreator");
    }
    
    public static Role objectAdmin() {
        return Role.of("roles/storage.objectAdmin");
    }
    
    public static Role legacyBucketOwner() {
        return Role.of("roles/storage.legacyBucketOwner");
    }
    
    public static Role legacyBucketWriter() {
        return Role.of("roles/storage.legacyBucketWriter");
    }
    
    public static Role legacyBucketReader() {
        return Role.of("roles/storage.legacyBucketReader");
    }
    
    public static Role legacyObjectOwner() {
        return Role.of("roles/storage.legacyObjectOwner");
    }
    
    public static Role legacyObjectReader() {
        return Role.of("roles/storage.legacyObjectReader");
    }
    
    private StorageRoles() {
        super();
    }
}
