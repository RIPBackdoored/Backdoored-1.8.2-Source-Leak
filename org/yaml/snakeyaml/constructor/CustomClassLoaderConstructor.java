package org.yaml.snakeyaml.constructor;

public class CustomClassLoaderConstructor extends Constructor
{
    private ClassLoader loader;
    
    public CustomClassLoaderConstructor(final ClassLoader a1) {
        this(Object.class, a1);
    }
    
    public CustomClassLoaderConstructor(final Class<?> a1, final ClassLoader a2) {
        super(a1);
        this.loader = CustomClassLoaderConstructor.class.getClassLoader();
        if (a2 == null) {
            throw new NullPointerException("Loader must be provided.");
        }
        this.loader = a2;
    }
    
    @Override
    protected Class<?> getClassForName(final String a1) throws ClassNotFoundException {
        return Class.forName(a1, true, this.loader);
    }
}
