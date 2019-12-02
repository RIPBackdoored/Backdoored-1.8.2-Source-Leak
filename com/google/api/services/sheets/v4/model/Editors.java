package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class Editors extends GenericJson
{
    @Key
    private Boolean domainUsersCanEdit;
    @Key
    private List<String> groups;
    @Key
    private List<String> users;
    
    public Editors() {
        super();
    }
    
    public Boolean getDomainUsersCanEdit() {
        return this.domainUsersCanEdit;
    }
    
    public Editors setDomainUsersCanEdit(final Boolean domainUsersCanEdit) {
        this.domainUsersCanEdit = domainUsersCanEdit;
        return this;
    }
    
    public List<String> getGroups() {
        return this.groups;
    }
    
    public Editors setGroups(final List<String> groups) {
        this.groups = groups;
        return this;
    }
    
    public List<String> getUsers() {
        return this.users;
    }
    
    public Editors setUsers(final List<String> users) {
        this.users = users;
        return this;
    }
    
    @Override
    public Editors set(final String a1, final Object a2) {
        return (Editors)super.set(a1, a2);
    }
    
    @Override
    public Editors clone() {
        return (Editors)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
