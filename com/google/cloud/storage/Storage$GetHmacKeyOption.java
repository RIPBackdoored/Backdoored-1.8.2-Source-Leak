package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class GetHmacKeyOption extends Option
{
    private GetHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static GetHmacKeyOption userProject(final String a1) {
        return new GetHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static GetHmacKeyOption projectId(final String a1) {
        return new GetHmacKeyOption(StorageRpc.Option.PROJECT_ID, a1);
    }
}
