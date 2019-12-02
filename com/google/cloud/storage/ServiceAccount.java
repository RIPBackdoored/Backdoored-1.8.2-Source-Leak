package com.google.cloud.storage;

import java.io.*;
import com.google.common.base.*;
import java.util.*;

public final class ServiceAccount implements Serializable
{
    static final Function<com.google.api.services.storage.model.ServiceAccount, ServiceAccount> FROM_PB_FUNCTION;
    static final Function<ServiceAccount, com.google.api.services.storage.model.ServiceAccount> TO_PB_FUNCTION;
    private static final long serialVersionUID = 4199610694227857331L;
    private final String email;
    
    private ServiceAccount(final String a1) {
        super();
        this.email = a1;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("email", this.email).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.email);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == this || (a1 instanceof ServiceAccount && Objects.equals(this.toPb(), ((ServiceAccount)a1).toPb()));
    }
    
    com.google.api.services.storage.model.ServiceAccount toPb() {
        final com.google.api.services.storage.model.ServiceAccount v1 = new com.google.api.services.storage.model.ServiceAccount();
        v1.setEmailAddress(this.email);
        return v1;
    }
    
    public static ServiceAccount of(final String a1) {
        return new ServiceAccount(a1);
    }
    
    static ServiceAccount fromPb(final com.google.api.services.storage.model.ServiceAccount a1) {
        return new ServiceAccount(a1.getEmailAddress());
    }
    
    static {
        FROM_PB_FUNCTION = new Function<com.google.api.services.storage.model.ServiceAccount, ServiceAccount>() {
            ServiceAccount$1() {
                super();
            }
            
            @Override
            public ServiceAccount apply(final com.google.api.services.storage.model.ServiceAccount a1) {
                return ServiceAccount.fromPb(a1);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((com.google.api.services.storage.model.ServiceAccount)o);
            }
        };
        TO_PB_FUNCTION = new Function<ServiceAccount, com.google.api.services.storage.model.ServiceAccount>() {
            ServiceAccount$2() {
                super();
            }
            
            @Override
            public com.google.api.services.storage.model.ServiceAccount apply(final ServiceAccount a1) {
                return a1.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((ServiceAccount)o);
            }
        };
    }
}
