package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.cloud.*;

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
