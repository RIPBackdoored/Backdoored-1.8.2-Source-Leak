package com.google.cloud.storage;

import com.google.cloud.http.*;
import com.google.api.core.*;
import java.util.*;
import java.io.*;
import com.google.api.client.googleapis.json.*;
import com.google.cloud.*;
import com.google.common.collect.*;

@InternalApi
public final class StorageException extends BaseHttpServiceException
{
    private static final Set<BaseServiceException.Error> RETRYABLE_ERRORS;
    private static final long serialVersionUID = -4168430271327813063L;
    
    public StorageException(final int a1, final String a2) {
        this(a1, a2, null);
    }
    
    public StorageException(final int a1, final String a2, final Throwable a3) {
        super(a1, a2, (String)null, true, (Set)StorageException.RETRYABLE_ERRORS, a3);
    }
    
    public StorageException(final IOException a1) {
        super(a1, true, (Set)StorageException.RETRYABLE_ERRORS);
    }
    
    public StorageException(final GoogleJsonError a1) {
        super(a1, true, (Set)StorageException.RETRYABLE_ERRORS);
    }
    
    public static StorageException translateAndThrow(final RetryHelper.RetryHelperException a1) {
        BaseServiceException.translate(a1);
        throw new StorageException(0, a1.getMessage(), a1.getCause());
    }
    
    static {
        RETRYABLE_ERRORS = ImmutableSet.of((Object)new BaseServiceException.Error(Integer.valueOf(504), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(503), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(502), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(500), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(429), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(408), (String)null), (Object[])new BaseServiceException.Error[] { new BaseServiceException.Error((Integer)null, "internalError") });
    }
}
