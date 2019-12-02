package org.reflections.serializers;

import org.reflections.util.*;
import java.nio.charset.*;
import com.google.common.io.*;
import java.io.*;
import org.reflections.scanners.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;
import java.lang.annotation.*;
import org.reflections.*;
import java.lang.reflect.*;

public class JavaCodeSerializer implements Serializer
{
    private static final String pathSeparator = "_";
    private static final String doubleSeparator = "__";
    private static final String dotSeparator = ".";
    private static final String arrayDescriptor = "$$";
    private static final String tokenSeparator = "_";
    
    public JavaCodeSerializer() {
        super();
    }
    
    @Override
    public Reflections read(final InputStream a1) {
        throw new UnsupportedOperationException("read is not implemented on JavaCodeSerializer");
    }
    
    @Override
    public File save(final Reflections v-6, String v-5) {
        if (v-5.endsWith("/")) {
            v-5 = v-5.substring(0, v-5.length() - 1);
        }
        final String concat = v-5.replace('.', '/').concat(".java");
        final File prepareFile = Utils.prepareFile(concat);
        final int v0 = v-5.lastIndexOf(46);
        String substring = null;
        String substring2 = null;
        if (v0 == -1) {
            final String a1 = "";
            final String a2 = v-5.substring(v-5.lastIndexOf(47) + 1);
        }
        else {
            substring = v-5.substring(v-5.lastIndexOf(47) + 1, v0);
            substring2 = v-5.substring(v0 + 1);
        }
        try {
            final StringBuilder v2 = new StringBuilder();
            v2.append("//generated using Reflections JavaCodeSerializer").append(" [").append(new Date()).append("]").append("\n");
            if (substring.length() != 0) {
                v2.append("package ").append(substring).append(";\n");
                v2.append("\n");
            }
            v2.append("public interface ").append(substring2).append(" {\n\n");
            v2.append(this.toString(v-6));
            v2.append("}\n");
            Files.write((CharSequence)v2.toString(), new File(concat), Charset.defaultCharset());
        }
        catch (IOException v3) {
            throw new RuntimeException();
        }
        return prepareFile;
    }
    
    @Override
    public String toString(final Reflections v-8) {
        if (v-8.getStore().get(TypeElementsScanner.class.getSimpleName()).isEmpty() && Reflections.log != null) {
            Reflections.log.warn("JavaCodeSerializer needs TypeElementsScanner configured");
        }
        final StringBuilder sb = new StringBuilder();
        List<String> arrayList = (List<String>)Lists.newArrayList();
        int v15 = 1;
        final List<String> arrayList2 = (List<String>)Lists.newArrayList((Iterable<?>)v-8.getStore().get(TypeElementsScanner.class.getSimpleName()).keySet());
        Collections.sort(arrayList2);
        for (final String s : arrayList2) {
            List<String> arrayList3;
            int v0;
            for (arrayList3 = Lists.newArrayList(s.split("\\.")), v0 = 0; v0 < Math.min(arrayList3.size(), arrayList.size()) && arrayList3.get(v0).equals(arrayList.get(v0)); ++v0) {}
            for (int a1 = arrayList.size(); a1 > v0; --a1) {
                sb.append(Utils.repeat("\t", --v15)).append("}\n");
            }
            for (int v2 = v0; v2 < arrayList3.size() - 1; ++v2) {
                sb.append(Utils.repeat("\t", v15++)).append("public interface ").append(this.getNonDuplicateName(arrayList3.get(v2), arrayList3, v2)).append(" {\n");
            }
            final String v3 = arrayList3.get(arrayList3.size() - 1);
            final List<String> v4 = (List<String>)Lists.newArrayList();
            final List<String> v5 = (List<String>)Lists.newArrayList();
            final Multimap<String, String> v6 = (Multimap<String, String>)Multimaps.newSetMultimap((Map)new HashMap(), (Supplier)new Supplier<Set<String>>() {
                final /* synthetic */ JavaCodeSerializer this$0;
                
                JavaCodeSerializer$1() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Set<String> get() {
                    return (Set<String>)Sets.newHashSet();
                }
                
                @Override
                public /* bridge */ Object get() {
                    return this.get();
                }
            });
            for (final String v7 : v-8.getStore().get(TypeElementsScanner.class.getSimpleName(), s)) {
                if (v7.startsWith("@")) {
                    v4.add(v7.substring(1));
                }
                else if (v7.contains("(")) {
                    if (v7.startsWith("<")) {
                        continue;
                    }
                    final int v8 = v7.indexOf(40);
                    final String v9 = v7.substring(0, v8);
                    final String v10 = v7.substring(v8 + 1, v7.indexOf(")"));
                    String v11 = "";
                    if (v10.length() != 0) {
                        v11 = "_" + v10.replace(".", "_").replace(", ", "__").replace("[]", "$$");
                    }
                    final String v12 = v9 + v11;
                    v6.put((Object)v9, (Object)v12);
                }
                else {
                    if (Utils.isEmpty(v7)) {
                        continue;
                    }
                    v5.add(v7);
                }
            }
            sb.append(Utils.repeat("\t", v15++)).append("public interface ").append(this.getNonDuplicateName(v3, arrayList3, arrayList3.size() - 1)).append(" {\n");
            if (!v5.isEmpty()) {
                sb.append(Utils.repeat("\t", v15++)).append("public interface fields {\n");
                for (final String v7 : v5) {
                    sb.append(Utils.repeat("\t", v15)).append("public interface ").append(this.getNonDuplicateName(v7, arrayList3)).append(" {}\n");
                }
                sb.append(Utils.repeat("\t", --v15)).append("}\n");
            }
            if (!v6.isEmpty()) {
                sb.append(Utils.repeat("\t", v15++)).append("public interface methods {\n");
                for (final Map.Entry<String, String> v13 : v6.entries()) {
                    final String v14 = v13.getKey();
                    final String v9 = v13.getValue();
                    String v10 = (v6.get(v14).size() == 1) ? v14 : v9;
                    v10 = this.getNonDuplicateName(v10, v5);
                    sb.append(Utils.repeat("\t", v15)).append("public interface ").append(this.getNonDuplicateName(v10, arrayList3)).append(" {}\n");
                }
                sb.append(Utils.repeat("\t", --v15)).append("}\n");
            }
            if (!v4.isEmpty()) {
                sb.append(Utils.repeat("\t", v15++)).append("public interface annotations {\n");
                for (String v14 : v4) {
                    final String v7 = v14;
                    v14 = this.getNonDuplicateName(v14, arrayList3);
                    sb.append(Utils.repeat("\t", v15)).append("public interface ").append(v14).append(" {}\n");
                }
                sb.append(Utils.repeat("\t", --v15)).append("}\n");
            }
            arrayList = arrayList3;
        }
        for (int i = arrayList.size(); i >= 1; --i) {
            sb.append(Utils.repeat("\t", i)).append("}\n");
        }
        return sb.toString();
    }
    
