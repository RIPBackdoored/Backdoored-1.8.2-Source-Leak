package org.reflections.util;

import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;
import org.reflections.*;
import java.util.regex.*;

public class FilterBuilder implements Predicate<String>
{
    private final List<Predicate<String>> chain;
    
    public FilterBuilder() {
        super();
        this.chain = (List<Predicate<String>>)Lists.newArrayList();
    }
    
    private FilterBuilder(final Iterable<Predicate<String>> a1) {
        super();
        this.chain = (List<Predicate<String>>)Lists.newArrayList((Iterable<?>)a1);
    }
    
    public FilterBuilder include(final String a1) {
        return this.add(new Include(a1));
    }
    
    public FilterBuilder exclude(final String a1) {
        this.add(new Exclude(a1));
        return this;
    }
    
    public FilterBuilder add(final Predicate<String> a1) {
        this.chain.add(a1);
        return this;
    }
    
    public FilterBuilder includePackage(final Class<?> a1) {
        return this.add(new Include(packageNameRegex(a1)));
    }
    
    public FilterBuilder excludePackage(final Class<?> a1) {
        return this.add(new Exclude(packageNameRegex(a1)));
    }
    
    public FilterBuilder includePackage(final String... v2) {
        for (final String a1 : v2) {
            this.add(new Include(prefix(a1)));
        }
        return this;
    }
    
    public FilterBuilder excludePackage(final String a1) {
        return this.add(new Exclude(prefix(a1)));
    }
    
    private static String packageNameRegex(final Class<?> a1) {
        return prefix(a1.getPackage().getName() + ".");
    }
    
    public static String prefix(final String a1) {
        return a1.replace(".", "\\.") + ".*";
    }
    
    @Override
    public String toString() {
        return Joiner.on(", ").join(this.chain);
    }
    
    @Override
    public boolean apply(final String v2) {
        boolean v3 = this.chain == null || this.chain.isEmpty() || this.chain.get(0) instanceof Exclude;
        if (this.chain != null) {
            for (final Predicate<String> a1 : this.chain) {
                if (v3 && a1 instanceof Include) {
                    continue;
                }
                if (!v3 && a1 instanceof Exclude) {
                    continue;
                }
                v3 = a1.apply(v2);
                if (!v3 && a1 instanceof Exclude) {
                    break;
                }
            }
        }
        return v3;
    }
    
    public static FilterBuilder parse(final String v-8) {
        final List<Predicate<String>> a2 = new ArrayList<Predicate<String>>();
        if (!Utils.isEmpty(v-8)) {
            for (final String s : v-8.split(",")) {
                final String trim = s.trim();
                final char char1 = trim.charAt(0);
                final String v0 = trim.substring(1);
                final Predicate<String> v2;
                switch (char1) {
                    case '+': {
                        final Predicate<String> a1 = new Include(v0);
                        break;
                    }
                    case '-': {
                        v2 = new Exclude(v0);
                        break;
                    }
                    default: {
                        throw new ReflectionsException("includeExclude should start with either + or -");
                    }
                }
                a2.add(v2);
            }
            return new FilterBuilder(a2);
        }
        return new FilterBuilder();
    }
    
    public static FilterBuilder parsePackages(final String v-8) {
        final List<Predicate<String>> a2 = new ArrayList<Predicate<String>>();
        if (!Utils.isEmpty(v-8)) {
            for (final String s : v-8.split(",")) {
                final String trim = s.trim();
                final char char1 = trim.charAt(0);
                String v0 = trim.substring(1);
                if (!v0.endsWith(".")) {
                    v0 += ".";
                }
                v0 = prefix(v0);
                final Predicate<String> v2;
                switch (char1) {
                    case '+': {
                        final Predicate<String> a1 = new Include(v0);
                        break;
                    }
                    case '-': {
                        v2 = new Exclude(v0);
                        break;
                    }
                    default: {
                        throw new ReflectionsException("includeExclude should start with either + or -");
                    }
                }
                a2.add(v2);
            }
            return new FilterBuilder(a2);
        }
        return new FilterBuilder();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object o) {
        return this.apply((String)o);
    }
    
    public abstract static class Matcher implements Predicate<String>
    {
        final Pattern pattern;
        
        public Matcher(final String a1) {
            super();
            this.pattern = Pattern.compile(a1);
        }
        
        @Override
        public abstract boolean apply(final String p0);
        
        @Override
        public String toString() {
            return this.pattern.pattern();
        }
        
        @Override
        public /* bridge */ boolean apply(final Object o) {
            return this.apply((String)o);
        }
    }
    
    public static class Include extends Matcher
    {
        public Include(final String a1) {
            super(a1);
        }
        
        @Override
        public boolean apply(final String a1) {
            return this.pattern.matcher(a1).matches();
        }
        
        @Override
        public String toString() {
            return "+" + super.toString();
        }
        
        @Override
        public /* bridge */ boolean apply(final Object o) {
            return this.apply((String)o);
        }
    }
    
    public static class Exclude extends Matcher
    {
        public Exclude(final String a1) {
            super(a1);
        }
        
        @Override
        public boolean apply(final String a1) {
            return !this.pattern.matcher(a1).matches();
        }
        
        @Override
        public String toString() {
            return "-" + super.toString();
        }
        
        @Override
        public /* bridge */ boolean apply(final Object o) {
            return this.apply((String)o);
        }
    }
}
