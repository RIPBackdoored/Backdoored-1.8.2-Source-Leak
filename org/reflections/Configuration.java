package org.reflections;

import java.util.*;
import org.reflections.scanners.*;
import java.net.*;
import org.reflections.adapters.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.concurrent.*;
import org.reflections.serializers.*;

public interface Configuration
{
    Set<Scanner> getScanners();
    
    Set<URL> getUrls();
    
    MetadataAdapter getMetadataAdapter();
    
    @Nullable
    Predicate<String> getInputsFilter();
    
    ExecutorService getExecutorService();
    
    Serializer getSerializer();
    
    @Nullable
    ClassLoader[] getClassLoaders();
    
    boolean shouldExpandSuperTypes();
}
