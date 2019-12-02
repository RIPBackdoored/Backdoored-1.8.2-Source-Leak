package org.spongepowered.asm.launch.platform;

import java.net.*;
import java.io.*;
import java.util.jar.*;
import java.util.*;

final class MainAttributes
{
    private static final Map<URI, MainAttributes> instances;
    protected final Attributes attributes;
    
    private MainAttributes() {
        super();
        this.attributes = new Attributes();
    }
    
    private MainAttributes(final File a1) {
        super();
        this.attributes = getAttributes(a1);
    }
    
    public final String get(final String a1) {
        if (this.attributes != null) {
            return this.attributes.getValue(a1);
        }
        return null;
    }
    
    private static Attributes getAttributes(final File v1) {
        if (v1 == null) {
            return null;
        }
        JarFile v2 = null;
        try {
            v2 = new JarFile(v1);
            final Manifest a1 = v2.getManifest();
            if (a1 != null) {
                return a1.getMainAttributes();
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                if (v2 != null) {
                    v2.close();
                }
            }
            catch (IOException ex2) {}
        }
        return new Attributes();
    }
    
    public static MainAttributes of(final File a1) {
        return of(a1.toURI());
    }
    
    public static MainAttributes of(final URI a1) {
        MainAttributes v1 = MainAttributes.instances.get(a1);
        if (v1 == null) {
            v1 = new MainAttributes(new File(a1));
            MainAttributes.instances.put(a1, v1);
        }
        return v1;
    }
    
    static {
        instances = new HashMap<URI, MainAttributes>();
    }
}
