package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class UpdateHmacKeyOption extends Option
{
    private UpdateHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static UpdateHmacKeyOption userProject(final String a1) {
        return new UpdateHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
    }
}
