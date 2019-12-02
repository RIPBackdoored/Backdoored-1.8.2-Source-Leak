package org.reflections;

import org.slf4j.*;
import javax.annotation.*;
import java.net.*;
import java.util.concurrent.*;
import org.reflections.vfs.*;
import org.reflections.serializers.*;
import org.reflections.util.*;
import java.io.*;
import java.util.*;
import java.lang.annotation.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.regex.*;
import java.lang.reflect.*;
import org.reflections.scanners.*;

public class Reflections
{
    @Nullable
    public static Logger log;
    protected final transient Configuration configuration;
    protected Store store;
    
    public Reflections(final Configuration v2) {
        super();
        this.configuration = v2;
        this.store = new Store(v2);
        if (v2.getScanners() != null && !v2.getScanners().isEmpty()) {
            for (final Scanner a1 : v2.getScanners()) {
                a1.setConfiguration(v2);
                a1.setStore(this.store.getOrCreate(a1.getClass().getSimpleName()));
            }
            this.scan();
            if (v2.shouldExpandSuperTypes()) {
                this.expandSuperTypes();
            }
        }
    }
    
    public Reflections(final String a1, @Nullable final Scanner... a2) {
        this(new Object[] { a1, a2 });
    }
    
    public Reflections(final Object... a1) {
        this(ConfigurationBuilder.build(a1));
    }
    
    protected Reflections() {
        super();
        this.configuration = new ConfigurationBuilder();
        this.store = new Store(this.configuration);
    }
    
    protected void scan() {
        if (this.configuration.getUrls() == null || this.configuration.getUrls().isEmpty()) {
            if (Reflections.log != null) {
                Reflections.log.warn("given scan urls are empty. set urls in the configuration");
            }
            return;
        }
        if (Reflections.log != null && Reflections.log.isDebugEnabled()) {
            Reflections.log.debug("going to scan these urls:\n" + Joiner.on("\n").join(this.configuration.getUrls()));
        }
        long currentTimeMillis = System.currentTimeMillis();
        int n = 0;
        final ExecutorService executorService = this.configuration.getExecutorService();
        final List<Future<?>> arrayList = (List<Future<?>>)Lists.newArrayList();
        for (final URL v0 : this.configuration.getUrls()) {
            try {
                if (executorService != null) {
                    arrayList.add(executorService.submit(new Runnable() {
                        final /* synthetic */ URL val$url;
                        final /* synthetic */ Reflections this$0;
                        
                        Reflections$1() {
                            this.this$0 = a1;
                            super();
                        }
                        
                        @Override
                        public void run() {
                            if (Reflections.log != null && Reflections.log.isDebugEnabled()) {
                                Reflections.log.debug("[" + Thread.currentThread().toString() + "] scanning " + v0);
                            }
                            this.this$0.scan(v0);
                        }
                    }));
                }
                else {
                    this.scan(v0);
                }
                ++n;
            }
            catch (ReflectionsException v2) {
                if (Reflections.log == null || !Reflections.log.isWarnEnabled()) {
                    continue;
                }
                Reflections.log.warn("could not create Vfs.Dir from url. ignoring the exception and continuing", v2);
            }
        }
        if (executorService != null) {
            for (final Future v3 : arrayList) {
                try {
                    v3.get();
                }
                catch (Exception v4) {
                    throw new RuntimeException(v4);
                }
            }
        }
        currentTimeMillis = System.currentTimeMillis() - currentTimeMillis;
        if (executorService != null) {
            executorService.shutdown();
        }
        if (Reflections.log != null) {
            int n2 = 0;
            int v5 = 0;
            for (final String v6 : this.store.keySet()) {
                n2 += this.store.get(v6).keySet().size();
                v5 += this.store.get(v6).size();
            }
            Reflections.log.info(String.format("Reflections took %d ms to scan %d urls, producing %d keys and %d values %s", currentTimeMillis, n, n2, v5, (executorService != null && executorService instanceof ThreadPoolExecutor) ? String.format("[using %d cores]", ((ThreadPoolExecutor)executorService).getMaximumPoolSize()) : ""));
        }
    }
    
