package org.reflections.scanners;

import org.reflections.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import org.reflections.vfs.*;
import javax.annotation.*;

public interface Scanner
{
    void setConfiguration(final Configuration p0);
    
    Multimap<String, String> getStore();
    
    void setStore(final Multimap<String, String> p0);
    
    Scanner filterResultsBy(final Predicate<String> p0);
    
    boolean acceptsInput(final String p0);
    
    Object scan(final Vfs.File p0, @Nullable final Object p1);
    
    boolean acceptResult(final String p0);
}
