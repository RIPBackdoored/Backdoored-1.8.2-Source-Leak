package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.cloud.*;

public final class StorageClass extends StringEnumValue
{
    private static final long serialVersionUID = -6938125060419556331L;
    private static final ApiFunction<String, StorageClass> CONSTRUCTOR;
    private static final StringEnumType<StorageClass> type;
    public static final StorageClass REGIONAL;
    public static final StorageClass MULTI_REGIONAL;
    public static final StorageClass NEARLINE;
    public static final StorageClass COLDLINE;
    public static final StorageClass STANDARD;
    public static final StorageClass DURABLE_REDUCED_AVAILABILITY;
    
    private StorageClass(final String a1) {
        super(a1);
    }
    
    public static StorageClass valueOfStrict(final String a1) {
        return (StorageClass)StorageClass.type.valueOfStrict(a1);
    }
    
    public static StorageClass valueOf(final String a1) {
        return (StorageClass)StorageClass.type.valueOf(a1);
    }
    
    public static StorageClass[] values() {
        return (StorageClass[])StorageClass.type.values();
    }
    
    StorageClass(final String a1, final StorageClass$1 a2) {
        this(a1);
    }
    
    static {
        CONSTRUCTOR = (ApiFunction)new ApiFunction<String, StorageClass>() {
            StorageClass$1() {
                super();
            }
            
            public StorageClass apply(final String a1) {
                return new StorageClass(a1, null);
            }
            
            public /* bridge */ Object apply(final Object o) {
                return this.apply((String)o);
            }
        };
        type = new StringEnumType((Class)StorageClass.class, (ApiFunction)StorageClass.CONSTRUCTOR);
        REGIONAL = (StorageClass)StorageClass.type.createAndRegister("REGIONAL");
        MULTI_REGIONAL = (StorageClass)StorageClass.type.createAndRegister("MULTI_REGIONAL");
        NEARLINE = (StorageClass)StorageClass.type.createAndRegister("NEARLINE");
        COLDLINE = (StorageClass)StorageClass.type.createAndRegister("COLDLINE");
        STANDARD = (StorageClass)StorageClass.type.createAndRegister("STANDARD");
        DURABLE_REDUCED_AVAILABILITY = (StorageClass)StorageClass.type.createAndRegister("DURABLE_REDUCED_AVAILABILITY");
    }
}