    protected void scan(final URL v-8) {
        final Vfs.Dir fromURL = Vfs.fromURL(v-8);
        try {
            for (final Vfs.File file : fromURL.getFiles()) {
                final Predicate<String> inputsFilter = this.configuration.getInputsFilter();
                final String relativePath = file.getRelativePath();
                final String replace = relativePath.replace('/', '.');
                if (inputsFilter == null || inputsFilter.apply(relativePath) || inputsFilter.apply(replace)) {
                    Object scan = null;
                    for (final Scanner v1 : this.configuration.getScanners()) {
                        try {
                            if (!v1.acceptsInput(relativePath) && !v1.acceptResult(replace)) {
                                continue;
                            }
                            scan = v1.scan(file, scan);
                        }
                        catch (Exception a1) {
                            if (Reflections.log == null || !Reflections.log.isDebugEnabled()) {
                                continue;
                            }
                            Reflections.log.debug("could not scan file " + file.getRelativePath() + " in url " + v-8.toExternalForm() + " with scanner " + v1.getClass().getSimpleName(), a1);
                        }
                    }
                }
            }
        }
        finally {
            fromURL.close();
        }
    }
    
    public static Reflections collect() {
        return collect("META-INF/reflections/", new FilterBuilder().include(".*-reflections.xml"), new Serializer[0]);
    }
    
