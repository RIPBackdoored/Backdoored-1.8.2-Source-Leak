package org.reflections.util;

import java.net.*;
import com.google.common.base.*;
import javax.annotation.*;
import org.reflections.scanners.*;
import org.reflections.*;
import java.util.*;
import org.reflections.adapters.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import org.reflections.serializers.*;
import com.google.common.collect.*;

public class ConfigurationBuilder implements Configuration
{
    @Nonnull
    private Set<Scanner> scanners;
    @Nonnull
    private Set<URL> urls;
    protected MetadataAdapter metadataAdapter;
    @Nullable
    private Predicate<String> inputsFilter;
    private Serializer serializer;
    @Nullable
    private ExecutorService executorService;
    @Nullable
    private ClassLoader[] classLoaders;
    private boolean expandSuperTypes;
    
    public ConfigurationBuilder() {
        super();
        this.expandSuperTypes = true;
        this.scanners = (Set<Scanner>)Sets.newHashSet((Object[])new Scanner[] { new TypeAnnotationsScanner(), new SubTypesScanner() });
        this.urls = (Set<URL>)Sets.newHashSet();
    }
    
    public static ConfigurationBuilder build(@Nullable final Object... v-7) {
        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        final List<Object> arrayList = Lists.newArrayList();
        if (v-7 != null) {
            for (final Object o : v-7) {
                if (o != null) {
                    if (o.getClass().isArray()) {
                        for (final Object a1 : (Object[])o) {
                            if (a1 != null) {
                                arrayList.add(a1);
                            }
                        }
                    }
                    else if (o instanceof Iterable) {
                        for (final Object v1 : (Iterable)o) {
                            if (v1 != null) {
                                arrayList.add(v1);
                            }
                        }
                    }
                    else {
                        arrayList.add(o);
                    }
                }
            }
        }
        final List<ClassLoader> arrayList2 = (List<ClassLoader>)Lists.newArrayList();
        for (final Object next : arrayList) {
            if (next instanceof ClassLoader) {
                arrayList2.add((ClassLoader)next);
            }
        }
        final ClassLoader[] v-8 = (ClassLoader[])(arrayList2.isEmpty() ? null : ((ClassLoader[])arrayList2.toArray(new ClassLoader[arrayList2.size()])));
        final FilterBuilder a2 = new FilterBuilder();
        final List<Scanner> arrayList3 = (List<Scanner>)Lists.newArrayList();
        for (final Object v1 : arrayList) {
            if (v1 instanceof String) {
                configurationBuilder.addUrls(ClasspathHelper.forPackage((String)v1, v-8));
                a2.includePackage((String)v1);
            }
            else if (v1 instanceof Class) {
                if (Scanner.class.isAssignableFrom((Class<?>)v1)) {
                    try {
                        configurationBuilder.addScanners(((Class)v1).newInstance());
                    }
                    catch (Exception ex) {}
                }
                configurationBuilder.addUrls(ClasspathHelper.forClass((Class<?>)v1, v-8));
                a2.includePackage((Class<?>)v1);
            }
            else if (v1 instanceof Scanner) {
                arrayList3.add((Scanner)v1);
            }
            else if (v1 instanceof URL) {
                configurationBuilder.addUrls((URL)v1);
            }
            else {
                if (v1 instanceof ClassLoader) {
                    continue;
                }
                if (v1 instanceof Predicate) {
                    a2.add((Predicate<String>)v1);
                }
                else if (v1 instanceof ExecutorService) {
                    configurationBuilder.setExecutorService((ExecutorService)v1);
                }
                else {
                    if (Reflections.log != null) {
                        throw new ReflectionsException("could not use param " + v1);
                    }
                    continue;
                }
            }
        }
        if (configurationBuilder.getUrls().isEmpty()) {
            if (v-8 != null) {
                configurationBuilder.addUrls(ClasspathHelper.forClassLoader(v-8));
            }
            else {
                configurationBuilder.addUrls(ClasspathHelper.forClassLoader());
            }
        }
        configurationBuilder.filterInputsBy(a2);
        if (!arrayList3.isEmpty()) {
            configurationBuilder.setScanners((Scanner[])arrayList3.toArray(new Scanner[arrayList3.size()]));
        }
        if (!arrayList2.isEmpty()) {
            configurationBuilder.addClassLoaders(arrayList2);
        }
        return configurationBuilder;
    }
    
