package com.google.cloud.storage;

enum Option
{
    HTTP_METHOD, 
    CONTENT_TYPE, 
    MD5, 
    EXT_HEADERS, 
    SERVICE_ACCOUNT_CRED, 
    SIGNATURE_VERSION, 
    HOST_NAME;
    
    private static final /* synthetic */ Option[] $VALUES;
    
    public static Option[] values() {
        return Option.$VALUES.clone();
    }
    
    public static Option valueOf(final String a1) {
        return Enum.valueOf(Option.class, a1);
    }
    
    static {
        $VALUES = new Option[] { Option.HTTP_METHOD, Option.CONTENT_TYPE, Option.MD5, Option.EXT_HEADERS, Option.SERVICE_ACCOUNT_CRED, Option.SIGNATURE_VERSION, Option.HOST_NAME };
    }
}
