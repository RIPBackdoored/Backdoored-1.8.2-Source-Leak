package org.yaml.snakeyaml.extensions.compactnotation;

public class PackageCompactConstructor extends CompactConstructor
{
    private String packageName;
    
    public PackageCompactConstructor(final String a1) {
        super();
        this.packageName = a1;
    }
    
    @Override
    protected Class<?> getClassForName(final String v2) throws ClassNotFoundException {
        if (v2.indexOf(46) < 0) {
            try {
                final Class<?> a1 = Class.forName(this.packageName + "." + v2);
                return a1;
            }
            catch (ClassNotFoundException ex) {}
        }
        return super.getClassForName(v2);
    }
}
