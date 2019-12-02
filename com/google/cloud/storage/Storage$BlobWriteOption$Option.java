package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

enum Option
{
    PREDEFINED_ACL, 
    IF_GENERATION_MATCH, 
    IF_GENERATION_NOT_MATCH, 
    IF_METAGENERATION_MATCH, 
    IF_METAGENERATION_NOT_MATCH, 
    IF_MD5_MATCH, 
    IF_CRC32C_MATCH, 
    CUSTOMER_SUPPLIED_KEY, 
    KMS_KEY_NAME, 
    USER_PROJECT;
    
    private static final /* synthetic */ Option[] $VALUES;
    
    public static Option[] values() {
        return Option.$VALUES.clone();
    }
    
    public static Option valueOf(final String a1) {
        return Enum.valueOf(Option.class, a1);
    }
    
    StorageRpc.Option toRpcOption() {
        return StorageRpc.Option.valueOf(this.name());
    }
    
    static {
        $VALUES = new Option[] { Option.PREDEFINED_ACL, Option.IF_GENERATION_MATCH, Option.IF_GENERATION_NOT_MATCH, Option.IF_METAGENERATION_MATCH, Option.IF_METAGENERATION_NOT_MATCH, Option.IF_MD5_MATCH, Option.IF_CRC32C_MATCH, Option.CUSTOMER_SUPPLIED_KEY, Option.KMS_KEY_NAME, Option.USER_PROJECT };
    }
}
