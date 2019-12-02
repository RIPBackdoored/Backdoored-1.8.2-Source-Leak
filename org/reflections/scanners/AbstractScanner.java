package org.reflections.scanners;

import com.google.common.collect.*;
import com.google.common.base.*;
import org.reflections.vfs.*;
import org.reflections.*;
import org.reflections.adapters.*;

public abstract class AbstractScanner implements Scanner
{
    private Configuration configuration;
    private Multimap<String, String> store;
    private Predicate<String> resultFilter;
    
    public AbstractScanner() {
        super();
        this.resultFilter = (Predicate<String>)Predicates.alwaysTrue();
    }
    
    @Override
    public boolean acceptsInput(final String a1) {
        return this.getMetadataAdapter().acceptsInput(a1);
    }
    
    @Override
    public Object scan(final Vfs.File v1, Object v2) {
        if (v2 == null) {
            try {
                v2 = this.configuration.getMetadataAdapter().getOfCreateClassObject(v1);
            }
            catch (Exception a1) {
                throw new ReflectionsException("could not create class object from file " + v1.getRelativePath(), a1);
            }
        }
        this.scan(v2);
        return v2;
    }
    
    public abstract void scan(final Object p0);
    
    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    @Override
    public void setConfiguration(final Configuration a1) {
        this.configuration = a1;
    }
    
    @Override
    public Multimap<String, String> getStore() {
        return this.store;
    }
    
    @Override
    public void setStore(final Multimap<String, String> a1) {
        this.store = a1;
    }
    
    public Predicate<String> getResultFilter() {
        return this.resultFilter;
    }
    
    public void setResultFilter(final Predicate<String> a1) {
        this.resultFilter = a1;
    }
    
    @Override
    public Scanner filterResultsBy(final Predicate<String> a1) {
        this.setResultFilter(a1);
        return this;
    }
    
    @Override
    public boolean acceptResult(final String a1) {
        return a1 != null && this.resultFilter.apply(a1);
    }
    
    protected MetadataAdapter getMetadataAdapter() {
        return this.configuration.getMetadataAdapter();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return this == a1 || (a1 != null && this.getClass() == a1.getClass());
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
