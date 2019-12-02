package org.yaml.snakeyaml.introspector;

public abstract class Property implements Comparable<Property>
{
    private final String name;
    private final Class<?> type;
    
    public Property(final String name, final Class<?> type) {
        super();
        this.name = name;
        this.type = type;
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public abstract Class<?>[] getActualTypeArguments();
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.getName() + " of " + this.getType();
    }
    
    @Override
    public int compareTo(final Property o) {
        return this.name.compareTo(o.name);
    }
    
    public boolean isWritable() {
        return true;
    }
    
    public boolean isReadable() {
        return true;
    }
    
    public abstract void set(final Object p0, final Object p1) throws Exception;
    
    public abstract Object get(final Object p0);
    
    @Override
    public int hashCode() {
        return this.name.hashCode() + this.type.hashCode();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Property) {
            final Property p = (Property)other;
            return this.name.equals(p.getName()) && this.type.equals(p.getType());
        }
        return false;
    }
    
    @Override
    public /* bridge */ int compareTo(final Object o) {
        return this.compareTo((Property)o);
    }
}
