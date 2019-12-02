package com.google.cloud.storage;

import java.io.*;
import com.google.api.services.storage.model.*;
import com.google.common.base.*;
import java.util.*;
import com.google.api.core.*;
import com.google.cloud.*;

public final class Acl implements Serializable
{
    private static final long serialVersionUID = 7516713233557576082L;
    static final Function<ObjectAccessControl, Acl> FROM_OBJECT_PB_FUNCTION;
    static final Function<BucketAccessControl, Acl> FROM_BUCKET_PB_FUNCTION;
    private final Entity entity;
    private final Role role;
    private final String id;
    private final String etag;
    
    private Acl(final Builder a1) {
        super();
        this.entity = Preconditions.checkNotNull(a1.entity);
        this.role = Preconditions.checkNotNull(a1.role);
        this.id = a1.id;
        this.etag = a1.etag;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public Role getRole() {
        return this.role;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getEtag() {
        return this.etag;
    }
    
    public Builder toBuilder() {
        return new Builder(this);
    }
    
    public static Acl of(final Entity a1, final Role a2) {
        return newBuilder(a1, a2).build();
    }
    
    public static Builder newBuilder(final Entity a1, final Role a2) {
        return new Builder(a1, a2);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("entity", this.entity).add("role", this.role).add("etag", this.etag).add("id", this.id).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.entity, this.role);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (a1 == null || this.getClass() != a1.getClass()) {
            return false;
        }
        final Acl v1 = (Acl)a1;
        return Objects.equals(this.entity, v1.entity) && Objects.equals(this.role, v1.role) && Objects.equals(this.etag, v1.etag) && Objects.equals(this.id, v1.id);
    }
    
    BucketAccessControl toBucketPb() {
        final BucketAccessControl v1 = new BucketAccessControl();
        v1.setEntity(this.getEntity().toString());
        v1.setRole(this.getRole().toString());
        v1.setId(this.getId());
        v1.setEtag(this.getEtag());
        return v1;
    }
    
    ObjectAccessControl toObjectPb() {
        final ObjectAccessControl v1 = new ObjectAccessControl();
        v1.setEntity(this.getEntity().toPb());
        v1.setRole(this.getRole().name());
        v1.setId(this.getId());
        v1.setEtag(this.getEtag());
        return v1;
    }
    
    static Acl fromPb(final ObjectAccessControl a1) {
        final Role v1 = Role.valueOf(a1.getRole());
        final Entity v2 = Entity.fromPb(a1.getEntity());
        return newBuilder(v2, v1).setEtag(a1.getEtag()).setId(a1.getId()).build();
    }
    
    static Acl fromPb(final BucketAccessControl a1) {
        final Role v1 = Role.valueOf(a1.getRole());
        final Entity v2 = Entity.fromPb(a1.getEntity());
        return newBuilder(v2, v1).setEtag(a1.getEtag()).setId(a1.getId()).build();
    }
    
    static /* synthetic */ Entity access$100(final Acl a1) {
        return a1.entity;
    }
    
    static /* synthetic */ Role access$200(final Acl a1) {
        return a1.role;
    }
    
    static /* synthetic */ String access$300(final Acl a1) {
        return a1.id;
    }
    
    static /* synthetic */ String access$400(final Acl a1) {
        return a1.etag;
    }
    
    Acl(final Builder a1, final Acl$1 a2) {
        this(a1);
    }
    
    static {
        FROM_OBJECT_PB_FUNCTION = new Function<ObjectAccessControl, Acl>() {
            Acl$1() {
                super();
            }
            
            @Override
            public Acl apply(final ObjectAccessControl a1) {
                return Acl.fromPb(a1);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((ObjectAccessControl)o);
            }
        };
        FROM_BUCKET_PB_FUNCTION = new Function<BucketAccessControl, Acl>() {
            Acl$2() {
                super();
            }
            
            @Override
            public Acl apply(final BucketAccessControl a1) {
                return Acl.fromPb(a1);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BucketAccessControl)o);
            }
        };
    }
    
    public static final class Role extends StringEnumValue
    {
        private static final long serialVersionUID = 123037132067643600L;
        private static final ApiFunction<String, Role> CONSTRUCTOR;
        private static final StringEnumType<Role> type;
        public static final Role OWNER;
        public static final Role READER;
        public static final Role WRITER;
        
        private Role(final String a1) {
            super(a1);
        }
        
        public static Role valueOfStrict(final String a1) {
            return (Role)Role.type.valueOfStrict(a1);
        }
        
        public static Role valueOf(final String a1) {
            return (Role)Role.type.valueOf(a1);
        }
        
        public static Role[] values() {
            return (Role[])Role.type.values();
        }
        
        Role(final String a1, final Acl$1 a2) {
            this(a1);
        }
        
        static {
            CONSTRUCTOR = (ApiFunction)new ApiFunction<String, Role>() {
                Acl$Role$1() {
                    super();
                }
                
                public Role apply(final String a1) {
                    return new Role(a1);
                }
                
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            };
            type = new StringEnumType((Class)Role.class, (ApiFunction)Role.CONSTRUCTOR);
            OWNER = (Role)Role.type.createAndRegister("OWNER");
            READER = (Role)Role.type.createAndRegister("READER");
            WRITER = (Role)Role.type.createAndRegister("WRITER");
        }
    }
    
    public static class Builder
    {
        private Entity entity;
        private Role role;
        private String id;
        private String etag;
        
        private Builder(final Entity a1, final Role a2) {
            super();
            this.entity = a1;
            this.role = a2;
        }
        
        private Builder(final Acl a1) {
            super();
            this.entity = a1.entity;
            this.role = a1.role;
            this.id = a1.id;
            this.etag = a1.etag;
        }
        
        public Builder setEntity(final Entity a1) {
            this.entity = a1;
            return this;
        }
        
        public Builder setRole(final Role a1) {
            this.role = a1;
            return this;
        }
        
        Builder setId(final String a1) {
            this.id = a1;
            return this;
        }
        
        Builder setEtag(final String a1) {
            this.etag = a1;
            return this;
        }
        
        public Acl build() {
            return new Acl(this, null);
        }
        
        static /* synthetic */ Entity access$700(final Builder a1) {
            return a1.entity;
        }
        
        static /* synthetic */ Role access$800(final Builder a1) {
            return a1.role;
        }
        
        static /* synthetic */ String access$900(final Builder a1) {
            return a1.id;
        }
        
        static /* synthetic */ String access$1000(final Builder a1) {
            return a1.etag;
        }
        
        Builder(final Acl a1, final Acl$1 a2) {
            this(a1);
        }
        
        Builder(final Entity a1, final Role a2, final Acl$1 a3) {
            this(a1, a2);
        }
    }
    
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
    
    public static final class Domain extends Entity
    {
        private static final long serialVersionUID = -3033025857280447253L;
        
        public Domain(final String a1) {
            super(Type.DOMAIN, a1);
        }
        
        public String getDomain() {
            return this.getValue();
        }
    }
    
    public static final class Group extends Entity
    {
        private static final long serialVersionUID = -1660987136294408826L;
        
        public Group(final String a1) {
            super(Type.GROUP, a1);
        }
        
        public String getEmail() {
            return this.getValue();
        }
    }
    
    public static final class User extends Entity
    {
        private static final long serialVersionUID = 3076518036392737008L;
        private static final String ALL_USERS = "allUsers";
        private static final String ALL_AUTHENTICATED_USERS = "allAuthenticatedUsers";
        
        public User(final String a1) {
            super(Type.USER, a1);
        }
        
        public String getEmail() {
            return this.getValue();
        }
        
        @Override
        String toPb() {
            final String value = this.getValue();
            switch (value) {
                case "allAuthenticatedUsers": {
                    return "allAuthenticatedUsers";
                }
                case "allUsers": {
                    return "allUsers";
                }
                default: {
                    return super.toPb();
                }
            }
        }
        
        public static User ofAllUsers() {
            return new User("allUsers");
        }
        
        public static User ofAllAuthenticatedUsers() {
            return new User("allAuthenticatedUsers");
        }
    }
    
    public static final class Project extends Entity
    {
        private static final long serialVersionUID = 7933776866530023027L;
        private final ProjectRole projectRole;
        private final String projectId;
        
        public Project(final ProjectRole a1, final String a2) {
            super(Type.PROJECT, a1.name().toLowerCase() + "-" + a2);
            this.projectRole = a1;
            this.projectId = a2;
        }
        
        public ProjectRole getProjectRole() {
            return this.projectRole;
        }
        
        public String getProjectId() {
            return this.projectId;
        }
        
        public static final class ProjectRole extends StringEnumValue
        {
            private static final long serialVersionUID = -8360324311187914382L;
            private static final ApiFunction<String, ProjectRole> CONSTRUCTOR;
            private static final StringEnumType<ProjectRole> type;
            public static final ProjectRole OWNERS;
            public static final ProjectRole EDITORS;
            public static final ProjectRole VIEWERS;
            
            private ProjectRole(final String a1) {
                super(a1);
            }
            
            public static ProjectRole valueOfStrict(final String a1) {
                return (ProjectRole)ProjectRole.type.valueOfStrict(a1);
            }
            
            public static ProjectRole valueOf(final String a1) {
                return (ProjectRole)ProjectRole.type.valueOf(a1);
            }
            
            public static ProjectRole[] values() {
                return (ProjectRole[])ProjectRole.type.values();
            }
            
            ProjectRole(final String a1, final Acl$1 a2) {
                this(a1);
            }
            
            static {
                CONSTRUCTOR = (ApiFunction)new ApiFunction<String, ProjectRole>() {
                    Acl$Project$ProjectRole$1() {
                        super();
                    }
                    
                    public ProjectRole apply(final String a1) {
                        return new ProjectRole(a1);
                    }
                    
                    public /* bridge */ Object apply(final Object o) {
                        return this.apply((String)o);
                    }
                };
                type = new StringEnumType((Class)ProjectRole.class, (ApiFunction)ProjectRole.CONSTRUCTOR);
                OWNERS = (ProjectRole)ProjectRole.type.createAndRegister("OWNERS");
                EDITORS = (ProjectRole)ProjectRole.type.createAndRegister("EDITORS");
                VIEWERS = (ProjectRole)ProjectRole.type.createAndRegister("VIEWERS");
            }
        }
    }
    
    public static final class RawEntity extends Entity
    {
        private static final long serialVersionUID = 3966205614223053950L;
        
        RawEntity(final String a1) {
            super(Type.UNKNOWN, a1);
        }
        
        @Override
        String toPb() {
            return this.getValue();
        }
    }
}
