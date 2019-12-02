package org.spongepowered.tools.obfuscation;

import java.util.*;

public class ObfuscationData<T> implements Iterable<ObfuscationType>
{
    private final Map<ObfuscationType, T> data;
    private final T defaultValue;
    
    public ObfuscationData() {
        this(null);
    }
    
    public ObfuscationData(final T a1) {
        super();
        this.data = new HashMap<ObfuscationType, T>();
        this.defaultValue = a1;
    }
    
    @Deprecated
    public void add(final ObfuscationType a1, final T a2) {
        this.put(a1, a2);
    }
    
    public void put(final ObfuscationType a1, final T a2) {
        this.data.put(a1, a2);
    }
    
    public boolean isEmpty() {
        return this.data.isEmpty();
    }
    
    public T get(final ObfuscationType a1) {
        final T v1 = this.data.get(a1);
        return (v1 != null) ? v1 : this.defaultValue;
    }
    
    @Override
    public Iterator<ObfuscationType> iterator() {
        return this.data.keySet().iterator();
    }
    
    @Override
    public String toString() {
        return String.format("ObfuscationData[%s,DEFAULT=%s]", this.listValues(), this.defaultValue);
    }
    
    public String values() {
        return "[" + this.listValues() + "]";
    }
    
    private String listValues() {
        final StringBuilder sb = new StringBuilder();
        boolean b = false;
        for (final ObfuscationType v1 : this.data.keySet()) {
            if (b) {
                sb.append(',');
            }
            sb.append(v1.getKey()).append('=').append(this.data.get(v1));
            b = true;
        }
        return sb.toString();
    }
}
