package com.google.cloud.storage;

import java.io.*;
import com.google.common.base.*;
import java.util.*;
import com.google.api.services.storage.model.*;

public static class CustomerEncryption implements Serializable
{
    private static final long serialVersionUID = -2133042982786959351L;
    private final String encryptionAlgorithm;
    private final String keySha256;
    
    CustomerEncryption(final String a1, final String a2) {
        super();
        this.encryptionAlgorithm = a1;
        this.keySha256 = a2;
    }
    
    public String getEncryptionAlgorithm() {
        return this.encryptionAlgorithm;
    }
    
    public String getKeySha256() {
        return this.keySha256;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("encryptionAlgorithm", this.getEncryptionAlgorithm()).add("keySha256", this.getKeySha256()).toString();
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(this.encryptionAlgorithm, this.keySha256);
    }
    
    @Override
    public final boolean equals(final Object a1) {
        return a1 == this || (a1 != null && a1.getClass().equals(CustomerEncryption.class) && Objects.equals(this.toPb(), ((CustomerEncryption)a1).toPb()));
    }
    
    StorageObject.CustomerEncryption toPb() {
        return new StorageObject.CustomerEncryption().setEncryptionAlgorithm(this.encryptionAlgorithm).setKeySha256(this.keySha256);
    }
    
    static CustomerEncryption fromPb(final StorageObject.CustomerEncryption a1) {
        return new CustomerEncryption(a1.getEncryptionAlgorithm(), a1.getKeySha256());
    }
}
