package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import java.util.*;
import java.nio.charset.*;
import com.google.common.io.*;
import java.io.*;

public final class TargetMap extends HashMap<TypeReference, Set<TypeReference>>
{
    private static final long serialVersionUID = 1L;
    private final String sessionId;
    
    private TargetMap() {
        this(String.valueOf(System.currentTimeMillis()));
    }
    
    private TargetMap(final String a1) {
        super();
        this.sessionId = a1;
    }
    
    public String getSessionId() {
        return this.sessionId;
    }
    
    public void registerTargets(final AnnotatedMixin a1) {
        this.registerTargets(a1.getTargets(), a1.getHandle());
    }
    
    public void registerTargets(final List<TypeHandle> v1, final TypeHandle v2) {
        for (final TypeHandle a1 : v1) {
            this.addMixin(a1, v2);
        }
    }
    
    public void addMixin(final TypeHandle a1, final TypeHandle a2) {
        this.addMixin(a1.getReference(), a2.getReference());
    }
    
    public void addMixin(final String a1, final String a2) {
        this.addMixin(new TypeReference(a1), new TypeReference(a2));
    }
    
    public void addMixin(final TypeReference a1, final TypeReference a2) {
        final Set<TypeReference> v1 = this.getMixinsFor(a1);
        v1.add(a2);
    }
    
    public Collection<TypeReference> getMixinsTargeting(final TypeElement a1) {
        return this.getMixinsTargeting(new TypeHandle(a1));
    }
    
    public Collection<TypeReference> getMixinsTargeting(final TypeHandle a1) {
        return this.getMixinsTargeting(a1.getReference());
    }
    
    public Collection<TypeReference> getMixinsTargeting(final TypeReference a1) {
        return Collections.unmodifiableCollection((Collection<? extends TypeReference>)this.getMixinsFor(a1));
    }
    
    private Set<TypeReference> getMixinsFor(final TypeReference a1) {
        Set<TypeReference> v1 = ((HashMap<K, Set<TypeReference>>)this).get(a1);
        if (v1 == null) {
            v1 = new HashSet<TypeReference>();
            this.put(a1, v1);
        }
        return v1;
    }
    
    public void readImports(final File v-1) throws IOException {
        if (!v-1.isFile()) {
            return;
        }
        for (final String v1 : Files.readLines(v-1, Charset.defaultCharset())) {
            final String[] a1 = v1.split("\t");
            if (a1.length == 2) {
                this.addMixin(a1[1], a1[0]);
            }
        }
    }
    
    public void write(final boolean v-2) {
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream v0 = null;
        try {
            final File a1 = getSessionFile(this.sessionId);
            if (v-2) {
                a1.deleteOnExit();
            }
            v0 = new FileOutputStream(a1, true);
            objectOutputStream = new ObjectOutputStream(v0);
            objectOutputStream.writeObject(this);
        }
        catch (Exception v2) {
            v2.printStackTrace();
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                }
                catch (IOException v3) {
                    v3.printStackTrace();
                }
            }
        }
        finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                }
                catch (IOException v4) {
                    v4.printStackTrace();
                }
            }
        }
    }
    
    private static TargetMap read(final File v-2) {
        ObjectInputStream objectInputStream = null;
        FileInputStream v0 = null;
        try {
            v0 = new FileInputStream(v-2);
            objectInputStream = new ObjectInputStream(v0);
            return (TargetMap)objectInputStream.readObject();
        }
        catch (Exception v2) {
            v2.printStackTrace();
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                }
                catch (IOException v3) {
                    v3.printStackTrace();
                }
            }
        }
        finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                }
                catch (IOException v4) {
                    v4.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static TargetMap create(final String v0) {
        if (v0 != null) {
            final File v = getSessionFile(v0);
            if (v.exists()) {
                final TargetMap a1 = read(v);
                if (a1 != null) {
                    return a1;
                }
            }
        }
        return new TargetMap();
    }
    
    private static File getSessionFile(final String a1) {
        final File v1 = new File(System.getProperty("java.io.tmpdir"));
        return new File(v1, String.format("mixin-targetdb-%s.tmp", a1));
    }
}