    private String getNonDuplicateName(final String a3, final List<String> v1, final int v2) {
        final String v3 = this.normalize(a3);
        for (int a4 = 0; a4 < v2; ++a4) {
            if (v3.equals(v1.get(a4))) {
                return this.getNonDuplicateName(v3 + "_", v1, v2);
            }
        }
        return v3;
    }
    
    private String normalize(final String a1) {
        return a1.replace(".", "_");
    }
    
    private String getNonDuplicateName(final String a1, final List<String> a2) {
        return this.getNonDuplicateName(a1, a2, a2.size());
    }
    
    public static Class<?> resolveClassOf(final Class a1) throws ClassNotFoundException {
        Class<?> v1 = (Class<?>)a1;
        final LinkedList<String> v2 = (LinkedList<String>)Lists.newLinkedList();
        while (v1 != null) {
            v2.addFirst(v1.getSimpleName());
            v1 = v1.getDeclaringClass();
        }
        final String v3 = Joiner.on(".").join(v2.subList(1, v2.size())).replace(".$", "$");
        return Class.forName(v3);
    }
    
    public static Class<?> resolveClass(final Class v1) {
        try {
            return resolveClassOf(v1);
        }
        catch (Exception a1) {
            throw new ReflectionsException("could not resolve to class " + v1.getName(), a1);
        }
    }
    
    public static Field resolveField(final Class v-1) {
        try {
            final String a1 = v-1.getSimpleName();
            final Class<?> v1 = (Class<?>)v-1.getDeclaringClass().getDeclaringClass();
            return resolveClassOf(v1).getDeclaredField(a1);
        }
        catch (Exception v2) {
            throw new ReflectionsException("could not resolve to field " + v-1.getName(), v2);
        }
    }
    
    public static Annotation resolveAnnotation(final Class v-1) {
        try {
            final String a1 = v-1.getSimpleName().replace("_", ".");
            final Class<?> v1 = (Class<?>)v-1.getDeclaringClass().getDeclaringClass();
            final Class<?> v2 = resolveClassOf(v1);
            final Class<? extends Annotation> v3 = (Class<? extends Annotation>)ReflectionUtils.forName(a1, new ClassLoader[0]);
            final Annotation v4 = v2.getAnnotation(v3);
            return v4;
        }
        catch (Exception v5) {
            throw new ReflectionsException("could not resolve to annotation " + v-1.getName(), v5);
        }
    }
    
    public static Method resolveMethod(final Class v-4) {
        final String simpleName = v-4.getSimpleName();
        try {
            String substring;
            Class<?>[] array;
            if (simpleName.contains("_")) {
                substring = simpleName.substring(0, simpleName.indexOf("_"));
                final String[] v0 = simpleName.substring(simpleName.indexOf("_") + 1).split("__");
                array = (Class<?>[])new Class[v0.length];
                for (int v2 = 0; v2 < v0.length; ++v2) {
                    final String a1 = v0[v2].replace("$$", "[]").replace("_", ".");
                    array[v2] = ReflectionUtils.forName(a1, new ClassLoader[0]);
                }
            }
            else {
                substring = simpleName;
                array = null;
            }
            final Class<?> v3 = (Class<?>)v-4.getDeclaringClass().getDeclaringClass();
            return resolveClassOf(v3).getDeclaredMethod(substring, array);
        }
        catch (Exception a2) {
            throw new ReflectionsException("could not resolve to method " + v-4.getName(), a2);
        }
    }
}
