package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class DeleteHmacKeyOption extends Option
{
    private DeleteHmacKeyOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static DeleteHmacKeyOption userProject(final String a1) {
        return new DeleteHmacKeyOption(StorageRpc.Option.USER_PROJECT, a1);
    }
}
