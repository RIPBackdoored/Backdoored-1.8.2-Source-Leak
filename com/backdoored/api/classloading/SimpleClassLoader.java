package com.backdoored.api.classloading;

import java.util.*;
import net.minecraftforge.fml.common.*;

public class SimpleClassLoader
{
    private Class[] b;
    private Set<Class> c;
    
    public SimpleClassLoader() {
        super();
    }
    
    public SimpleClassLoader build(final Class[] b) {
        this.b = b;
        this.c = new HashSet<Class>(b.length);
        return this;
    }
    
    public SimpleClassLoader initialise() {
        for (final Class clazz : this.b) {
            try {
                clazz.newInstance();
            }
            catch (Exception ex) {
                this.c.add(clazz);
                FMLLog.log.info("Error initialising class " + clazz.getName());
                ex.printStackTrace();
            }
        }
        return this;
    }
    
    public Set<Class> getErroredClasses() {
        return this.c;
    }
}