    public static Reflections collect(final String v-12, final Predicate<String> v-11, @Nullable final Serializer... v-10) {
        final Serializer serializer = (v-10 != null && v-10.length == 1) ? v-10[0] : new XmlSerializer();
        final Collection<URL> forPackage = ClasspathHelper.forPackage(v-12, new ClassLoader[0]);
        if (forPackage.isEmpty()) {
            return null;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final Reflections reflections = new Reflections();
        final Iterable<Vfs.File> files = Vfs.findFiles(forPackage, v-12, v-11);
        for (final Vfs.File a3 : files) {
            InputStream a4 = null;
            try {
                a4 = a3.openInputStream();
                reflections.merge(serializer.read(a4));
            }
            catch (IOException a5) {
                throw new ReflectionsException("could not merge " + a3, a5);
            }
            finally {
                Utils.close(a4);
            }
        }
        if (Reflections.log != null) {
            final Store store = reflections.getStore();
            int n = 0;
            int n2 = 0;
            for (final String v1 : store.keySet()) {
                n += store.get(v1).keySet().size();
                n2 += store.get(v1).size();
            }
            Reflections.log.info(String.format("Reflections took %d ms to collect %d url%s, producing %d keys and %d values [%s]", System.currentTimeMillis() - currentTimeMillis, forPackage.size(), (forPackage.size() > 1) ? "s" : "", n, n2, Joiner.on(", ").join(forPackage)));
        }
        return reflections;
    }
    
    public Reflections collect(final InputStream v2) {
        try {
            this.merge(this.configuration.getSerializer().read(v2));
            if (Reflections.log != null) {
                Reflections.log.info("Reflections collected metadata from input stream using serializer " + this.configuration.getSerializer().getClass().getName());
            }
        }
        catch (Exception a1) {
            throw new ReflectionsException("could not merge input stream", a1);
        }
        return this;
    }
    
    public Reflections collect(final File v2) {
        FileInputStream v3 = null;
        try {
            v3 = new FileInputStream(v2);
            return this.collect(v3);
        }
        catch (FileNotFoundException a1) {
            throw new ReflectionsException("could not obtain input stream from file " + v2, a1);
        }
        finally {
            Utils.close(v3);
        }
    }
    
    public Reflections merge(final Reflections v-4) {
        if (v-4.store != null) {
            for (final String s : v-4.store.keySet()) {
                final Multimap<String, String> value = v-4.store.get(s);
                for (final String v1 : value.keySet()) {
                    for (final String a1 : value.get(v1)) {
                        this.store.getOrCreate(s).put((Object)v1, (Object)a1);
                    }
                }
            }
        }
        return this;
    }
    
    public void expandSuperTypes() {
        if (this.store.keySet().contains(index(SubTypesScanner.class))) {
            final Multimap<String, String> value = this.store.get(index(SubTypesScanner.class));
            final Sets.SetView<String> difference = (Sets.SetView<String>)Sets.difference(value.keySet(), (Set)Sets.newHashSet((Iterable<?>)value.values()));
            final Multimap<String, String> create = (Multimap<String, String>)HashMultimap.create();
            for (final String v0 : difference) {
                final Class<?> v2 = ReflectionUtils.forName(v0, new ClassLoader[0]);
                if (v2 != null) {
                    this.expandSupertypes(create, v0, v2);
                }
            }
            value.putAll((Multimap)create);
        }
    }
    
    private void expandSupertypes(final Multimap<String, String> a3, final String v1, final Class<?> v2) {
        for (final Class<?> a4 : ReflectionUtils.getSuperTypes(v2)) {
            if (a3.put((Object)a4.getName(), (Object)v1)) {
                if (Reflections.log != null) {
                    Reflections.log.debug("expanded subtype {} -> {}", a4.getName(), v1);
                }
                this.expandSupertypes(a3, a4.getName(), a4);
            }
        }
    }
    
    public <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> a1) {
        return (Set<Class<? extends T>>)Sets.newHashSet((Iterable<?>)ReflectionUtils.forNames(this.store.getAll(index(SubTypesScanner.class), Arrays.asList(a1.getName())), this.loaders()));
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> a1) {
        return this.getTypesAnnotatedWith(a1, false);
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> a1, final boolean a2) {
        final Iterable<String> v1 = this.store.get(index(TypeAnnotationsScanner.class), a1.getName());
        final Iterable<String> v2 = this.getAllAnnotated(v1, a1.isAnnotationPresent(Inherited.class), a2);
        return (Set<Class<?>>)Sets.newHashSet(Iterables.concat((Iterable<?>)ReflectionUtils.forNames(v1, this.loaders()), (Iterable<?>)ReflectionUtils.forNames(v2, this.loaders())));
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Annotation a1) {
        return this.getTypesAnnotatedWith(a1, false);
    }
    
    public Set<Class<?>> getTypesAnnotatedWith(final Annotation a1, final boolean a2) {
        final Iterable<String> v1 = this.store.get(index(TypeAnnotationsScanner.class), a1.annotationType().getName());
        final Iterable<Class<?>> v2 = ReflectionUtils.filter(ReflectionUtils.forNames(v1, this.loaders()), ReflectionUtils.withAnnotation(a1));
        final Iterable<String> v3 = this.getAllAnnotated(Utils.names(v2), a1.annotationType().isAnnotationPresent(Inherited.class), a2);
        return (Set<Class<?>>)Sets.newHashSet(Iterables.concat((Iterable<?>)v2, (Iterable<?>)ReflectionUtils.forNames(ReflectionUtils.filter(v3, Predicates.not((Predicate)Predicates.in((Collection<?>)Sets.newHashSet((Iterable<?>)v1)))), this.loaders())));
    }
    
