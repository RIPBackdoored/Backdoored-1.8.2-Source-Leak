package com.google.cloud.storage;

import com.google.api.services.storage.model.*;
import com.google.cloud.*;
import java.util.*;

class PolicyHelper
{
    static com.google.cloud.Policy convertFromApiPolicy(final Policy v-2) {
        final com.google.cloud.Policy.Builder builder = com.google.cloud.Policy.newBuilder();
        for (final Policy.Bindings v1 : v-2.getBindings()) {
            for (final String a1 : v1.getMembers()) {
                builder.addIdentity(Role.of(v1.getRole()), Identity.valueOf(a1), new Identity[0]);
            }
        }
        return builder.setEtag(v-2.getEtag()).build();
    }
    
    static Policy convertToApiPolicy(final com.google.cloud.Policy v-3) {
        final List<Policy.Bindings> bindings = new ArrayList<Policy.Bindings>(v-3.getBindings().size());
        for (final Map.Entry<Role, Set<Identity>> v0 : v-3.getBindings().entrySet()) {
            final List<String> v2 = new ArrayList<String>(v0.getValue().size());
            for (final Identity a1 : v0.getValue()) {
                v2.add(a1.strValue());
            }
            bindings.add(new Policy.Bindings().setMembers((List)v2).setRole(v0.getKey().getValue()));
        }
        return new Policy().setBindings((List)bindings).setEtag(v-3.getEtag());
    }
    
    private PolicyHelper() {
        super();
    }
}
