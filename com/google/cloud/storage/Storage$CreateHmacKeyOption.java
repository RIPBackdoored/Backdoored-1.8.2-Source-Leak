package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class CreateHmacKeyOption extends Option
{
    private CreateHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static CreateHmacKeyOption userProject(final String a1) {
        return new CreateHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static CreateHmacKeyOption projectId(final String a1) {
        return new CreateHmacKeyOption(StorageRpc.Option.PROJECT_ID, a1);
    }
}