    protected Iterable<String> getAllAnnotated(final Iterable<String> v1, final boolean v2, final boolean v3) {
        if (!v3) {
            final Iterable<String> a2 = Iterables.concat((Iterable<? extends String>)v1, (Iterable<? extends String>)this.store.getAll(index(TypeAnnotationsScanner.class), v1));
            return Iterables.concat((Iterable<? extends String>)a2, (Iterable<? extends String>)this.store.getAll(index(SubTypesScanner.class), a2));
        }
        if (v2) {
            final Iterable<String> a3 = this.store.get(index(SubTypesScanner.class), ReflectionUtils.filter(v1, new Predicate<String>() {
                final /* synthetic */ Reflections this$0;
                
                Reflections$2() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public boolean apply(@Nullable final String a1) {
                    final Class<?> v1 = ReflectionUtils.forName(a1, Reflections.this.loaders());
                    return v1 != null && !v1.isInterface();
                }
                
                @Override
                public /* bridge */ boolean apply(@Nullable final Object o) {
                    return this.apply((String)o);
                }
            }));
            return Iterables.concat((Iterable<? extends String>)a3, (Iterable<? extends String>)this.store.getAll(index(SubTypesScanner.class), a3));
        }
        return v1;
    }
    
    public Set<Method> getMethodsAnnotatedWith(final Class<? extends Annotation> a1) {
        final Iterable<String> v1 = this.store.get(index(MethodAnnotationsScanner.class), a1.getName());
        return Utils.getMethodsFromDescriptors(v1, this.loaders());
    }
    
    public Set<Method> getMethodsAnnotatedWith(final Annotation a1) {
        return ReflectionUtils.filter(this.getMethodsAnnotatedWith(a1.annotationType()), ReflectionUtils.withAnnotation(a1));
    }
    
    public Set<Method> getMethodsMatchParams(final Class<?>... a1) {
        return Utils.getMethodsFromDescriptors(this.store.get(index(MethodParameterScanner.class), Utils.names(a1).toString()), this.loaders());
    }
    
    public Set<Method> getMethodsReturn(final Class a1) {
        return Utils.getMethodsFromDescriptors(this.store.get(index(MethodParameterScanner.class), Utils.names(a1)), this.loaders());
    }
    
    public Set<Method> getMethodsWithAnyParamAnnotated(final Class<? extends Annotation> a1) {
        return Utils.getMethodsFromDescriptors(this.store.get(index(MethodParameterScanner.class), a1.getName()), this.loaders());
    }
    
    public Set<Method> getMethodsWithAnyParamAnnotated(final Annotation a1) {
        return ReflectionUtils.filter(this.getMethodsWithAnyParamAnnotated(a1.annotationType()), ReflectionUtils.withAnyParameterAnnotation(a1));
    }
    
    public Set<Constructor> getConstructorsAnnotatedWith(final Class<? extends Annotation> a1) {
        final Iterable<String> v1 = this.store.get(index(MethodAnnotationsScanner.class), a1.getName());
        return Utils.getConstructorsFromDescriptors(v1, this.loaders());
    }
    
    public Set<Constructor> getConstructorsAnnotatedWith(final Annotation a1) {
        return (Set<Constructor>)ReflectionUtils.filter(this.getConstructorsAnnotatedWith(a1.annotationType()), ReflectionUtils.withAnnotation(a1));
    }
    
    public Set<Constructor> getConstructorsMatchParams(final Class<?>... a1) {
        return Utils.getConstructorsFromDescriptors(this.store.get(index(MethodParameterScanner.class), Utils.names(a1).toString()), this.loaders());
    }
    
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(final Class<? extends Annotation> a1) {
        return Utils.getConstructorsFromDescriptors(this.store.get(index(MethodParameterScanner.class), a1.getName()), this.loaders());
    }
    
    public Set<Constructor> getConstructorsWithAnyParamAnnotated(final Annotation a1) {
        return (Set<Constructor>)ReflectionUtils.filter(this.getConstructorsWithAnyParamAnnotated(a1.annotationType()), ReflectionUtils.withAnyParameterAnnotation(a1));
    }
    
    public Set<Field> getFieldsAnnotatedWith(final Class<? extends Annotation> v2) {
        final Set<Field> v3 = (Set<Field>)Sets.newHashSet();
        for (final String a1 : this.store.get(index(FieldAnnotationsScanner.class), v2.getName())) {
            v3.add(Utils.getFieldFromString(a1, this.loaders()));
        }
        return v3;
    }
    
