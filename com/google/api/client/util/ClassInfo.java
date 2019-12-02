package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

public final class ClassInfo
{
    private static final Map<Class<?>, ClassInfo> CACHE;
    private static final Map<Class<?>, ClassInfo> CACHE_IGNORE_CASE;
    private final Class<?> clazz;
    private final boolean ignoreCase;
    private final IdentityHashMap<String, FieldInfo> nameToFieldInfoMap;
    final List<String> names;
    
    public static ClassInfo of(final Class<?> a1) {
        return of(a1, false);
    }
    
    public static ClassInfo of(final Class<?> a2, final boolean v1) {
        if (a2 == null) {
            return null;
        }
        final Map<Class<?>, ClassInfo> v2 = v1 ? ClassInfo.CACHE_IGNORE_CASE : ClassInfo.CACHE;
        synchronized (v2) {
            ClassInfo a3 = v2.get(a2);
            if (a3 == null) {
                a3 = new ClassInfo(a2, v1);
                v2.put(a2, a3);
            }
        }
        final ClassInfo v3;
        return v3;
    }
    
    public Class<?> getUnderlyingClass() {
        return this.clazz;
    }
    
    public final boolean getIgnoreCase() {
        return this.ignoreCase;
    }
    
    public FieldInfo getFieldInfo(String a1) {
        if (a1 != null) {
            if (this.ignoreCase) {
                a1 = a1.toLowerCase(Locale.US);
            }
            a1 = a1.intern();
        }
        return this.nameToFieldInfoMap.get(a1);
    }
    
    public Field getField(final String a1) {
        final FieldInfo v1 = this.getFieldInfo(a1);
        return (v1 == null) ? null : v1.getField();
    }
    
    public boolean isEnum() {
        return this.clazz.isEnum();
    }
    
    public Collection<String> getNames() {
        return this.names;
    }
    
    private ClassInfo(final Class<?> v-8, final boolean v-7) {
        super();
        this.nameToFieldInfoMap = new IdentityHashMap<String, FieldInfo>();
        this.clazz = v-8;
        this.ignoreCase = v-7;
        Preconditions.checkArgument(!v-7 || !v-8.isEnum(), (Object)("cannot ignore case on an enum: " + v-8));
        final TreeSet<String> set = new TreeSet<String>(new Comparator<String>() {
            final /* synthetic */ ClassInfo this$0;
            
            ClassInfo$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public int compare(final String a1, final String a2) {
                return Objects.equal(a1, a2) ? 0 : ((a1 == null) ? -1 : ((a2 == null) ? 1 : a1.compareTo(a2)));
            }
            
            @Override
            public /* bridge */ int compare(final Object o, final Object o2) {
                return this.compare((String)o, (String)o2);
            }
        });
        for (final Field v-9 : v-8.getDeclaredFields()) {
            final FieldInfo a1 = FieldInfo.of(v-9);
            if (a1 != null) {
                String a2 = a1.getName();
                if (v-7) {
                    a2 = a2.toLowerCase(Locale.US).intern();
                }
                final FieldInfo v1 = this.nameToFieldInfoMap.get(a2);
                Preconditions.checkArgument(v1 == null, "two fields have the same %sname <%s>: %s and %s", v-7 ? "case-insensitive " : "", a2, v-9, (v1 == null) ? null : v1.getField());
                this.nameToFieldInfoMap.put(a2, a1);
                set.add(a2);
            }
        }
        final Class<?> superclass = v-8.getSuperclass();
        if (superclass != null) {
            final ClassInfo of = of(superclass, v-7);
            set.addAll(of.names);
            for (final Map.Entry<String, FieldInfo> entry : of.nameToFieldInfoMap.entrySet()) {
                final String s = entry.getKey();
                if (!this.nameToFieldInfoMap.containsKey(s)) {
                    this.nameToFieldInfoMap.put(s, entry.getValue());
                }
            }
        }
        this.names = (set.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList((List<? extends String>)new ArrayList<String>(set)));
    }
    
    public Collection<FieldInfo> getFieldInfos() {
        return Collections.unmodifiableCollection((Collection<? extends FieldInfo>)this.nameToFieldInfoMap.values());
    }
    
    static {
        CACHE = new WeakHashMap<Class<?>, ClassInfo>();
        CACHE_IGNORE_CASE = new WeakHashMap<Class<?>, ClassInfo>();
    }
}
