package com.google.cloud.storage;

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