    public Set<Field> getFieldsAnnotatedWith(final Annotation a1) {
        return ReflectionUtils.filter(this.getFieldsAnnotatedWith(a1.annotationType()), ReflectionUtils.withAnnotation(a1));
    }
    
    public Set<String> getResources(final Predicate<String> a1) {
        final Iterable<String> v1 = Iterables.filter((Iterable<String>)this.store.get(index(ResourcesScanner.class)).keySet(), a1);
        return (Set<String>)Sets.newHashSet((Iterable<?>)this.store.get(index(ResourcesScanner.class), v1));
    }
    
    public Set<String> getResources(final Pattern a1) {
        return this.getResources(new Predicate<String>() {
            final /* synthetic */ Pattern val$pattern;
            final /* synthetic */ Reflections this$0;
            
            Reflections$3() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public boolean apply(final String a1) {
                return a1.matcher(a1).matches();
            }
            
            @Override
            public /* bridge */ boolean apply(final Object o) {
                return this.apply((String)o);
            }
        });
    }
    
    public List<String> getMethodParamNames(final Method a1) {
        final Iterable<String> v1 = this.store.get(index(MethodParameterNamesScanner.class), Utils.name(a1));
        return Iterables.isEmpty(v1) ? Arrays.asList(new String[0]) : Arrays.asList(Iterables.getOnlyElement(v1).split(", "));
    }
    
    public List<String> getConstructorParamNames(final Constructor a1) {
        final Iterable<String> v1 = this.store.get(index(MethodParameterNamesScanner.class), Utils.name(a1));
        return Iterables.isEmpty(v1) ? Arrays.asList(new String[0]) : Arrays.asList(Iterables.getOnlyElement(v1).split(", "));
    }
    
    public Set<Member> getFieldUsage(final Field a1) {
        return Utils.getMembersFromDescriptors(this.store.get(index(MemberUsageScanner.class), Utils.name(a1)), new ClassLoader[0]);
    }
    
    public Set<Member> getMethodUsage(final Method a1) {
        return Utils.getMembersFromDescriptors(this.store.get(index(MemberUsageScanner.class), Utils.name(a1)), new ClassLoader[0]);
    }
    
    public Set<Member> getConstructorUsage(final Constructor a1) {
        return Utils.getMembersFromDescriptors(this.store.get(index(MemberUsageScanner.class), Utils.name(a1)), new ClassLoader[0]);
    }
    
    public Set<String> getAllTypes() {
        final Set<String> v1 = (Set<String>)Sets.newHashSet((Iterable<?>)this.store.getAll(index(SubTypesScanner.class), Object.class.getName()));
        if (v1.isEmpty()) {
            throw new ReflectionsException("Couldn't find subtypes of Object. Make sure SubTypesScanner initialized to include Object class - new SubTypesScanner(false)");
        }
        return v1;
    }
    
    public Store getStore() {
        return this.store;
    }
    
    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public File save(final String a1) {
        return this.save(a1, this.configuration.getSerializer());
    }
    
    public File save(final String a1, final Serializer a2) {
        final File v1 = a2.save(this, a1);
        if (Reflections.log != null) {
            Reflections.log.info("Reflections successfully saved in " + v1.getAbsolutePath() + " using " + a2.getClass().getSimpleName());
        }
        return v1;
    }
    
    private static String index(final Class<? extends Scanner> a1) {
        return a1.getSimpleName();
    }
    
    private ClassLoader[] loaders() {
        return this.configuration.getClassLoaders();
    }
    
    static /* bridge */ ClassLoader[] access$000(final Reflections a1) {
        return a1.loaders();
    }
    
    static {
        Reflections.log = Utils.findLogger(Reflections.class);
    }
}