    public ConfigurationBuilder forPackages(final String... v2) {
        for (final String a1 : v2) {
            this.addUrls(ClasspathHelper.forPackage(a1, new ClassLoader[0]));
        }
        return this;
    }
    
    @Nonnull
    @Override
    public Set<Scanner> getScanners() {
        return this.scanners;
    }
    
    public ConfigurationBuilder setScanners(@Nonnull final Scanner... a1) {
        this.scanners.clear();
        return this.addScanners(a1);
    }
    
    public ConfigurationBuilder addScanners(final Scanner... a1) {
        this.scanners.addAll(Sets.newHashSet((Object[])a1));
        return this;
    }
    
    @Nonnull
    @Override
    public Set<URL> getUrls() {
        return this.urls;
    }
    
    public ConfigurationBuilder setUrls(@Nonnull final Collection<URL> a1) {
        this.urls = (Set<URL>)Sets.newHashSet((Iterable<?>)a1);
        return this;
    }
    
    public ConfigurationBuilder setUrls(final URL... a1) {
        this.urls = (Set<URL>)Sets.newHashSet((Object[])a1);
        return this;
    }
    
    public ConfigurationBuilder addUrls(final Collection<URL> a1) {
        this.urls.addAll(a1);
        return this;
    }
    
    public ConfigurationBuilder addUrls(final URL... a1) {
        this.urls.addAll(Sets.newHashSet((Object[])a1));
        return this;
    }
    
    @Override
    public MetadataAdapter getMetadataAdapter() {
        if (this.metadataAdapter != null) {
            return this.metadataAdapter;
        }
        try {
            return this.metadataAdapter = new JavassistAdapter();
        }
        catch (Throwable v1) {
            if (Reflections.log != null) {
                Reflections.log.warn("could not create JavassistAdapter, using JavaReflectionAdapter", v1);
            }
            return this.metadataAdapter = new JavaReflectionAdapter();
        }
    }
    
    public ConfigurationBuilder setMetadataAdapter(final MetadataAdapter a1) {
        this.metadataAdapter = a1;
        return this;
    }
    
    @Nullable
    @Override
    public Predicate<String> getInputsFilter() {
        return this.inputsFilter;
    }
    
    public void setInputsFilter(@Nullable final Predicate<String> a1) {
        this.inputsFilter = a1;
    }
    
    public ConfigurationBuilder filterInputsBy(final Predicate<String> a1) {
        this.inputsFilter = a1;
        return this;
    }
    
    @Nullable
    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
    }
    
    public ConfigurationBuilder setExecutorService(@Nullable final ExecutorService a1) {
        this.executorService = a1;
        return this;
    }
    
    public ConfigurationBuilder useParallelExecutor() {
        return this.useParallelExecutor(Runtime.getRuntime().availableProcessors());
    }
    
    public ConfigurationBuilder useParallelExecutor(final int a1) {
        final ThreadFactory v1 = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("org.reflections-scanner-%d").build();
        this.setExecutorService(Executors.newFixedThreadPool(a1, v1));
        return this;
    }
    
    @Override
    public Serializer getSerializer() {
        return (this.serializer != null) ? this.serializer : (this.serializer = new XmlSerializer());
    }
    
    public ConfigurationBuilder setSerializer(final Serializer a1) {
        this.serializer = a1;
        return this;
    }
    
    @Nullable
    @Override
    public ClassLoader[] getClassLoaders() {
        return this.classLoaders;
    }
    
    @Override
    public boolean shouldExpandSuperTypes() {
        return this.expandSuperTypes;
    }
    
    public ConfigurationBuilder setExpandSuperTypes(final boolean a1) {
        this.expandSuperTypes = a1;
        return this;
    }
    
    public void setClassLoaders(@Nullable final ClassLoader[] a1) {
        this.classLoaders = a1;
    }
    
    public ConfigurationBuilder addClassLoader(final ClassLoader a1) {
        return this.addClassLoaders(a1);
    }
    
    public ConfigurationBuilder addClassLoaders(final ClassLoader... a1) {
        this.classLoaders = (ClassLoader[])((this.classLoaders == null) ? a1 : ObjectArrays.concat((Object[])this.classLoaders, (Object[])a1, (Class)ClassLoader.class));
        return this;
    }
    
    public ConfigurationBuilder addClassLoaders(final Collection<ClassLoader> a1) {
        return this.addClassLoaders((ClassLoader[])a1.toArray(new ClassLoader[a1.size()]));
    }
}
