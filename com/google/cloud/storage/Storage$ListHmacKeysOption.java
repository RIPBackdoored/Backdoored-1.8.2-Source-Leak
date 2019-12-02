package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class ListHmacKeysOption extends Option
{
    private ListHmacKeysOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static ListHmacKeysOption serviceAccount(final ServiceAccount a1) {
        return new ListHmacKeysOption(StorageRpc.Option.SERVICE_ACCOUNT_EMAIL, a1.getEmail());
    }
    
    public static ListHmacKeysOption maxResults(final long a1) {
        return new ListHmacKeysOption(StorageRpc.Option.MAX_RESULTS, a1);
    }
    
    public static ListHmacKeysOption pageToken(final String a1) {
        return new ListHmacKeysOption(StorageRpc.Option.PAGE_TOKEN, a1);
    }
    
    public static ListHmacKeysOption showDeletedKeys(final boolean a1) {
        return new ListHmacKeysOption(StorageRpc.Option.SHOW_DELETED_KEYS, a1);
    }
    
    public static ListHmacKeysOption userProject(final String a1) {
        return new ListHmacKeysOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static ListHmacKeysOption projectId(final String a1) {
        return new ListHmacKeysOption(StorageRpc.Option.PROJECT_ID, a1);
    }
}
