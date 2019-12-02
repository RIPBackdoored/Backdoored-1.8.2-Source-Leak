package com.google.cloud.storage;

import java.io.*;
import java.util.*;

public abstract static class Entity implements Serializable
{
    private static final long serialVersionUID = -2707407252771255840L;
    private final Type type;
    private final String value;
    
    Entity(final Type a1, final String a2) {
        super();
        this.type = a1;
        this.value = a2;
    }
    
    public Type getType() {
        return this.type;
    }
    
    protected String getValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (a1 == null || this.getClass() != a1.getClass()) {
            return false;
        }
        final Entity v1 = (Entity)a1;
        return Objects.equals(this.type, v1.type) && Objects.equals(this.value, v1.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.value);
    }
    
    @Override
    public String toString() {
        return this.toPb();
    }
    
    String toPb() {
        return this.type.name().toLowerCase() + "-" + this.getValue();
    }
    
    static Entity fromPb(final String v-1) {
        if (v-1.startsWith("user-")) {
            return new User(v-1.substring(5));
        }
        if (v-1.equals("allUsers")) {
            return User.ofAllUsers();
        }
        if (v-1.equals("allAuthenticatedUsers")) {
            return User.ofAllAuthenticatedUsers();
        }
        if (v-1.startsWith("group-")) {
            return new Group(v-1.substring(6));
        }
        if (v-1.startsWith("domain-")) {
            return new Domain(v-1.substring(7));
        }
        if (v-1.startsWith("project-")) {
            final int a1 = v-1.indexOf(45, 8);
            final String v1 = v-1.substring(8, a1);
            final String v2 = v-1.substring(a1 + 1);
            return new Project(Project.ProjectRole.valueOf(v1.toUpperCase()), v2);
        }
        return new RawEntity(v-1);
    }
    
    public enum Type
    {
        DOMAIN, 
        GROUP, 
        USER, 
        PROJECT, 
        UNKNOWN;
        
        private static final /* synthetic */ Type[] $VALUES;
        
        public static Type[] values() {
            return Type.$VALUES.clone();
        }
        
        public static Type valueOf(final String a1) {
            return Enum.valueOf(Type.class, a1);
        }
        
        static {
            $VALUES = new Type[] { Type.DOMAIN, Type.GROUP, Type.USER, Type.PROJECT, Type.UNKNOWN };
        }
    }
}
